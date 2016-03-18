package org.gxz.mydemo.cycleviewpager;

import java.util.ArrayList;

import org.gxz.mydemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class CircleViewPagerActivity extends Activity {
	ViewPager viewPager;
	ArrayList<View> list;
	ViewGroup main, group;
	ImageView imageView;
	ImageView[] imageViews;
	private static int c_id = 0;
	private int realSize;
	private static final String LOGTAG = CircleViewPagerActivity.class
			.getCanonicalName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = getLayoutInflater();
		list = new ArrayList<View>();
		list.add(inflater.inflate(R.layout.view_pager_circle, null));
		list.add(inflater.inflate(R.layout.view_pager_circle, null));
		// list.add(inflater.inflate(R.layout.view_pager_circle, null));

		realSize = list.size();
		imageViews = new ImageView[list.size()];
		ViewGroup main = (ViewGroup) inflater.inflate(
				R.layout.activity_circle_viewpager, null);

		ViewGroup group = (ViewGroup) main.findViewById(R.id.viewGroup);

		viewPager = (ViewPager) main.findViewById(R.id.viewPager);

		for (int i = 0; i < list.size(); i++) {
			imageView = new ImageView(CircleViewPagerActivity.this);
			imageView.setLayoutParams(new LayoutParams(12, 12));
			imageViews[i] = imageView;
			if (i == 0) {
				imageViews[i]
						.setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				imageViews[i]
						.setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
			group.addView(imageView);
		}

		setContentView(main);

		viewPager.setAdapter(new MyAdapter());
		viewPager.setOnPageChangeListener(new MyListener());
		viewPager.setCurrentItem(0);

	}

	class MyAdapter extends PagerAdapter {

		private int count = 0;

		@Override
		public int getCount() {
			if (list.size() > 2)
				return Integer.MAX_VALUE;
			else
				return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub
			// ((ViewPager) arg0).removeView(list.get(arg1));
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			Log.d(LOGTAG,
					"index:" + arg1 + ";size:"
							+ ((ViewPager) arg0).getChildCount());
			if (((ViewPager) arg0).getChildCount() == realSize) {
				((ViewPager) arg0).removeView(list.get(arg1 % realSize));
			}
			try {
				((ViewPager) arg0).addView(list.get(arg1 % realSize), 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			View view = list.get(arg1 % realSize);
			return view;
		}

		@Override
		public void setPrimaryItem(ViewGroup container, int position,
				Object object) {
			View view = list.get(position % realSize);
			TextView txt = (TextView) view.findViewById(R.id.textView1);
			txt.setText("position=" + position + "      count=" + count++);
			super.setPrimaryItem(container, position, object);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}
	}

	class MyListener implements OnPageChangeListener {

		// 当滑动状态改变时调用
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			// arg0=arg0%list.size();

		}

		// 当当前页面被滑动时调用
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		// 当新的页面被选中时调用
		@Override
		public void onPageSelected(int arg0) {
			if (arg0 > 2) {
				arg0 = arg0 % list.size();
			}
			c_id = arg0;
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.page_indicator_focused);
				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.page_indicator_unfocused);
				}
			}

			Log.d(LOGTAG, "当前是第" + c_id + "页");
		}

	}
}