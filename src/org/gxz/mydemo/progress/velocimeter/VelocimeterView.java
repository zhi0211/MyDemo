package org.gxz.mydemo.progress.velocimeter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import org.gxz.mydemo.R;

/**
 * @author Adrián García Lomas
 */
public class VelocimeterView extends View {

	private ValueAnimator progressValueAnimator;
	private Interpolator interpolator = new AccelerateDecelerateInterpolator();
	private InternalVelocimeterPainter internalVelocimeterPainter;
	private ProgressVelocimeterPainter progressVelocimeterPainter;
	private Digital digitalPainter;
	private Digital digitalBlurPainter;
	private int min = 0;
	private float progressLastValue = min;
	private int max = 100;
	private float value;
	private int duration = 1000;
	private long progressDelay = 350;
	private int margin = 15;
	private int digitalSize = 65;
	private int insideProgressColor = Color.parseColor("#094e35");
	private int externalProgressColor = Color.parseColor("#9cfa1d");
	private int digitalNumberColor = Color.GREEN;
	private int digitalNumberBlurColor = Color.GREEN;
	private String units = "";

	public VelocimeterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public VelocimeterView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int size;
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();

		if (width > height) {
			size = height;
		} else {
			size = width;
		}
		setMeasuredDimension(size, size);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		internalVelocimeterPainter.onSizeChanged(h, w);
		progressVelocimeterPainter.onSizeChanged(h, w);
		digitalPainter.onSizeChanged(h, w);
		digitalBlurPainter.onSizeChanged(h, w);
	}

	private void init(Context context, AttributeSet attributeSet) {
		TypedArray attributes = context.obtainStyledAttributes(attributeSet,
				R.styleable.VelocimeterView);
		initAttributes(attributes);

		int marginPixels = DimensionUtils.getSizeInPixels(margin, getContext());
		setLayerType(LAYER_TYPE_SOFTWARE, null);
		internalVelocimeterPainter = new InternalVelocimeterPainterImp(
				insideProgressColor, marginPixels, getContext());
		progressVelocimeterPainter = new ProgressVelocimeterPainterImp(
				externalProgressColor, max, marginPixels, getContext());
		initValueAnimator();

		digitalPainter = new DigitalImp(digitalNumberColor, getContext(),
				DimensionUtils.getSizeInPixels(margin, context), digitalSize,
				units);
		digitalBlurPainter = new DigitalBlurImp(digitalNumberBlurColor,
				getContext(), DimensionUtils.getSizeInPixels(margin, context),
				digitalSize, units);
	}

	private void initAttributes(TypedArray attributes) {
		insideProgressColor = attributes.getColor(
				R.styleable.VelocimeterView_inside_progress_color,
				insideProgressColor);
		externalProgressColor = attributes.getColor(
				R.styleable.VelocimeterView_external_progress_color,
				externalProgressColor);
		digitalNumberColor = attributes.getColor(
				R.styleable.VelocimeterView_digital_number_color,
				digitalNumberColor);
		digitalNumberBlurColor = attributes.getColor(
				R.styleable.VelocimeterView_digital_number_blur_color,
				digitalNumberBlurColor);
		digitalSize = (int) attributes.getDimension(
				R.styleable.VelocimeterView_digital_number_size, digitalSize);
		max = attributes.getInt(R.styleable.VelocimeterView_max, max);
		units = (String) attributes.getText(R.styleable.VelocimeterView_units);
		if (units == null) {
			units = "";
		}
		attributes.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		digitalBlurPainter.draw(canvas);
		digitalPainter.draw(canvas);
		internalVelocimeterPainter.draw(canvas);
		progressVelocimeterPainter.draw(canvas);
	}

	public void setValue(float value) {
		this.value = value;
		if (value <= max && value >= min) {
			animateProgressValue();
		}
	}

	public void setValue(float value, boolean animate) {
		this.value = value;
		if (value <= max && value >= min) {
			if (!animate) {
				updateValueProgress(value);
			} else {
				animateProgressValue();
			}
		}
	}

	private void initValueAnimator() {
		progressValueAnimator = new ValueAnimator();
		progressValueAnimator.setInterpolator(interpolator);
		progressValueAnimator
				.addUpdateListener(new ProgressAnimatorListenerImp());
	}

	private void animateProgressValue() {
		if (progressValueAnimator != null) {
			progressValueAnimator.setFloatValues(progressLastValue, value);
			progressValueAnimator.setDuration(duration + progressDelay);
			progressValueAnimator.start();
		}
	}

	public void setProgress(Interpolator interpolator) {
		this.interpolator = interpolator;

		if (progressValueAnimator != null) {
			progressValueAnimator.setInterpolator(interpolator);
		}
	}

	public float getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	private void updateValueProgress(float value) {
		progressVelocimeterPainter.setValue(value);
		digitalPainter.setValue(value);
		digitalBlurPainter.setValue(value);
	}

	private class ProgressAnimatorListenerImp implements
			ValueAnimator.AnimatorUpdateListener {
		@Override
		public void onAnimationUpdate(ValueAnimator valueAnimator) {
			Float value = (Float) valueAnimator.getAnimatedValue();
			updateValueProgress(value);
			progressLastValue = value;
		}
	}
}
