package org.gxz.mydemo.pie;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class PanelPieChartLabel extends View {

	private int mBackgroundColor = Color.TRANSPARENT;
	private float mStartAngle = 0;
	private int mBlankColor = 0XA0FFFFFF;
	private int[] mColors;
	private float[] mAngles;
	private float mTextSize = 12;
	private int mTextColor = Color.BLACK;
	private float mPadding;

	public PanelPieChartLabel(Context context) {
		super(context);

		// 解决4.1版本 以下canvas.drawTextOnPath()不显示问题
		this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}

	public PanelPieChartLabel(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}

	public PanelPieChartLabel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}

	public void setBackgroundColor(int color) {
		mBackgroundColor = color;
	}

	public void setStartAngle(float angle) {
		mStartAngle = angle;
	}

	public void setBlankColor(int color) {
		mBlankColor = color;
	}

	public void setTextParam(float size, int color) {
		mTextColor = color;
		mTextSize = size;
	}

	public void setPercentage(int[] colors, float[] angles) {
		mColors = colors;
		mAngles = angles;
	}
	
	public void setPadding(float padding){
		this.mPadding=padding;
	}

	public void onDraw(Canvas canvas) {
		// 画布背景
		canvas.drawColor(mBackgroundColor);

		// 画笔初始化
		Paint PaintArc = new Paint();
		PaintArc.setColor(mBlankColor);
		PaintArc.setAntiAlias(true);

		Paint PaintText = new Paint();
		PaintText.setColor(mTextColor);
		PaintText.setStyle(Style.STROKE);
		PaintText.setTextSize(mTextSize);

		float cirX = getWidth() / 2;
		float cirY = getHeight() / 2;

		float radius = 0;
		if (getWidth() > getHeight()) {
			radius = getHeight() / 2 - mPadding;
		} else {
			radius = getWidth() / 2 - mPadding;
		}
		// 先画个圆确定下显示位置
		canvas.drawCircle(cirX, cirY, radius, PaintArc);
		float arcLeft = cirX - radius;
		float arcTop = cirY - radius;
		float arcRight = cirX + radius;
		float arcBottom = cirY + radius;
		RectF arcRF0 = new RectF(arcLeft, arcTop, arcRight, arcBottom);
		// 位置计算类
		PieChartCalc piecalc = new PieChartCalc();
		// 实际用于计算的半径
		float calcRadius = radius / 2;

		Paint[] mPaints = null;
		float startAngle = mStartAngle;
		if (mAngles != null && mAngles.length > 0) {
			if (isLargeThan360(mAngles)) {
				return;
			}
			if (mColors != null && mColors.length > 0) {
				mPaints = new Paint[mColors.length];
				for (int i = 0; i < mPaints.length; i++) {
					Paint paint = new Paint();
					paint.setColor(mColors[i]);
					paint.setStyle(Style.FILL);
					paint.setAntiAlias(true);
					mPaints[i] = paint;
				}
			}
			for (int i = 0; i < mAngles.length; i++) {
				Paint paint = PaintArc;
				if (mPaints != null && i < mPaints.length) {
					paint = mPaints[i];
				}
				// 填充扇形
				canvas.drawArc(arcRF0, startAngle, mAngles[i], true, paint);
				piecalc.CalcArcEndPointXY(cirX, cirY, calcRadius, startAngle
						+ mAngles[i] / 2);
				 canvas.drawText(getPercentage(mAngles[i]), piecalc.getPosX(),
				 piecalc.getPosY(), PaintText);
				startAngle += mAngles[i];
			}
		} else {
			return;
		}
	}

	private boolean isLargeThan360(float[] angles) {
		float total = 0;
		for (int i = 0; i < angles.length; i++) {
			total += angles[i];
		}
		if (total > 360) {
			return true;
		} else {
			return false;
		}
	}

	private String getPercentage(float angle) {
		String percent = "";
		percent = Float.valueOf((int) (angle * 10000 / 360)) / 100 + "%";
		return percent;
	}
	
	
	private class PieChartCalc {
	     
		 
	    //Position位置
	    private float posX = 0.0f;
	    private float posY = 0.0f;
	     
	    public PieChartCalc()
	    {
	         
	    }
	             
	     
	    //依圆心坐标，半径，扇形角度，计算出扇形终射线与圆弧交叉点的xy坐标
	    public void CalcArcEndPointXY(float cirX, float cirY, float radius, float cirAngle){
	 
	        //将角度转换为弧度      
	        float arcAngle = (float) (Math.PI * cirAngle / 180.0);
	        if (cirAngle < 90)
	        {
	            posX = cirX + (float)(Math.cos(arcAngle)) * radius;
	            posY = cirY + (float)(Math.sin(arcAngle)) * radius;
	        }
	        else if (cirAngle == 90)
	        {
	            posX = cirX;
	            posY = cirY + radius;
	        }
	        else if (cirAngle > 90 && cirAngle < 180)
	        {
	            arcAngle = (float) (Math.PI * (180 - cirAngle) / 180.0);
	            posX = cirX - (float)(Math.cos(arcAngle)) * radius;
	            posY = cirY + (float)(Math.sin(arcAngle)) * radius;
	        }
	        else if (cirAngle == 180)
	        {
	            posX = cirX - radius;
	            posY = cirY;
	        }
	        else if (cirAngle > 180 && cirAngle < 270)
	        {
	            arcAngle = (float) (Math.PI * (cirAngle - 180) / 180.0);
	            posX = cirX - (float)(Math.cos(arcAngle)) * radius;
	            posY = cirY - (float)(Math.sin(arcAngle)) * radius;
	        }
	        else if (cirAngle == 270)
	        {
	            posX = cirX;
	            posY = cirY - radius;
	        }
	        else
	        {
	            arcAngle = (float) (Math.PI * (360 - cirAngle) / 180.0);
	            posX = cirX + (float)(Math.cos(arcAngle)) * radius;
	            posY = cirY - (float)(Math.sin(arcAngle)) * radius;
	        }
	                 
	    }
	 
	    public float getPosX() {
	        return posX;
	    }
	 
	 
	    public float getPosY() {
	        return posY;
	    }   
	}
}