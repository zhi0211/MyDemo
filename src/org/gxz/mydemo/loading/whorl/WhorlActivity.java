package org.gxz.mydemo.loading.whorl;

import android.os.Bundle;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

/**
 * Created by gxz on 2015/11/12.
 */
public class WhorlActivity extends BaseActivity {
	private WhorlView view1;
	private WhorlView view2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_whorl);

		view1 = (WhorlView) findViewById(R.id.whorlView1);
		view2 = (WhorlView) findViewById(R.id.whorlView2);

		view1.start();
		view2.start();

	}


	@Override
	protected void onDestroy() {
		view1.stop();
		view2.stop();
		super.onDestroy();
	}
}
