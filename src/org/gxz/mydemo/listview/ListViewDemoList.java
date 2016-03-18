package org.gxz.mydemo.listview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.listview.draglist.DragListActivity;
import org.gxz.mydemo.listview.expanddraglistview.ExpandableDragListActivity;
import org.gxz.mydemo.listview.horizontal.HorizaontalList;
import org.gxz.mydemo.listview.itemdrag.MyWeiXinListviewActivity;
import org.gxz.mydemo.listview.popuwindow.PopuListDemoActivity;
import org.gxz.mydemo.listview.pull.PullAndLoadMoreActivity;
import org.gxz.mydemo.listview.pullrefreshLayout.DemoActivity;
import org.gxz.mydemo.listview.recyclerView.RecycleActivity;
import org.gxz.mydemo.listview.swiperefresh.SwiperefreshListView;
import org.gxz.mydemo.listview.swipyrefresh.SwipyActivity;
import org.gxz.mydemo.listview.toudelete.MyListViewActivity;
import org.gxz.mydemo.listview.xrecyclerview.MainActivity;
import org.gxz.mydemo.listview.zoom.PullToZoomList;

public class ListViewDemoList extends BaseActivity implements
		OnItemClickListener {
	private final String[] DEMO_ANIM_NAME = {"滑动删除按钮", "微信list拖动效果-draglist",
			"item拖动效果-draglist_item", "PopuWindows悬浮效果",
			"拖拽的ExpandableListview", "下拉横条刷新", "下拉放大", "下拉刷新与加载更多", "横向ListView", "横向RecycleView",
			"下拉刷新的RecyclerView", "下拉刷新Layout", "什么值得买下拉刷新"};
	@SuppressWarnings("rawtypes")
	private final Class[] DEMO_ANIM_CLASS = {MyListViewActivity.class,
			DragListActivity.class, MyWeiXinListviewActivity.class,
			PopuListDemoActivity.class, ExpandableDragListActivity.class,
			SwiperefreshListView.class, PullToZoomList.class,
			PullAndLoadMoreActivity.class, HorizaontalList.class, RecycleActivity.class,
			MainActivity.class, DemoActivity.class, SwipyActivity.class};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView lv = (ListView) findViewById(R.id.main_listview);
		lv.setBackgroundColor(Color.WHITE);
		lv.setOnItemClickListener(this);
		lv.setSelector(R.drawable.item_bg_selector);
		ListAdapter la = new ArrayAdapter<String>(this,
				R.layout.main_list_item, DEMO_ANIM_NAME);
		lv.setAdapter(la);
		lv.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
											ContextMenuInfo menuInfo) {
				MenuInflater inflater = getMenuInflater();
				inflater.inflate(R.menu.listview_demo_menu, menu);
			}
		});
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = (int) info.id;// 这里的info.id对应的就是数据库中_id的值

		switch (item.getItemId()) {
			case R.id.add:
				// 添加操作
				Toast.makeText(ListViewDemoList.this, "添加:position=" + position,
						Toast.LENGTH_SHORT).show();
				break;

			case R.id.modify:
				Toast.makeText(ListViewDemoList.this, "修改:position=" + position,
						Toast.LENGTH_SHORT).show();
				break;

			case R.id.delete:
				Toast.makeText(ListViewDemoList.this, "删除:position=" + position,
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 > DEMO_ANIM_CLASS.length)
			return;
		Intent i = new Intent(this, DEMO_ANIM_CLASS[arg2]);
		startActivity(i);
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
	}
}
