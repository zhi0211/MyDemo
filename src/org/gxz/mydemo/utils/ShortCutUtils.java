package org.gxz.mydemo.utils;

import java.util.List;

import org.gxz.mydemo.R;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;

/**
 * @author wangli 快捷方式工具类
 */
public class ShortCutUtils {
	/**
	 * 添加当前应用的桌面快捷方式
	 * 
	 * @param ctx
	 */
	public static void addShortcut(Context ctx) {
		Intent shortcut = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");

		Intent shortcutIntent = ctx.getPackageManager()
				.getLaunchIntentForPackage(ctx.getPackageName());
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		// 获取当前应用名称
		String title = null;
		try {
			final PackageManager pm = ctx.getPackageManager();
			title = pm.getApplicationLabel(
					pm.getApplicationInfo(ctx.getPackageName(),
							PackageManager.GET_META_DATA)).toString();
		} catch (Exception e) {
		}
		// 快捷方式名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		// 不允许重复创建（不一定有效）
		shortcut.putExtra("duplicate", false);
		// 快捷方式的图标
		Parcelable iconResource = Intent.ShortcutIconResource.fromContext(ctx,
				R.drawable.ic_launcher);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);

		ctx.sendBroadcast(shortcut);
	}

	/**
	 * 删除当前应用的桌面快捷方式
	 * 
	 * @param ctx
	 */
	public static void delShortcut(Context ctx) {
		Intent shortcut = new Intent(
				"com.android.launcher.action.UNINSTALL_SHORTCUT");

		// 获取当前应用名称
		String title = null;
		try {
			final PackageManager pm = ctx.getPackageManager();
			title = pm.getApplicationLabel(
					pm.getApplicationInfo(ctx.getPackageName(),
							PackageManager.GET_META_DATA)).toString();
		} catch (Exception e) {
		}
		// 快捷方式名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		Intent shortcutIntent = ctx.getPackageManager()
				.getLaunchIntentForPackage(ctx.getPackageName());
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		ctx.sendBroadcast(shortcut);
	}

	/**
	 * 判断当前应用在桌面是否有桌面快捷方式
	 * 
	 * @param ctx
	 */
	public static boolean hasShortcut(Context ctx) {
		boolean result = false;
		String title = null;
		try {
			final PackageManager pm = ctx.getPackageManager();
			title = pm.getApplicationLabel(
					pm.getApplicationInfo(ctx.getPackageName(),
							PackageManager.GET_META_DATA)).toString();
		} catch (Exception e) {
		}

		final String uriStr;
		if (android.os.Build.VERSION.SDK_INT < 8) {
			uriStr = "content://com.android.launcher.settings/favorites?notify=true";
		} else {
			uriStr = "content://com.android.launcher2.settings/favorites?notify=true";
		}
		final Uri CONTENT_URI = Uri.parse(uriStr);
		final Cursor c = ctx.getContentResolver().query(CONTENT_URI, null,
				"title=?", new String[] { title }, null);
		if (c != null && c.getCount() > 0) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 获取权限的AUTHORITY
	 * 
	 * @param context
	 * @param permission
	 * @return
	 */
	public static String getAuthorityFromPermission(Context context,
			String permission) {
		if (permission == null)
			return null;
		List<PackageInfo> packs = context.getPackageManager()
				.getInstalledPackages(PackageManager.GET_PROVIDERS);
		if (packs != null) {
			for (PackageInfo pack : packs) {
				ProviderInfo[] providers = pack.providers;
				if (providers != null) {
					for (ProviderInfo provider : providers) {
						if (provider != null && provider.readPermission != null
								&& provider.readPermission.equals(permission))
							return provider.authority;
						if (provider != null
								&& provider.writePermission != null
								&& provider.writePermission
										.equals(permission))
							return provider.authority;
					}
				}
			}
		}
		return null;
	}
	
	
	/** 判断当前应用在桌面是否有桌面快捷方式(有的机子无效)
	 * 
	 * @param ctx
	 * @param appName
	 *            为null自动读取
	 */
	public static boolean hasShortcut(Context ctx, String appName) {
		boolean result = false;

		String title = null;
		if (null != appName) {
			title = appName;
		} else {
			try {
				final PackageManager pm = ctx.getPackageManager();
				title = pm.getApplicationLabel(
						pm.getApplicationInfo(ctx.getPackageName(),
								PackageManager.GET_META_DATA)).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String AUTHORITY = getAuthorityFromPermission(ctx,
				"com.android.launcher.permission.READ_SETTINGS");
		final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
				+ "/favorites?notify=true");
		final Cursor c = ctx.getContentResolver().query(CONTENT_URI, null,
				"title=?", new String[] { title }, null);
		if (c != null && c.getCount() > 0) {
			c.close();
			result = true;
		}
		return result;
	}
}