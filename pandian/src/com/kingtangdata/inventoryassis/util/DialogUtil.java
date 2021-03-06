package com.kingtangdata.inventoryassis.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;

/**
 * 
 * 对话框工具类
 * 
 * @author liyang
 * 
 */
public class DialogUtil {

	public AlertDialog dialog = null;

	public AlertDialog.Builder builder = null;

	public DialogUtil(Activity activity) {
		//如果页面已经关闭了再弹出dialog没有任何意义
		if(activity.isFinishing()){
			return;
		}
		
		if (builder == null) {
			builder = new AlertDialog.Builder(activity);
		}

		if (dialog == null) {
			dialog = builder.create();
		}
	}

	public void show(String title, String msg, String btnTxt,OnClickListener listener) {
		dialog.setTitle(title);
		dialog.setMessage(msg);
		dialog.setButton(btnTxt, listener);
		dialog.show();
	}

	public void show(Activity activity, String title, String msg,String leftBtnTxt, String rightBtnTxt,OnClickListener leftListener, OnClickListener rightListener) {
		dialog.setTitle(title);
		dialog.setMessage(msg);
		dialog.setButton(leftBtnTxt, leftListener);
		dialog.setButton2(rightBtnTxt, rightListener);
		dialog.show();
	}
}
