package org.gxz.mydemo.main.manyfragments;

import java.util.ArrayList;
import java.util.List;

import org.gxz.mydemo.DemoApplication;
import org.gxz.mydemo.R;
import org.gxz.mydemo.main.tabhost.Home_Fragment;
import org.gxz.mydemo.main.tabhost.Me_Fragment;
import org.gxz.mydemo.main.tabhost.Message_Fragment;
import org.gxz.mydemo.main.tabhost.Wall_Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

//FragmentActivity页面

public class ViewPagerDemoActivity extends FragmentActivity {

	/** 页面list **/
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	/** 页面title list **/
	private List<String> titleList = new ArrayList<String>();
	private Home_Fragment homefrag;
	private Wall_Fragment wallfrag;
	private Message_Fragment messagefrag;
	private Me_Fragment mefrag;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		((DemoApplication) getApplication()).removeActivity(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((DemoApplication) getApplication()).addActivity(this);
		setContentView(R.layout.activity_view_pager_demo);

		ViewPager vp = (ViewPager) findViewById(R.id.viewPager);
		PagerTabStrip pagerTab=(PagerTabStrip) findViewById(R.id.pagertab);
		pagerTab.setTabIndicatorColor(Color.GREEN);
		homefrag = new Home_Fragment();
		wallfrag = new Wall_Fragment();
		messagefrag = new Message_Fragment();
		mefrag = new Me_Fragment();
		fragmentList.add(homefrag);
		fragmentList.add(wallfrag);
		fragmentList.add(messagefrag);
		fragmentList.add(mefrag);
		titleList.add("主页 ");
		titleList.add("心情墙");
		titleList.add("信息");
		titleList.add("我");
		vp.setAdapter(new myPagerAdapter(getSupportFragmentManager(),
				fragmentList, titleList));
	}

	@Override
	public void onBackPressed() {
		if (messagefrag.getisDetail()) {
			messagefrag.setListView();
			return;
		}
		super.onBackPressed();
	}

	/**
	 * 定义适配器
	 * 
	 * 
	 */
	class myPagerAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragmentList;
		private List<String> titleList;

		public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList,
				List<String> titleList) {
			super(fm);
			this.fragmentList = fragmentList;
			this.titleList = titleList;
		}

		/**
		 * 得到每个页面
		 */
		@Override
		public Fragment getItem(int arg0) {
			return (fragmentList == null || fragmentList.size() == 0) ? null
					: fragmentList.get(arg0);
		}

		/**
		 * 每个页面的title
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return (titleList.size() > position) ? titleList.get(position) : "";
		}

		/**
		 * 页面的总个数
		 */
		@Override
		public int getCount() {
			return fragmentList == null ? 0 : fragmentList.size();
		}
	}
}