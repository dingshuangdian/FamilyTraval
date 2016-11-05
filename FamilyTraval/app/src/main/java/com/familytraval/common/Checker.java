package com.familytraval.common;

import android.os.Handler;
import android.os.Message;

import com.soubao.tpshop.utils.SPStringUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dings on 2016/10/26.
 */

public class Checker {
    private String TAG = "'Checker";

    static {
        System.loadLibrary("curl");
        System.loadLibrary("SPMobile");
    }

    @SuppressWarnings("JniMissingFunction")
    public static native boolean Init();

    @SuppressWarnings("JniMissingFunction")
    public static native int Check(String header, String url);

    @SuppressWarnings("JniMissingFunction")
    public static native void Finished();

    /**public void responseMessage(String mesage) {
     try {
     if (!SPStringUtils.isEmpty(mesage) && SPMainActivity.getmInstance() != null && SPMainActivity.getmInstance().mHandler != null) {
     Handler handler = SPMainActivity.getmInstance().mHandler;
     Message message = handler.obtainMessage(MobileConstants.MSG_CODE_SHOW);
     JSONObject jsonObject = new JSONObject(mesage);
     if (jsonObject.has("msg")) {
     message.obj = jsonObject.getString("msg");
     }
     handler.sendMessage(message);
     }
     } catch (JSONException e) {
     e.printStackTrace();
     }


     }*/

}

