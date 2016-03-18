package org.gxz.mydemo.main.residemenu;

import org.gxz.mydemo.BaseFragmentActivity;
import org.gxz.mydemo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends BaseFragmentActivity implements
		View.OnClickListener {

	private ResideMenu resideMenu;
	private ResideMenuItem itemHome;
	private ResideMenuItem itemProfile;
	private ResideMenuItem itemCalendar;
	private ResideMenuItem itemSettings;
	private HomeFragment homeFragment;
	private ProfileFragment profileFragment;
	private SettingsFragment settingFragment;

	private Fragment[] fragments;
	private Button[] mTabs;
	private int index;
	// 当前fragment的index
	private int currentTabIndex;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_residemenu_main);
		setUpMenu();
		initView();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		mTabs = new Button[3];
		mTabs[0] = (Button) findViewById(R.id.btn_conversation);
		mTabs[1] = (Button) findViewById(R.id.btn_address_list);
		mTabs[2] = (Button) findViewById(R.id.btn_setting);
		// 把第一个tab设为选中状态
		mTabs[0].setSelected(true);
		homeFragment = new HomeFragment();
		profileFragment = new ProfileFragment();
		settingFragment = new SettingsFragment();
		fragments = new Fragment[] { homeFragment, profileFragment,
				settingFragment };
		getSupportFragmentManager().beginTransaction()
				.add(R.id.main_fragment, homeFragment).show(homeFragment)
				.commit();

	}

	/**
	 * button点击事件
	 * 
	 * @param view
	 */
	public void onTabClicked(View view) {
		switch (view.getId()) {
		case R.id.btn_conversation:
			index = 0;
			break;
		case R.id.btn_address_list:
			index = 1;
			break;
		case R.id.btn_setting:
			index = 2;
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.main_fragment, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		mTabs[currentTabIndex].setSelected(false);
		// 把当前tab设为选中状态
		mTabs[index].setSelected(true);
		currentTabIndex = index;
	}

	private void setUpMenu() {

		// attach to current activity;
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.menu_background);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);
		// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		resideMenu.setScaleValue(0.6f);

		// create menu items;
		itemHome = new ResideMenuItem(this, R.drawable.icon_home, "Home");
		itemProfile = new ResideMenuItem(this, R.drawable.icon_profile,
				"Profile");
		itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar,
				"Calendar");
		itemSettings = new ResideMenuItem(this, R.drawable.icon_settings,
				"Settings");

		itemHome.setOnClickListener(this);
		itemProfile.setOnClickListener(this);
		itemCalendar.setOnClickListener(this);
		itemSettings.setOnClickListener(this);

		resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);

		// You can disable a direction by setting ->
		// resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

		findViewById(R.id.title_bar_left_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
					}
				});
		findViewById(R.id.title_bar_right_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
					}
				});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}

	@Override
	public void onClick(View view) {

		// if (view == itemHome) {
		// changeFragment(new HomeFragment());
		// } else if (view == itemProfile) {
		// changeFragment(new ProfileFragment());
		// } else if (view == itemCalendar) {
		// changeFragment(new CalendarFragment());
		// } else if (view == itemSettings) {
		// changeFragment(new SettingsFragment());
		// }

		resideMenu.closeMenu();
	}

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
		}

		@Override
		public void closeMenu() {
		}
	};

	// What good method is to access resideMenu？
	public ResideMenu getResideMenu() {
		return resideMenu;
	}
}
