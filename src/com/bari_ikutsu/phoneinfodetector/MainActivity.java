package com.bari_ikutsu.phoneinfodetector;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

		String telno = telephonyManager.getLine1Number();
		String iccid = telephonyManager.getSimSerialNumber();
		String imei = telephonyManager.getDeviceId();
		String mcc_mnc = telephonyManager.getSimOperator();

		WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		int ipAddress = info.getIpAddress();
		String ipAddressStr = String.format("%d.%d.%d.%d",
				(ipAddress & 0xff),
				(ipAddress >> 8 & 0xff),
				(ipAddress >> 16 & 0xff),
				(ipAddress >> 24 & 0xff));

		String macAddress = info.getMacAddress();


		BluetoothAdapter myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		String btMacAddress = myBluetoothAdapter.getAddress();

		String android_id = android.provider.Settings.Secure.getString(
				  getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

		String msgTemplate = "電話番号：\n%s\nICCID：\n%s\nIMEI：\n%s\nMCC/MNC：\n%s\nIPアドレス：\n%s\nWi-Fi MACアドレス：\n%s\nBluetooth MACアドレス：\n%s\nAndroid ID:\n%s\n";
		String msg = String.format(msgTemplate, new Object[]{telno, iccid, imei, mcc_mnc, ipAddressStr, macAddress, btMacAddress, android_id});

		TextView textViewMsg = (TextView)findViewById(R.id.textViewMsg);
		textViewMsg.setText(msg);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}
}
