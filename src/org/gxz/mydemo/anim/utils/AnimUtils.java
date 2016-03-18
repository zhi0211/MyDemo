package org.gxz.mydemo.anim.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

/**
 * Created by gxz on 2015/8/14. 动画工具类
 */
public class AnimUtils {

	/**
	 * 设置View隐藏GONE
	 *
	 * @param view
	 * @param animResId 动画资源
	 */
	public static void hideView(Context context, final View view, int animResId) {
		if (context == null || view == null
				|| view.getVisibility() == View.GONE) {
			return;
		}
		view.clearAnimation();
		Animation anim = AnimationUtils.loadAnimation(context, animResId);
		if (anim != null) {
			anim.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					view.setVisibility(View.GONE);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}
			});
			view.startAnimation(anim);
		} else {
			view.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置View显示VISIBLE
	 *
	 * @param view
	 * @param animResId 动画资源
	 */
	public static void showView(Context context, View view, int animResId) {
		if (context == null || view == null
				|| view.getVisibility() == View.VISIBLE) {
			return;
		}
		view.clearAnimation();
		Animation anim = AnimationUtils.loadAnimation(context,
				android.R.anim.fade_in);
		view.setVisibility(View.VISIBLE);
		if (anim != null) {
			view.startAnimation(anim);
		}

	}


	/**
	 * 展开动画
	 *
	 * @param view
	 * @param start
	 * @param end
	 * @return
	 */
	public static ValueAnimator ExpandAnimation(final View view, int start,
												final int end) {
		ValueAnimator animator = ValueAnimator.ofInt(start, end);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator arg0) {
				// TODO Auto-generated method stub
				//通过更改height也可以，但是有些os不行，还不知道什么原因
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
				params.bottomMargin = (Integer) arg0.getAnimatedValue() - end;
				view.requestLayout();
			}
		});
		animator.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub
				view.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub

			}
		});
		return animator;
	}

	/**
	 * 收起动画
	 *
	 * @param view
	 * @param start
	 * @param end
	 * @return
	 */
	public static ValueAnimator CollapseAnimation(final View view, final int start,
												  int end) {
		ValueAnimator animator = ValueAnimator.ofInt(start, end);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator arg0) {
				// TODO Auto-generated method stub
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
				params.bottomMargin = (Integer) arg0.getAnimatedValue() - start;
				view.requestLayout();
			}
		});
		animator.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				// TODO Auto-generated method stub
				view.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub

			}
		});
		return animator;
	}

	public static void expand(final View v) {
		v.setVisibility(View.VISIBLE);
		final ViewTreeObserver vto = v.getViewTreeObserver();
		final String str = "false";
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				if (!str.equals("false")) {
					final int targetHeight = v.getMeasuredHeight();
					Log.d("expand", "targetHeight=" + targetHeight);
					// Older versions of android (pre API 21) cancel animations for views with a height of 0.
					v.getLayoutParams().height = 1;
					Animation a = new Animation() {
						@Override
						protected void applyTransformation(float interpolatedTime, Transformation t) {
							v.getLayoutParams().height = (int) (targetHeight * interpolatedTime);
							Log.d("expand", "heigt=" + v.getLayoutParams().height + ",interpolatedTime=" + interpolatedTime);
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
					str.toUpperCase();
				}
				return true;
			}
		});
//		v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

	}

	public static void collapse(final View v) {
		final int initialHeight = v.getMeasuredHeight();
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


	public static void expand(final View v, int duration, int targetHeight) {
		v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);
		ValueAnimator valueAnimator = ValueAnimator.ofInt(0, targetHeight);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				v.getLayoutParams().height = (Integer) animation.getAnimatedValue();
				v.requestLayout();
			}
		});
		valueAnimator.setInterpolator(new DecelerateInterpolator());
		valueAnimator.setDuration(duration);
		valueAnimator.start();
	}

	public static void collapse(final View v, int duration, int targetHeight) {
		ValueAnimator valueAnimator = ValueAnimator.ofInt(0, targetHeight);
		valueAnimator.setInterpolator(new DecelerateInterpolator());
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				v.getLayoutParams().height = (Integer) animation.getAnimatedValue();
				v.requestLayout();
			}
		});
		valueAnimator.setInterpolator(new DecelerateInterpolator());
		valueAnimator.setDuration(duration);
		valueAnimator.start();
	}
}
