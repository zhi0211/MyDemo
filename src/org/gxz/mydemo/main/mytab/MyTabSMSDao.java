package org.gxz.mydemo.main.mytab;

import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.util.Log;

public class MyTabSMSDao {

	SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日 HH:mm");
	private Context ctx;
	public MyTabSMSDao(Context ctx){
		this.ctx=ctx;
	}
	
	
	public List<Map<String,Object>> queryConversations(List<Map<String,Object>> list){
		list.clear();
		Cursor cur=ctx.getContentResolver().query(Uri.parse("content://sms/conversations"), null, null, null, null);
		Map<String,Object> m;
		long tid;
		String calling,name;
		int count;
		while(cur.moveToNext()){
			m=new HashMap<String, Object>();
			list.add(m);
			tid=cur.getLong(cur.getColumnIndex("thread_id"));
			m.put("tid", tid);
			count=cur.getInt(cur.getColumnIndex("msg_count"));
			m.put("msg_count",count);
			String snippet=cur.getString(cur.getColumnIndex("snippet"));
			m.put("snippet", snippet);
			m.put("last_body", snippet.length()>17?snippet.substring(0, 17)+"...":snippet);
			calling=getAddress(tid);
			m.put("calling", calling);
			name=getNameByNumber(calling);
			if(name!=null){
				m.put("brief",name+" ("+count+")");
			}else{
			    m.put("brief",calling+" ("+count+" )");
			}
		}
		Log.d(MyTabActivity.MY_TAB_TAG, "读取信息完成"+list.size());
		return list;
	}
	
	private String getAddress(long tid){
		String s="";
		Cursor addCur = ctx.getContentResolver().query(ContentUris.withAppendedId(Uri.parse("content://sms/conversations"), tid), null, null, null, null);
		if (addCur.moveToFirst())
			s+=addCur.getString(addCur.getColumnIndex("address"));
		else 
			s+="nonumber";
		addCur.close();
		return s;
	}
	public Bitmap getPhotoByNumber(String number){
		if(number.startsWith("+86")){
			number=number.substring(3,number.length());
		}
		Bitmap photo=null;
		Uri uri=Uri.parse("content://com.android.contacts/"+ "data/phones/filter/"+number);
		Cursor cur=ctx.getContentResolver().query(uri, null, null, null, null);
		if(cur.getCount()>0){
			cur.moveToFirst();
			long cid=cur.getLong(cur.getColumnIndex("contact_id"));
			Uri puri=ContentUris.withAppendedId(Contacts.CONTENT_URI,cid);
			InputStream is=Contacts.openContactPhotoInputStream(ctx.getContentResolver(),puri);
			photo=BitmapFactory.decodeStream(is);
		}
		cur.close();
		return photo;
	}
	public String getNameByNumber(String number){
		if(number.startsWith("+86")){
			number=number.substring(3,number.length());
		}
		String name=null;
		Cursor cur=ctx.getContentResolver().query(Phone.CONTENT_URI, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME},Phone.NUMBER+"=?",new String[]{number},null);
		if(cur.moveToFirst()){
			name=cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
		}
		cur.close();
		return name;
	}

//	public List<Map<String, Object>> querySmsbyTid(List<Map<String, Object>> l, long tid) {
//		l.clear();
//		ContentResolver cr = ctx.getContentResolver();
//		Cursor cur = cr.query(ContentUris.withAppendedId(Uri.parse("content://sms/conversations"), tid), null, null, null, "date desc");
//		Map<String, Object> m;
//		String calling,name;
//		while (cur.moveToNext()) {
//			m = new HashMap<String, Object>();
//			l.add(m);
//			m.put("sid", cur.getLong(cur.getColumnIndex("_id")));
//			m.put("body", cur.getString(cur.getColumnIndex("body")));
//			calling=cur.getString(cur.getColumnIndex("address"));
//			m.put("calling", calling);
//			name=getNameByNumber(calling);
//			if(name!=null){
//				m.put("name",name);
//			}else{
//			    m.put("name",calling);
//			}
//			m.put("type", cur.getInt(cur.getColumnIndex("type")));
//			m.put("date", sdf.format(new Date(cur.getLong(cur.getColumnIndex("date")))));
//		}
//		Log.d(MyTabActivity.MY_TAB_TAG, "获取会话短信成功:"+l.size());
//		cur.close();
//		return l;
//	}
	
	
	public List<Map<String, Object>> getContentDetail(List<Map<String, Object>> l, long tid) {
		l.clear();
		ContentResolver cr = ctx.getContentResolver();
		Cursor cur = cr.query(ContentUris.withAppendedId(Uri.parse("content://sms/conversations"), tid), null, null, null, "date desc");
		Map<String, Object> m;
		while (cur.moveToNext()) {
			m = new HashMap<String, Object>();
			l.add(m);
			m.put("sid", cur.getLong(cur.getColumnIndex("_id")));
			m.put("body", cur.getString(cur.getColumnIndex("body")));
			m.put("calling", cur.getString(cur.getColumnIndex("address")));
			m.put("type", cur.getInt(cur.getColumnIndex("type")));
			m.put("date", sdf.format(new Date(cur.getLong(cur.getColumnIndex("date")))));
//			m.put("from", MessageFormat.format("{0} - {1} : {2}", ((Integer) m.get("type")) == 1 ? "收到" : "发送", m.get("calling"), sdf.format(new Date((Long) m.get("date")))));
		}
		cur.close();
		return l;
	}
}
