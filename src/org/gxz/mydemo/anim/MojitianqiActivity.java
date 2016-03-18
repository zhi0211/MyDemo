package org.gxz.mydemo.anim;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class MojitianqiActivity extends BaseActivity {

	private ImageView mCircleIn, mCircleOut, mStar;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anim_mojitianqi);
		mCircleIn = (ImageView) findViewById(R.id.org_circle_in);
		mCircleIn.setVisibility(View.GONE);
		mCircleOut = (ImageView) findViewById(R.id.org_circle_out);
		mCircleOut.setVisibility(View.GONE);
		mStar = (ImageView) findViewById(R.id.org_star);

		final Animation rotateAnim = AnimationUtils.loadAnimation(
				MojitianqiActivity.this, R.anim.rotate_circle);

		final Animation circleAnim1 = AnimationUtils.loadAnimation(
				MojitianqiActivity.this, R.anim.circleout_scale_out);

		final Animation circleAnim2 = AnimationUtils.loadAnimation(
				MojitianqiActivity.this, R.anim.circleout_scale_out);

		rotateAnim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				mStar.setVisibility(View.GONE);
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						mCircleIn.setVisibility(View.VISIBLE);
						mCircleIn.setAnimation(circleAnim1);
					}
				}, 100);
			}
		});

		circleAnim1.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						mCircleOut.setVisibility(View.VISIBLE);
						mCircleOut.setAnimation(circleAnim2);
					}
				}, 100);

			}
		});

		mStar.startAnimation(rotateAnim);
	}

}
