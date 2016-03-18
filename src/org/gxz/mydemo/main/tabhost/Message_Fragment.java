package org.gxz.mydemo.main.tabhost;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gxz.mydemo.DemoApplication;
import org.gxz.mydemo.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Message_Fragment extends Fragment implements OnItemClickListener {

	private List<Map<String,Object>> listData;
	private SMSDao dao;
	private ListView msmLv;
	private View smsView;
	private Context ctx;
	private boolean isDetail = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((DemoApplication)getActivity().getApplication()).addActivity(getActivity());
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		((DemoApplication)getActivity().getApplication()).removeActivity(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		smsView=inflater.inflate(R.layout.message, container, false);
		ctx=smsView.getContext();
		msmLv=(ListView)smsView.findViewById(R.id.msm_listview);
		listData=new ArrayList<Map<String,Object>>();
		dao=new SMSDao(ctx);
		isDetail = false;
		Log.d("测试", "创建页面");
		listData=dao.queryAllThreads(listData);
		msmLv.setAdapter(new SimpleAdapter(ctx, listData, android.R.layout.simple_list_item_2, new String[] { "from", "brief" }, new int[] { android.R.id.text1, android.R.id.text2 }));
		msmLv.setOnItemClickListener(this);
		return smsView;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if (isDetail)
			return;
		long tid = (Long) listData.get(position).get("tid");
		listData = dao.getContentDetail(listData, tid);
		isDetail = true;
		((SimpleAdapter) msmLv.getAdapter()).notifyDataSetChanged();
	}
	
	public void setListView(){
		isDetail = false;
		listData = dao.queryAllThreads(listData);
		((SimpleAdapter) msmLv.getAdapter()).notifyDataSetChanged();
	}
	public boolean getisDetail(){
		return isDetail;
	}
}
