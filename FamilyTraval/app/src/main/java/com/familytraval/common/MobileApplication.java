package com.familytraval.common;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.familytraval.bean.Category;
import com.familytraval.bean.Collect;
import com.familytraval.bean.Plugin;
import com.familytraval.bean.ServiceConfig;
import com.familytraval.bean.User;
import com.familytraval.data.SaveData;
import com.familytraval.http.MobileHttptRequest;

import com.soubao.tpshop.utils.SPMyFileTool;
import com.soubao.tpshop.utils.SPStringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by dings on 2016/10/25.
 */

public class MobileApplication extends Application {

    private static MobileApplication instance;

    public List<Collect> collects;

    public boolean isLogined;
    private User loginUser;
    private String deviceId;
    DisplayMetrics mDisplayMetrics;
    public JSONObject json;
    public JSONObject json1;
    public Map<String, String> map;
    public List list;
    public JSONArray jsonArray;
    public int productListType = 1; //1: 商品列表, 2: 产品搜索结果列表
    private long cutServiceTime;
    private List<Category> topCategorys;//分类左边菜单一级分类
    private List<ServiceConfig> serviceConfigs;
    private Map<String, Plugin> servicePluginMap;
    private TelephonyManager telephonyManager;

    @Override
    public void onCreate() {
        super.onCreate();

        /** 初始化 Vollery 网络请求 */
        MobileHttptRequest.init(getApplicationContext());
        /** 初始化 Facebook SimpleDraweeView 网络请求 */

        loginUser = SaveData.loadUser(getApplicationContext());
        if (SPStringUtils.isEmpty(loginUser.getUserID()) || loginUser.getUserID().equals("-1")) {
            isLogined = false;
        } else {
            isLogined = true;
        }
        instance = this;
        //初始化购物车管理类
        ShopCartManager.getInstance(getApplicationContext());


        PackageManager manager = this.getPackageManager();
        mDisplayMetrics = getResources().getDisplayMetrics();

        telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            String deviceId = telephonyManager.getDeviceId();
            SPMyFileTool.cacheValue(this, SPMyFileTool.key1, deviceId);
        }
    }

    public DisplayMetrics getDisplayMetrics() {
        return mDisplayMetrics;
    }

    public static MobileApplication getInstance() {
        return instance;
    }

    public List<ServiceConfig> getServiceConfigs() {
        return serviceConfigs;
    }

    public void setServiceConfigs(List<ServiceConfig> serviceConfigs) {
        this.serviceConfigs = serviceConfigs;
    }

    public Map<String, Plugin> getServicePluginMap() {
        return servicePluginMap;
    }

    public void setServicePluginMap(Map<String, Plugin> servicePluginMap) {
        this.servicePluginMap = servicePluginMap;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
        if (this.loginUser != null) {
            SaveData.saveUser(getApplicationContext(), "user", this.loginUser);
            isLogined = true;
        } else {
            isLogined = false;
        }
    }

    public String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    public String getSystemPackage() {
        return this.getPackageName();
    }

    //退出登录
    public void exitLogin() {
        loginUser = null;
        isLogined = false;
        SaveData.clearUser(getApplicationContext());

    }

    public List<Category> getTopCategorys() {
        return topCategorys;
    }

    public void setTopCategorys(List<Category> topCategorys) {
        this.topCategorys = topCategorys;
    }

    /**
     * 获取设备IMEI
     *
     * @return
     */
    public String getDeviceId() {
        if (telephonyManager != null) {
            deviceId = telephonyManager.getDeviceId();//String
        } else {
            deviceId = "unionid001";
        }
        return deviceId;
    }

    public long getCutServiceTime() {
        return cutServiceTime;
    }

    public void setCutServiceTime(long cutServiceTime) {
        this.cutServiceTime = cutServiceTime;
    }
}

