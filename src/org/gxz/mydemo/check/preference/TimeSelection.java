package org.gxz.mydemo.check.preference;

import org.gxz.mydemo.BasePreferenceActivity;
import org.gxz.mydemo.widgets.TitleBar;

import android.preference.Preference;

public class TimeSelection extends BasePreferenceActivity {

	private TitleBar mTitle;
	private final static String MODE = "time_select_mode";
	private final static String START_TIME = "time_select_start";
	private final static String END_TILE = "time_select_end";
	private final static String APPLY_TO = "time_select_apply_to";

	private Preference mMode, mStartTime, mEndTime, mApplyTo;
//
//	@SuppressWarnings("deprecation")
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		String title = getIntent().getStringExtra(IntentControl.INTENT_DATA_TEMPLATE_TITLE);
//		setContentView(R.layout.activity_time_selection);
//
//		mTitle = (TitleBar) findViewById(R.id.time_selection_title);
//		mTitle.init(title, true);
//		mTitle.initRightBtn(true, "确定");
//		mTitle.setOnTitleBtnOnClickListener(new ImplOnTitleBtnOnClickListener());
//
//		addPreferencesFromResource(R.xml.time_selection);
//		mMode = findPreference(MODE);
//		mStartTime = findPreference(START_TIME);
//		mEndTime = findPreference(END_TILE);
//		mApplyTo = findPreference(APPLY_TO);
//
//		mMode.setSummary("允许");
//		mStartTime.setSummary("07:00");
//		mEndTime.setSummary("22:00");
//		mApplyTo.setSummary("周五；周六；周天");
//
//	}
//
//	private class ImplOnTitleBtnOnClickListener implements OnTitleBtnOnClickListener {
//
//		@Override
//		public void onLeftBtnClick() {
//			finish();
//		}
//
//		@Override
//		public void onRightBtnClick() {
//			Intent intent = getIntent();
//			setResult(Activity.RESULT_OK, intent);
//			finish();
//
//		}
//	}
//
//	@Override
//	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
//		String key = preference.getKey();
//		Intent intent = null;
//		if (key != null) {
//			if (key.equals(MODE)) {
//			} else if (key.equals(START_TIME)) {
//				intent = new Intent(TimeSelection.this, DialogTimePick.class);
//				startActivityForResult(intent, 0xfe);
//			} else if (key.equals(END_TILE)) {
//				intent = new Intent(TimeSelection.this, DialogTimePick.class);
//				startActivityForResult(intent, 0xff);
//			} else if (key.equals(APPLY_TO)) {
//
//			}
//		}
//
//		return super.onPreferenceTreeClick(preferenceScreen, preference);
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (requestCode == 0xfe) {
//			if (resultCode == Activity.RESULT_OK) {
//				mStartTime.setSummary(data.getStringExtra("hour") + ":" + data.getStringExtra("min"));
//			}
//		} else if (requestCode == 0xff) {
//			if (resultCode == Activity.RESULT_OK) {
//				mEndTime.setSummary(data.getStringExtra("hour") + ":" + data.getStringExtra("min"));
//			}
//		}
//
//	}
}
