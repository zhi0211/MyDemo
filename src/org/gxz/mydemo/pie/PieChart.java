/*
 * PieChart.java
 * Android-Charts
 *
 * Created by limc on 2011/05/29.
 *
 * Copyright 2011 limc.cn All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gxz.mydemo.pie;

import java.util.List;

import org.gxz.mydemo.utils.DensityUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

public class PieChart extends BaseChart {
	public static final String DEFAULT_TITLE = "Pie Chart";
	public static final boolean DEFAULT_DISPLAY_RADIUS = true;
	public static final int DEFAULT_RADIUS_LENGTH = 80;
	public static final int DEFAULT_RADIUS_COLOR = Color.WHITE;
	public static final int DEFAULT_CIRCLE_BORDER_COLOR = Color.WHITE;
	public static final Point DEFAULT_POSITION = new Point(0, 0);

	private List<TitleValueColorEntity> data;
	private String title = DEFAULT_TITLE;
	private Point position = DEFAULT_POSITION;
	private int radiusLength = DEFAULT_RADIUS_LENGTH;
	private int radiusColor = DEFAULT_RADIUS_COLOR;
	private int circleBorderColor = DEFAULT_CIRCLE_BORDER_COLOR;
	private boolean displayRadius = DEFAULT_DISPLAY_RADIUS;

	/*
	 * (non-Javadoc)
	 * 
	 * @param context
	 * 
	 * @see cn.limc.androidcharts.view.BaseChart#BaseChart(Context)
	 */
	public PieChart(Context context) {
		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param context
	 * 
	 * @param attrs
	 * 
	 * @param defStyle
	 * 
	 * @see cn.limc.androidcharts.view.BaseChart#BaseChart(Context,
	 * AttributeSet, int)
	 */
	public PieChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param context
	 * 
	 * @param attrs
	 * 
	 * @see cn.limc.androidcharts.view.BaseChart#BaseChart(Context,
	 * AttributeSet)
	 */
	public PieChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * <p>Called when is going to draw this chart</p>
	 * <p>銉併儯銉笺儓銈掓浉銇忓墠銆併儭銈姐儍銉夈倰鍛笺伓</p> <p>缁樺埗鍥捐〃鏃惰皟鐢/p>
	 * 
	 * @param canvas
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// get safe rect
		int rect = super.getWidth() > super.getHeight() ? super.getHeight()
				: super.getWidth();

		// calculate radius length
		radiusLength = (int) ((rect / 2f) * 0.90);

		// calculate position
		position = new Point((int) (getWidth() / 2f), (int) (getHeight() / 2f));

		// draw this chart
		drawCircle(canvas);

		// draw data on chart
		drawData(canvas);
	}

	protected void drawCircle(Canvas canvas) {

		Paint mPaintCircleBorder = new Paint();
		mPaintCircleBorder.setColor(Color.WHITE);
		mPaintCircleBorder.setStyle(Style.STROKE);
		mPaintCircleBorder.setStrokeWidth(2);
		mPaintCircleBorder.setAntiAlias(true);

		// draw a circle
		canvas.drawCircle(position.x, position.y, radiusLength,
				mPaintCircleBorder);
	}

	protected void drawData(Canvas canvas) {
		if (null != data) {
			// sum all data's value
			float sum = 0;
			for (int i = 0; i < data.size(); i++) {
				sum = sum + data.get(i).getValue();
			}

			Paint mPaintFill = new Paint();
			mPaintFill.setStyle(Style.FILL);
			mPaintFill.setAntiAlias(true);

			Paint mPaintBorder = new Paint();
			mPaintBorder.setStyle(Style.STROKE);
			mPaintBorder.setColor(radiusColor);
			mPaintBorder.setAntiAlias(true);

			int offset = -90;
			// draw arcs of every piece
			for (int j = 0; j < data.size(); j++) {
				TitleValueColorEntity e = data.get(j);

				// get color
				mPaintFill.setColor(e.getColor());

				RectF oval = new RectF(position.x - radiusLength, position.y
						- radiusLength, position.x + radiusLength, position.y
						+ radiusLength);
				int sweep = Math.round(e.getValue() / sum * 360f);
				canvas.drawArc(oval, offset, sweep, true, mPaintFill);
				canvas.drawArc(oval, offset, sweep, true, mPaintBorder);
				offset = offset + sweep;
			}

			float sumvalue = 0f;
			float temp = 270;
			for (TitleValueColorEntity e : data) {
				float value = e.getValue();
				sumvalue = sumvalue + value;
				float rate = (sumvalue - value / 2) / sum;
				mPaintFill.setColor(Color.BLUE);

				// percentage
				float percentage = (int) (value / sum * 10000) / 100f;

				float offsetX = (float) (position.x - radiusLength * 0.5
						* Math.sin(rate * -2 * Math.PI));
				float offsetY = (float) (position.y - radiusLength * 0.5
						* Math.cos(rate * -2 * Math.PI));

				Paint mPaintFont = new Paint();
				mPaintFont.setColor(Color.BLACK);
				DensityUtil utils = new DensityUtil(getContext());
				mPaintFont.setTextSize(utils.dip2px(15));
				mPaintFont.setAntiAlias(true);
				mPaintFont.setStrokeWidth(5);

				// draw titles
				String title = e.getTitle();
				float realx = 0;
				float realy = 0;

				// TODO title position
				if (offsetX < position.x) {
					realx = offsetX - mPaintFont.measureText(title) - 5;
				} else if (offsetX > position.x) {
					realx = offsetX + 5;
				} else if (offsetX == position.x) {
					realx = offsetX - (mPaintFont.measureText(title) / 2);
				}

				if (offsetY >= position.y) {
					if (value / sum < 0.2f) {
						realy = offsetY + 10;
					} else {
						realy = offsetY + 5;
					}
				} else if (offsetY < position.y) {
					if (value / sum < 0.2f) {
						realy = offsetY - 10;
					} else {
						realy = offsetY + 5;
					}
				}
				float sweepAngle = 360 * percentage/100 + temp;
//				Log.i("ssssssssssss", "=======start:"+temp+";"+sweepAngle+";title:"+title);
				Path path = getpath(temp, sweepAngle);
				if (percentage != 0.0f) {
					// canvas.drawText(title+String.valueOf(percentage) + "%",
					// realx, realy, mPaintFont);
					canvas.drawTextOnPath(title + String.valueOf(percentage)
							+ "%", path, 90, 10, mPaintFont);
					// canvas.drawText(String.valueOf(value), realx,
					// realy + utils.dip2px(15), mPaintFont);
					// canvas.drawText(String.valueOf(percentage) + "%", realx,
					// realy + utils.dip2px(15), mPaintFont);
				}
				temp = sweepAngle;
			}
		}
	}

	public Path getpath(float startAngle, float sweepAngle) {
		float l = (float) (((-(startAngle + sweepAngle) / 2) * Math.PI) / 180);

		// 中心点坐标
		float centerX = getWidth() / 2;
		float centerY = getHeight() / 2;

		// 初始位置
		float pointX = getWidth() / 2 + radiusLength;
		float pointY = getHeight() / 2;
		float newX = (float) ((pointX - centerX) * Math.cos(l)
				+ (pointY - centerY) * Math.sin(l) + centerX);
		float newY = (float) (-(pointX - centerX) * Math.sin(l)
				+ (pointY - centerY) * Math.cos(l) + centerY);
		Path path = new Path();
		path.moveTo(getWidth() / 2, getHeight() / 2);
		path.lineTo(newX, newY);
		return path;
	}

	/**
	 * @return the data
	 */
	public List<TitleValueColorEntity> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<TitleValueColorEntity> data) {
		this.data = data;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	/**
	 * @return the radiusLength
	 */
	public int getRadiusLength() {
		return radiusLength;
	}

	/**
	 * @param radiusLength
	 *            the radiusLength to set
	 */
	public void setRadiusLength(int radiusLength) {
		this.radiusLength = radiusLength;
	}

	/**
	 * @return the radiusColor
	 */
	public int getRadiusColor() {
		return radiusColor;
	}

	/**
	 * @param radiusColor
	 *            the radiusColor to set
	 */
	public void setRadiusColor(int radiusColor) {
		this.radiusColor = radiusColor;
	}

	/**
	 * @return the circleBorderColor
	 */
	public int getCircleBorderColor() {
		return circleBorderColor;
	}

	/**
	 * @param circleBorderColor
	 *            the circleBorderColor to set
	 */
	public void setCircleBorderColor(int circleBorderColor) {
		this.circleBorderColor = circleBorderColor;
	}

	/**
	 * @return the displayRadius
	 */
	public boolean isDisplayRadius() {
		return displayRadius;
	}

	/**
	 * @param displayRadius
	 *            the displayRadius to set
	 */
	public void setDisplayRadius(boolean displayRadius) {
		this.displayRadius = displayRadius;
	}
}