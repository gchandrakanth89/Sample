package com.pervacio.sample;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String tag = "MainActivity";
	private static final int REQ_CODE_SPEECH_INPUT = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);

		AsyncTask asyncTask = null;

		Log.d(tag, "Chandu");

		try {
			PackageInfo pInfo = getPackageManager().getPackageInfo("com.verizon.mats", 0);
			String versionName = pInfo.versionName;
			int versionCode = pInfo.versionCode;
			Log.d(tag, versionName + "=Chandu=" + versionCode);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void onClickButton(View view) {
		String batteryCapacity = getBatteryCapacity();
		Log.d(tag, "Capacity = " + batteryCapacity);
	}

	public String getBatteryCapacity2() {
		String powerProfileClassName = "com.android.internal.os.PowerProfile";
		String capacity="";
		try {
			Class<?> powerPrifileClass = Class.forName(powerProfileClassName);
			Object poewrProfileObject = powerPrifileClass.getConstructor(Context.class).newInstance(this);
			Method getAveragePowerMethod = powerPrifileClass.getDeclaredMethod("getAveragePower", String.class);
			Object invoke = getAveragePowerMethod.invoke(poewrProfileObject, "battery.capacity");
			capacity=invoke.toString()+"mAh";
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return capacity;
	}

	public String getBatteryCapacity() {
		Object mPowerProfile_ = null;
		String result = "";
		final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";
		try {
			mPowerProfile_ = Class.forName(POWER_PROFILE_CLASS).getConstructor(Context.class).newInstance(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			double batteryCapacity = (Double) Class.forName(POWER_PROFILE_CLASS).getMethod("getAveragePower", String.class).invoke(mPowerProfile_,
					"battery.capacity");
			result = result + batteryCapacity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result + " mAh";
	}

	private void promptSpeechInput() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello");
		try {
			startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
		} catch (ActivityNotFoundException a) {
			Toast.makeText(getApplicationContext(), "This feature not available", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Receiving speech input
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case REQ_CODE_SPEECH_INPUT: {
			if (resultCode == RESULT_OK && null != data) {
				ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				Log.d(tag, "Output " + result.get(0));
				Toast.makeText(this, result.get(0), Toast.LENGTH_LONG).show();
			}
			break;
		}

		}
	}

	public String getMin(Context context) {
		TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		Class<? extends TelephonyManager> managerClass = manager.getClass();
		try {
			Method getCdmaMdnMethod = managerClass.getDeclaredMethod("getCdmaMin");
			getCdmaMdnMethod.setAccessible(true);
			Object cdmaMdnStringObject = getCdmaMdnMethod.invoke(manager);
			String cdmaMin = cdmaMdnStringObject.toString();

			Log.d(tag, "MIN = " + cdmaMin);
			return cdmaMin;

		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getMdn(Context context) {
		TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		Class<? extends TelephonyManager> managerClass = manager.getClass();
		try {
			Method getCdmaMdnMethod = managerClass.getDeclaredMethod("getCdmaMdn");
			getCdmaMdnMethod.setAccessible(true);
			Object cdmaMdnStringObject = getCdmaMdnMethod.invoke(manager);
			String cdmaMdn = cdmaMdnStringObject.toString();

			Log.d(tag, "MDN = " + cdmaMdn);
			return cdmaMdn;

		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * get bluetooth adapter MAC address Need "android.permission.BLUETOOTH"
	 * permission
	 * 
	 * @return MAC address String
	 */
	public static String getBluetoothMacAddress() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// if device does not support Bluetooth
		if (mBluetoothAdapter == null) {
			Log.d(tag, "device does not support bluetooth");
			return null;
		}

		return mBluetoothAdapter.getAddress();
	}

	/**
	 * Requires "android.permission.ACCESS_WIFI_STATE" permission.
	 * 
	 * @param context
	 * @return
	 */
	public static String getWifiMacAddress(Context context) {
		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		String address = info.getMacAddress();
		return address;
	}

	private void getApnNames() {
		Log.d(tag, "getApnNames");
		Cursor c = getContentResolver().query(Uri.parse("content://telephony/carriers/current"), null, null, null, null);

		String[] columnNames = c.getColumnNames();
		for (int i = 0; i < columnNames.length; i++) {
			Log.d(tag, i + " " + c.getColumnName(i));
		}

		int index = c.getColumnIndex("name");
		while (c.moveToNext()) {
			String apnName = c.getString(index);
			Log.d(tag, apnName);

		}
		c.close();

	}

}
