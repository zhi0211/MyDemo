package org.gxz.mydemo.progress.styleprogress;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

import android.R.animator;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MyProgressActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_progress);
		final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar10);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((Animatable) progressBar.getIndeterminateDrawable()).start();
			}
		});
		Button btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((Animatable) progressBar.getIndeterminateDrawable()).stop();
				
			}
		});
	}

}
