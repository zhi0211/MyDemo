package org.gxz.mydemo.widgets;

import org.gxz.mydemo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 标题控件
 */
public class TitleBar extends LinearLayout implements OnClickListener {
	public static final int BTN_EQUMENT_DETAIL = 0;
	public static final int BTN_MORE = 1;

	public static final int RIGHT_BTN_IMAGE = 0;
	public static final int RIGHT_BTN_STRINT = 1;

	private Button mLeftBtn;
	private ImageButton mRightBtn;
	private Button mRithgBtnText;
	private TextView mTitle;

	private LinearLayout mProgressBar;

	private int mTarget = -1;

	private OnTitleBtnOnClickListener mCallBack = null;

	/**
	 * 标题按钮监听接口
	 */
	public interface OnTitleBtnOnClickListener {
		// 左按钮点击事件
		void onLeftBtnClick();

		// 右按钮点击事件
		void onRightBtnClick();
	}

	/**
	 * 设置实现接口
	 *
	 * @param listener
	 */
	public void setOnTitleBtnOnClickListener(OnTitleBtnOnClickListener listener) {
		mCallBack = listener;
	}

	/**
	 * 初始化
	 *
	 * @param context
	 */
	public TitleBar(Context context) {
		super(context, null);
	}

	/**
	 * 初始化
	 *
	 * @param context
	 * @param attrs
	 */
	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.widgets_title_bar, this, true);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mLeftBtn = (Button) findViewById(R.id.nav_title_btn_left);
		mLeftBtn.setOnClickListener(this);
		mRightBtn = (ImageButton) findViewById(R.id.nav_title_btn_right);
		mRightBtn.setOnClickListener(this);
		mRithgBtnText = (Button) findViewById(R.id.nav_title_btn_right_with_text);
		mRithgBtnText.setOnClickListener(this);
		mTitle = (TextView) findViewById(R.id.nav_title_msg);
		mProgressBar = (LinearLayout) findViewById(R.id.title_progressBar);
	}

	/**
	 * 设置标题
	 *
	 * @param title         标题内容
	 * @param isShowBackBtn 是否显示返回按钮
	 */
	public void init(String title, boolean isShowBackBtn) {
		mTitle.setText(title);
		if (isShowBackBtn) {
			mLeftBtn.setText("返回");
			mLeftBtn.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 设置标题
	 *
	 * @param title 标题内容
	 */
	public void init(String title) {
		mTitle.setText(title);
	}

	/**
	 * 设置标题左按钮
	 *
	 * @param isShowLeftBtn 是否显示左按钮
	 * @param leftBtnMsg    左按钮显示内容
	 */
	public void initLeftBtn(boolean isShowLeftBtn, String leftBtnMsg) {
		if (isShowLeftBtn) {
			if (leftBtnMsg == null || leftBtnMsg.equals(""))
				return;
			mLeftBtn.setText(leftBtnMsg);
			mLeftBtn.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 设置标题右按钮
	 *
	 * @param isShowRightBtn 是否显示右按钮
	 * @param msg            右按钮显示内容
	 */
	public void initRightBtn(boolean isShowRightBtn, String msg) {
		if (isShowRightBtn) {
			mRithgBtnText.setText(msg);
			mRithgBtnText.setVisibility(View.VISIBLE);
			mRightBtn.setVisibility(View.GONE);
			mTarget = RIGHT_BTN_STRINT;
		}
	}

	/**
	 * 设置标题右按钮
	 *
	 * @param isShowRightBtn 是否显示右按钮
	 * @param type           右按钮类型
	 */
	public void initRightBtn(boolean isShowRightBtn, int type) {
		if (isShowRightBtn) {
			mTarget = RIGHT_BTN_IMAGE;
			switch (type) {
				case BTN_EQUMENT_DETAIL:
					mRightBtn
							.setImageResource(R.drawable.title_btn_equipment_normal);
					mRightBtn.setVisibility(View.VISIBLE);
					break;
				case BTN_MORE:
					mRightBtn.setImageResource(R.drawable.title_btn_menu_normal);
					mRightBtn.setVisibility(View.VISIBLE);
					break;
				default:
					break;
			}
		}
	}

	/**
	 * 显示正在刷新状态
	 *
	 * @param show
	 */
	public void showProgressBar(boolean show) {
		if (show) {
			mRightBtn.setVisibility(View.GONE);
			mRithgBtnText.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.VISIBLE);
		} else {
			mProgressBar.setVisibility(View.GONE);
			switch (mTarget) {
				case RIGHT_BTN_IMAGE:
					mRithgBtnText.setVisibility(View.GONE);
					mRightBtn.setVisibility(View.VISIBLE);
					break;
				case RIGHT_BTN_STRINT:
					mRightBtn.setVisibility(View.GONE);
					mRithgBtnText.setVisibility(View.VISIBLE);
					break;
				default:
					mRightBtn.setVisibility(View.INVISIBLE);
					mRithgBtnText.setVisibility(View.GONE);
					break;
			}
		}
	}

	/**
	 * 初始化标题
	 *
	 * @param title
	 * @param isShowLeftBtn
	 * @param leftBtnMsg
	 * @param isShowRightBtn
	 * @param rightBtnMsg
	 */
	public void init(String title, boolean isShowLeftBtn, String leftBtnMsg,
					 boolean isShowRightBtn, int type) {
		mTitle.setText(title);
		if (isShowLeftBtn) {
			if (leftBtnMsg == null || leftBtnMsg.equals(""))
				return;
			mLeftBtn.setText(leftBtnMsg);
			mLeftBtn.setVisibility(View.VISIBLE);
		}
		initRightBtn(isShowRightBtn, type);
	}

	@Override
	public void onClick(View v) {
		if (mCallBack == null)
			return;
		switch (v.getId()) {
			case R.id.nav_title_btn_left:
				mCallBack.onLeftBtnClick();
				break;
			case R.id.nav_title_btn_right:
				mCallBack.onRightBtnClick();
				break;
			case R.id.nav_title_btn_right_with_text:
				mCallBack.onRightBtnClick();
				break;
			default:
				break;
		}
	}

}
