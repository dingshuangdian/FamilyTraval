package com.familytraval.common;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

import cn.smssdk.SMSSDK;

/**
 * Created by dings on 2016/10/24.
 */

public class MyAppContext extends Application {
    private static MyAppContext app;

    public MyAppContext() {
        app = this;
    }

    public static synchronized MyAppContext getInstance() {
        if (app == null) {
            app = new MyAppContext();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SMSSDK.initSDK(this, "17ed216af0524", "3aee8204fdf147bb586ee03fd8658d84");
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}

