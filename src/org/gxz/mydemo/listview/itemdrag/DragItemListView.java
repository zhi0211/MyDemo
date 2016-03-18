package org.gxz.mydemo.listview.itemdrag;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class DragItemListView extends ListView {

	
	/**
	 * 被拖拽的项，其实就是一个ImageView
	 */
    private ImageView dragImageView;
    /**
     * 手指拖动项原始在列表中的位置
     */
    private float dragSrcPosition;
    /**
     * 手指拖动的时候，当前拖动项在列表中的位置
     */
    private float dragPosition;
    /**
     * 在当前数据项中的位置
     */
    private float dragPoint;
    /**
     * 当前视图和屏幕的距离(这里只使用了y方向上)
     */
    private float dragOffset;
	private Context ctx;

	private float downX, downY, moveX, moveY, upX, upY;

	private WindowManager windowManager;

	private WindowManager.LayoutParams windowParams;

	private View dragView;

	public DragItemListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.ctx = context;
		windowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
	}

	public float getDownX() {
		return downX;
	}

	public float getMoveX() {
		return moveX;
	}

	public float getUpX() {
		return upX;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = ev.getRawX();
			downY = ev.getRawY();
			 int x = (int)ev.getX();
	            int y = (int)ev.getY();
	            dragSrcPosition = dragPosition = pointToPosition(x, y);
	            if(dragPosition==AdapterView.INVALID_POSITION){
	                return super.onInterceptTouchEvent(ev);
	            }

//	            ViewGroup itemView = (ViewGroup) getChildAt(dragPosition-getFirstVisiblePosition());
//	            dragPoint = y - itemView.getTop();
//	            dragOffset = (int) (ev.getRawY() - y);
//	            View dragger = itemView.findViewById(R.id.drag_list_item_image);
//	            if(dragger!=null&&x>dragger.getLeft()-20){
	                //
//	                upScrollBounce = Math.min(y-scaledTouchSlop, getHeight()/3);
//	                downScrollBounce = Math.max(y+scaledTouchSlop, getHeight()*2/3);
	                
//	                itemView.setDrawingCacheEnabled(true);
//	                Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache());
//	                startDrag(bm, y);
			break;

		default:
			break;
		}

		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:

			break;

		case MotionEvent.ACTION_MOVE:
			moveX = ev.getRawX();
			moveY = ev.getRawY();
			break;
		case MotionEvent.ACTION_UP:
			upX = ev.getRawX();
			upY = ev.getRawY();
			stopDrag();
			break;
		case MotionEvent.ACTION_CANCEL:
			upX = ev.getRawX();
			upY = ev.getRawY();
			stopDrag();
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 初始化dragView
	 */
	private void startDrag() {

		stopDrag();

		WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
		windowParams.gravity = Gravity.TOP;
		windowParams.x = 0;
		windowParams.y = 0;
		windowParams.alpha = 0.1f;
		windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		windowParams.format = PixelFormat.TRANSLUCENT;
		windowParams.windowAnimations = 0;

		View view = new View(ctx);
		view.setBackgroundColor(Color.BLUE);
		windowManager.addView(view, windowParams);
		dragView = view;
	}

	/**
	 * 停止拖动，去除拖动项的头像
	 */
	public void stopDrag() {
		if (dragView != null) {
			windowManager.removeView(dragView);
			dragView = null;
		}
	}
}
