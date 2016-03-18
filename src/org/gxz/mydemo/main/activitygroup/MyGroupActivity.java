package org.gxz.mydemo.main.activitygroup;

import java.util.ArrayList;

import org.gxz.mydemo.DemoApplication;
import org.gxz.mydemo.R;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MyGroupActivity extends ActivityGroup implements OnClickListener {
	private String title[] = { "one", "two", "three", "four", "five", "six" };
	private LinearLayout linearLayout;
	private ArrayList<TextView> textViews;
	private ViewPager viewPager;
	private ArrayList<View> pageViews;

	/***
	 * init view
	 */
	void InItView() {
		pageViews = new ArrayList<View>();
		View view01 = getLocalActivityManager().startActivity("activity01",
				new Intent(this, ViewPagerActivity1.class)).getDecorView();
		View view02 = getLocalActivityManager().startActivity("activity02",
				new Intent(this, ViewPagerActivity2.class)).getDecorView();
		View view03 = getLocalActivityManager().startActivity("activity02",
				new Intent(this, ViewPagerActivity3.class)).getDecorView();
		View view04 = getLocalActivityManager().startActivity("activity03",
				new Intent(this, ViewPagerActivity4.class)).getDecorView();
		View view05 = getLocalActivityManager().startActivity("activity04",
				new Intent(this, ViewPagerActivity5.class)).getDecorView();
		View view06 = getLocalActivityManager().startActivity("activity05",
				new Intent(this, ViewPagerActivity6.class)).getDecorView();
		pageViews.add(view01);
		pageViews.add(view02);
		pageViews.add(view03);
		pageViews.add(view04);
		pageViews.add(view05);
		pageViews.add(view06);
	}

	/**
	 * init title
	 */
	void InItTitle1() {
		textViews = new ArrayList<TextView>();
		int width = getWindowManager().getDefaultDisplay().getWidth()
				/ title.length;
		int height = 70;
		for (int i = 0; i < title.length; i++) {
			TextView textView = new TextView(this);
			textView.setText(title[i]);
			textView.setTextSize(17);
			textView.setTextColor(Color.BLACK);
			textView.setWidth(width);
			textView.setHeight(height - 30);
			textView.setGravity(Gravity.CENTER);
			textView.setId(i);
			textView.setOnClickListener(this);
			textViews.add(textView);
			// 分割线
			View view = new View(this);
			LinearLayout.LayoutParams layoutParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.width = 1;
			layoutParams.height = height - 30;
			layoutParams.gravity = Gravity.CENTER;
			view.setLayoutParams(layoutParams);
			view.setBackgroundColor(Color.GRAY);
			linearLayout.addView(textView);
			if (i != title.length - 1) {
				linearLayout.addView(view);
			}

		}
	}

	/***
	 * 选中效果
	 */
	public void setSelector(int id) {
		for (int i = 0; i < title.length; i++) {
			if (id == i) {
				// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				// R.drawable.grouplist_item_bg_normal);
				// textViews.get(id).setBackgroundDrawable(
				// new BitmapDrawable(bitmap));
				textViews.get(id).setBackgroundColor(Color.BLUE);
				textViews.get(id).setTextColor(Color.RED);
				viewPager.setCurrentItem(i);
			}

			else {
				textViews.get(i).setBackgroundDrawable(null);
				textViews.get(i).setTextColor(Color.BLACK);
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		((DemoApplication) getApplication()).removeActivity(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((DemoApplication) getApplication()).addActivity(this);
		setContentView(R.layout.activity_view_pager_chage);
		linearLayout = (LinearLayout) findViewById(R.id.ll_main);
		viewPager = (ViewPager) findViewById(R.id.pager);
		// horizontalScrollView = (HorizontalScrollView)
		// findViewById(R.id.horizontalScrollView);
		InItTitle1();
		setSelector(0);
		InItView();
		viewPager.setAdapter(new myPagerView());
		viewPager.clearAnimation();
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				setSelector(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		setSelector(v.getId());
	}

	private class myPagerView extends PagerAdapter {
		// 显示数目
		@Override
		public int getCount() {
			return pageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(pageViews.get(arg1));
		}

		/***
		 * 获取每一个item， 类于listview中的getview
		 */
		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(pageViews.get(arg1));
			return pageViews.get(arg1);
		}

	}

}