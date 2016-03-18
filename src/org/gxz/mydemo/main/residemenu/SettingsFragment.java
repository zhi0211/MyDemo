package org.gxz.mydemo.main.residemenu;

import org.gxz.mydemo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * User: special Date: 13-12-22 Time: 下午3:28 Mail: specialcyci@gmail.com
 */
public class SettingsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frame_residemenu_settings,
				container, false);
		WebView mWebView = (WebView) view.findViewById(R.id.webview);
		mWebView.clearCache(true);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
		mWebView.loadUrl("http://www.baidu.com");
		return view;
	}

}
