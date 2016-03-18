package org.gxz.mydemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseFragmentActivity extends FragmentActivity {

	private DemoApplication mApplication;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication=(DemoApplication)getApplication();
		mApplication.addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		mApplication.removeActivity(this);
		super.onDestroy();
	}
}
