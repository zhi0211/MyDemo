package org.gxz.mydemo.webview.third;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gxz.mydemo.R;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class QQLoginWeb extends Activity {

	private static final String LOGTAG = QQLoginWeb.class.getCanonicalName();
	private WebView mWebView;
	private String mAppId;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				showDialog((String) msg.obj);
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		mWebView = (WebView) findViewById(R.id.myWebView);
		initWebView();
	}

	private void initWebView() {
		mAppId = ThirdPartyConfigUtil.QQ_CLIENT_ID_NEXHOME;
		mWebView.clearCache(true);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.setWebChromeClient(new MyWebChromeClient());
		mWebView.loadUrl(ThirdPartyConfigUtil.getQQAuthorizeURL(mAppId));
	}

	private class MyWebChromeClient extends WebChromeClient {

		JSONObject json;

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			if (newProgress == 100) {
				// 延时处理加载圈
				// mHandler.sendEmptyMessageDelayed(0, 1000);
			}
		}
	}

	private class MyWebViewClient extends WebViewClient {
		private int index = 0;

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
		
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			Log.w(LOGTAG,"web error code:" +errorCode);
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

		/**
		 * 由于腾讯授权页面采用https协议 执行此方法接受所有证书
		 */
		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
			Log.w(LOGTAG, "onReceivedSslError:"+error.toString());
			handler.proceed();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			/**
			 * url.contains(ConfigUtil.callBackUrl) 如果授权成功url中包含之前设置的callbackurl
			 * 包含：授权成功 index == 0 由于该方法onPageStarted可能被多次调用造成重复跳转 则添加此标示
			 */
			Log.i(LOGTAG, "ui uri:" + url);
			try {
				URI uri = new URI(url);
				Log.d(LOGTAG,
						"ui uri host:" + uri.getHost() + ";data:"
								+ uri.getQuery() + ";frag:" + uri.getFragment());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
			if (url.startsWith(ThirdPartyConfigUtil.getQQRedirectURI())
					&& index == 0) {
				++index;
				String[] urlSplits = url.split("#");
				if (urlSplits.length > 1) {
					try {
						JSONObject json = new JSONObject();
						String[] params = urlSplits[1].split("&");
						for (String str : params) {
							String[] strArr = str.split("=");
							if ("access_token".equals(strArr[0])) {
								json.put("access_token", strArr[1]);
							}
							if ("expires_in".equals(strArr[0])) {
								json.put("expires_in", strArr[1]);

							}
							// if ("openid".equals(strArr[0])) {
							// json.put("openid", strArr[1]);
							// }
						}
						Log.i(LOGTAG, "qq json length:" + json.length()
								+ ";json:" + json.toString());
						final String atk = json.getString("access_token");
						if (atk != null && !atk.equals("")) {
							final String openurl = ThirdPartyConfigUtil
									.getQQOpenidURL(atk);
							Log.i(LOGTAG, "qq open id url:" + openurl);
							if (openurl != null) {
								new Thread() {
									public void run() {
										String result = getResultFromUrl(openurl);
										Log.i(LOGTAG, "qq call back:" + result);
										if (result != null) {
											String openid = getOpenid(result);
											String userinfURL = ThirdPartyConfigUtil
													.getQQUserInfoURL(atk,
															mAppId, openid);
											Log.i(LOGTAG, "qq  userinfo url:"
													+ userinfURL);
											String userinfo = getResultFromUrl(userinfURL);
											Log.i(LOGTAG, "qq userinfo:"
													+ userinfo);
											mHandler.sendMessage(mHandler
													.obtainMessage(0, userinfo));
										}

									};
								}.start();

								// view.loadUrl(openurl);
							}
						}
						showDialog(url);
					} catch (Exception e) {
						e.printStackTrace();
						setResult(Activity.RESULT_CANCELED);
						finish();
					}
				} else {
					setResult(Activity.RESULT_CANCELED);
					finish();
				}
			} else if (url.startsWith(ThirdPartyConfigUtil
					.getQQRedirectErrorURI())) {
				Log.d(LOGTAG, "ui error url:" + url);
				String error = ThirdPartyConfigUtil.analyErrorCode(url);
				if (error != null) {
				} else {
				}
			}
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}
	}

	private void showDialog(String url) {
		TextView v = new TextView(this);
		v.setText(url);
		v.setTextSize(12);
		new AlertDialog.Builder(this).setView(v).setNegativeButton("确定", null)
				.show();
	}

	private String getResultFromUrl(String url) {
		String result = null;
		HttpURLConnection urlConn = null;
		ByteArrayOutputStream dis = null;
		try {
			URL mUrl = new URL(url);
			urlConn = (HttpURLConnection) mUrl.openConnection();
			urlConn.setConnectTimeout(5000);
			urlConn.connect();
			InputStream is = urlConn.getInputStream();
			dis = new ByteArrayOutputStream();
			int realRead = 0;
			byte[] buff = new byte[1024];
			while ((realRead = is.read(buff)) != -1) {
				dis.write(buff, 0, realRead);
			}
			result = new String(dis.toByteArray(), "UTF-8").trim();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (urlConn != null) {
					urlConn.disconnect();
				}
				if (dis != null)
					dis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return result;
	}

	private String getOpenid(String result) {
		String str = null;
		Pattern p = Pattern.compile("\\{(\\S+?)\\}");
		Matcher matcher = p.matcher(result);
		while (matcher.find()) {
			String jsonStr = matcher.group();
			try {
				JSONObject json = new JSONObject(jsonStr);
				str = json.getString("openid");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		}
		return str;
	}
}
