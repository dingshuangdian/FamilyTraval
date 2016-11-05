package com.familytraval.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.familytraval.bean.User;
import com.familytraval.common.MobileConstants;

/**
 * Created by dings on 2016/10/25.
 */

public class SaveData {
    public final static String KEY_IS_FIRST_STARTUP = "is_first_startup";
    private final static String TAG = "SPSaveData";
    static SharedPreferences mShare = null;

    private static SharedPreferences getShared(Context context) {
        if (mShare == null) {
            mShare = context.getSharedPreferences(MobileConstants.APP_NAME, Context.MODE_PRIVATE);
        }
        return mShare;
    }

    public static String getString(Context context, String key) {
        return getShared(context).getString(key, "");
    }

    public static boolean getValue(Context context, String key, boolean defaultValue) {
        return getShared(context).getBoolean(key, defaultValue);
    }

    public static boolean getValue(Context context, String key) {
        return getShared(context).getBoolean(key, false);
    }

    public static void putValue(Context context, String key, String val) {
        SharedPreferences.Editor editor = getShared(context).edit();
        editor.putString(key, val);
        editor.commit();
    }

    public static User loadUser(Context context) {

        User user = new User();
        user.setUserID(getShared(context).getString("userId", "-1"));
        user.setNickname(getShared(context).getString("nickName", user.getNickname()));
        String couponCount = getShared(context).getString("couponCount", "0");
        String userMoney = getShared(context).getString("userMoney", "0");
        String payPoints = getShared(context).getString("payPoints", "0");
        String level = getShared(context).getString("level", "0");
        String levelName = getShared(context).getString("levelName", "0");
        String token = getShared(context).getString("token", "0");
        user.setCouponCount(couponCount);
        user.setUserMoney(userMoney);
        user.setPayPoints(payPoints);
        user.setLevel(level);
        user.setLevelName(levelName);
        user.setToken(token);
        return user;
    }

    public static void clearUser(Context context) {
        SharedPreferences.Editor editor = getShared(context).edit();
        editor.putString("userId", "-1");
        editor.putString("nickName", "-1");
        editor.putString("couponCount", "0");
        editor.putString("userMoney", "0");
        editor.putString("payPoints", "0");
        editor.putString("level", "0");
        editor.putString("levelName", "");
        editor.putString("token", "");
        editor.commit();
    }

    public static void saveUser(Context context, String key, User user) {

        SharedPreferences.Editor editor = getShared(context).edit();
        editor.putString("userId", user.getUserID());
        editor.putString("nickName", user.getNickname());
        editor.putString("couponCount", String.valueOf(user.getCouponCount()));
        editor.putString("userMoney", String.valueOf(user.getUserMoney()));
        editor.putString("payPoints", String.valueOf(user.getPayPoints()));
        editor.putString("level", String.valueOf(user.getLevel()));
        editor.putString("levelName", String.valueOf(user.getLevelName()));
        editor.putString("token", user.getToken());
        editor.commit();
    }

    public static void putValue(Context context, String key, int val) {
        SharedPreferences.Editor editor = getShared(context).edit();
        editor.putInt(key, val);
        editor.commit();
    }

    public static void putValue(Context context, String key, float val) {
        SharedPreferences.Editor editor = getShared(context).edit();
        editor.putFloat(key, val);
        editor.commit();
    }

    public static void putValue(Context context, String key, boolean val) {
        SharedPreferences.Editor editor = getShared(context).edit();
        editor.putBoolean(key, val);
        editor.commit();
    }
}



