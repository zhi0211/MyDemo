package org.gxz.mydemo.rocker;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

/**
 * Created by gxz on 2015/9/1.
 */
public class RockerActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rocker);
		FrameLayout layout = (FrameLayout) findViewById(R.id.container);
		RockerView view = new RockerView(this);
		layout.addView(view);
		final DirectionView directionView = (DirectionView) findViewById(R.id.directionview);
		findViewById(R.id.button_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				directionView.moved(DirectionView.Direction.left);
			}
		});

		findViewById(R.id.button_right).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				directionView.moved(DirectionView.Direction.right);
			}
		});

		findViewById(R.id.button_up).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				directionView.moved(DirectionView.Direction.up);
			}
		});

		findViewById(R.id.button_down).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				directionView.moved(DirectionView.Direction.down);
			}
		});
	}
}
