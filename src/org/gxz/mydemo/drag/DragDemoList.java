package org.gxz.mydemo.drag;

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
import org.gxz.mydemo.drag.rearrangeable.RearrangeableLayoutActivity;

public class DragDemoList extends BaseActivity implements
		OnItemClickListener {
	private final String[] DEMO_ANIM_NAME = {"子类拖拽的View"};
	@SuppressWarnings("rawtypes")
	private final Class[] DEMO_ANIM_CLASS = {RearrangeableLayoutActivity.class};

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
				Toast.makeText(DragDemoList.this, "添加:position=" + position,
						Toast.LENGTH_SHORT).show();
				break;

			case R.id.modify:
				Toast.makeText(DragDemoList.this, "修改:position=" + position,
						Toast.LENGTH_SHORT).show();
				break;

			case R.id.delete:
				Toast.makeText(DragDemoList.this, "删除:position=" + position,
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
