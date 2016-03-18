package org.gxz.mydemo.rangeseekbar;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.rangeseekbar.RangeSeekBar.OnRangeSeekBarChangeListener;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RangeSeekBarActivity extends BaseActivity implements
		OnClickListener {

	private EditText mEditText1, mEditText2;
	private RangeSeekBar<Integer> seekBar;
	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_range_seekbar);

		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
		
		mTextView = (TextView) findViewById(R.id.textView1);

		// create RangeSeekBar as Integer range between 20 and 75
		seekBar = new RangeSeekBar<Integer>(0, 100, this);
		seekBar.setNotifyWhileDragging(true);
		seekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
					Integer minValue, Integer maxValue) {
				// handle changed range values
				
				mTextView.setText(minValue+"  ------  "+maxValue);
				Log.i(this.getClass().getCanonicalName(),
						"User selected new range values: MIN=" + minValue
								+ ", MAX=" + maxValue);
			}
		});

		seekBar.setMinimumHeight(20);
		// add RangeSeekBar to pre-defined layout
		layout.addView(seekBar);

		mEditText1 = (EditText) findViewById(R.id.edittext1);
		mEditText2 = (EditText) findViewById(R.id.edittext2);

		Button btn = (Button) findViewById(R.id.confirm);

		btn.setOnClickListener(this);
		
		Button resetBtn = (Button) findViewById(R.id.reset);
		resetBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.confirm:
			String n1 = mEditText1.getText().toString();
			String n2 = mEditText2.getText().toString();
			if (!n1.equals("")) {
				try {
					int int1 = Integer.valueOf(n1);
					Log.i(this.getClass().getCanonicalName(),
							"User selected new range values: int1="+int1);
					seekBar.setSelectedMinValue(int1);
				} catch (Exception e) {
					Toast.makeText(this, "输入不合法", Toast.LENGTH_SHORT).show();

				}
			}

			if (!n2.equals("")) {
				try {
					int int2 = Integer.valueOf(n2);
					seekBar.setSelectedMaxValue(int2);
					Log.i(this.getClass().getCanonicalName(),
							"User selected new range values: int2="+int2);
				} catch (Exception e) {
					Toast.makeText(this, "输入不合法", Toast.LENGTH_SHORT).show();

				}
			}

			break;
			
		case R.id.reset:
			n1 = mEditText1.getText().toString();
			n2 = mEditText2.getText().toString();
			int int1 = -1,int2 = -1;
			if (!n1.equals("")) {
				try {
					int1 = Integer.valueOf(n1);
				} catch (Exception e) {
					Toast.makeText(this, "输入不合法", Toast.LENGTH_SHORT).show();

				}
			}

			if (!n2.equals("")) {
				try {
					int2 = Integer.valueOf(n2);
					seekBar.setSelectedMaxValue(int2);
					Log.i(this.getClass().getCanonicalName(),
							"User selected new range values: int2="+int2);
				} catch (Exception e) {
					Toast.makeText(this, "输入不合法", Toast.LENGTH_SHORT).show();

				}
			}
			if(int1>=0&&int2>0&&int1<int2){
				seekBar.resetRangeSeekBar(int1, int2);
				mTextView.setText(seekBar.getSelectedMinValue()+"  ------  "+seekBar.getSelectedMaxValue());
			}else{
				Toast.makeText(this, "输入不合法", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

}
