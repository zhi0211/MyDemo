package org.gxz.mydemo.datetime;

import java.util.Calendar;

import org.gxz.mydemo.BaseFragmentActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.datetime.date.DatePickerDialog;
import org.gxz.mydemo.datetime.date.DatePickerDialog.OnDateSetListener;
import org.gxz.mydemo.datetime.time.RadialPickerLayout;
import org.gxz.mydemo.datetime.time.TimePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.Toast;

public class DateTimePickActivity extends BaseFragmentActivity implements
		OnDateSetListener, TimePickerDialog.OnTimeSetListener {

	public static final String DATEPICKER_TAG = "datepicker";
	public static final String TIMEPICKER_TAG = "timepicker";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datetime_pick_demo);

		final Calendar calendar = Calendar.getInstance();

		final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
				this, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), isVibrate());
		final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
				this, calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE),calendar.get(Calendar.SECOND), true, false);

		findViewById(R.id.dateButton).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				datePickerDialog.setVibrate(isVibrate());
				datePickerDialog.setYearRange(1985, 2028);
				datePickerDialog
						.setCloseOnSingleTapDay(isCloseOnSingleTapDay());
				datePickerDialog.show(getSupportFragmentManager(),
						DATEPICKER_TAG);
			}
		});

		findViewById(R.id.timeButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				timePickerDialog.setVibrate(isVibrate());
				timePickerDialog.setIs24Hour(is24Hour());
				timePickerDialog
						.setCloseOnSingleTapHour(isCloseOnSingleTapHour());
				timePickerDialog.show(getSupportFragmentManager(),
						TIMEPICKER_TAG);
			}
		});

		if (savedInstanceState != null) {
			DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager()
					.findFragmentByTag(DATEPICKER_TAG);
			if (dpd != null) {
				dpd.setOnDateSetListener(this);
			}

			TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager()
					.findFragmentByTag(TIMEPICKER_TAG);
			if (tpd != null) {
				tpd.setOnTimeSetListener(this);
			}
		}
		findViewById(R.id.time_select).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						Intent intent = new Intent(DateTimePickActivity.this,
								DateTimeLayoutActivity.class);
						startActivity(intent);
					}
				});
	}

	private boolean isVibrate() {
		return ((CheckBox) findViewById(R.id.checkBoxVibrate)).isChecked();
	}

	private boolean isCloseOnSingleTapDay() {
		return ((CheckBox) findViewById(R.id.checkBoxCloseOnSingleTapDay))
				.isChecked();
	}

	private boolean isCloseOnSingleTapHour() {
		return ((CheckBox) findViewById(R.id.checkBoxCloseOnSingleTapHour))
				.isChecked();
	}

	private boolean is24Hour() {
		return ((CheckBox) findViewById(R.id.checkBox24hour)).isChecked();
	}

	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year,
			int month, int day) {
		Toast.makeText(DateTimePickActivity.this,
				"new date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute,int second) {
		Toast.makeText(DateTimePickActivity.this,
				"new time:" + hourOfDay + ":" + minute, Toast.LENGTH_LONG)
				.show();
	}
}
