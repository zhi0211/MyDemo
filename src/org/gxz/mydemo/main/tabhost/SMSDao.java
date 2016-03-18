package org.gxz.mydemo.main.tabhost;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class SMSDao {

	private Context ctx;
	@SuppressLint("SimpleDateFormat")
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
	public SMSDao(Context ctx){
		this.ctx=ctx;
	}
	
	public List<Map<String,Object>> queryAllThreads(List<Map<String,Object>> threadsList){
		threadsList.clear();
		ContentResolver cr=ctx.getContentResolver();
		Cursor cur=cr.query(Uri.parse("content://sms/conversations"), null, null, null, null);
		Map<String, Object> m;
		long tid = 0;
		while (cur.moveToNext()) {
			m = new HashMap<String, Object>();
			threadsList.add(m);
			tid = cur.getLong(cur.getColumnIndex("thread_id"));
			m.put("tid", tid);
			m.put("messagecount", cur.getInt(cur.getColumnIndex("msg_count")));
			m.put("brief", cur.getString(cur.getColumnIndex("snippet")));
			m.put("calling",getAddress(tid));
			m.put("from", m.get("calling") + " - (" + m.get("messagecount") + ")");
		}
		cur.close();
		return threadsList;
	}
	
	public List<Map<String, Object>> getContentDetail(List<Map<String, Object>> l, long tid) {
		l.clear();
		ContentResolver cr = ctx.getContentResolver();
		Cursor cur = cr.query(ContentUris.withAppendedId(Uri.parse("content://sms/conversations"), tid), null, null, null, "date desc");
		Map<String, Object> m;
		while (cur.moveToNext()) {
			m = new HashMap<String, Object>();
			l.add(m);
			m.put("sid", cur.getLong(cur.getColumnIndex("_id")));
			m.put("brief", cur.getString(cur.getColumnIndex("body")));
			m.put("calling", cur.getString(cur.getColumnIndex("address")));
			m.put("type", cur.getInt(cur.getColumnIndex("type")));
			m.put("date", cur.getLong(cur.getColumnIndex("date")));
			m.put("from", MessageFormat.format("{0} - {1} : {2}", ((Integer) m.get("type")) == 1 ? "收到" : "发送", m.get("calling"), sdf.format(new Date((Long) m.get("date")))));
		}
		cur.close();
		return l;
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
}
