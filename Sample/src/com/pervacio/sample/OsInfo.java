package com.pervacio.sample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.util.Log;

public class OsInfo {
	private static final String tag = "OsInfo";
	private String releaseVersion, buildVersion, kernalVersion, firmware;
	private int sdk;

	public OsInfo() {
		releaseVersion = android.os.Build.VERSION.RELEASE;
		sdk = android.os.Build.VERSION.SDK_INT;
		buildVersion = android.os.Build.DISPLAY;
		kernalVersion = readKernalVersion();
		firmware = readFirmware();
	}
	
	

	public String getReleaseVersion() {
		return releaseVersion;
	}



	public String getBuildVersion() {
		return buildVersion;
	}



	public String getKernalVersion() {
		return kernalVersion;
	}



	public String getFirmware() {
		return firmware;
	}



	public int getSdk() {
		return sdk;
	}



	private String readFirmware() {
		// SystemProperties.get("gsm.version.baseband")
		try {
			Class class1 = Class.forName("android.os.SystemProperties");
			Method declaredMethod = class1.getDeclaredMethod("get", String.class);
			declaredMethod.setAccessible(true);
			Object invoke = declaredMethod.invoke(null, "gsm.version.baseband");
			return invoke.toString();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String readKernalVersion() {
		String line = null;
		try {
			BufferedReader version = new BufferedReader(new FileReader("/proc/version"));

			while ((line = version.readLine()) != null) {
				break;
			}
			version.close();
		} catch (FileNotFoundException e) {
			Log.d(tag, "Cannot find /proc/version...");
		} catch (IOException e) {
			Log.d(tag, "Ran into problems reading /proc/version...");
		}
		if (line != null) {
			line = line.substring("Linux version ".length());
			line = line.substring(0, line.indexOf(" "));
		}
		return line;
	}
}
