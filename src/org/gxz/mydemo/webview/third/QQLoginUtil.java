package org.gxz.mydemo.webview.third;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * 
 * 应用QQ登岸对象类
 * 
 * @author yw-tony
 * 
 * 
 */

public class QQLoginUtil {

	public String token = "，";

	public Tencent tencen = null;

	private static String APP_ID = "你的Appid";

	private Context context;

	private QQLoginUtil() {
	}

	private static QQLoginUtil instance = new QQLoginUtil();

	public static QQLoginUtil getInstance() {

		return instance;

	}

	public void initTencen(Context context) {

		tencen = Tencent.createInstance(APP_ID, context);

		this.context = context;

	}

	public void qqLogin(Activity activity) {

		if (!tencen.isSessionValid()) {

			IUiListener listener = new BaseUiListener() {

				@Override
				protected void doComplete(JSONObject values) {

					/*
					 * UserInfo();
					 * 
					 * LoginButton();
					 */

				}

			};

			tencen.login(activity, "all", listener);

		} else {

			tencen.logout(activity);

			// UserInfo();

			// LoginButton();

		}

	}

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(JSONObject response) {

			// Util.showResultDialog(MainActivity.this,response.toString(),"登录成功");

			Log.e("响应字符串", response.toString());

			doComplete(response);

		}

		protected void doComplete(final JSONObject values) {

		}

		@Override
		public void onError(UiError e) {

			Log.e("错误信息", e.errorDetail);

			// Util.toastMessage(MainActivity.this,"onError: " + e.errorDetail);

			// Util.dismissDialog();

		}

		@Override
		public void onCancel() {

			Log.e("作废了当前的登录操纵", "作废了当前的登录操纵");

			// Util.toastMessage(context,"onCancel: ");

			// Util.dismissDialog();

		}

	}

}