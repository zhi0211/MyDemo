package org.gxz.mydemo.listview.toudelete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.widgets.MyListView;

import android.os.Bundle;

public class MyListViewActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_list_view);
		MyListView lv=(MyListView)findViewById(R.id.mylistview);
		MyListViewAdapter ma=new MyListViewAdapter(getApplicationContext(), getData());
		lv.setAdapter(ma);
	}

	private List<Map<String,Object>> getData(){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> m;
		for(int i=0;i<30;i++){
		m=new HashMap<String, Object>();
		m.put("title", "测试"+i);
		list.add(m);
		}
		return list;
	}

}
