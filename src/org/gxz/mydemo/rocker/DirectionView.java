package org.gxz.mydemo.rocker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import org.gxz.mydemo.utils.DensityUtil;

/**
 * Created by gxz on 2015/9/1.
 */
public class DirectionView extends View {

	private static final int CONTROL_RADIUS_POINTS_OF_WIDTH = 3;
	private static final int VIEW_CIRCLE_BG = 0x8C000000;
	private static final int VIEW_EDGE_BG = 0X99ffffff;
	private static final int POINT_CIRCLE_BG = 0xffffffff;
	private static final int POINT_EDGE_BG = 0x80ffffff;
	private static final int POINT_CIRCLE_PRESSED_BG = 0xff2582b7;


	//圆心
	private int width = 0;
	private int height = 0;
	private int centerX = 0;
	private int centerY = 0;
	private int viewRadius = 0;
	private int pointRadius = 0;
	private int viewEdgeWidth = 0;
	private int pointEdgeWidth = 0;
	private int pointCenterX = 0;
	private int pointCenterY = 0;
	private int padding = 0;

	private boolean mIsPointDown = false;
	private Boolean mIsClick = null;
	private Direction mDirection = null;

	private OnDirectionListener mOnDirectionListener;


	public interface OnDirectionListener {

		void onDirectionMoved(DirectionView.Direction direction);

		void onPointClick();

		void onTouch(MotionEvent event);
	}


	public void setOnDirectionListener(OnDirectionListener listener) {
		this.mOnDirectionListener = listener;
	}

	private void onDirectionCallback(Direction direction) {
		if (mOnDirectionListener != null) {
			mOnDirectionListener.onDirectionMoved(direction);
		}
	}

	private void onClickCallback() {
		if (mOnDirectionListener != null) {
			mOnDirectionListener.onPointClick();
		}
	}

	private void onTouchCallback(MotionEvent event) {
		if (mOnDirectionListener != null) {
			mOnDirectionListener.onTouch(event);
		}
	}


	public DirectionView(Context context) {
		super(context);
	}

	public DirectionView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DirectionView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
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
//		padding = DensityUtil.dip2px(5);
		width = w - padding * 2;
		height = h - padding * 2;
		centerX = w / 2;
		centerY = h / 2;
		viewRadius = w / 2;
		pointRadius = viewRadius / CONTROL_RADIUS_POINTS_OF_WIDTH;
		pointCenterX = centerX;
		pointCenterY = centerY;
		viewEdgeWidth = DensityUtil.dip2px(3);
		pointEdgeWidth = DensityUtil.dip2px(3);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//设置透明度
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(VIEW_CIRCLE_BG);
		//绘制摇杆背景
		canvas.drawCircle(centerX, centerY, viewRadius - viewEdgeWidth, paint);
		if (mIsPointDown) {
			paint.setColor(POINT_CIRCLE_PRESSED_BG);
		} else {
			paint.setColor(POINT_CIRCLE_BG);
		}
		canvas.drawCircle(pointCenterX, pointCenterY,
				pointRadius - pointEdgeWidth / 2, paint);
		//绘制圆环
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(VIEW_EDGE_BG);
		paint.setStrokeWidth(viewEdgeWidth);
		canvas.drawCircle(centerX, centerY, viewRadius - viewEdgeWidth / 2, paint);

		paint.setColor(POINT_EDGE_BG);
		paint.setStrokeWidth(pointEdgeWidth);
		canvas.drawCircle(pointCenterX, pointCenterY, pointRadius, paint);


		//绘制摇杆

//		paint.setColor(0xffffffff);
//		canvas.drawCircle(centerX - viewRadius, centerY, 5, paint);//左边
//		canvas.drawCircle(centerX + viewRadius, centerY, 5, paint);//右边
//		canvas.drawCircle(centerX, centerY - viewRadius, 5, paint);//上边
//		canvas.drawCircle(centerX, centerY + viewRadius, 5, paint);//下边
	}

	/**
	 * Tries to claim the user's drag motion, and requests disallowing any
	 * ancestors from stealing events in the drag.
	 */
	private ViewParent mParent;

	private void attemptClaimDrag() {
		mParent = getParent();
		if (mParent != null) {
			mParent.requestDisallowInterceptTouchEvent(true);
		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				onTouchCallback(event);
				mIsPointDown = isMoved(x, y);
				if (mIsPointDown) {
					mIsClick = true;
					attemptClaimDrag();
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (mIsPointDown) {
					moved(x, y);
				}
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				onTouchCallback(event);
				mIsPointDown = false;
				if (mIsClick != null && mIsClick) {
					onClickCallback();
				}
				moved(Direction.center);
				mIsClick = null;
				mDirection = null;
				break;

		}

		return true;
	}

	/**
	 * 移动
	 *
	 * @param x
	 * @param y
	 */
	public void moved(float x, float y) {
		double l = Math.sqrt(Math.pow(Math.abs(x - centerX), 2) + Math.pow(Math.abs(y - centerY), 2));
		if (l > pointRadius) {//大于半径时候移动
			Direction direction = getDirection(x, y);
			moved(direction);
		} else {
			pointCenterX = (int) x;
			pointCenterY = (int) y;
			invalidate();
		}
	}

	public Direction getDirection(float x, float y) {
		if (x == centerX && y == centerY) {
			return Direction.center;
		} else if (x == centerX && y > centerY) {
			return Direction.down;
		} else if (x == centerX && y < centerY) {
			return Direction.up;
		} else if (y == centerY && x < centerX) {
			return Direction.left;
		} else if (y == centerY && x > centerX) {
			return Direction.right;
		} else {
			//计算角度[0,90]
			double degree = Math.abs(Math.toDegrees(Math.atan(Math.abs(y - centerY) / Math.abs(x - centerX))));
			if (x > centerX && y <= centerY) {//第一象限
				if (degree > 45) {//角度大于45
					return Direction.up;
				} else {
					return Direction.right;
				}
			} else if (x <= centerX && y < centerY) {//第二象限
				if (degree >= 45) {//角度大于45
					return Direction.up;
				} else {
					return Direction.left;
				}
			} else if (x < centerX && y >= centerY) {//第三象限
				if (degree > 45) {//角度大于45
					return Direction.down;
				} else {
					return Direction.left;
				}
			} else if (x >= centerX && y > centerY) {//第四象限
				if (degree >= 45) {//角度大于45
					return Direction.down;
				} else {
					return Direction.right;
				}
			}
		}
		return null;
	}

	public enum Direction {
		up, down, left, right, center
	}

	public void moved(Direction direction) {
		switch (direction) {
			case up:
				pointCenterX = centerX;
				pointCenterY = pointRadius;
				break;
			case down:
				pointCenterX = centerX;
				pointCenterY = height - pointRadius;
				break;
			case left:
				pointCenterY = centerY;
				pointCenterX = pointRadius;
				break;
			case right:
				pointCenterY = centerY;
				pointCenterX = width - pointRadius;
				break;
			case center:
				pointCenterX = centerX;
				pointCenterY = centerY;
				break;
			default:
				break;
		}
		if (direction != null) {
			mIsClick = false;
			invalidate();
			if (mDirection != direction) {
				mDirection = direction;
				onDirectionCallback(direction);
			}
		}
	}

	/**
	 * 是否移动
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isMoved(float x, float y) {
		double l = Math.sqrt(Math.pow(Math.abs(x - centerX), 2) + Math.pow(Math.abs(y - centerY), 2));
		//点中操作点
		if (l < pointRadius) {
			return true;
		} else {
			return false;
		}
	}


}


