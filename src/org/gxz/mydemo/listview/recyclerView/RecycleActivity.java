package org.gxz.mydemo.listview.recyclerView;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

/**
 * Created by gxz on 2015/10/10.
 */
public class RecycleActivity extends BaseActivity {

	private String[] mSimpleListValues = new String[]{"Android", "iPhone",
			"WindowsMobile", "Blackberry", "WebOS", "Ubuntu", "Windows7",
			"Max OS X", "Linux", "OS/2"};

	private int index = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycle_list);
		final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
		final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		layoutManager.setRecycleChildrenOnDetach(true);
		// 为RecyclerView指定布局管理对象  
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)
				.color(getResources().getColor(R.color.gray))
				.size(getResources().getDimensionPixelSize(R.dimen.space_1)).build());
		recyclerView.setAdapter(new MyAdapter());
		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int position = index % recyclerView.getAdapter().getItemCount();
				recyclerView.smoothScrollToPosition(position);
				Toast.makeText(getApplicationContext(), "跳转:" + mSimpleListValues[position], Toast.LENGTH_SHORT).show();
				index++;
			}
		});

	}


	private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


		@Override
		public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
			View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_recycle_list, null);

			return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(ViewHolder viewHolder, int i) {
			viewHolder.textView.setText(mSimpleListValues[i]);
			viewHolder.textView2.setText(mSimpleListValues[i] + "A");
		}


		@Override
		public int getItemCount() {
			return mSimpleListValues.length;
		}

		class ViewHolder extends RecyclerView.ViewHolder {

			TextView textView;
			TextView textView2;

			public ViewHolder(View itemView) {
				super(itemView);
				textView = (TextView) itemView.findViewById(R.id.textView1);
				textView2 = (TextView) itemView.findViewById(R.id.textView2);
			}
		}
	}
}
