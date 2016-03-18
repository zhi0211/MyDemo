package org.gxz.mydemo.listview.pull;

import java.util.Arrays;
import java.util.LinkedList;

import org.gxz.mydemo.R;
import org.gxz.mydemo.listview.pull.PullRefreshAndLoadMoreListView.OnLoadMoreListener;
import org.gxz.mydemo.listview.pull.PullToRefreshListView.OnRefreshListener;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PullAndLoadMoreActivity extends Activity {
	private PullRefreshAndLoadMoreListView mPullRefreshAndLoadMoreListView;
	private MyAdapter adapter;

	private String[] mNames = { "Fabian", "Carlos", "Alex", "Andrea", "Karla",
			"Freddy", "Lazaro", "Hector", "Carolina", "Edwin", "Jhon",
			"Edelmira", "Andres", "Fabian", "Carlos", "Alex", "Andrea",
			"Karla", "Freddy", "Lazaro", "Hector", "Carolina", "Edwin", "Jhon",
			"Edelmira", "Andres" };
	private LinkedList<String> mListItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pull_and_loadmore);
		findView();
		initView();
	}

	private void findView() {
		mPullRefreshAndLoadMoreListView = (PullRefreshAndLoadMoreListView) findViewById(R.id.palmlv);
	}

	private void initView() {
		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mNames));

		adapter = new MyAdapter();
		mPullRefreshAndLoadMoreListView.setAdapter(adapter);

		mPullRefreshAndLoadMoreListView
				.setOnRefreshListener(new OnRefreshListener() {

					@Override
					public void onRefresh() {
						new AsyncTask<Void, Void, Void>() {

							@Override
							protected Void doInBackground(Void... params) {
								SystemClock.sleep(1000);
								mListItems.add("add after refresh");
								return null;
							}

							@Override
							protected void onPostExecute(Void result) {
								super.onPostExecute(result);
								adapter.notifyDataSetChanged();
								mPullRefreshAndLoadMoreListView
										.onRefreshComplete();
							}
						}.execute();
					}
				});

		mPullRefreshAndLoadMoreListView
				.setOnLoadMoreListener(new OnLoadMoreListener() {

					@Override
					public void onLoadMore() {
						new LoadDataTask().execute();
					}
				});

		mPullRefreshAndLoadMoreListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// If you call addHeaderView, the position will contains
						// the count of header view.
						// int realPosition = position
						// -
						// mPullRefreshAndLoadMoreListView.getHeaderViewsCount();
						// Toast.makeText(PullAndLoadMoreActivity.this,
						// mListItems.get(realPosition),
						// Toast.LENGTH_SHORT).show();

						Toast.makeText(
								PullAndLoadMoreActivity.this,
								"position="
										+ position
										+ " content="
										+ (String) mPullRefreshAndLoadMoreListView
												.getAdapter().getItem(position),
								Toast.LENGTH_SHORT).show();

					}

				});

	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mListItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mListItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(getApplicationContext());
			textView.setTextSize(20);
			textView.setText(mListItems.get(position));

			return textView;
		}
	}

	private class LoadDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			if (isCancelled()) {
				return null;
			}

			SystemClock.sleep(1000);

			mListItems.add("Load More....");
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			adapter.notifyDataSetChanged();

			mPullRefreshAndLoadMoreListView.onLoadMoreComplete();

			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			mPullRefreshAndLoadMoreListView.onLoadMoreComplete();
		}
	}
}
