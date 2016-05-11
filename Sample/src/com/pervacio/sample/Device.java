package com.pervacio.sample;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Build;

public class Device {
	private String brand, manufacturer, model;
	private long ramSize;

	public Device(Context context) {
		brand = android.os.Build.BRAND;
		manufacturer = android.os.Build.MANUFACTURER;
		model = android.os.Build.MODEL;
		ramSize = readRamSize(context);
	}

	public String getBrand() {
		return brand;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getModel() {
		return model;
	}

	/**
	 * @return Returns the ram size in MB's
	 */
	public long getRamSize() {
		return ramSize;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private static long readRamSize(Context context) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			MemoryInfo mi = new MemoryInfo();
			ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityManager.getMemoryInfo(mi);
			long totalMegs = mi.totalMem / 1048576L;
			return totalMegs;
		} else {
			return -1;
		}
	}

}
