package org.gxz.mydemo.main;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.main.activitygroup.MyGroupActivity;
import org.gxz.mydemo.main.manyfragments.ViewPagerDemoActivity;
import org.gxz.mydemo.main.mytab.MyTabActivity;
import org.gxz.mydemo.main.residemenu.MenuActivity;
import org.gxz.mydemo.main.tabhost.MyFramentActivity;
import org.gxz.mydemo.main.weixin5.Weixin5Activity;
import org.gxz.mydemo.main.weixin6.WeiXin6Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainDemoList extends BaseActivity implements OnItemClickListener {

	private final String[] DEMO_ANIM_NAME = { "Tabhost+Frament",
			"ActivityGroup+ViewPager", "ViewPager+Fragment",
			"TabHost+menuslide", "ResideMenu+Fragment", "微信5.0","微信6.0" };
	@SuppressWarnings("rawtypes")
	private final Class[] DEMO_ANIM_CLASS = { MyFramentActivity.class,
			MyGroupActivity.class, ViewPagerDemoActivity.class,
			MyTabActivity.class, MenuActivity.class, Weixin5Activity.class ,WeiXin6Activity.class};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView lv = (ListView) findViewById(R.id.main_listview);
		lv.setBackgroundColor(Color.WHITE);
		lv.setOnItemClickListener(this);
		ListAdapter la = new ArrayAdapter<String>(this,
				R.layout.main_list_item, DEMO_ANIM_NAME);
		lv.setAdapter(la);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 > DEMO_ANIM_CLASS.length)
			return;
		Intent i = new Intent(this, DEMO_ANIM_CLASS[arg2]);
		startActivity(i);
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
	}

}
