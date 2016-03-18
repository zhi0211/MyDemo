package org.gxz.mydemo.listview.toudelete;

import java.util.List;
import java.util.Map;

import org.gxz.mydemo.R;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyListViewAdapter extends BaseAdapter {
	private GestureDetector detector;
	private Context ctx;
	private List<Map<String, Object>> list;
	private LayoutInflater inflater = null;
	private FlingListeber listener;
	private Boolean[] sections;
	private int index = -1;

	@SuppressWarnings("deprecation")
	public MyListViewAdapter(Context ctx, List<Map<String, Object>> list) {
		this.ctx = ctx;
		this.list = list;
		sections = new Boolean[list.size()];
		for (int i = 0; i < list.size(); i++) {
			sections[i] = false;
		}
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		listener = new FlingListeber();
		detector = new GestureDetector(listener);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup arg2) {
		ViewHolder holder = null;
		final int p=pos;
		if (null == view) {
			view = inflater.inflate(R.layout.my_listview_item, null);
			holder = new ViewHolder();
			holder.title = (TextView) view.findViewById(R.id.mylistview_item_tv);
			holder.delBtn = (Button) view.findViewById(R.id.mylistview_item_btn);
			holder.layout = (RelativeLayout) view.findViewById(R.id.my_listview_item_layout);
			holder.layout.setBackgroundResource(R.drawable.bg_item_click);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.title.setText(list.get(p).get("title").toString());
		if (sections[p]){
			holder.delBtn.setVisibility(View.VISIBLE);
			Animation btn_animation = AnimationUtils.loadAnimation(ctx, R.anim.btn_push_left_in);
			holder.delBtn.setAnimation(btn_animation);
		}
		else {
			holder.delBtn.setVisibility(View.GONE);
		}
		holder.layout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				detector.onTouchEvent(event);
				listener.setPosition(p);
				return true;
			}
		});
		return view;
	}

	static class ViewHolder {
		TextView title;
		Button delBtn;
		RelativeLayout layout;
	}

	class FlingListeber implements GestureDetector.OnGestureListener {

		ViewHolder holder;
		int position;

		public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			Log.d("测试", position+"");
			this.position = position;
		}

		public ViewHolder getHolder() {
			return holder;
		}

		public void setHolder(ViewHolder holder) {
			this.holder = holder;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (e2.getX() - e1.getX() > 20) {
				if (index != -1)
					sections[index] = false;
				sections[position] = true;
				index = position;
				notifyDataSetChanged();
				Toast.makeText(ctx, "右滑"+position, 0).show();

			} else if (e1.getX() - e2.getX() > 20) {
				Log.d("测试", "右滑动");
				if (index != -1)
					sections[index] = false;
				sections[position] = true;
				index = position;
				notifyDataSetChanged();
				Toast.makeText(ctx, "左滑"+position, 0).show();
			}
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {

		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			Toast.makeText(ctx, "点击item"+position, 0).show();
			if (index != -1) {
				sections[index] = false;
				notifyDataSetChanged();
				index = -1;
			}
			return false;
		}

	}

}
