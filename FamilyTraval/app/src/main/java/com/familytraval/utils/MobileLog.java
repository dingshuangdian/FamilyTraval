package com.familytraval.utils;

import android.util.Log;

/**
 * Created by dings on 2016/10/25.
 */

public class MobileLog {
    //发布的时候改成false
    public static boolean DEBUG = true;

    public static void i(String TAG, String msg) {
        if (DEBUG) Log.i(TAG, msg);
    }

    public static void e(String TAG, String msg) {
        Log.e(TAG, msg);
    }
}
