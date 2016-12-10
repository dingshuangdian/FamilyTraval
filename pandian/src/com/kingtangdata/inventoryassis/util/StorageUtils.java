package com.kingtangdata.inventoryassis.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author liyang
 * @since 2013-10-30
 * @description 用于保存信息到本地文件
 */
public class StorageUtils {
	/**
	 * 文件名
	 */
	private static final String FILE_NAME = "app_info";

	/**
	 * 专门用来读写用户账号
	 */
	public static final String USER_ACCOUNT = "user_account";
	
	/**
	 * 专门用来读写用户密码
	 */
	public static final String USER_PASSWORD = "user_pwd";
	
	/**
	 * 专门用来读写登录用户id
	 */
	public static final String USER_ID = "user_id";
	
	/**
	 * 是否记住密码
	 */
	public static final String IS_REMEMBER = "is_remember";
	
	/**
	 * 服务器ip地址
	 */
	public static final String IP_ADDRESS = "ip_address";
	
	/**
	 * 端口号
	 */
	public static final String PORT = "port";
	
	/**
	 * 应用名
	 */
	public static final String APP = "eam";	
	
	
	
	public static final String CHECK_ID = "check_id";
	
	
	//背景路径的保存
	public static final String BG_PATH = "bg_path";
	
	
	/**
	 * 屏幕密度
	 */
	public static final String DPI = "dpi";
	
	//用户数据是否初始化的标志
	public static final String ISINIT = "isinit"; 

	public static void setProperty(String key, String value, Context context) {
		SharedPreferences preferences = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void setProperty(String key, boolean value, Context context) {
		SharedPreferences preferences = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean getBoolean(String key, Context context) {
		SharedPreferences preferences = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return preferences.getBoolean(key, false);
	}

	public static String getString(String key, Context context,String defaultStr) {
		SharedPreferences preferences = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return preferences.getString(key, defaultStr);
	}

	public static int getInt(String key, Context context) {
		SharedPreferences preferences = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return preferences.getInt(key, 0);
	}

	public static void setProperty(String key, int value, Context context) {
		SharedPreferences preferences = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static void removeProperty(String key,Context context) {
		SharedPreferences preferences = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.remove(key);
		editor.commit();
	}

	/**
	 * 清除所有登陆信息
	 */
	public static void clearAll(Context context){
		removeProperty(USER_ACCOUNT, context);
		removeProperty(USER_PASSWORD, context);
		removeProperty(IS_REMEMBER, context);
	}
}
