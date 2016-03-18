package org.gxz.mydemo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gxz.mydemo.utils.DensityUtil;
import org.gxz.mydemo.utils.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class DemoApplication extends Application {

	private static final String LOGTAG = DemoApplication.class
			.getCanonicalName();
	private List<Activity> activityList = new ArrayList<Activity>();
	private static final SimpleDateFormat SDF_DATE = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public void onCreate() {
		super.onCreate();
		new DensityUtil(getApplicationContext());
		Log.i(LOGTAG, "屏幕密度因子:" + DensityUtil.getDmDensityDpi());
	}

	public void printSomeInfo() {
		if (Utils.isZh(getApplicationContext())) {
			Log.i(LOGTAG, "中文");
		}
		long sysTime = System.currentTimeMillis();
		Date date = new Date(sysTime);
		Log.i(LOGTAG,
				"==============时间证明====================");
		Log.i(LOGTAG,
				"System.curr:" + sysTime + ";date.getTime():" + date.getTime());
		Log.i(LOGTAG,
				"当前时间(系统当前时间):" + SDF_DATE.format(date) + ";时区:"
						+ date.getTimezoneOffset() + "");
		printTime(sysTime);
		readSDCard();
		readSystem();
		printStorageFile();
	}

	private void readSDCard() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcardDir.getPath());
			long blockSize = sf.getBlockSize();
			long blockCount = sf.getBlockCount();
			long availCount = sf.getAvailableBlocks();
			Log.i(LOGTAG,
					"==============SD卡空间状态====================");
			Log.i(LOGTAG, "block大小:" + blockSize + ",block数目:" + blockCount
					+ ",总大小:" + blockSize * blockCount / 1024 / 1024 + "MB");
			Log.i(LOGTAG, "可用的block数目：:" + availCount + ",剩余空间:" + availCount
					* blockSize / 1024 / 1024 + "MB");
		}
	}

	private void readSystem() {
		File root = Environment.getRootDirectory();
		StatFs sf = new StatFs(root.getPath());
		long blockSize = sf.getBlockSize();
		long blockCount = sf.getBlockCount();
		long availCount = sf.getAvailableBlocks();
		Log.i(LOGTAG,
				"==============内存空间状态====================");
		Log.i(LOGTAG, "block大小:" + blockSize + ",block数目:" + blockCount + ",总大小:"
				+ blockSize * blockCount / 1024 / 1024 + "MB");
		Log.i(LOGTAG, "可用的block数目：:" + availCount + ",可用大小:" + availCount
				* blockSize / 1024 / 1024 + "MB");
	}

	private void printTime(long time) {
		long count = time / 1000;
		long ss = count % 60;
		count = count / 60;
		long mm = count % 60;
		count = count / 60;
		long hh = count % 24;
		count = count / 24;
		Log.i(LOGTAG, "证明：取出来的是格林位置时间(根据毫秒计算出来的时间)>>>>" + hh + ":" + mm + ":"
				+ ss);
	}
	
	private void printStorageFile(){
		Log.i(LOGTAG,"getCacheDir="+getApplicationContext().getCacheDir().getAbsolutePath());
		Log.i(LOGTAG,"getFilesDir="+getApplicationContext().getFilesDir().getAbsolutePath());
		Log.i(LOGTAG,"getExternalCacheDir="+getApplicationContext().getExternalCacheDir().getAbsolutePath());
		Log.i(LOGTAG,"DIRECTORY_MUSIC="+getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath());
		Log.i(LOGTAG,"ExternalStorageState="+Environment.getExternalStorageState());
		Log.i(LOGTAG,"DataDirectory="+Environment.getDataDirectory().getAbsolutePath());
		Log.i(LOGTAG,"DownloadCacheDirectory="+Environment.getDownloadCacheDirectory().getAbsolutePath());
		Log.i(LOGTAG,"ExternalStorageDirectory="+Environment.getExternalStorageDirectory().getAbsolutePath());
		Log.i(LOGTAG,"RootDirectory="+Environment.getRootDirectory().getAbsolutePath());
		Log.i(LOGTAG,"-------------------------- getExternalStoragePublicDirectory -------------------------");
		Log.i(LOGTAG,"DIRECTORY_ALARMS = "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS).getAbsolutePath());
		Log.i(LOGTAG,"DIRECTORY_DCIM = "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath());
		Log.i(LOGTAG,"DIRECTORY_DOWNLOADS = "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
		Log.i(LOGTAG,"DIRECTORY_MOVIES = "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath());
		Log.i(LOGTAG,"DIRECTORY_NOTIFICATIONS = "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS).getAbsolutePath());
		Log.i(LOGTAG,"DIRECTORY_PICTURES = "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
		Log.i(LOGTAG,"DIRECTORY_PODCASTS = "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS).getAbsolutePath());
		Log.i(LOGTAG,"DIRECTORY_RINGTONES = "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).getAbsolutePath());
		Log.i(LOGTAG,"DIRECTORY_MUSIC = "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath());
	}

	/** 重新启动一个App */
	public void restartApp() {
		Intent intent = getPackageManager().getLaunchIntentForPackage(
				getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	/** 将启动的Activity加入到自己管控的栈中 */
	public void addActivity(Activity activity) {
		Log.i(LOGTAG, "add an activity:" + activity.getLocalClassName());
		activityList.add(activity);
	}

	/** 将启动的Activity移除 */
	public void removeActivity(Activity activity) {
		Log.i(LOGTAG, "remove an activity:" + activity.getLocalClassName());
		activityList.remove(activity);
	}

	/** 结束所有的Activity */
	public void finishAllActivity() {
		for (Activity activity : activityList) {
			if (activity != null) {
				Log.i(LOGTAG, "finish activity:" + activity.getLocalClassName());
				activity.finish();
			}
		}
		Log.i(LOGTAG, "clear  all  activity:" + activityList.size());
		for (Activity activity : activityList) {
			Log.i(LOGTAG, "clear activity:" + activity.getLocalClassName());
		}
		activityList.clear();
	}
	
	public void startService(){
		getApplicationContext().startService(DemoService.getIntent());
	}
	
	public void exitApp(){
		getApplicationContext().stopService(DemoService.getIntent());
	}

}
