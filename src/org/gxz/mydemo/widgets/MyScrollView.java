package org.gxz.mydemo.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 可锁的scrollView
 */
public class MyScrollView extends ScrollView {

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			if (!isScrollable) {
				return false;
			}
		}

		return super.onInterceptTouchEvent(ev);
	}

	/**
	 * 是否可滚动，true为可滚动，false为不可滚动
	 */
	private boolean isScrollable=true;

	/**
	 * 设置为可滚动
	 */
	public void setScroll() {
		isScrollable = true;
	}

	/**
	 * 设置为不可滚动
	 */
	public void setUnScroll() {
		isScrollable = false;
	}

}
