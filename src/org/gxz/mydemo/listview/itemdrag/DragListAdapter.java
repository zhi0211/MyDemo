package org.gxz.mydemo.listview.itemdrag;

import java.util.List;

import org.gxz.mydemo.R;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 上网设备列表适配器
 */
public class DragListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<HostRecord> mListData;
	private Activity mActivity;
	private int downIndex = -1;
	private View viewIndex;
	private GestureDetector detector;
	private itemOnGestureListener gesListener;
	private OnFunctionBtnListener mCallBack = null;
	private int count = 0;
	private int itemlockIndex = -1;
	private WindowManager window;
	private DragItemListView listview;
	private ViewParent mParent;

	public DragListAdapter(Activity activity, DragItemListView listview,List<HostRecord> list, OnFunctionBtnListener listener) {
		this.mInflater = activity.getLayoutInflater();
		this.mListData = list;
		this.mActivity = activity;
		this.listview=listview;
		mCallBack = listener;
		gesListener = new itemOnGestureListener();
		detector = new GestureDetector(gesListener);
		window=(WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
		// this.itemGesListener=itemGesListener;
	}

	interface OnFunctionBtnListener {
		public void onContentClick(HostRecord hr);

		public void onForeverBtnClick(HostRecord hr);

		public void onTempBtnClick(HostRecord hr);
	}

	@Override
	public int getCount() {
		return mListData.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mListData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public void refreshListData(List<HostRecord> list) {
		mListData = list;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_device_list2, null);
			holder = new ViewHolder();
			holder.content = (LinearLayout) convertView.findViewById(R.id.device_list_item_content);
			holder.left = (LinearLayout) convertView.findViewById(R.id.device_list_item_content_left);
			holder.image = (ImageView) convertView.findViewById(R.id.device_image);
			holder.name = (TextView) convertView.findViewById(R.id.device_name);
			holder.mac = (TextView) convertView.findViewById(R.id.device_mac);
			holder.mode = (ImageView) convertView.findViewById(R.id.device_mode);
			holder.right = (LinearLayout) convertView.findViewById(R.id.device_list_item_content_right);
			holder.forever = (TextView) convertView.findViewById(R.id.device_btn_forever);
			holder.temp = (TextView) convertView.findViewById(R.id.device_btn_temp);
			holder.state = (TextView) convertView.findViewById(R.id.device_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.left.setOnTouchListener(new leftOntouch(holder, position));
		HostRecord item = (HostRecord) getItem(position);
		holder.forever.setOnClickListener(new rightBtnOnClickListener(item));
		holder.temp.setOnClickListener(new rightBtnOnClickListener(item));
		if (position % 3 == 0) {
			holder.state.setText("黑");
		} else {
			holder.state.setText("白");
		}

		if (item.nick == null || item.nick.trim().equals("")) {
			if (item.hostType == DBConstants.HOST_TYPE_LOCALHOST) {
				holder.name.setText(item.name);
			} else {
				holder.name.setText(item.name);
			}
			holder.name.setTextColor(color.black);
		} else {
			if (item.hostType == DBConstants.HOST_TYPE_LOCALHOST) {
				holder.name.setText(item.nick);
			} else {
				holder.name.setText(item.nick);
			}
			holder.name.setTextColor(mInflater.getContext().getResources().getColor(R.color.blue));
		}
		holder.mac.setText(item.mac);
		if (item.state == DBConstants.HOST_STATE_ONLINE) {
			if (item.mode == DBConstants.HOST_MODE_WIFI) {
				holder.mode.setImageResource(R.drawable.icon_wifi_normal);
			} else {
				holder.mode.setImageResource(R.drawable.icon_wired_normal);
			}
		} else {
			if (item.mode == DBConstants.HOST_MODE_WIFI) {
				holder.mode.setImageResource(R.drawable.icon_wifi_disabled);
			} else {
				holder.mode.setImageResource(R.drawable.icon_wired_disabled);
			}
		}
		if (item.type != null) {
			if (item.type.equals(DBConstants.HOST_TYPE_OTHER)) {
				holder.image.setImageResource(R.drawable.default_else);
			} else if (item.type.equals(DBConstants.HOST_TYPE_PAD)) {
				holder.image.setImageResource(R.drawable.default_pad);
			} else if (item.type.equals(DBConstants.HOST_TYPE_MOBILE)) {
				holder.image.setImageResource(R.drawable.default_telephone);
			} else {
				holder.image.setImageResource(R.drawable.default_pc);
			}
		} else {
			holder.image.setImageResource(R.drawable.default_pc);
		}

		return convertView;
	}


	class ViewHolder {
		LinearLayout content;
		LinearLayout left;
		LinearLayout right;
		ImageView image;
		TextView name;
		TextView mac;
		ImageView mode;
		TextView temp, forever;
		TextView state;
	}

	private class leftOntouch implements OnTouchListener{
		/**
		 * 滚动显示和隐藏menu时，手指滑动需要达到的速度。
		 */
		public static final int SNAP_VELOCITY = 300;

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
		 * menu完全显示时，留给content的宽度值。
		 */
		private int menuPadding = 360;

		/**
		 * 主内容的布局。
		 */
		private View content;

		/**
		 * menu的布局。
		 */
		private View menu;

		/**
		 * content布局的参数，通过此参数来更改leftMargin的值。
		 */
		private LinearLayout.LayoutParams contentParams;

		/**
		 * 记录手指按下时的横坐标。
		 */
		private float xDown;

		/**
		 * 记录手指移动时的横坐标。
		 */
		private float xMove;

		/**
		 * 记录手机抬起时的横坐标。
		 */
		private float xUp;

		private float yDown, yUp;
		/**
		 * menu当前是显示还是隐藏。只有完全显示或隐藏menu时才会更改此值，滑动过程中此值无效。
		 */
		private boolean isMenuVisible;

		/**
		 * 用于计算手指滑动的速度。
		 */
		private VelocityTracker mVelocityTracker;
		/**
		 * list的位置
		 */
		private int pos;
		/**
		 * 整个layout
		 */
		private View layout;
		
		/**
		 * 拖动时候产生的Layout
		 */
		private View dragView;
		

		public leftOntouch(ViewHolder holder, int pos) {
			this.pos = pos;
			this.content = holder.left;
			this.menu = holder.right;
			this.layout = holder.content;
			initValues();
		}

		/**
		 * 初始化一些关键性数据。包括获取屏幕的宽度，给content布局重新设置宽度，给menu布局重新设置宽度和偏移距离等。
		 */
		private void initValues() {
			screenWidth = window.getDefaultDisplay().getWidth();
			contentParams = (LinearLayout.LayoutParams) content.getLayoutParams();
			contentParams.width = screenWidth;
			// 左边缘的值赋值为menu宽度的负数
			contentLeftEdge = -(screenWidth - menuPadding + menu.getPaddingLeft() + menu.getPaddingRight());
			contentRightEdge = 0;
			// content的leftMargin设置为右边缘的值，这样初始化时menu就变为不可见
			contentParams.leftMargin = 0;
			// 将content的宽度设置为屏幕宽度
			menu.getLayoutParams().width = screenWidth - menuPadding;
			isMenuVisible = false;
		}
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			createVelocityTracker(event);
			if (event.getAction() == MotionEvent.ACTION_DOWN && itemlockIndex == -1) {
				itemlockIndex = pos;
				// 手指按下时，记录按下时的横坐标
				xDown = event.getRawX();
				yDown = event.getRawY();
				// Log.d("down", xDown + "   " + pos);
				if (viewIndex == null) {
//					layout.setBackgroundResource(R.drawable.listitem_pressed);
				} else if (viewIndex != null && downIndex != pos) {
					content = viewIndex;
					scrollToContent();
					notifyDataSetChanged();
					return true;
				} else if (viewIndex != null && downIndex == pos) {

				}
			}
			if (event.getAction() == MotionEvent.ACTION_MOVE && itemlockIndex == pos) {
				// 手指移动时，对比按下时的横坐标，计算出移动的距离，来调整menu的leftMargin值，从而显示和隐藏menu
				xMove = event.getRawX();
				int distanceX = (int) (xMove - xDown);
				if (isMenuVisible) {
					contentParams.leftMargin = contentLeftEdge + distanceX;
					attemptClaimDrag(v);
				} else if (!isMenuVisible && Math.abs(distanceX) > 20) {
					attemptClaimDrag(v);
					contentParams.leftMargin = contentRightEdge + distanceX;
				}
				if (contentParams.leftMargin < contentLeftEdge) {
					contentParams.leftMargin = contentLeftEdge;
				} else if (contentParams.leftMargin > contentRightEdge) {
					contentParams.leftMargin = contentRightEdge;
				}
				content.setLayoutParams(contentParams);
				return true;
			}
			if (event.getAction() == MotionEvent.ACTION_UP && itemlockIndex == pos) {
				xUp = event.getRawX();
				yUp = event.getRawY();
//				layout.setBackgroundResource(getBackground(pos));
				// Log.d("up", "" + xUp);
				if (Math.abs(yUp - yDown) > 40 && Math.abs(yUp - yDown) >= Math.abs(xUp - xDown)) {
					scrollToContent();
					recycleVelocityTracker();
					return true;
				}
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
				itemlockIndex = -1;
			}
			if (event.getAction() == MotionEvent.ACTION_CANCEL && itemlockIndex == pos) {
				xUp = event.getRawX();
				yUp = event.getRawY();
//				layout.setBackgroundResource(getBackground(pos));
				// Log.d("cancel", "" + xUp);
				if (Math.abs(yUp - yDown) > 40 && Math.abs(yUp - yDown) >= Math.abs(xUp - xDown)) {
					scrollToContent();
					recycleVelocityTracker();
					return true;
				}
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
				}
				recycleVelocityTracker();
				itemlockIndex = -1;
			}
			gesListener.setView(content, pos);
			detector.onTouchEvent(event);
			return true;
		}

		/**
		 * 判断当前手势的意图是不是想显示content。如果手指移动的距离是负数，且当前menu是可见的，则认为当前手势是想要显示content。
		 * 
		 * @return 当前手势想显示content返回true，否则返回false。
		 */
		private boolean wantToShowContent() {
			return xUp - xDown > 40 && isMenuVisible;
		}

		/**
		 * 判断当前手势的意图是不是想显示menu。如果手指移动的距离是正数，且当前menu是不可见的，则认为当前手势是想要显示menu。
		 * 
		 * @return 当前手势想显示menu返回true，否则返回false。
		 */
		private boolean wantToShowMenu() {
			return xUp - xDown < -40 && !isMenuVisible;
		}

		/**
		 * 判断是否应该滚动将menu展示出来。如果手指移动距离大于屏幕的1/2，或者手指移动速度大于SNAP_VELOCITY，
		 * 就认为应该滚动将menu展示出来。
		 * 
		 * @return 如果应该滚动将menu展示出来返回true，否则返回false。
		 */
		private boolean shouldScrollToMenu() {
			return xDown - xUp > menu.getLayoutParams().width / 2 || getScrollVelocity() > SNAP_VELOCITY;
		}

		/**
		 * 判断是否应该滚动将content展示出来。如果手指移动距离加上menuPadding大于屏幕的1/2，
		 * 或者手指移动速度大于SNAP_VELOCITY， 就认为应该滚动将content展示出来。
		 * 
		 * @return 如果应该滚动将content展示出来返回true，否则返回false。
		 */
		private boolean shouldScrollToContent() {
			return xUp - xDown + menuPadding > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
		}

		/**
		 * 将屏幕滚动到menu界面，滚动速度设定为-30.
		 */
		public void scrollToMenu() {
			viewIndex = content;
			downIndex = pos;
			new ScrollTask().execute(-30);
		}

		/**
		 * 将屏幕滚动到content界面，滚动速度设定为30.
		 */
		public void scrollToContent() {
			viewIndex = null;
			downIndex = -1;
			new ScrollTask().execute(30);
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
			
			mVelocityTracker.computeCurrentVelocity(1000);
			int velocity = (int) mVelocityTracker.getXVelocity();
			Log.d("速率", ""+velocity);
			return Math.abs(velocity);
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

		class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

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
				} else {
					isMenuVisible = false;
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
				itemlockIndex = -1;
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
	}
	private void attemptClaimDrag(View view) {
		mParent = view.getParent();
		if (mParent != null) {
			mParent.requestDisallowInterceptTouchEvent(true);
		}
	}

	private class itemOnGestureListener implements OnGestureListener {

		private int pos;
		private View view;

		public void setView(View view, int pos) {
			this.pos = pos;
			this.view = view;
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
				Log.d("单击single", pos + ";" + itemlockIndex + ";" + downIndex);
				if (itemlockIndex == -1 && downIndex == -1) {
					mCallBack.onContentClick((HostRecord) getItem(pos));
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
//			switch (e2.getAction()) {
//			case MotionEvent.ACTION_UP:
//				Log.d("单击fling", pos + ";" + itemlockIndex + ";" + downIndex);
//				if (Math.abs(e2.getRawX() - e1.getRawX()) < 20 && itemlockIndex == -1 && downIndex == -1) {
//					mCallBack.onContentClick((HostRecord) getItem(pos));
//				}
//				break;
//			default:
//				break;
//			}
			return false;
		}

	}

	class rightBtnOnClickListener implements OnClickListener {

		private HostRecord hr;

		public rightBtnOnClickListener(HostRecord hr) {
			this.hr = hr;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.device_btn_forever:
				mCallBack.onForeverBtnClick(hr);
				break;

			case R.id.device_btn_temp:
				mCallBack.onTempBtnClick(hr);
				break;
			default:
				break;
			}
		}

	}

}
