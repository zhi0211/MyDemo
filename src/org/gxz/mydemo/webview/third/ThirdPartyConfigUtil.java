package org.gxz.mydemo.webview.third;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class ThirdPartyConfigUtil {
	private static final String LOGTAG = ThirdPartyConfigUtil.class
			.getCanonicalName();
	public static final String QQ_CLIENT_ID = "222222";
	public static final String QQ_CLIENT_ID_NEXHOME="101025534";
	public static final String QQ_CLIENT_ID_YOUDAO="100255473";
	public static final String QQ_PACKNAME = "com.tencent.mobileqq";

	// QQ
	private static final String QQ_AUTHORIZ_URL = "https://graph.qq.com/oauth2.0/authorize";
	private static final String QQ_AUTHORIZ_URL_MOBILE = "https://openmobile.qq.com/oauth2.0/m_authorize";
	private static final String QQ_REDIRECT_URI = "www.qq.com";
	private static final String QQ_GET_OPENID_URL="https://graph.qq.com/oauth2.0/me";
	private static final String QQ_GET_USER_INFO_URL = "https://graph.qq.com/user/get_user_info";
	private static final String QQ_REDIRECT_ERROR_URI="http://openapi.qzone.qq.com/oauth/show?which=error";
	/** qq授权类型，此值固定为“token” */
	private static final String QQ_RESPONSE_TYPE = "token";
	/** qq使用的权限 */
//	private static final String QQ_SCOPE = "all";
	private static final String QQ_SCOPE = "get_simple_userinfo";
	/** qq授权类型，在本步骤中，此值为“authorization_code” */
	private static final String QQ_GRANT_TYPE = "authorization_code";

	public static final String encoding = "utf-8"; // URL编码方式

	public static String getQQOpenidURL(String access_token){
		StringBuilder sb=new StringBuilder(QQ_GET_OPENID_URL+"?");
		try {
			sb.append("access_token=");
			sb.append(URLEncoder.encode(access_token,encoding));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}
	
	
	public static String getQQAuthorizeURL(String client_id) {

		StringBuilder sb = new StringBuilder(QQ_AUTHORIZ_URL_MOBILE + "?");
		/*
		 * 添加参数
		 */
		try {
			sb.append("client_id=" + URLEncoder.encode(client_id, encoding));
			sb.append("&response_type=" + QQ_RESPONSE_TYPE);
			sb.append("&redirect_uri=" + URLEncoder.encode(QQ_REDIRECT_URI,encoding));
			if (QQ_SCOPE != null && !QQ_SCOPE.equals(""))
				sb.append("&scope=" + QQ_SCOPE);
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
		return sb.toString();
	}

	/**
	 * 获取个人信息的URL
	 * 
	 * @param accessToken
	 * @param appId
	 *            即client_id
	 * @param openId
	 * @return
	 */
	public static String getQQUserInfoURL(String accessToken, String appId,
			String openId) {
		if (accessToken == null || appId == null || openId == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(QQ_GET_USER_INFO_URL + "?");
		try {
			sb.append("access_token=");
			sb.append(URLEncoder.encode(accessToken, encoding));
			sb.append("&oauth_consumer_key=");
			sb.append(URLEncoder.encode(appId, encoding));
			sb.append("&openid=");
			sb.append(URLEncoder.encode(openId, encoding));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}
	public static String getQQRedirectURI() {
		return "http://"+QQ_REDIRECT_URI;
	}
	
	public static String getQQRedirectErrorURI() {
		return QQ_REDIRECT_ERROR_URI;
	}

	/**
	 * 解析出QQUrl返回的错误码
	 * 
	 * (附上错误示例)http://openapi.qzone.qq.com/oauth/show?which=error&display=
	 * mobile
	 * &error=100010&client_id=101025534&response_type=token&redirect_uri=http
	 * %3A%2F%2Fwww.qq.com&scope=get_simple_userinfo
	 * 
	 * @param url
	 * @return
	 */
	public static String analyErrorCode(String url) {
		if (url == null) {
			return null;
		} else {
			URL errorURL = null;
			try {
				errorURL = new URL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
			String error = null;
			String query = errorURL.getQuery();
			if (query == null)
				return null;
			String[] strs = query.split("&");
			for (String str : strs) {
				String[] strArr = str.split("=");
				if ("error".equals(strArr[0])) {
					if (strArr.length > 1) {
						error = strArr[1];
						break;
					}
				}
			}
			return error;
		}
	}

	/**
	 * 获取access_token与expires_in
	 * <将解析access_token的代码移入utils中>
	 * (示例：http://graph.qq.com/demo/index.jsp?#access_token
	 * =FE04************************CCE2&expires_in=7776000&state=test
	 * 
	 * @param url
	 * @return
	 */
	public static Map<String, String> analyAtkUrl(String url) {
		return null;
	}

}
