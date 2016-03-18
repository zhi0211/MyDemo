package org.gxz.mydemo.listview.zoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.utils.DensityUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PullToZoomList extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_pulltozoom);
		
		initListView(getData());
	}

	
	


	 
	 private  PullToZoomListView mListView;
	 private MessageListAdapter mAdapter;
	 private void initListView( List<HashMap<String, Object>> listItem) {
		
		
		mListView=(PullToZoomListView) findViewById(R.id.listView01);
		mAdapter = new MessageListAdapter(listItem,R.layout.news_list_item_view,getLayoutInflater(),PullToZoomList.this);

		/*========================= 设置头部的图片====================================*/
		mListView.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);
		mListView.getHeaderView().setImageResource(R.drawable.story_member_center_bg);
		
		/*========================= 设置头部的高度 ====================================*/
		mListView.setmHeaderHeight(DensityUtil.dip2px(260));

		/*========================= 设置头部的的布局====================================*/
		 View mHeaderView=getLayoutInflater().inflate(R.layout.layout_story_userinfo, null);
		 mListView.getHeaderContainer().addView(mHeaderView);
		 mListView.setHeaderView();
		
		 
		 mListView.setAdapter(mAdapter);
	}
	
	private List<HashMap<String, Object>> listItem;
	private List<HashMap<String, Object>> getData()	{
		listItem=new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<20;i++)	{
			HashMap<String,Object> map=new HashMap<String, Object>();
			map.put("key", ""+i);
			listItem.add(map);
		}
		return listItem; 
	}

}
