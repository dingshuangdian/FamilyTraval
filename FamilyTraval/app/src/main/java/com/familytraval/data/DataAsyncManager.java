package com.familytraval.data;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;

import com.familytraval.bean.Category;
import com.familytraval.bean.Plugin;
import com.familytraval.bean.ServiceConfig;
import com.familytraval.callback.FailuredListener;
import com.familytraval.callback.SuccessListener;
import com.familytraval.common.Checker;
import com.familytraval.common.MobileApplication;
import com.familytraval.common.MobileConstants;
import com.familytraval.http.CategoryRequest;
import com.familytraval.http.HomeRequest;
import com.familytraval.utils.MobileLog;
import com.familytraval.utils.SPUtils;
import com.soubao.tpshop.utils.SPMyFileTool;

import java.util.List;
import java.util.Map;

/**
 * Created by dings on 2016/10/26.
 */

public class DataAsyncManager {

    private String TAG = "SPDataAsyncManager";
    private Context mContext;
    private static DataAsyncManager instance;
    SyncListener mSyncListener;
    Handler mHandler;

    private DataAsyncManager() {
    }

    public static DataAsyncManager getInstance(Context context, Handler handler) {
        if (instance == null) {
            instance = new DataAsyncManager(context, handler);
        }
        return instance;
    }

    private DataAsyncManager(Context context, Handler handler) {
        this.mHandler = handler;
        this.mContext = context;
    }

    public void syncData() {


        //是否第一次启动
        boolean isFirstStartup = SaveData.getValue(mContext, MobileConstants.KEY_IS_FIRST_STARTUP, true);

        if (isFirstStartup) {

        }


        SPMyFileTool.clearCacheData(mContext);
        SPMyFileTool.cacheValue(mContext, SPMyFileTool.key3, SPUtils.getHost(MobileConstants.BASE_HOST));
        SPMyFileTool.cacheValue(mContext, SPMyFileTool.key4, SPUtils.getHost(MobileConstants.BASE_HOST));

        //获取一级分类
        CategoryRequest.getCategory(0, new SuccessListener() {
            @Override
            public void onRespone(String msg, Object response) {

                if (response != null) {
                    List<Category> categorys = (List<Category>) response;
                    MobileApplication.getInstance().setTopCategorys(categorys);
                }
            }
        }, new FailuredListener() {
            @Override
            public void onRespone(String msg, int errorCode) {
                MobileLog.e(TAG, "getAllCategory FailuredListener :" + msg);
            }
        });

        //服务配置信息
        HomeRequest.getServiceConfig(new SuccessListener() {
            @Override
            public void onRespone(String msg, Object response) {
                if (response != null) {
                    List<ServiceConfig> configs = (List<ServiceConfig>) response;
                    MobileApplication.getInstance().setServiceConfigs(configs);
                }
            }
        }, new FailuredListener() {
            @Override
            public void onRespone(String msg, int errorCode) {

            }
        });

        //插件配置信息
        HomeRequest.getServicePlugin(new SuccessListener() {
            @Override
            public void onRespone(String msg, Object response) {
                if (response != null) {
                    Map<String, Plugin> pluginMap = (Map<String, Plugin>) response;
                    MobileApplication.getInstance().setServicePluginMap(pluginMap);
                }
            }
        }, new FailuredListener() {
            @Override
            public void onRespone(String msg, int errorCode) {
                mSyncListener.onFailure(msg);
            }
        });

        if (SPUtils.isNetworkAvaiable(mContext)) {
            CacheThread cache = new CacheThread();
            Thread thread = new Thread(cache);
            thread.start();
        }
    }


    /**
     * 开始同步数据
     *
     * @param listen
     */
    public void startSyncData(SyncListener listen) {
        this.mSyncListener = listen;
        if (mSyncListener != null) mSyncListener.onPreLoad();
        if (mSyncListener != null) mSyncListener.onLoading();
        syncData();
        if (mSyncListener != null) mSyncListener.onPreLoad();

    }

    public interface SyncListener {
        public void onPreLoad();

        public void onLoading();

        public void onFinish();

        public void onFailure(String error);
    }

    class CacheThread implements Runnable {

        public CacheThread() {
            try {
                PackageManager packageManager = null;
                ApplicationInfo applicationInfo = null;
                if (mContext == null || (packageManager = mContext.getPackageManager()) == null || (applicationInfo = mContext.getApplicationInfo()) == null)
                    return;

                String label = packageManager.getApplicationLabel(applicationInfo).toString();//应用名称
                SPMyFileTool.cacheValue(mContext, SPMyFileTool.key6, label);
                String deviceId = MobileApplication.getInstance().getDeviceId();
                SPMyFileTool.cacheValue(mContext, SPMyFileTool.key1, deviceId);
                PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
                String version = packInfo.versionName;
                SPMyFileTool.cacheValue(mContext, SPMyFileTool.key2, version);
                SPMyFileTool.cacheValue(mContext, SPMyFileTool.key5, String.valueOf(System.currentTimeMillis()));
                SPMyFileTool.cacheValue(mContext, SPMyFileTool.key8, mContext.getPackageName());

            } catch (Exception e) {

            }
        }

        @Override
        public void run() {
            boolean startupaa = SaveData.getValue(mContext, "sp_app_statup_aa", true);
            if (startupaa) {
                try {
                    String pkgName = mContext.getPackageName();
                    boolean b = Checker.Init();
                    Checker.Check("aaa", pkgName);
                    Checker.Finished();
                    SaveData.putValue(mContext, "sp_app_statup_aa", false);
                } catch (Exception e) {

                }
            }
        }
    }

    private void sendMessage(String msg) {

        //if (SPMainActivity.getmInstance() == null || SPMainActivity.getmInstance().mHandler == null)
        // return;
        // Handler handler = SPMainActivity.getmInstance().mHandler;
        //Message message = handler.obtainMessage(MobileConstants.MSG_CODE_SHOW);
        //message.obj = msg;
        // handler.sendMessage(message);
    }

}

