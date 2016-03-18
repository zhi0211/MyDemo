package org.gxz.mydemo.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 可拖动listview
 */
public class DragListView extends ListView {

	/**
	 * 被拖拽的项，其实就是一个ImageView
	 */
	private ImageView dragImageView;
	/**
	 * 手指拖动的时候，当前拖动项在列表中的位置
	 */
	private int dragPosition;
	/**
	 * 在当前数据项中的位置
	 */
	private float dragPoint;
	/**
	 * 当前视图和屏幕的距离(这里只使用了y方向上)
	 */
	private float dragOffset;

	/**
	 * windows窗口控制类
	 */
	private WindowManager windowManager;
	/**
	 * 用于控制拖拽项的显示的参数
	 */
	private WindowManager.LayoutParams windowParams;

	/**
	 * 按下时的item
	 */
	private LinearLayout item;

	/**
	 * 按下时的rawX
	 */
	private int downX;
	/**
	 * 抬起或取消时的rawX
	 */
	private int UpX;
	/**
	 * 移动时的rawX
	 */
	private int moveX;

	/**
	 * 滚动显示和隐藏menu时，手指滑动需要达到的速度。
	 */
	private static final int SNAP_VELOCITY = 300;

	/**
	 * menu出现，消失的速度
	 */
	private static final int MENU_VELCITY = 50;
	/**
	 * 屏幕宽度值。
	 */
	private int screenWidth;

	/**
	 * content最多可以滑动到的左边缘。值由menu布局的宽度来定，marginLeft到达此值之后，不能再减少。
	 */
	private int contentLeftEdge;
	/**
	 * content最多可以滑动到的右边缘,即marginLeft到达右边缘之后，不能增加。
	 */
	private int contentRightEdge;
	/**
	 * menu的宽度
	 */
	private int menuWidth = 360;

	/**
	 * 主内容的布局。
	 */
	private LinearLayout content;

	/**
	 * menu的布局。
	 */
	private LinearLayout menu;

	/**
	 * content布局的参数，通过此参数来更改leftMargin的值。
	 */
	private LinearLayout.LayoutParams contentParams;

	/**
	 * menu当前是显示还是隐藏。只有完全显示或隐藏menu时才会更改此值，滑动过程中此值无效。
	 */
	private boolean isMenuVisible = false;

	/**
	 * 用于计算手指滑动的速度。
	 */
	private VelocityTracker mVelocityTracker;
	/**
	 * menu显示时候，指向相应的content
	 */
	private LinearLayout viewIndex;
	private Context ctx;
	/**
	 * 自身
	 */
	private DragListView mDragListView = this;
	/**
	 * 全局手势，主要用于click事件
	 */
	private GestureDetector detector;
	/**
	 * 全局手势监听
	 */
	private itemOnGestureListener listener;
	/**
	 * 用于item的锁，使click与move不能同时触发
	 */
	private int itemLockIndex = -1;

	private onDragListItemClickListener itemListener;

	/**
	 * 回调接口
	 */
	public interface onDragListItemClickListener {
		/**
		 * item的click事件
		 */
		public void onContentClickListener(Object o);
	}

	public DragListView(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		this.ctx = ctx;
		windowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
		listener = new itemOnGestureListener();
		detector = new GestureDetector(listener);
	}

	/**
	 * 
	 * @param width
	 *            menu宽度
	 */
	public void initMenuWidth(int width) {
		this.menuWidth = width;
	}

	public void setDragItemListener(onDragListItemClickListener itemListener) {
		this.itemListener = itemListener;
	}

