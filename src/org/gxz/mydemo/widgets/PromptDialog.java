package org.gxz.mydemo.widgets;

import org.gxz.mydemo.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 提示控件
 */
public class PromptDialog extends Dialog implements
		android.view.View.OnClickListener {

	private TextView mMsg;
	private Button mLeftBtn;
	private Button mRightBtn;
	private View mDivide;
	private TextView mTitle;
	private onPromPtClick listener;
	public static int ONBACK_PRESSED_TYPE_NOT_ALLOW_CLOSE = 0;
	public static int ONBACK_PRESSED_TYPE_ALLOW_COLSE = 1;
	private int mOnBackType = ONBACK_PRESSED_TYPE_ALLOW_COLSE;

	public interface onPromPtClick {
		public void onPromPtLeftBtnClick();
		public void onPromPtRightBtnClick();
	}

//	public PromptDialog(Context context) {
//		super(context);
//	}
//
//	public PromptDialog(Context context, boolean cancelable,
//			OnCancelListener cancelListener) {
//		super(context, cancelable, cancelListener);
//	}

	public PromptDialog(Context context, int theme) {
		super(context, theme);

		View view = LayoutInflater.from(context).inflate(
				R.layout.view_dialog_prompt, null);

		setContentView(view);
		mMsg = (TextView) view.findViewById(R.id.prompt_msg);
		mLeftBtn = (Button) view.findViewById(R.id.prompt_btn_left);
		mTitle = (TextView) view.findViewById(R.id.prompt_title);
		mLeftBtn.setOnClickListener(this);
		mRightBtn=(Button)view.findViewById(R.id.prompt_btn_right);
		mRightBtn.setOnClickListener(this);
		mDivide=findViewById(R.id.prompt_btn_view);
		setCancelable(false);
	}
	
	public void setRightBtnVisibility(int visibility){
		mRightBtn.setVisibility(visibility);
		mDivide.setVisibility(visibility);
	}

	public void setMessage(String msg) {
		mMsg.setText(msg);
	}

	public void setTitle(int resId) {
		mTitle.setText(resId);
	}

	public void setTitle(String title) {
		mTitle.setText(title);
	}

	public void setMessage(int resId) {
		mMsg.setText(resId);
	}

	public void setLeftBtnText(int resId) {
		mLeftBtn.setText(resId);
	}

	public void setLeftBtnText(String text) {
		mLeftBtn.setText(text);
	}
	
	public void setRightBtnText(String text) {
		mRightBtn.setText(text);
	}
	
	public void setRightBtnText(int resid) {
		mRightBtn.setText(resid);
	}

	public void setPromptClickListener(onPromPtClick listener) {
		this.listener = listener;
	}
	
	/**
	 * 设置Onback键的类型
	 * 
	 * @param type
	 *            ONBACK_PRESSED_TYPE_ALLOW_COLSE/
	 *            ONBACK_PRESSED_TYPE_NOT_ALLOW_CLOSE
	 */
	public void setOnBackPressedType(int type) {
		mOnBackType = type;
	}

	@Override
	public void onBackPressed() {
		if(mOnBackType==ONBACK_PRESSED_TYPE_ALLOW_COLSE){
			dismiss();
			return;
		}
		super.onBackPressed();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.prompt_btn_left:
			if (listener != null)
				listener.onPromPtLeftBtnClick();
			break;
		case R.id.prompt_btn_right:
			if(listener!=null)
				listener.onPromPtRightBtnClick();
		default:
			break;
		}

	}
}
