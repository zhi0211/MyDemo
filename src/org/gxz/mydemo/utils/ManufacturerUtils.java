package org.gxz.mydemo.utils;

import android.os.Build;

/**
 * Created by gxz on 2015/12/14.
 * 制造商判断
 */
public class ManufacturerUtils {

	private static final String MANUFACTURER_XIAOMI = "xiaomi";
	private static final String MANUFACTURER_LG = "LG";
	private static final String MANUFACTURER_SAMSUNG = "samsung";
	private static final String MANUFACTURER_HTC = "HTC";
	private static final String MANUFACTURER_SONY = "sony";
	private static final String MANUFACTURER_MEIZU = "meizu";


	public static boolean isXIAOMI() {
		if (Build.MANUFACTURER.equalsIgnoreCase(MANUFACTURER_XIAOMI)) {
			return true;
		}
		return false;
	}

	public static boolean isMEIZU() {
		if (Build.MANUFACTURER.equalsIgnoreCase(MANUFACTURER_MEIZU)) {
			return true;
		}
		return false;
	}


	public static boolean isLG() {
		if (Build.MANUFACTURER.equalsIgnoreCase(MANUFACTURER_LG)) {
			return true;
		}
		return false;
	}

	public static boolean isSAMSUNG() {
		if (Build.MANUFACTURER.equalsIgnoreCase(MANUFACTURER_SAMSUNG)) {
			return true;
		}
		return false;
	}

	public static boolean isHTC() {
		if (Build.MANUFACTURER.equalsIgnoreCase(MANUFACTURER_HTC)) {
			return true;
		}
		return false;
	}

	public static boolean isSONY() {
		if (Build.MANUFACTURER.equalsIgnoreCase(MANUFACTURER_SONY)) {
			return true;
		}
		return false;
	}


}
