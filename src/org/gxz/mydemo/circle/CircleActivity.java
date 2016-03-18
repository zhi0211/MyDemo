package org.gxz.mydemo.circle;

import org.gxz.mydemo.R;
import org.gxz.mydemo.circle.CircularSeekBar.OnSeekChangeListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class CircleActivity extends Activity {
	CircularSeekBar circularSeekbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle);
		circularSeekbar=(CircularSeekBar) findViewById(R.id.circle);
		circularSeekbar.setMaxProgress(100);
		circularSeekbar.setProgress(0);
		circularSeekbar.invalidate();

		circularSeekbar.setSeekBarChangeListener(new OnSeekChangeListener() {

			@Override
			public void onProgressChange(CircularSeekBar view, int newProgress) {
				Log.d("Welcome",
						"Progress:" + view.getProgress() + "/"
								+ view.getMaxProgress() + ";new progress:"
								+ newProgress);
			}
		});

	}
}
