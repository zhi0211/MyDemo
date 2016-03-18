package org.gxz.mydemo.telinfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.gxz.mydemo.BaseActivity;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.telephony.TelephonyManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class TelManager extends BaseActivity {

	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
			"yyyy/MM/dd     HH:mm:ss");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScrollView sv = new ScrollView(this);
		LayoutParams lpsv = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		sv.setLayoutParams(lpsv);
		sv.setBackgroundColor(Color.WHITE);
		setContentView(sv);

		LinearLayout ll = new LinearLayout(this);
		LinearLayout.LayoutParams lp = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ll.setLayoutParams(lp);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setBackgroundColor(Color.WHITE);

		sv.addView(ll);
		LayoutParams lptv = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);

		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(TELEPHONY_SERVICE);
		/*
		 * 电话状态： 1.tm.CALL_STATE_IDLE=0 无活动 2.tm.CALL_STATE_RINGING=1 响铃
		 * 3.tm.CALL_STATE_OFFHOOK=2 摘机
		 */
		tm.getCallState();// int
		TextView tv1 = new TextView(this);
		tv1.setLayoutParams(lptv);
		tv1.setTextColor(Color.BLUE);
		tv1.setText("电话状态:" + tm.getCallState());
		ll.addView(tv1);

		/*
		 * 电话方位： 权限 ：ACCESS_COARSE_LOCATION
		 */
		tm.getCellLocation();// CellLocation
		TextView tv2 = new TextView(this);
		tv2.setLayoutParams(lptv);
		tv2.setTextColor(Color.BLUE);
		tv2.setText("\n电话方位:" + tm.getCellLocation());
		ll.addView(tv2);

		/*
		 * 唯一的设备ID： GSM手机的 IMEI 和 CDMA手机的 MEID. Return null if device ID is not
		 * available. 权限 read_phone_state
		 */
		tm.getDeviceId();// String
		TextView tv3 = new TextView(this);
		tv3.setLayoutParams(lptv);
		tv3.setTextColor(Color.BLUE);
		tv3.setText("\n唯一的设备ID/IMEI:" + tm.getDeviceId());
		ll.addView(tv3);
		/*
		 * 设备的软件版本号： 例如：the IMEI/SV(software version) for GSM phones. Return
		 * null if the software version is not available.
		 */
		tm.getDeviceSoftwareVersion();// String
		TextView tv4 = new TextView(this);
		tv4.setLayoutParams(lptv);
		tv4.setTextColor(Color.BLUE);
		tv4.setText("\n设备的软件版本号:" + tm.getDeviceSoftwareVersion());
		ll.addView(tv4);

		/*
		 * 手机号： GSM手机的 MSISDN. Return null if it is unavailable.
		 */
		tm.getLine1Number();// String
		TextView tv5 = new TextView(this);
		tv5.setLayoutParams(lptv);
		tv5.setTextColor(Color.BLUE);
		tv5.setText("\n手机号:" + tm.getLine1Number());
		ll.addView(tv5);

		/*
		 * 附近的电话的信息: 类型：List<NeighboringCellInfo>
		 * 需要权限：android.Manifest.permission#ACCESS_COARSE_UPDATES
		 */
		tm.getNeighboringCellInfo();// List<NeighboringCellInfo>

		/*
		 * 获取ISO标准的国家码，即国际长途区号。 注意：仅当用户已在网络注册后有效。 在CDMA网络中结果也许不可靠。
		 */
		tm.getNetworkCountryIso();// String
		TextView tv6 = new TextView(this);
		tv6.setLayoutParams(lptv);
		tv6.setTextColor(Color.BLUE);
		tv6.setText("\n获取ISO标准的国家码:" + tm.getNetworkCountryIso());
		ll.addView(tv6);

		/*
		 * MCC+MNC(mobile country code + mobile network code) 注意：仅当用户已在网络注册时有效。
		 * 在CDMA网络中结果也许不可靠。
		 */
		tm.getNetworkOperator();// String
		TextView tv7 = new TextView(this);
		tv7.setLayoutParams(lptv);
		tv7.setTextColor(Color.BLUE);
		tv7.setText("\nMCC+MNC:" + tm.getNetworkOperator());
		ll.addView(tv7);

		/*
		 * 按照字母次序的current registered operator(当前已注册的用户)的名字 注意：仅当用户已在网络注册时有效。
		 * 在CDMA网络中结果也许不可靠。
		 */
		tm.getNetworkOperatorName();// String

		/*
		 * 当前使用的网络类型： 例如： NETWORK_TYPE_UNKNOWN 网络类型未知 0 NETWORK_TYPE_GPRS GPRS网络
		 * 1 NETWORK_TYPE_EDGE EDGE网络 2 NETWORK_TYPE_UMTS UMTS网络 3
		 * NETWORK_TYPE_HSDPA HSDPA网络 8 NETWORK_TYPE_HSUPA HSUPA网络 9
		 * NETWORK_TYPE_HSPA HSPA网络 10 NETWORK_TYPE_CDMA CDMA网络,IS95A 或 IS95B. 4
		 * NETWORK_TYPE_EVDO_0 EVDO网络, revision 0. 5 NETWORK_TYPE_EVDO_A EVDO网络,
		 * revision A. 6 NETWORK_TYPE_1xRTT 1xRTT网络 7
		 */
		tm.getNetworkType();// int

		/*
		 * 手机类型： 例如： PHONE_TYPE_NONE 无信号 PHONE_TYPE_GSM GSM信号 PHONE_TYPE_CDMA
		 * CDMA信号
		 */
		tm.getPhoneType();// int
		TextView tv8 = new TextView(this);
		tv8.setLayoutParams(lptv);
		tv8.setTextColor(Color.BLUE);
		tv8.setText("\n手机类型:" + tm.getPhoneType());
		ll.addView(tv8);
		/*
		 * Returns the ISO country code equivalent for the SIM provider's
		 * country code. 获取ISO国家码，相当于提供SIM卡的国家码。
		 */
		tm.getSimCountryIso();// String

		/*
		 * Returns the MCC+MNC (mobile country code + mobile network code) of
		 * the provider of the SIM. 5 or 6 decimal digits.
		 * 获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字. SIM卡的状态必须是
		 * SIM_STATE_READY(使用getSimState()判断).
		 */
		tm.getSimOperator();// String

		/*
		 * 服务商名称： 例如：中国移动、联通 SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
		 */
		tm.getSimOperatorName();// String

		/*
		 * SIM卡的序列号： 需要权限：READ_PHONE_STATE
		 */
		tm.getSimSerialNumber();// String

		TextView tv10 = new TextView(this);
		tv10.setLayoutParams(lptv);
		tv10.setTextColor(Color.BLUE);
		tv10.setText("\nSIM卡的序列号:" + tm.getSimSerialNumber());
		ll.addView(tv10);

		/*
		 * SIM的状态信息： SIM_STATE_UNKNOWN 未知状态 0 SIM_STATE_ABSENT 没插卡 1
		 * SIM_STATE_PIN_REQUIRED 锁定状态，需要用户的PIN码解锁 2 SIM_STATE_PUK_REQUIRED
		 * 锁定状态，需要用户的PUK码解锁 3 SIM_STATE_NETWORK_LOCKED 锁定状态，需要网络的PIN码解锁 4
		 * SIM_STATE_READY 就绪状态 5
		 */
		tm.getSimState();// int
		TextView tv12 = new TextView(this);
		tv12.setLayoutParams(lptv);
		tv12.setTextColor(Color.BLUE);
		tv12.setText("\nSIM的状态信息:" + tm.getSimState());
		ll.addView(tv12);

		/*
		 * 唯一的用户ID： 例如：IMSI(国际移动用户识别码) for a GSM phone. 需要权限：READ_PHONE_STATE
		 */
		tm.getSubscriberId();// String
		TextView tv13 = new TextView(this);
		tv13.setLayoutParams(lptv);
		tv13.setTextColor(Color.BLUE);
		tv13.setText("\n区分使用的是什么类型的卡-IMSI:" + tm.getSubscriberId());
		ll.addView(tv13);

		/*
		 * 取得和语音邮件相关的标签，即为识别符 需要权限：READ_PHONE_STATE
		 */
		tm.getVoiceMailAlphaTag();// String
		TextView tv14 = new TextView(this);
		tv14.setLayoutParams(lptv);
		tv14.setTextColor(Color.BLUE);
		tv14.setText("\n取得和语音邮件相关的标签:" + tm.getVoiceMailAlphaTag());
		ll.addView(tv14);

		/*
		 * 获取语音邮件号码： 需要权限：READ_PHONE_STATE
		 */
		tm.getVoiceMailNumber();// String
		TextView tv15 = new TextView(this);
		tv15.setLayoutParams(lptv);
		tv15.setTextColor(Color.BLUE);
		tv15.setText("\n获取语音邮件号码:" + tm.getVoiceMailNumber());
		ll.addView(tv15);

		/*
		 * ICC卡是否存在
		 */
		tm.hasIccCard();// boolean
		TextView tv16 = new TextView(this);
		tv16.setLayoutParams(lptv);
		tv16.setTextColor(Color.BLUE);
		tv16.setText("\nICC卡是否存在:" + tm.hasIccCard());
		ll.addView(tv16);

		/*
		 * 是否漫游: (在GSM用途下)
		 */
		tm.isNetworkRoaming();//
		TextView tv17 = new TextView(this);
		tv17.setLayoutParams(lptv);
		tv17.setTextColor(Color.BLUE);
		tv17.setText("\n是否漫游:" + tm.isNetworkRoaming());
		ll.addView(tv17);

		// Wifi 权限ACCESS_WIFI_STATE
		WifiManager wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifiMan.getConnectionInfo();
		String mac = info.getMacAddress();
		TextView tv9 = new TextView(this);
		tv9.setLayoutParams(lptv);
		tv9.setTextColor(Color.BLUE);
		tv9.setText("\nWifi mac:" + mac);
		ll.addView(tv9);

		// android 版本
		TextView tv11 = new TextView(this);
		tv11.setLayoutParams(lptv);
		tv11.setTextColor(Color.BLUE);
		tv11.setText("\n设备信息:\n-android version:"
				+ android.os.Build.VERSION.RELEASE + "\n-MODEL:"
				+ android.os.Build.MODEL + "\n-SDK:"
				+ android.os.Build.VERSION.SDK + "\n-商标:"
				+ android.os.Build.BRAND + "\n-board:" + android.os.Build.BOARD
				+ "\n-BOOTLOADER:" + android.os.Build.BOOTLOADER
				+ "\n-CPU_ABI:" + android.os.Build.CPU_ABI + "\n-DEVICE:"
				+ android.os.Build.DEVICE + "\n-DISPLAY:"
				+ android.os.Build.DISPLAY + "\n-FINGERPRINT:"
				+ android.os.Build.FINGERPRINT + "\n-HARDWARE:"
				+ android.os.Build.HARDWARE + "\n-HOST:"
				+ android.os.Build.HOST + "\n-ID:" + android.os.Build.ID
				+ "\n-制造商:" + android.os.Build.MANUFACTURER + "\n-PRODUCT:"
				+ android.os.Build.PRODUCT + "\n-RADIO:"
				+ android.os.Build.RADIO + "\n-SERIAL:"
				+ android.os.Build.SERIAL + "\n-TAGS:" + android.os.Build.TAGS
				+ "\n-出厂日期:"
				+ TIME_FORMAT.format(new Date(android.os.Build.TIME))
				+ "\n-UNKNOWN:" + android.os.Build.UNKNOWN + "\n-USER:"
				+ android.os.Build.USER + "\n-RadioVersion:"
				+ android.os.Build.getRadioVersion());
		ll.addView(tv11);
		
		
		TextView tv19 = new TextView(this);
		tv19.setLayoutParams(lptv);
		tv19.setTextColor(Color.BLUE);
		StringBuffer sb = new StringBuffer();
		String androidId=android.provider.Settings.Secure.getString(
				getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
		sb.append("\nandroidId=").append(androidId);
		tv19.setText(sb.toString());
		ll.addView(tv19);
		

		// 存储卡位置
		TextView tv18 = new TextView(this);

		tv18.setLayoutParams(lptv);
		tv18.setTextColor(Color.BLACK);
		

		StorageManager storageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
		try {
			Class<?>[] paramClasses = {};
			Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
			getVolumePathsMethod.setAccessible(true);
			Object[] params = {};
			Object invoke = getVolumePathsMethod.invoke(storageManager, params);
			for (int i = 0; i < ((String[])invoke).length; i++) {
				tv18.append(((String[])invoke)[i]+"\n");
			}
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		ll.addView(tv18);
	}
	

}
