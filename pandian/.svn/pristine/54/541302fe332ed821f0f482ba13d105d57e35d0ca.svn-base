package com.kingtangdata.inventoryassis.base;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;

import com.kingtangdata.inventoryassis.util.LogUtils;

/**
 * @author liyang
 * @date 2013-11-11
 * @description Activity管理器
 */
public class ActivityManager {

	/** 用列表来管理activity */
	public static List<Activity> list = new ArrayList<Activity>();

	/**
	 * 在activity的oncreate方法中将其添加到list中
	 */
	public static void addActivity(Activity activity) {
		if (activity != null) {
			list.add(activity);
		}
	}

	/**
	 * 关闭所有的activity
	 */
	public static void closeAll() {
		for (Activity activity : list) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}

	/**
	 * 获取最近加入的activity
	 * 
	 * @return
	 */
	public static Activity getLatestActivity() {

		int i = list.size() - 1;

		if (i >= 0) {
			return list.get(i);
		}

		return null;
	}

	public static Activity getActivity(String activityName) {
		for (int i = list.size() - 1; i >= 0; i--) {
			Activity activity = list.get(i);
			String className = activity.getClass().getSimpleName();
			boolean isEqual = className.equals(activityName);
			if (isEqual) {
				return activity;
			}
		}
		return null;
	}

	public static void close(String activityName) {
		for (Activity activity : list) {
			String className = activity.getClass().getSimpleName();
			boolean isEqual = activityName.equals(className);
			if (!activity.isFinishing() && isEqual) {
				activity.finish();
			}
		}
	}

	public static void closeOthersExcept(String activityName) {
		for (Activity activity : list) {
			String className = activity.getClass().getSimpleName();
			boolean isEqual = activityName.equals(className);
			if (!activity.isFinishing() && !isEqual) {
				list.remove(activity);
				activity.finish();
			}
		}
	}

	/**
	 * 用来判断服务是否运行.
	 * 
	 * @param context
	 * 
	 * @param className
	 *            判断的服务名字
	 * 
	 * @return true 在运行 false 不在运行
	 */

	public static boolean isServiceRunning(Context mContext, String className) {

		boolean isRunning = false;

		android.app.ActivityManager activityManager = (android.app.ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);

		List<android.app.ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);

		if (serviceList.size() <= 0) {
			return false;
		}

		for (int i = 0; i < serviceList.size(); i++) {
			LogUtils.logd("ActivityManager", "className = "+serviceList.get(i).service.getClassName());
			
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}

		return isRunning;

	}
}
