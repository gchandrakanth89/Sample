package com.pervacio.sample;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;

public class StorageInfo {

	private static final String tag = "StorageInfo";
	private long internalStorageTotalSpace;
	private long internalStorageUsedSpace;
	private long internalStorageFreeSpace;

	private long externalStorageTotalSpace;
	private long externalStorageUsedSpace;
	private long externalStorageFreeSpace;

	public StorageInfo(Context context) {
		readInternalStorageInfo();
		readExternalStorageInfo(context);
	}

	public long getInternalStorageTotalSpace() {
		return internalStorageTotalSpace;
	}

	public long getInternalStorageUsedSpace() {
		return internalStorageUsedSpace;
	}

	public long getInternalStorageFreeSpace() {
		return internalStorageFreeSpace;
	}

	public long getExternalStorageTotalSpace() {
		return externalStorageTotalSpace;
	}

	public long getExternalStorageUsedSpace() {
		return externalStorageUsedSpace;
	}

	public long getExternalStorageFreeSpace() {
		return externalStorageFreeSpace;
	}

	private void readInternalStorageInfo() {
		File externalStorageDirectory = Environment.getExternalStorageDirectory();
		internalStorageTotalSpace = externalStorageDirectory.getTotalSpace() / (1024 * 1024);
		internalStorageFreeSpace = externalStorageDirectory.getFreeSpace() / (1024 * 1024);
		internalStorageUsedSpace = internalStorageTotalSpace - internalStorageFreeSpace;

	}

	private void readExternalStorageInfo(Context context) {
		String sdCardPath = getSDCardPath(context);
		File sdCard = new File(sdCardPath);

		externalStorageFreeSpace = sdCard.getFreeSpace() / (1024 * 1024);
		externalStorageTotalSpace = sdCard.getTotalSpace() / (1024 * 1024);
		externalStorageUsedSpace = externalStorageTotalSpace - externalStorageFreeSpace;
	}

	private String getSDCardPath(Context context) {
		try {
			StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
			Method method = storageManager.getClass().getDeclaredMethod("getVolumeList");
			method.setAccessible(true);
			Object[] volumes = (Object[]) method.invoke(storageManager);
			for (Object object : volumes) {
				Class<? extends Object> svCls = object.getClass();
				boolean removable = (Boolean) getDeclaredFieldValue("mRemovable", svCls, object);
				boolean emulated = (Boolean) getDeclaredFieldValue("mEmulated", svCls, object);
				boolean mounted = true;
				Object state = getDeclaredFieldValue("mState", svCls, object);
				if (state != null && !state.equals("mounted")) {
					mounted = false;
				}
				if (removable && !emulated && mounted) {
					String path = getDeclaredFieldValue("mPath", svCls, object).toString();
					if (!path.toUpperCase(Locale.US).contains("USB")) {
						return path;
					}
				}
			}
		} catch (Exception e) {
			Log.e(tag, "Exception in getSDCardPath(Context) : " + e.getMessage());
		}
		return "";
	}

	private static Object getDeclaredFieldValue(String fieldName, Class<?> cls, Object receiver) {
		try {
			Field field = cls.getDeclaredField(fieldName);
			field.setAccessible(true);
			Object object = field.get(receiver);
			return object;
		} catch (Exception e) {
		}
		return null;
	}
}
