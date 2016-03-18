package org.gxz.mydemo.utils;

import android.content.Context;
import android.util.Log;

import java.util.Locale;

public class Utils {

	public static boolean isZh(Context ctx) {
		Locale locale = ctx.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		Log.i("MyDemo", "language:" + language + ";Locale CHINA:"
				+ Locale.CHINA.getLanguage());
		if (language.endsWith(Locale.CHINA.getLanguage()))
			return true;
		else
			return false;
	}

	public static String getLOGTAG(Class<?> clz) {
		return clz.getCanonicalName();
	}
}
