package org.gxz.mydemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class DemoService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public static Intent getIntent(){
		return new Intent("org.gxz.mydemo.DemoService");
	}

	@Override
	public void onCreate() {
		Log.e(this.getClass().getCanonicalName(), "Service onCreate");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(this.getClass().getCanonicalName(), "Service onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		Log.e(this.getClass().getCanonicalName(), "Service onStart");
		super.onStart(intent, startId);
	}

	@Override
	public boolean stopService(Intent name) {
		Log.e(this.getClass().getCanonicalName(), "Service stopService");
		return super.stopService(name);
	}

	@Override
	public void onDestroy() {
		Log.e(this.getClass().getCanonicalName(), "Service onDestroy");
		super.onDestroy();
	}
}
