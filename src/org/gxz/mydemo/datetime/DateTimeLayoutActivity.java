package org.gxz.mydemo.datetime;

import org.gxz.mydemo.R;
import org.gxz.mydemo.datetime.time.RadialPickerLayout;
import org.gxz.mydemo.datetime.time.TimePickLayout;
import org.gxz.mydemo.datetime.time.TimePickLayout.OnTimeSetListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class DateTimeLayoutActivity extends Activity implements
		OnTimeSetListener, OnCheckedChangeListener {

	private TextView txt;
	private CheckBox secondCb;
	private TimePickLayout pickLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_datetime_pick_demo2);
		secondCb = (CheckBox) findViewById(R.id.second_check);
		secondCb.setOnCheckedChangeListener(this);
		pickLayout = (TimePickLayout) findViewById(R.id.timepick);
		pickLayout.setOnTimeSetListener(this);
		pickLayout.setIs24Hour(false);
		pickLayout.setVibrate(true);
		pickLayout.setCloseOnSingleTapHour(true);
		pickLayout.setIsSecondShow(secondCb.isChecked());
		txt = (TextView) findViewById(R.id.picktime_txt);
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, boolean isSecondShow,
			int hourOfDay, int minute, int second) {
		if (isSecondShow)
			txt.setText("你选择的时间：" + String.format("%02d", hourOfDay) + ":"
					+ String.format("%02d", minute) + ":"
					+ String.format("%02d", second));
		else
			txt.setText("你选择的时间：" + String.format("%02d", hourOfDay) + ":"
					+ String.format("%02d", minute));
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		pickLayout.setIsSecondShow(isChecked);
	}
}
