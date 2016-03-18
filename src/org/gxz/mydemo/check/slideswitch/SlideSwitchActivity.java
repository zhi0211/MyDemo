package org.gxz.mydemo.check.slideswitch;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.widgets.WiperSwitch;
import org.gxz.mydemo.widgets.WiperSwitch.OnChangedListener;

import android.os.Bundle;
import android.widget.Toast;

public class SlideSwitchActivity extends BaseActivity implements OnChangedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slide_switch);

		WiperSwitch ws = (WiperSwitch) findViewById(R.id.switch_on_off);

		// 设置初始状态为false
		ws.setChecked(false);
		// 设置监听

		ws.setOnChangedListener(this);
	}



	@Override
	public void OnChanged(boolean CheckState) {
		if (CheckState) {
			Toast.makeText(this, "开", 0).show();
		} else {
			Toast.makeText(this, "关", 0).show();
		}
		
	}

}
