package org.gxz.mydemo.listview.popuwindow;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class PopuListDemoActivity extends BaseActivity {
	/**
	 * Listview selected row
	 */
	private int mSelectedRow = 0;

	/**
	 * Right arrow icon on each listview row
	 */
	private ImageView mMoreIv = null;

	private static final int ID_USER = 1;
	private static final int ID_GROUP = 2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_layout_popu_list);

		ListView mList = (ListView) findViewById(R.id.l_list);
		
		View headViewLayout =LayoutInflater.from(this).inflate(R.layout.layout_head_img_title, null);
		mList.addHeaderView(headViewLayout);
		NewQAAdapter adapter = new NewQAAdapter(this);

		final String[] data = { "我的痛你何曾明白。", "天边的夕阳╮沵为谁灿烂", "回忆 是一首不眠的歌°", "永恒的音乐谱写着思念い", "倾国倾城万般妖娆又如何や", "如果、没有那么多如果多好。",
				"ぃ骄傲是女人的资本", "再美的剧本乜有最后一页ゝ", "谁说爱情让人心旷神怡﹌", "习惯黑白颠倒的生活。ヽ", "〃铅笔划不出的界限", "つ我的微笑只给懂的人看", "忧伤安静的旋律 醉了回忆",
				"日光独自倾城却少了旧人▼", "゛无法承受，你给的回忆。", "浓郁咖啡衬托了苦涩心情∞" };

		adapter.setData(data);
		mList.setAdapter(adapter);

		PopuItem addItem = new PopuItem(ID_USER, "user", getResources().getDrawable(R.drawable.child_image));
		PopuItem acceptItem = new PopuItem(ID_GROUP, "group", getResources().getDrawable(R.drawable.user_group));

		final PopuJar mPopuJar = new PopuJar(this, PopuJar.HORIZONTAL);

		mPopuJar.addPopuItem(addItem);
		mPopuJar.addPopuItem(acceptItem);

		// setup the action item click listener
		mPopuJar.setOnPopuItemClickListener(new PopuJar.OnPopuItemClickListener() {
			@Override
			public void onItemClick(PopuJar PopuJar, int pos, int actionId) {
				PopuItem PopuItem = PopuJar.getPopuItem(pos);

				if (actionId == ID_USER) { // Add item selected
					Toast.makeText(getApplicationContext(), "selected user:" + data[mSelectedRow], Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(getApplicationContext(), PopuItem.getTitle() + " selected position " + mSelectedRow,
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		// setup on dismiss listener, set the icon back to normal
		mPopuJar.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				mMoreIv.setImageResource(R.drawable.ic_list_more);
			}
		});

		mList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				Toast.makeText(PopuListDemoActivity.this,data[position], Toast.LENGTH_SHORT).show();
			}
		});
		
		mList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				
				mSelectedRow = position; // set the selected row
				mPopuJar.show(view);
				// change the right arrow icon to selected state
				mMoreIv = (ImageView) view.findViewById(R.id.i_more);
				mMoreIv.setImageResource(R.drawable.ic_list_more_selected);
				return false;
			}
		});
	}
}