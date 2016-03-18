package org.gxz.mydemo.main.mytab;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

import android.content.Context;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MyHomeActivity extends BaseActivity{

	private ListView homelv;
	private Context ctx;
	private MyTabSMSDao dao;
	private List<Map<String,Object>> listData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_home);
		ctx=getApplicationContext();
		dao=new MyTabSMSDao(ctx);
		listData=new ArrayList<Map<String,Object>>();
		listData=dao.queryConversations(listData);
		homelv=(ListView)findViewById(R.id.my_home_listview);
		BaseAdapter ba=new SMSAdapter(this, listData);
		homelv.setAdapter(ba);
//		homelv.setOnItemClickListener(new HomeOnItemclickListener());
//		homelv.setOnItemLongClickListener(new HomeOnItemLongClickListener());
	}

//	private class HomeOnItemclickListener implements OnItemClickListener{
//
//		@Override
//		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//			Toast.makeText(ctx, ""+arg2, 0).show();
//		}
//
//		
//	}
//	private class HomeOnItemLongClickListener implements OnItemLongClickListener{
//
//		@Override
//		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//			Toast.makeText(ctx, ""+arg2, 0).show();
//			return false;
//		}
//		
//	}
}
