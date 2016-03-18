package org.gxz.mydemo.anim.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by gxz on 2015/9/30.
 */
public class ExpandCollapseRecord {

	private boolean flag = false;
	private View v;
	private int height = 0;


	public ExpandCollapseRecord(final View v) {
		this.v = v;
		ViewTreeObserver vto = v.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				if (!flag) {
					height = v.getMeasuredHeight();
					Log.d("MyDemo", "height=" + height);
				}
				flag = true;
				return true;
			}
		});
	}


	public void expand() {
		if (height == 0) {
			v.setVisibility(View.VISIBLE);
			return;
		}
		final int targetHeight = height;
		Log.d("expand", "targetHeight=" + targetHeight);
		// Older versions of android (pre API 21) cancel animations for views with a height of 0.
		v.getLayoutParams().height = 1;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				v.getLayoutParams().height = (int) (targetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
		v.startAnimation(a);

	}

	public void collapse() {
		if (height == 0) {
			v.setVisibility(View.GONE);
			return;
		}
		final int initialHeight = height;
		Log.d("collapse", "targetHeight=" + initialHeight);
		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
				} else {
					v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};
		a.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				v.getLayoutParams().height = initialHeight;
				v.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		// 1dp/ms
		a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
		v.startAnimation(a);
	}


}
