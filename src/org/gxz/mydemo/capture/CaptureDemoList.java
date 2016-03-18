package org.gxz.mydemo.capture;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.capture.camera.CameraActivity;
import org.gxz.mydemo.capture.qrcode.CaptureActivity;

public class CaptureDemoList extends BaseActivity implements OnItemClickListener {
	private final String[] DEMO_ANIM_NAME = {"原状态扫二维码", "仿QQ与微信扫二维码", "摄像头基本使用"};
	@SuppressWarnings("rawtypes")
	private final Class[] DEMO_ANIM_CLASS = {CaptureActivity.class,
			CaptureActivity.class, CameraActivity.class};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView lv = (ListView) findViewById(R.id.main_listview);
		lv.setBackgroundColor(Color.WHITE);
		lv.setOnItemClickListener(this);
		lv.setSelector(R.drawable.item_bg_selector);
		ListAdapter la = new ArrayAdapter<String>(this,
				R.layout.main_list_item, DEMO_ANIM_NAME);
		lv.setAdapter(la);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 > DEMO_ANIM_CLASS.length)
			return;
		Intent i = new Intent(this, DEMO_ANIM_CLASS[arg2]);
		i.putExtra("position", arg2);
		startActivity(i);
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
	}
}
