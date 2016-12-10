package com.kingtangdata.inventoryassis.util;

/*
 *@author liyang
 */

import android.app.AlertDialog;
import android.content.Context;

/**
 * 
 * author liyang
 */
public class MessageUtil {

	/**
	 * 设置显示异常
	 * 
	 * @param e
	 * @param tv
	 */
	public static void setException(Exception e, Context context) {
		if (context != null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("提示");
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setMessage(e.getMessage());
			builder.setPositiveButton("确定", null);
			builder.show();
		}
	}

	/**
	 * 设置其他提示信息
	 * 
	 * @param e
	 * @param tv
	 */
	public static void setMessage(String msg, Context context) {
		if (context != null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("提示");
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setMessage(msg);
			builder.setPositiveButton("确定", null);
			builder.show();
		}
	}
}
