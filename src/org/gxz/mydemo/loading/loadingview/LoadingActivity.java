package org.gxz.mydemo.loading.loadingview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

/**
 * Created by gxz on 2015/11/12.
 */
public class LoadingActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);

		final LoadingView view = (LoadingView) findViewById(R.id.loadingView);

		Button btn = (Button) findViewById(R.id.btn1);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Boolean flag = (Boolean) v.getTag();
				if (flag == null || !flag) {
					v.setTag(true);
					view.stopLoading();
				} else {
					v.setTag(false);
					view.startLoading(200);
				}
			}
		});
	}
}
