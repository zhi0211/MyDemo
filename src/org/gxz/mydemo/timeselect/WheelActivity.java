package org.gxz.mydemo.timeselect;

import java.util.Date;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WheelActivity extends BaseActivity {
	public static WheelView hour;
	public static WheelView days;
	public static WheelView mins;
	private Button btn_ok;
	private TextView txt_show_time;

	// 在WheelView.java中的notifyChangingListeners方法会触发这个Handler,目的是不让用户选择今天已经过去的时间
	public static Handler mHandler = new Handler() {
		public static final int changeFlag = 1001;

		@Override
		public void handleMessage(Message msg) {
			// 判断今天的时间
			switch (msg.what) {
			case changeFlag:
				if (days.getCurrentItem() == 0) {
					Date date = new Date();
					hour.setAdapter(new NumericWheelAdapter(date.getHours() + 1, 23));// 从当前小时+1
																						// 开始
				} else {
					hour.setAdapter(new NumericWheelAdapter(0, 23));
				}
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wheel);

		days = (WheelView) findViewById(R.id.days);
		hour = (WheelView) findViewById(R.id.hour);
		mins = (WheelView) findViewById(R.id.mins);
		txt_show_time = (TextView) findViewById(R.id.txt_show_time);
		btn_ok = (Button) findViewById(R.id.btn_ok);

		days.setAdapter(new ArrayWheelAdapter<String>(new String[] { "星期一", "星期二", "星期三","星期四", "星期五", "星期六","星期天" }));
		days.TEXT_SIZE = 30;
		days.setCurrentItem(0);

		hour.setAdapter(new NumericWheelAdapter(0, 23));
//		hour.setLabel("点");
		hour.TEXT_SIZE = 30;

		mins.setAdapter(new NumericWheelAdapter(0, 59));
//		mins.setLabel("分");
		mins.TEXT_SIZE = 30;

		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				txt_show_time.setText(days.getAdapter().getItem(days.getCurrentItem())+"    "
						+ hour.getAdapter().getItem(hour.getCurrentItem()) + ":"
						+ mins.getAdapter().getItem(mins.getCurrentItem()) );
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
