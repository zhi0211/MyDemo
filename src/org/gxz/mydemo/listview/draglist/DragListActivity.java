package org.gxz.mydemo.listview.draglist;

import java.util.ArrayList;
import java.util.List;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.widgets.DragListView;
import org.gxz.mydemo.widgets.DragListView.onDragListItemClickListener;
import org.gxz.mydemo.widgets.MyScrollView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class DragListActivity extends BaseActivity implements OnCheckedChangeListener {

	private static List<String> list1, list2;
	private DragListAdapter nAdapter, fAdapter;
	private DragListView dragListViewNormal;
	private DragListView dragListViewForbid;
	public static final int MENU_TYPE_NORMAL = 0;
	public static final int MENU_TYPE_FORBID = 1;
	private static final int MENU_WIDTH = 180;
	private MyScrollView mScrollView;

	private List<String> navList = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drag_list_activity);

		initData();
		mScrollView = (MyScrollView) findViewById(R.id.my_scrollview);

		dragListViewNormal = (DragListView) findViewById(R.id.drag_list_normal);
		dragListViewForbid = (DragListView) findViewById(R.id.drag_list_forbid);
		dragListViewNormal.setScrollView(mScrollView);
		dragListViewForbid.setScrollView(mScrollView);
		dragListViewNormal.initMenuWidth(MENU_WIDTH * 2);
		dragListViewForbid.initMenuWidth(MENU_WIDTH);
		nAdapter = new DragListAdapter(this, dragListViewNormal, list1, MENU_TYPE_NORMAL);
		dragListViewNormal.setAdapter(nAdapter);
		fAdapter = new DragListAdapter(this, dragListViewForbid, list2, MENU_TYPE_FORBID);
		dragListViewForbid.setAdapter(fAdapter);
		dragListViewNormal.setDragItemListener(new ImplOnItemClickListener());
		dragListViewForbid.setDragItemListener(new ImplOnItemClickListener());
		
		
		DragListActivity.setListViewHeightBasedOnChildren(dragListViewNormal);
		DragListActivity.setListViewHeightBasedOnChildren(dragListViewForbid);
		// 初始化scrollview的头部位置
		mScrollView.smoothScrollTo(0, 0);
		dragListViewNormal.setVisibility(View.VISIBLE);
		dragListViewForbid.setVisibility(View.GONE);
		RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
		rg.setOnCheckedChangeListener(this);
	}

	public void initData() {
		// 数据结果
		list1 = new ArrayList<String>();

		for (int i = 0; i < 20; i++) {
			navList.add("设备A" + i);
		}
		list1.addAll(navList);
		
		list2=new ArrayList<String>();
		navList.clear();
		for(int i=0;i<10;i++){
			navList.add("设备B"+i);
		}
		list2.addAll(navList);
	}

	public class DragListAdapter extends ArrayAdapter<String> {

		private Context mContext;
		private DragListView draglist;
		private List<String> list;
		private int type;

		public DragListAdapter(Context context, DragListView draglist, List<String> objects, int type) {
			super(context, 0, objects);
			this.mContext = context;
			this.draglist = draglist;
			this.list = objects;
			this.type = type;
		}

		public List<String> getList() {
			return list;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.drag_list_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.drag_list_item_text);
				holder.forever = (TextView) convertView.findViewById(R.id.btn_forever);
				holder.temp = (TextView) convertView.findViewById(R.id.btn_temp);
				holder.left = (LinearLayout) convertView.findViewById(R.id.item_left);
				holder.right = (LinearLayout) convertView.findViewById(R.id.item_right);
				holder.img = (ImageView) convertView.findViewById(R.id.drag_item_img);
				holder.state = (ImageView) convertView.findViewById(R.id.drag_item_state);
				holder.clearLock = (TextView) convertView.findViewById(R.id.btn_clear_lock);
				holder.divider = (View) convertView.findViewById(R.id.right_divide);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (type == DragListActivity.MENU_TYPE_NORMAL) {
				holder.temp.setVisibility(View.VISIBLE);
				holder.forever.setVisibility(View.VISIBLE);
				holder.divider.setVisibility(View.VISIBLE);
				holder.clearLock.setVisibility(View.GONE);
			} else if (type == DragListActivity.MENU_TYPE_FORBID) {
				holder.temp.setVisibility(View.GONE);
				holder.forever.setVisibility(View.GONE);
				holder.divider.setVisibility(View.GONE);
				holder.clearLock.setVisibility(View.VISIBLE);
			}
			holder.name.setText(list.get(position));
			if (position % 4 == 0) {
				holder.img.setImageResource(R.drawable.default_pc);
				holder.state.setImageResource(R.drawable.icon_wifi_normal);
			} else if (position % 4 == 1) {
				holder.img.setImageResource(R.drawable.default_pad);
				holder.state.setImageResource(R.drawable.icon_wifi_disabled);
			} else if (position % 4 == 2) {
				holder.img.setImageResource(R.drawable.default_telephone);
				holder.state.setImageResource(R.drawable.icon_wired_disabled);
			} else {
				holder.img.setImageResource(R.drawable.default_else);
				holder.state.setImageResource(R.drawable.icon_wired_normal);
			}
			holder.forever.setOnClickListener(new itemOnclick(position));
			holder.temp.setOnClickListener(new itemOnclick(position));
			holder.clearLock.setOnClickListener(new itemOnclick(position));
			// holder.left.setOnClickListener(new itemOnclick(position));
			return convertView;
		}

		class ViewHolder {
			TextView forever, temp, name, clearLock;
			LinearLayout left, right;
			ImageView img, state;
			View divider;
		}

		private class itemOnclick implements OnClickListener {
			private int pos;

			public itemOnclick(int pos) {
				this.pos = pos;
			}

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btn_forever:
					Toast.makeText(mContext, "永久" + list.get(pos), 0).show();
					draglist.touchScrollToContent();
					break;
				case R.id.btn_temp:
					Toast.makeText(mContext, "临时" +list.get(pos), 0).show();
					draglist.touchScrollToContent();
					break;
				case R.id.btn_clear_lock:
					Toast.makeText(mContext, "解禁" +list.get(pos), 0).show();
					draglist.touchScrollToContent();
					break;
				default:
					break;
				}

			}
		}
	}

	/**
	 * 刷新列表高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
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
	
	private class ImplOnItemClickListener implements onDragListItemClickListener{

		@Override
		public void onContentClickListener(Object o) {
			Toast.makeText(DragListActivity.this, "you click:" + o.toString(), 0).show();
			
		}
		
	}
}