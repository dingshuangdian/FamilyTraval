package com.familytraval.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by dings on 2016/10/26.
 */

public class SPUtils {

    public static String getHost(String url) {
        if (com.soubao.tpshop.utils.SPStringUtils.isEmpty(url)) {
            return null;
        }
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url = url.replaceAll("http://", "").replaceAll("https://", "");
        }
        return url;
    }


    /**
     * @throws
     * @Title: isNetworkAvaiable
     * @Description:(是否打开网络)
     * @param: @param pContext
     * @param: @return
     * @return: boolean
     */
    public static boolean isNetworkAvaiable(Context pContext) {
        boolean isAvaiable = false;
        ConnectivityManager cm = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            isAvaiable = true;
        }
        return isAvaiable;
    }


    public static String convertFullTimeFromPhpTime(long phpTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(phpTime * 1000));
    }

}

