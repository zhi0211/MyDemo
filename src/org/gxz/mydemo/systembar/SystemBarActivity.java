package org.gxz.mydemo.systembar;

import android.os.Bundle;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

/**
 * Created by gxz on 2015/12/25.
 */
public class SystemBarActivity extends BaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_systembar);
		SystemBarTools.initStatusBarTintManager(this,R.color.white);
		SystemBarTools.setStatusBarDarkMode(true,this);
//		StatusBarCompat.compat(this, R.color.statusbar_color);
	}
}
