package org.gxz.mydemo.listview.expanddraglistview;

import java.util.ArrayList;
import java.util.List;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.listview.expanddraglistview.ExpandDragListView.OnSingleClickListener;
import org.gxz.mydemo.listview.itemdrag.HostRecord;
import org.gxz.mydemo.widgets.MyScrollView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class ExpandableDragListActivity extends BaseActivity implements
		OnCheckedChangeListener, OnGroupExpandListener {

	private  List<String> list1, list2;
	private  List<List<String>> child1, child2;
	private ExpandDragAdapter nAdapter, fAdapter;
	private ExpandDragListView dragListViewNormal;
	private ExpandDragListView dragListViewForbid;
	public static final int MENU_TYPE_NORMAL = 0;
	public static final int MENU_TYPE_FORBID = 1;
	private static final int MENU_WIDTH = 180;
	private MyScrollView mScrollView;

	private List<String> navList = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expand_listview);

		initData();
		mScrollView = (MyScrollView) findViewById(R.id.my_scrollview);

		dragListViewNormal = (ExpandDragListView) findViewById(R.id.drag_list_normal);
		dragListViewForbid = (ExpandDragListView) findViewById(R.id.drag_list_forbid);
		dragListViewNormal.setScrollView(mScrollView);
		dragListViewForbid.setScrollView(mScrollView);
		dragListViewNormal.initMenuWidth(MENU_WIDTH * 2);
		dragListViewForbid.initMenuWidth(MENU_WIDTH);
		dragListViewNormal.setOnGroupExpandListener(this);
//		dragListViewNormal.setGroupIndicator(getResources().getDrawable(R.drawable.expandable_selector));
//		dragListViewForbid.setGroupIndicator(getResources().getDrawable(R.drawable.expandable_selector));
		nAdapter = new ExpandDragAdapter(this, list1, child1, MENU_TYPE_NORMAL);
		dragListViewNormal.setAdapter(nAdapter);
		fAdapter = new ExpandDragAdapter(this, list2, child2, MENU_TYPE_FORBID);
		dragListViewForbid.setAdapter(fAdapter);
		dragListViewNormal
				.setSingleClickLister(new ImplOnChildItemClickListener());
		dragListViewForbid
				.setSingleClickLister(new ImplOnChildItemClickListener());

		// DragListActivity.setListViewHeightBasedOnChildren(dragListViewNormal);
		// DragListActivity.setListViewHeightBasedOnChildren(dragListViewForbid);
		// 初始化scrollview的头部位置
		mScrollView.smoothScrollTo(0, 0);
		dragListViewNormal.setVisibility(View.VISIBLE);
		dragListViewForbid.setVisibility(View.GONE);
		RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
		rg.setOnCheckedChangeListener(this);
	}

	public void initData() {
		// 数据结果
		list1 = new ArrayList<String>();

		for (int i = 0; i < 4; i++) {
			navList.add("A组" + i);
		}
		list1.addAll(navList);

		list2 = new ArrayList<String>();
		navList.clear();
		for (int i = 0; i < 3; i++) {
			navList.add("B" + i);
		}
		list2.addAll(navList);

		child1 = new ArrayList<List<String>>();
		List<String> l=null;
		for (int i = 0; i < list1.size(); i++) {
			l=new ArrayList<String>();
			for (int j = 0; j < 5; j++) {
				l.add("设备A" + i + "_" + j);
			}
			child1.add(l);
		}

		child2 = new ArrayList<List<String>>();
		for (int i = 0; i < list2.size(); i++) {
			l=new ArrayList<String>();
			for (int j = 0; j < 3; j++) {
				l.add("设备B" + i + "_" + j);
			}
			child2.add(l);
		}
		Log.d("init data ", "group1 size:" + list1.size() + "\ngroup2 size:"
				+ list2.size() + "\nchild1 size:" + child1.get(0).size()
				+ "\nchild2 size:" + child2.get(0).size());
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio0:
			dragListViewForbid.setVisibility(View.GONE);
			dragListViewNormal.setVisibility(View.VISIBLE);
			mScrollView.smoothScrollTo(0, 0);
			break;
		case R.id.radio1:
			dragListViewForbid.setVisibility(View.VISIBLE);
			dragListViewNormal.setVisibility(View.GONE);
			mScrollView.smoothScrollTo(0, 0);
			break;
		default:
			break;
		}
	}

	private class ImplOnChildItemClickListener implements OnSingleClickListener {

		@Override
		public void onContentClick(int groupId, int childId,
				HostRecord hr) {
			Toast.makeText(
					ExpandableDragListActivity.this,
					"you click:group" + groupId + ";child:" + childId + ";name"
							+ hr.name.toString(), 0).show();

		}

	}

	@Override
	public void onGroupExpand(int groupPosition) {
		for(int i=0;i<dragListViewNormal.getExpandableListAdapter().getGroupCount();i++){
			if(i!=groupPosition)
				dragListViewNormal.collapseGroup(i);
		}
	}

}