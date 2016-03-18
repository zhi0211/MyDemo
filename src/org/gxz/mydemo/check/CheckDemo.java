package org.gxz.mydemo.check;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.check.preference.CycleTemplate;
import org.gxz.mydemo.check.slideswitch.SlideSwitchActivity;
import org.gxz.mydemo.check.togglebtn.ToggleBtnActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CheckDemo extends BaseActivity implements OnItemClickListener {
	private final String[] DEMO_ANIM_NAME = { "滑动开关", "周期设置", "模仿IOS风格开关(不使用图片资源)" };
	@SuppressWarnings("rawtypes")
	private final Class[] DEMO_ANIM_CLASS = { SlideSwitchActivity.class,
			CycleTemplate.class,ToggleBtnActivity.class };

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
