/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\  =  /O
 * ____/`---'\____
 * .'  \\|     |//  `.
 * /  \\|||  :  |||//  \
 * /  _||||| -:- |||||-  \
 * |   | \\\  -  /// |   |
 * | \_|  ''\---/''  |   |
 * \  .-\__  `-`  ___/-. /
 * ___`. .'  /--.--\  `. . __
 * ."" '<  `.___\_<|>_/___.'  >'"".
 * | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * \  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * 佛祖保佑       永无BUG
 */

package org.gxz.mydemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.gxz.mydemo.anim.AnimListActivity;
import org.gxz.mydemo.capture.CaptureDemoList;
import org.gxz.mydemo.chart.ChartActivity;
import org.gxz.mydemo.check.CheckDemo;
import org.gxz.mydemo.circle.CircleActivity;
import org.gxz.mydemo.contact.ContactListActivity;
import org.gxz.mydemo.cycleviewpager.CircleViewPagerActivity;
import org.gxz.mydemo.datetime.DateTimePickActivity;
import org.gxz.mydemo.drag.DragDemoList;
import org.gxz.mydemo.holocolorpick.HoloColorPickActivity;
import org.gxz.mydemo.img.ImageViewDemoList;
import org.gxz.mydemo.listview.ListViewDemoList;
import org.gxz.mydemo.loading.LoadingDemoList;
import org.gxz.mydemo.main.MainDemoList;
import org.gxz.mydemo.pie.PieActivity;
import org.gxz.mydemo.progress.ProgressList;
import org.gxz.mydemo.rangeseekbar.RangeSeekBarActivity;
import org.gxz.mydemo.ratingbar.RatingBarViewActivity;
import org.gxz.mydemo.rocker.RockerActivity;
import org.gxz.mydemo.rotatetextview.RotateTextActivity;
import org.gxz.mydemo.spannablestringbuilder.SpanStringBuilderActivity;
import org.gxz.mydemo.spinner.SelectActivity;
import org.gxz.mydemo.systembar.SystemBarActivity;
import org.gxz.mydemo.telinfo.TelManager;
import org.gxz.mydemo.test.TestActivity;
import org.gxz.mydemo.text.TextListDemo;
import org.gxz.mydemo.timeselect.WheelActivity;
import org.gxz.mydemo.video.MyVideoActivity;
import org.gxz.mydemo.webview.WebDemoList;
import org.gxz.mydemo.wifi.WifiActivity;

public class MainActivity extends BaseActivity implements OnItemClickListener {

	private final static String TAG = MainActivity.class.getCanonicalName();
	private final String[] DEMO_NAME = {"测试", "联系人列表demo", "Main Demo", "动画",
			"ListView Demo", "加载圈", "拖拽 Demo", "文本Demo", "时间选择器", "CheckBox Demo", "Image Demo",
			"沉浸式状态栏", "TextView风格设计+anim-list实现gif", "倾斜的TextView", "Progress",
			"星级评分", "折线图", "摄像头Demo", "手机信息", "Wifi操作", "仿qq登入复选框", "Web Demo",
			"圆环", "饼图", "时间圆环选择器", "颜色选择器", "视频播放", "循环ViewPager", "范围选择器",
			"摇杆"};
	@SuppressWarnings("rawtypes")
	private final Class[] DEMO_CLASS = {TestActivity.class, ContactListActivity.class,
			MainDemoList.class, AnimListActivity.class, ListViewDemoList.class, LoadingDemoList.class,
			DragDemoList.class, TextListDemo.class, WheelActivity.class, CheckDemo.class, ImageViewDemoList.class, SystemBarActivity.class,
			SpanStringBuilderActivity.class, RotateTextActivity.class,
			ProgressList.class, RatingBarViewActivity.class,
			ChartActivity.class, CaptureDemoList.class, TelManager.class,
			WifiActivity.class, SelectActivity.class, WebDemoList.class,
			CircleActivity.class, PieActivity.class,
			DateTimePickActivity.class, HoloColorPickActivity.class,
			MyVideoActivity.class, CircleViewPagerActivity.class,
			RangeSeekBarActivity.class, RockerActivity.class};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView lv = (ListView) findViewById(R.id.main_listview);
		lv.setOnItemClickListener(this);
		ListAdapter la = new ArrayAdapter<String>(this,
				R.layout.main_list_item, DEMO_NAME);
		lv.setAdapter(la);
		mApplication.printSomeInfo();
		mApplication.startService();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 > DEMO_CLASS.length)
			return;
		Intent i = new Intent(this, DEMO_CLASS[arg2]);
		startActivity(i);
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("提示").setMessage("是否退出?")
				.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mApplication.exitApp();
						MainActivity.this.finish();
					}
				}).setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).create().show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.main_menu_exit:
				new AlertDialog.Builder(this).setTitle("提示").setMessage("是否退出?")
						.setPositiveButton("确定", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mApplication.exitApp();
								MainActivity.this.finish();
							}
						}).setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create().show();
				break;
			case R.id.main_menu_restart:
				new AlertDialog.Builder(this).setTitle("提示").setMessage("是否重启?")
						.setPositiveButton("确定", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								((DemoApplication) getApplication())
										.finishAllActivity();
								((DemoApplication) getApplication()).restartApp();
							}
						}).setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create().show();
				break;
			case R.id.main_menu_test:
				Intent intent = new Intent(this, TestActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
}
