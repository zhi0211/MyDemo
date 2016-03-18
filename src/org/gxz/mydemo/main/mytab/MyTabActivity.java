package org.gxz.mydemo.main.mytab;

import org.gxz.mydemo.DemoApplication;
import org.gxz.mydemo.R;
import org.gxz.mydemo.widgets.SlideMenu;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MyTabActivity extends ActivityGroup implements
		OnTabChangeListener, OnClickListener {

	public final static String MY_TAB_TAG = "MyTab";
	private RelativeLayout tabIndicator1, tabIndicator2, tabIndicator3,
			tabIndicator4;
	private TabHost tabHost;
	private TabWidget tw;
	private SlideMenu slideMenu;
	private TextView titleTv;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		((DemoApplication) getApplication()).removeActivity(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((DemoApplication) getApplication()).addActivity(this);
		setContentView(R.layout.activity_my_tab);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(getLocalActivityManager());
		tw = tabHost.getTabWidget();
		findSlideMenu();
		findTab();
		tabHost.setOnTabChangedListener(this);
		initTab();
		tabHost.setCurrentTab(0);

	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.my_tab, menu);
	// return true;
	// }

	private void findSlideMenu() {
		titleTv = (TextView) findViewById(R.id.title_bar_name);
		slideMenu = (SlideMenu) findViewById(R.id.slide_menu);
		ImageView menuBtn = (ImageView) findViewById(R.id.title_bar_menu_btn);
		menuBtn.setOnClickListener(this);
		TextView tv1 = (TextView) findViewById(R.id.menu_tv_1);
		tv1.setOnClickListener(this);
		TextView tv2 = (TextView) findViewById(R.id.menu_tv_2);
		tv2.setOnClickListener(this);
		TextView tv3 = (TextView) findViewById(R.id.menu_tv_3);
		tv3.setOnClickListener(this);
	}

	private void findTab() {
		tabIndicator1 = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		TextView tvTab1 = (TextView) tabIndicator1.findViewById(R.id.title);
		ImageView ivTab1 = (ImageView) tabIndicator1.findViewById(R.id.icon);
		ivTab1.setBackgroundResource(R.drawable.selector_mood_message);
		tvTab1.setText("信息");

		tabIndicator2 = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		TextView tvTab2 = (TextView) tabIndicator2.findViewById(R.id.title);
		ImageView ivTab2 = (ImageView) tabIndicator2.findViewById(R.id.icon);
		ivTab2.setBackgroundResource(R.drawable.selector_mood_wall);
		tvTab2.setText("通讯录");

		tabIndicator3 = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		TextView tvTab3 = (TextView) tabIndicator3.findViewById(R.id.title);
		ImageView ivTab3 = (ImageView) tabIndicator3.findViewById(R.id.icon);
		ivTab3.setBackgroundResource(R.drawable.selector_mood_home);
		tvTab3.setText("首页");

		tabIndicator4 = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		TextView tvTab4 = (TextView) tabIndicator4.findViewById(R.id.title);
		ImageView ivTab4 = (ImageView) tabIndicator4.findViewById(R.id.icon);
		ivTab4.setBackgroundResource(R.drawable.selector_mood_my_wall);
		tvTab4.setText("我");
	}

	private void initTab() {
		TabHost.TabSpec tSpecHome = tabHost.newTabSpec("信息");
		tSpecHome.setIndicator(tabIndicator1);
		tSpecHome.setContent(new Intent(this, MyHomeActivity.class));
		tabHost.addTab(tSpecHome);

		TabHost.TabSpec tSpecWall = tabHost.newTabSpec("联系人");
		tSpecWall.setIndicator(tabIndicator2);
		tSpecWall.setContent(new Intent(this, MyMoodActivity.class));
		tabHost.addTab(tSpecWall);

		TabHost.TabSpec tSpecMessage = tabHost.newTabSpec("主页");
		tSpecMessage.setIndicator(tabIndicator3);
		tSpecMessage.setContent(new Intent(this, MyMsgActivity.class));
		tabHost.addTab(tSpecMessage);

		TabHost.TabSpec tSpecMe = tabHost.newTabSpec("我");
		tSpecMe.setIndicator(tabIndicator4);
		tSpecMe.setContent(new Intent(this, MySettingActivity.class));
		tabHost.addTab(tSpecMe);
	}

	@Override
	public void onTabChanged(String tabId) {
		tabHost.setCurrentTabByTag(tabId);
		titleTv.setText(tabId);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_menu_btn:
			if (slideMenu.isMainScreenShowing()) {
				slideMenu.openMenu();
			} else {
				slideMenu.closeMenu();
			}
			break;
		case R.id.menu_tv_1:
			Toast.makeText(this, "你点击了菜单1", 0).show();
			break;
		case R.id.menu_tv_2:
			Toast.makeText(this, "你点击了菜单2", 0).show();
			break;
		case R.id.menu_tv_3:
			Toast.makeText(this, "你点击了菜单3", 0).show();
			break;
		default:
			slideMenu.closeMenu();
			break;
		}
	}

}
