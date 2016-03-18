/*
 * Created by Storm Zhang, Mar 31, 2014.
 */

package org.gxz.mydemo.listview.swiperefresh;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gxz.mydemo.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SwiperefreshListView extends Activity implements
		SwipeRefreshLayout.OnRefreshListener {

	private static final int REFRESH_COMPLETE = 0X110;
	private SwipeRefreshLayout mSwipeLayout;
	private ListView mListView;
	private ArrayAdapter<String> mAdapter;
	private List<String> mDatas = new ArrayList<String>(Arrays.asList("Java",
			"Javascript", "C++", "Ruby", "Json", "HTML"));

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH_COMPLETE:
				mDatas.addAll(Arrays.asList("Lucene", "Canvas", "Bitmap"));
				mAdapter.notifyDataSetChanged();
				mSwipeLayout.setRefreshing(false);
				break;

			}
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_swiperefresh);

		mListView = (ListView) findViewById(R.id.id_listview);
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);

		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_green_dark,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mDatas);
		mListView.setAdapter(mAdapter);
		ViewTreeObserver vto = mSwipeLayout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@SuppressLint("NewApi")
			public void onGlobalLayout() {

				final DisplayMetrics metrics = getResources()
						.getDisplayMetrics();
				Float mDistanceToTriggerSync = Math.min(
						((View) mSwipeLayout.getParent()).getHeight() * 0.4f,
						500 * metrics.density);

				try {
					Field field = SwipeRefreshLayout.class
							.getDeclaredField("mDistanceToTriggerSync");
					field.setAccessible(true);
					field.setFloat(mSwipeLayout, mDistanceToTriggerSync);
				} catch (Exception e) {
					e.printStackTrace();
				}

				ViewTreeObserver obs = mSwipeLayout.getViewTreeObserver();
				obs.removeOnGlobalLayoutListener(this);
			}
		});

	}

	public void onRefresh() {
		// Log.e("xxx", Thread.currentThread().getName());
		// UI Thread

		mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);

	}
}
