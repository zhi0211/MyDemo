package org.gxz.mydemo;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

	protected DemoApplication mApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = (DemoApplication) getApplication();
		mApplication.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		mApplication.removeActivity(this);
		super.onDestroy();
	}
	
	protected String getTag(){
		return this.getClass().getCanonicalName();
	}
}
