package org.gxz.mydemo.listview.horizontal;

import java.util.ArrayList;
import java.util.List;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HorizaontalList extends BaseActivity {

	private HorizontalListView horizontalListView;
	private List<LauncherItem> mItems;
	private TextView textView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_horizatollistview);
		horizontalListView = (HorizontalListView) findViewById(R.id.listView);
		mItems = getLauncher();
		horizontalListView.setAdapter(new MyAdapter(getApplicationContext(), mItems));
		textView1 = (TextView) findViewById(R.id.textView1);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				horizontalListView.setSelection(horizontalListView.getAdapter().getCount() / 2);
			}
		}, 1000);

	}

	static class MyAdapter extends BaseAdapter {

		private List<LauncherItem> items;
		private Context ctx;

		public MyAdapter(Context ctx, List<LauncherItem> items) {
			this.items = items;
			this.ctx = ctx;
		}

		public void notifyData(List<LauncherItem> items) {
			this.items = items;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public LauncherItem getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(ctx).inflate(R.layout.item_imageview, null);
				holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
				holder.textView = (TextView) convertView.findViewById(R.id.textview);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			LauncherItem item = getItem(position);
			if (null != item) {
				holder.imageView.setImageDrawable(item.icon);
				holder.textView.setText(item.label + position + "");
			}
			return convertView;
		}

		static class ViewHolder {
			ImageView imageView;
			TextView textView;
		}

	}

	public void onBtnClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			horizontalListView.setSelection(10);
			break;
		case R.id.btn2:
			horizontalListView.setSelection(30);
			break;
		case R.id.btn3:
			horizontalListView.setSelection(56);
			break;
		case R.id.btn4:
			horizontalListView.setSelection(23);
			break;
		case R.id.btn5:
			mItems.remove(0);
			((MyAdapter) horizontalListView.getAdapter()).notifyData(mItems);
			break;
		default:
			break;
		}
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				textView1.setText("X=" + horizontalListView.mCurrentX + ";width="
						+ horizontalListView.getChildAt(0).getMeasuredWidth());
			}
		}, 200);
	}

	// 获得app 列表信息
	public List<LauncherItem> getLauncher() {
		List<LauncherItem> lists = new ArrayList<LauncherItem>();

		PackageManager pkgMgt = this.getPackageManager();// 这个方法是关键

		Intent it = new Intent(Intent.ACTION_MAIN);
		it.addCategory(Intent.CATEGORY_LAUNCHER);

		List<ResolveInfo> ra = pkgMgt.queryIntentActivities(it, 0);// 查询
		// 存入集合中
		for (int i = 0; i < ra.size(); i++) {
			ActivityInfo ai = ra.get(i).activityInfo;
			Drawable icon = ai.loadIcon(pkgMgt);
			String label = ai.loadLabel(pkgMgt).toString();
			LauncherItem item = new LauncherItem(icon, label);
			lists.add(item);
		}
		return lists;

	}

	static class LauncherItem {
		Drawable icon;
		String label;

		public LauncherItem(Drawable d, String label) {
			this.icon = d;
			this.label = label;
		}
	}

}
