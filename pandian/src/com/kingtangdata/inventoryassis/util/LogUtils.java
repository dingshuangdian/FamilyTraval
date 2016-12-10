/**
 * 
 */
package com.kingtangdata.inventoryassis.util;

import android.util.Log;

/**
 * 
 * debug 工具类
 * @author leo
 * 
 */
public class LogUtils {

	public static boolean D = true;

	
	public static void logi(String tag, String msg) {
		if(D)
		Log.i(tag, msg);
	}
	
	public static void logd(String tag, String msg) {
		if(D)
			Log.d(tag, msg);
	}
	
	public static void logv(String tag, String msg) {
		if(D)
			Log.v(tag, msg);
	}
	
	public static void logw(String tag, String msg) {
		if(D)
			Log.w(tag, msg);
	}
	
	public static void loge(String tag, String msg) {
		Log.e(tag, msg);
	}
	
	public static void logi(Class<?> clazz, String msg) {
		Log.i(clazz.getSimpleName(), msg);
	}

	public static void logi(Class<?> clazz, String msg, Throwable tr) {
		Log.i(clazz.getSimpleName(), msg, tr);
	}

	public static void logi(Class<?> clazz, Exception e) {
		Log.i(clazz.getSimpleName(), e.getLocalizedMessage());
	}

	public static void logd(Class<?> clazz, String msg) {
		Log.d(clazz.getSimpleName(), msg);
	}

	public static void logd(Class<?> clazz, String msg, Throwable tr) {
		Log.d(clazz.getSimpleName(), msg, tr);
	}

	public static void logd(Class<?> clazz, Exception e) {
		Log.d(clazz.getSimpleName(), e.getLocalizedMessage());
	}

	public static void loge(Class<?> clazz, String msg) {
		Log.e(clazz.getSimpleName(), msg);
	}

	public static void loge(Class<?> clazz, String msg, Throwable tr) {
		Log.e(clazz.getSimpleName(), msg, tr);
	}

	public static void loge(Class<?> clazz, Exception e) {
		Log.e(clazz.getSimpleName(), e.getLocalizedMessage());
	}

	public static void logw(Class<?> clazz, String msg) {
		Log.w(clazz.getSimpleName(), msg);
	}

	public static void logw(Class<?> clazz, String msg, Throwable tr) {
		Log.w(clazz.getSimpleName(), msg, tr);
	}

	public static void logw(Class<?> clazz, Exception e) {
		Log.w(clazz.getSimpleName(), e.getLocalizedMessage());
	}
	

	/**
	 * 输出完整的错误堆栈
	 * @param e
	 * @return
	 */
	public static String getStackTrace(Throwable e) {
		
		StringBuffer stack = new StringBuffer();
		stack.append(e);
		stack.append("\r\n");
		stack.append(e.getMessage());
		stack.append("\r\n");

		Throwable rootCause = e.getCause();

		while (rootCause != null) {
			stack.append("Root Cause:\r\n");
			stack.append(rootCause);
			stack.append("\r\n");
			stack.append(rootCause.getMessage());
			stack.append("\r\n");
			stack.append("StackTrace:\r\n");
			stack.append(rootCause);
			stack.append("\r\n");
			rootCause = rootCause.getCause();
		}

		for (int i = 0; i < e.getStackTrace().length; i++) {
			stack.append(e.getStackTrace()[i].toString());
			stack.append("\r\n");
		}
		
		return stack.toString();
	}
}
