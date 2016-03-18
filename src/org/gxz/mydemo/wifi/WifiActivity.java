package org.gxz.mydemo.wifi;

import java.util.List;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WifiActivity extends BaseActivity {
	/** Called when the activity is first created. */
	private TextView allNetWork;
	private Button scan;
	private Button start;
	private Button stop;
	private Button check;
	private WifiUtils mWifiAdmin;
	// 扫描结果列表
	private EditText etSsid, etPassword, etType;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi);
		mWifiAdmin = new WifiUtils(WifiActivity.this);
		init();
	}

	public void init() {
		allNetWork = (TextView) findViewById(R.id.allNetWork);
		scan = (Button) findViewById(R.id.scan);
		start = (Button) findViewById(R.id.start);
		stop = (Button) findViewById(R.id.stop);
		check = (Button) findViewById(R.id.check);
		Button connect = (Button) findViewById(R.id.wifi_connect);
		Button getCon = (Button) findViewById(R.id.getconfition);
		getCon.setOnClickListener(new MyListener());
		Button getinfo = (Button) findViewById(R.id.getwifiinfo);
		getinfo.setOnClickListener(new MyListener());
		Button connectOldWifi = (Button) findViewById(R.id.connect_old_wifi);
		connectOldWifi.setOnClickListener(new MyListener());
		scan.setOnClickListener(new MyListener());
		start.setOnClickListener(new MyListener());
		stop.setOnClickListener(new MyListener());
		check.setOnClickListener(new MyListener());
		connect.setOnClickListener(new MyListener());
		etSsid = (EditText) findViewById(R.id.wifi_ssid);
		etPassword = (EditText) findViewById(R.id.wifi_password);
		etType = (EditText) findViewById(R.id.wifi_type);
	}

	private class MyListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.getwifiinfo:
				allNetWork.setText(mWifiAdmin.getWifiInfo());
				break;
			case R.id.getconfition:
				getConfiguration();
				break;
			case R.id.scan:// 扫描网络
				getAllNetWorkList();
				break;
			case R.id.start:// 打开Wifi
				mWifiAdmin.openWifi();
				Toast.makeText(WifiActivity.this,
						"当前wifi状态为：" + mWifiAdmin.checkState(), 1).show();
				break;
			case R.id.stop:// 关闭Wifi
				mWifiAdmin.closeWifi();
				Toast.makeText(WifiActivity.this,
						"当前wifi状态为：" + mWifiAdmin.checkState(), 1).show();
				break;
			case R.id.check:// Wifi状态
				Toast.makeText(WifiActivity.this,
						"当前wifi状态为：" + mWifiAdmin.checkState(), 1).show();
				break;
			case R.id.wifi_connect:
				try {
					connectWifi();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case R.id.connect_old_wifi:
				try {
					int netId = Integer.parseInt(etType.getText().toString());
					WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
					manager.enableNetwork(netId, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	}

	private void connectWifi() {
		mWifiAdmin.openWifi();
		int type = Integer.valueOf(etType.getText().toString());
		mWifiAdmin.addNetWork(mWifiAdmin.createWifiInfo(etSsid.getText()
				.toString(), etPassword.getText().toString(), type));
	}

	public void getAllNetWorkList() {
		// 开始扫描网络
		mWifiAdmin.startScan();
		List<ScanResult> list = mWifiAdmin.getWifiList();
		StringBuffer sb = new StringBuffer();
		if (list != null) {
			ScanResult mScanResult;
			for (int i = 0; i < list.size(); i++) {
				// 得到扫描结果
				mScanResult = list.get(i);
				sb = sb.append(i + ":mac->" + mScanResult.BSSID + "\n")
						.append("  ssid->" + mScanResult.SSID + "\n")
						.append(mScanResult.toString() + "\n")
						.append("信号强度->"
								+ WifiManager.calculateSignalLevel(
										mScanResult.level, 100) + "\n")
						.append("信道->"
								+ getChannelByFrequency(mScanResult.frequency)
								+ "\n")
						.append("是否加密->"
								+ isWifiEncrypted(mScanResult.capabilities)
								+ "\n")
						.append("强度->" + mScanResult.level + "dbm\n")
						.append("\n\n");

			}
			allNetWork.setText("扫描到的wifi网络：\n" + sb.toString());
		}
	}

	public static boolean isWifiEncrypted(String capabilities) {
		if (capabilities != null) {
			if (capabilities.contains("WPA") || capabilities.contains("wpa")) {
				return true;
			} else if (capabilities.contains("WEP")
					|| capabilities.contains("wep")) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据频率获得信道
	 * 
	 * @param frequency
	 * @return
	 */
	public static int getChannelByFrequency(int frequency) {
		int channel = -1;
		switch (frequency) {
		case 2412:
			channel = 1;
			break;
		case 2417:
			channel = 2;
			break;
		case 2422:
			channel = 3;
			break;
		case 2427:
			channel = 4;
			break;
		case 2432:
			channel = 5;
			break;
		case 2437:
			channel = 6;
			break;
		case 2442:
			channel = 7;
			break;
		case 2447:
			channel = 8;
			break;
		case 2452:
			channel = 9;
			break;
		case 2457:
			channel = 10;
			break;
		case 2462:
			channel = 11;
			break;
		case 2467:
			channel = 12;
			break;
		case 2472:
			channel = 13;
			break;
		case 2484:
			channel = 14;
			break;
		case 5745:
			channel = 149;
			break;
		case 5765:
			channel = 153;
			break;
		case 5785:
			channel = 157;
			break;
		case 5805:
			channel = 161;
			break;
		case 5825:
			channel = 165;
			break;
		}
		return channel;
	}

	public void getConfiguration() {
		mWifiAdmin.startScan();
		List<WifiConfiguration> list = mWifiAdmin.getConfiguration();
		StringBuffer sb = new StringBuffer();
		if (list != null) {
			WifiConfiguration result;
			for (int i = 0; i < list.size(); i++) {
				// 得到扫描结果
				result = list.get(i);
				sb = sb.append(i + ":mac->" + result.BSSID + "\n")
						.append("  ssid->" + result.SSID + "\n")
						.append("  networkId->" + result.networkId + "\n")
						.append("  status->" + result.status + "\n")
						.append("  preSharedKey->" + result.preSharedKey + "\n")
						.append("  priority->" + result.priority + "\n")
						.append("  allowedKeyManagement->"
								+ result.allowedKeyManagement.toString() + "\n")
						.append("  allowedAuthAlgorithms->"
								+ result.allowedAuthAlgorithms.toString()
								+ "\n")
						.append("  allowedGroupCiphers->"
								+ result.allowedGroupCiphers.toString() + "\n")
						.append("  allowedPairwiseCiphers->"
								+ result.allowedPairwiseCiphers.toString()
								+ "\n")
						.append("  allowedProtocols->"
								+ result.allowedProtocols.toString() + "\n");
				String[] wepKeys = result.wepKeys;
				sb.append("  wepKeys->{");
				for (int j = 0; j < wepKeys.length; j++) {
					sb.append(wepKeys[j] + ", ");
				}
				sb.append("}\n\n");
				sb.append(result.toString() + "\n\n");
			}
			allNetWork.setText("扫描到的配置好的列表：\n" + sb.toString());
		}
	}
}