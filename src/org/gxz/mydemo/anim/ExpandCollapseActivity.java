package org.gxz.mydemo.anim;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.anim.utils.AnimUtils;

/**
 * Created by gxz on 2015/9/30.
 */
public class ExpandCollapseActivity extends BaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expand_collapse);

		final View view = findViewById(R.id.imageView);
		final Button btn = (Button) findViewById(R.id.button);
		btn.setText("展开");
		btn.setTag(false);
		view.setVisibility(View.GONE);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Boolean flag = (Boolean) btn.getTag();
				if (flag != null && !flag) {//展开
					btn.setTag(true);
					btn.setText("收起");
					AnimUtils.expand(view);
				} else {//收起
					btn.setTag(false);
					btn.setText("展开");
					AnimUtils.collapse(view);
				}
			}
		});
	}
	
}