	/**
	 * menu下item的onTouch监听器
	 */
	private class menuOnTouch implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touchScrollToMenu();
				break;
			default:
				break;
			}
			return false;
		}

	}

	/**
	 * 设置menu下item的onTouch事件
	 */
	private void setMenuListener() {
		for (int i = 0; i < menu.getChildCount(); i++) {
			menu.getChildAt(i).setOnTouchListener(new menuOnTouch());
		}
	}

	/**
	 * 拦截touch事件，第一层
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			downX = (int) ev.getRawX();
			dragPosition = pointToPosition(x, y);
			itemLockIndex = -1;
			item = (LinearLayout) getChildAt(dragPosition - getFirstVisiblePosition());
			if (item == null) {
				break;
			}
			if (viewIndex != null) {
				content = viewIndex;
				setMenuListener();
			} else {
				initValues();
			}
			dragPoint = y - item.getTop();
			dragOffset = (int) (ev.getRawY() - y);
			item.setDrawingCacheEnabled(true);
			Bitmap bm = Bitmap.createBitmap(item.getDrawingCache());
			startDrag(bm, y);
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	/**
	 * 外层scrollView
	 */
	private MyScrollView mScrollView;

	/**
	 * 设置外层scrollView
	 * 
	 * @param scrollview
	 */
	public void setScrollView(MyScrollView scrollview) {
		this.mScrollView = scrollview;
	}

	/**
	 * 初始化被选中item
	 */
	private void initValues() {
		if (item.getChildCount() != 2) {
			return;
		}
		content = (LinearLayout) item.getChildAt(0);
		menu = (LinearLayout) item.getChildAt(1);
		screenWidth = windowManager.getDefaultDisplay().getWidth();
		contentParams = (LinearLayout.LayoutParams) content.getLayoutParams();
		contentParams.width = screenWidth;
		// 左边缘的值赋值为menu宽度的负数
		contentLeftEdge = -menuWidth;
		contentRightEdge = 0;
		// content的leftMargin设置为右边缘的值，这样初始化时menu就变为不可见
		contentParams.leftMargin = 0;
		// 将content的宽度设置为屏幕宽度
		menu.getLayoutParams().width = menuWidth;

	}

	/**
	 * 触摸事件与抬起事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (dragImageView != null) {
			createVelocityTracker(ev);
			int action = ev.getAction();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				stopDrag();
				UpX = (int) ev.getRawX();
				if (isMenuVisible) {
					scrollToContent();
					recycleVelocityTracker();
					return true;
				}
				// 手指抬起时，进行判断当前手势的意图，从而决定是滚动到menu界面，还是滚动到content界面
				if (wantToShowMenu()) {
					if (shouldScrollToMenu()) {
						scrollToMenu();
					} else {
						scrollToContent();
					}
				} else if (wantToShowContent()) {
					if (shouldScrollToContent()) {
						scrollToContent();
					} else {
						scrollToMenu();
					}
				} else {
					scrollToContent();
				}
				recycleVelocityTracker();
				break;
			case MotionEvent.ACTION_MOVE:
				float moveY = ev.getY();
				onDrag(moveY);
				moveX = (int) ev.getRawX();
				int distanceX = moveX - downX;
				if (isMenuVisible) {
					contentParams.leftMargin = contentLeftEdge + distanceX;
				} else if (!isMenuVisible && distanceX < -20) {
					lockScroll();
					itemLockIndex = dragPosition;
					contentParams.leftMargin = contentRightEdge + distanceX + 20;

				}
				if (contentParams.leftMargin < contentLeftEdge) {
					contentParams.leftMargin = contentLeftEdge;
				} else if (contentParams.leftMargin > contentRightEdge) {
					contentParams.leftMargin = contentRightEdge;
				}
				content.setLayoutParams(contentParams);
				return true;
			default:
				break;
			}
		}
		// 传递手势event
		listener.setView(dragPosition);
		detector.onTouchEvent(ev);
		return super.onTouchEvent(ev);
	}

	/**
	 * 锁住不让滚动
	 */
	private void lockScroll() {
		if (mScrollView != null)
			mScrollView.setUnScroll();
	}

	/**
	 * 接触滚动锁
	 */
	private void clearLockScroll() {
		if (mScrollView != null)
			mScrollView.setScroll();
	}

	/**
	 * 判断当前手势的意图是不是想显示content。如果手指移动的距离是负数，且当前menu是可见的，则认为当前手势是想要显示content。
	 * 
	 * @return 当前手势想显示content返回true，否则返回false。
	 */
	private boolean wantToShowContent() {
		return UpX - downX > 0 && isMenuVisible;
	}

	/**
	 * 判断当前手势的意图是不是想显示menu。如果手指移动的距离是正数，且当前menu是不可见的，则认为当前手势是想要显示menu。
	 * 
	 * @return 当前手势想显示menu返回true，否则返回false。
	 */
	private boolean wantToShowMenu() {
		return UpX - downX < 0 && !isMenuVisible;
	}

	/**
	 * 判断是否应该滚动将menu展示出来。如果手指移动距离大于屏幕的1/2，或者手指移动速度大于SNAP_VELOCITY，
	 * 就认为应该滚动将menu展示出来。
	 * 
	 * @return 如果应该滚动将menu展示出来返回true，否则返回false。
	 */
	private boolean shouldScrollToMenu() {
		return downX - UpX > menuWidth / 2 || getScrollVelocity() < -SNAP_VELOCITY;
	}

	/**
	 * 判断是否应该滚动将content展示出来。如果手指移动距离加上menuPadding大于屏幕的1/2，
	 * 或者手指移动速度大于SNAP_VELOCITY， 就认为应该滚动将content展示出来。
	 * 
	 * @return 如果应该滚动将content展示出来返回true，否则返回false。
	 */
	private boolean shouldScrollToContent() {
		return UpX - downX + screenWidth - menuWidth > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * 将屏幕滚动到menu界面，滚动速度设定为-MENU_VELCITY.
	 */
	public void scrollToMenu() {
		stopDrag();
		new ScrollTask().execute(-MENU_VELCITY);
	}

	/**
	 * 将屏幕滚动到content界面，滚动速度设定为MENU_VELCITY.
	 */
	public void scrollToContent() {
		stopDrag();
		new ScrollTask().execute(MENU_VELCITY);
	}

	/**
	 * 选中项，弹出content，隐藏menu
	 */
	public void touchScrollToContent() {
		stopDrag();
		new ScrollTask().execute(1000);
	}

	/**
	 * 选中项，弹出menu
	 */
	public void touchScrollToMenu() {
		stopDrag();
		new ScrollTask().execute(-1000);
	}

	/**
	 * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
	 * 
	 * @param event
	 *            content界面的滑动事件
	 */
	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	/**
	 * 获取手指在content界面滑动的速度。
	 * 
	 * @return 滑动速度，以每秒钟移动了多少像素值为单位。
	 */
	private int getScrollVelocity() {
		if (mVelocityTracker != null) {
			mVelocityTracker.computeCurrentVelocity(1000);
			int velocityX = (int) mVelocityTracker.getXVelocity();
			int velocityY = (int) mVelocityTracker.getYVelocity();
			if (Math.abs(velocityX) > Math.abs(velocityY))
				return velocityX;
		}
		return 0;
	}

	/**
	 * 回收VelocityTracker对象。
	 */
	private void recycleVelocityTracker() {
		if (mVelocityTracker != null) {
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
	}

	/**
	 * 滚动任务
	 */
	private class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... speed) {
			int leftMargin = contentParams.leftMargin;
			// 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
			while (true) {
				leftMargin = leftMargin + speed[0];
				if (leftMargin > contentRightEdge) {
					leftMargin = contentRightEdge;
					break;
				}
				if (leftMargin < contentLeftEdge) {
					leftMargin = contentLeftEdge;
					break;
				}
				publishProgress(leftMargin);
				// 为了要有滚动效果产生，每次循环使线程睡眠20毫秒，这样肉眼才能够看到滚动动画。
				sleep(20);
			}
			if (speed[0] < 0) {
				isMenuVisible = true;
				lockScroll();
				viewIndex = content;
			} else {
				isMenuVisible = false;
				clearLockScroll();
				viewIndex = null;
			}
			return leftMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... leftMargin) {
			contentParams.leftMargin = leftMargin[0];
			content.setLayoutParams(contentParams);
		}

		@Override
		protected void onPostExecute(Integer leftMargin) {
			contentParams.leftMargin = leftMargin;
			content.setLayoutParams(contentParams);
			itemLockIndex = -1;
		}
	}

	/**
	 * 使当前线程睡眠指定的毫秒数。
	 * 
	 * @param millis
	 *            指定当前线程睡眠多久，以毫秒为单位
	 */
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 准备拖动，初始化拖动项的图像
	 * 
	 * @param bm
	 * @param y
	 */
	public void startDrag(Bitmap bm, int y) {
		stopDrag();
		windowParams = new WindowManager.LayoutParams();
		windowParams.gravity = Gravity.TOP;
		windowParams.x = 0;
		windowParams.alpha = 0f;
		windowParams.y = (int) (y - dragPoint + dragOffset);
		windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		windowParams.format = PixelFormat.TRANSLUCENT;
		windowParams.windowAnimations = 0;

		ImageView imageView = new ImageView(getContext());
		imageView.setImageBitmap(bm);
		windowManager = (WindowManager) getContext().getSystemService("window");
		windowManager.addView(imageView, windowParams);
		dragImageView = imageView;
	}

	/**
	 * 停止拖动，去除拖动的图像
	 */
	public void stopDrag() {
		if (dragImageView != null) {
			windowManager.removeView(dragImageView);
			dragImageView = null;
		}
	}

	/**
	 * 拖动执行，在Move方法中执行
	 * 
	 * @param y
	 */
	public void onDrag(float y) {
		if (dragImageView != null) {
			windowParams.y = (int) (y - dragPoint + dragOffset);
			windowManager.updateViewLayout(dragImageView, windowParams);
		}
	}

	/**
	 * item手势监听.
	 */
	private class itemOnGestureListener implements OnGestureListener {

		private int pos;

		/**
		 * 
		 * @param view
		 *            主容器
		 * @param pos
		 *            位置
		 */
		public void setView(int pos) {
			this.pos = pos;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {

		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			switch (e.getAction()) {
			case MotionEvent.ACTION_UP:
				if (itemLockIndex == -1 && viewIndex == null && pos != -1) {
					if (itemListener != null)
						itemListener.onContentClickListener((mDragListView.getAdapter()).getItem(pos));
				}
				break;
			default:
				break;
			}
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {

		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			return false;
		}

	}

	// /**
	// * 弹回menu任务
	// */
	// private class clickScrollTask extends AsyncTask<Integer, Integer,
	// Integer> {
	//
	// private LinearLayout.LayoutParams Lparams;
	//
	// public clickScrollTask(LinearLayout.LayoutParams Lparams) {
	// this.Lparams = Lparams;
	// }
	//
	// @Override
	// protected Integer doInBackground(Integer... speed) {
	// int leftMargin = Lparams.leftMargin;
	// // 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
	// while (true) {
	// leftMargin = leftMargin + speed[0];
	// if (leftMargin > contentRightEdge) {
	// leftMargin = contentRightEdge;
	// break;
	// }
	// if (leftMargin < contentLeftEdge) {
	// leftMargin = contentLeftEdge;
	// break;
	// }
	// publishProgress(leftMargin);
	// }
	// if (speed[0] < 0) {
	// isMenuVisible = true;
	// lockScroll();
	// viewIndex=content;
	// } else {
	// isMenuVisible = false;
	// clearLockScroll();
	// }
	// return leftMargin;
	// }
	//
	// @Override
	// protected void onProgressUpdate(Integer... leftMargin) {
	// Lparams.leftMargin = leftMargin[0];
	// viewIndex.setLayoutParams(Lparams);
	// }
	//
	// @Override
	// protected void onPostExecute(Integer leftMargin) {
	// Lparams.leftMargin = leftMargin;
	// viewIndex.setLayoutParams(Lparams);
	// if(!isMenuVisible)
	// viewIndex = null;
	// stopDrag();
	// itemLockIndex = -1;
	// }
	// }

}
