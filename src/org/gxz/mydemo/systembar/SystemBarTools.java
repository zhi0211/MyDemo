package org.gxz.mydemo.systembar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import org.gxz.mydemo.R;
import org.gxz.mydemo.utils.ManufacturerUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Created by gxz on 2015/10/22.
 * 状态栏工具
 */
public class SystemBarTools {

	

	/**
	 * 初始化状态栏
	 * 注释:根布局加入android:fitsSystemWindows="true"，否则将全屏延伸到状态栏
	 *
	 * @param activity
	 */
	public static void initStatusBarTintManager(Activity activity, int color) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			//状态栏透明 需要在创建SystemBarTintManager 之前调用。
			setTranslucentStatus(activity, true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(activity);
		tintManager.setStatusBarTintEnabled(true);
		//使StatusBarTintView 和 actionbar的颜色保持一致，风格统一。
		tintManager.setStatusBarTintResource(color);
		// 设置状态栏的文字颜色
		tintManager.setStatusBarDarkMode(true, activity);
	}


	/**
	 * 初始化状态栏
	 * 注释:根布局加入android:fitsSystemWindows="true"，否则将全屏延伸到状态栏
	 *
	 * @param activity
	 */
	public static void initStatusBarTintManager(Activity activity) {
		initStatusBarTintManager(activity, R.color.statusbar_color);
	}


	/**
	 * 设置状态栏透明
	 *
	 * @param activity
	 * @param on
	 */
	@TargetApi(19)
	private static void setTranslucentStatus(Activity activity, boolean on) {
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}


	/**
	 * 设置图标是否为dark模式
	 * @param darkMode
	 * @param activity
	 */
	public static void setStatusBarDarkMode(boolean darkMode, Activity activity) {
		if(ManufacturerUtils.isXIAOMI()){
			setStatusBarDarkModeOfMIUI(darkMode, activity);
		}else if(ManufacturerUtils.isMEIZU()){
			setStatusBarDarkModeOfMEIZU(darkMode, activity);
		}
	}


	/**
	 * 小米改变状态栏文字与图标模式
	 *
	 * @param darkmode
	 * @param activity
	 */
	private static void setStatusBarDarkModeOfMIUI(boolean darkmode, Activity activity) {
		Class<? extends Window> clazz = activity.getWindow().getClass();
		try {
			Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
			Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
			int darkModeFlag = field.getInt(layoutParams);
			Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
			extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置魅族状态栏颜色
	 *
	 * @param darkmode
	 * @param activity
	 */
	private static void setStatusBarDarkModeOfMEIZU(boolean darkmode, Activity activity) {
		try {
			WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
			Field flags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
			int newFlags;
			if (darkmode) {
				newFlags = flags.getInt(lp) | 0x200;
			} else {
				newFlags = flags.getInt(lp) & ~0x200;
			}
			flags.set(lp, newFlags);
			activity.getWindow().setAttributes(lp);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
