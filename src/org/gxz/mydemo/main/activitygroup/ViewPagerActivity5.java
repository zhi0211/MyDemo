package org.gxz.mydemo.main.activitygroup;

import org.gxz.mydemo.BaseActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class ViewPagerActivity5 extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button button = new Button(this);
		button.setText("number five");
		getWindow().getDecorView().setBackgroundColor(0xff666555);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

		setContentView(button, layoutParams);

		
		
	}
}
