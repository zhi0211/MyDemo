package org.gxz.mydemo.test;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.systembar.SystemBarTools;

public class TestActivity extends BaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		final SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar);
		final TextView textView = (TextView) findViewById(R.id.text);
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				System.out.println("=====onProgressChanged======progress=" + progress + ",fromUser=" + fromUser);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				System.out.println("=====onStartTrackingTouch======");
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				System.out.println("=====onStopTrackingTouch======");
			}
		});

		seekBar.setProgress(50);
		seekBar.setSecondaryProgress(70);

		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				seekBar.setProgress(50);
			}
		});

		final EditText text = (EditText) findViewById(R.id.editText);
		text.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				System.out.println("======beforeTextChanged=======s=" + s.toString() + ",start=" + start + ",after=" + after + ",count=" + count);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				System.out.println("======onTextChanged=======s=" + s.toString() + ",start=" + start + ",before=" + before + ",count=" + count);
			}

			@Override
			public void afterTextChanged(Editable s) {
				System.out.println("======afterTextChanged=======s=" + s.toString());
			}
		});
		textView.requestFocus();
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textView.setText(text.getText().toString());
				textView.requestFocus();
			}
		});

		initBar();
	}

	/**
	 * 设置沉浸式状态栏
	 */
	private void initBar() {
		SystemBarTools.initStatusBarTintManager(this);
	}
}
