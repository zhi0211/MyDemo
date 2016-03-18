package org.gxz.mydemo.check.preference;

import org.gxz.mydemo.BasePreferenceActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.widgets.TitleBar;
import org.gxz.mydemo.widgets.TitleBar.OnTitleBtnOnClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;

/**
 * 周期模板
 */
public class CycleTemplate extends BasePreferenceActivity {

	
	public static final String INTENT_DATA_TEMPLATE_TITLE="org.gxz.mydemo.template.title";
	private TitleBar mTitle;

	private static final String CYCLE_MONDAY = "cycle_monday";
	private static final String CYCLE_TUESDAY = "cycle_tuesday";
	private static final String CYCLE_WEDNESDAY = "cycle_wednesday";
	private static final String CYCLE_THURSDAY = "cycle_thursday";
	private static final String CYCLE_FRIDAY = "cycle_friday";
	private static final String CYCLE_SATURDAY = "cycle_saturday";
	private static final String CYCLE_SUNDAY = "cycle_sunday";

	private SlideBoxPreference mMonday;
	private SlideBoxPreference mTuesday;
	private SlideBoxPreference mWednesday;
	private SlideBoxPreference mThursday;
	private SlideBoxPreference mFriday;
	private SlideBoxPreference mSaturday;
	private SlideBoxPreference mSunday;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_cycle_template);

		mTitle = (TitleBar) findViewById(R.id.cycle_template_title);
		mTitle.init("周期设定", true);
		mTitle.initRightBtn(true, "确定");
		mTitle.setOnTitleBtnOnClickListener(new ImplOnTitleBtnOnClickListener());

		addPreferencesFromResource(R.xml.cycle_template);

		mMonday = (SlideBoxPreference) findPreference(CYCLE_MONDAY);
		mTuesday = (SlideBoxPreference) findPreference(CYCLE_TUESDAY);
		mWednesday = (SlideBoxPreference) findPreference(CYCLE_WEDNESDAY);
		mThursday = (SlideBoxPreference) findPreference(CYCLE_THURSDAY);
		mFriday = (SlideBoxPreference) findPreference(CYCLE_FRIDAY);
		mSaturday = (SlideBoxPreference) findPreference(CYCLE_SATURDAY);
		mSunday = (SlideBoxPreference) findPreference(CYCLE_SUNDAY);
		
		mMonday.setSummaryOn("07:30-20:00");
		mTuesday.setSummaryOn("20:00-22:00");
		mWednesday.setSummaryOn("09:30-20:00");
		mThursday.setSummaryOn("07:30-20:00");
		mFriday.setSummaryOn("07:30-20:00");
		mSaturday.setSummaryOn("07:30-20:00");
		mSunday.setSummaryOn("10:30-20:00");

	}

	private class ImplOnTitleBtnOnClickListener implements OnTitleBtnOnClickListener {

		@Override
		public void onLeftBtnClick() {
			finish();
		}

		@Override
		public void onRightBtnClick() {
			Intent intent = getIntent();
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
		String key=preference.getKey();
		if(key!=null){
//			Intent intent=new Intent(CycleTemplate.this,TimeSelection.class);
//			String title=preference.getTitle().toString();
//			intent.putExtra(INTENT_DATA_TEMPLATE_TITLE, title);
			if(key.equals(CYCLE_MONDAY)){
				
			}else if(key.equals(CYCLE_TUESDAY)){
				
			}else if(key.equals(CYCLE_WEDNESDAY)){
				
			}else if(key.equals(CYCLE_THURSDAY)){
				
			}else if(key.equals(CYCLE_FRIDAY)){
				
			}else if(key.equals(CYCLE_SATURDAY)){
				
			}else if(key.equals(CYCLE_SUNDAY)){
				
			}
//			startActivityForResult(intent, 0xff);
				
		}
		
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==0xff){
			if(resultCode==Activity.RESULT_OK){
			}
		}
	}
}
