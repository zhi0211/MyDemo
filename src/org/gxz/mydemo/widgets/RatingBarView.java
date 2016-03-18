package org.gxz.mydemo.widgets;

import org.gxz.mydemo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 评星组件 包含星级别，柱状条，百分数
 */
public class RatingBarView extends LinearLayout {
	private int displayWidth;
	private int maxWidth;
	private int minWidth = 10;
	private TextView mBarChart;
	private TextView mRatio;
	private RatingBar mRatingBar;
	private int mRayoutId = R.layout.widgets_rating_bar;

	public RatingBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		displayWidth = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getWidth();
		maxWidth = displayWidth / 3;
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(mRayoutId, this, true);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mRatingBar = (RatingBar) findViewById(R.id.rating_bar);
		mRatio = (TextView) findViewById(R.id.rating_ratio);
		mBarChart = (TextView) findViewById(R.id.rating_barchart);
		mBarChart.setWidth(minWidth);
		mRatio.setText("0.0%");
	}

	public void setRatio(float ratio) {
		if (ratio <= 0) {
			ratio = 0;
		} else if (ratio > 100) {
			ratio = 100;
		}
		mRatio.setText(ratio + "%");
		int chartWidth = (int) (maxWidth * (ratio / 100.0)) + minWidth;
		mBarChart.setWidth(chartWidth);

	}

	public void setRating(float rating) {
		mRatingBar.setRating(rating);
	}

}
