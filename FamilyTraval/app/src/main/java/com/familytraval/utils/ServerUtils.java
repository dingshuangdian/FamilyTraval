package com.familytraval.utils;

import com.familytraval.bean.ServiceConfig;
import com.familytraval.common.MobileApplication;
import com.familytraval.common.MyAppContext;

import java.util.List;

/**
 * Created by dings on 2016/10/25.
 */

public class ServerUtils {

    final static String CONFIG_TPSHOP_HTTP = "tpshop_http";
    final static String CONFIG_QQ = "qq";
    final static String CONFIG_QQ1 = "qq1";
    final static String CONFIG_STORE_NAME = "store_name";
    final static String CONFIG_POINT_RATE = "point_rate";//积分抵扣金额

    final static String CONFIG_PHONE = "phone";
    final static String CONFIG_ADDRESS = "address";
    final static String CONFIG_WORK_TIME = "worktime";
    final static String CONFIG_HOT_KEYWORDS = "hot_keywords";
    final static String CONFIG_KEY_SMS_TIME_OUT = "sms_time_out";
    final static String CONFIG_KEY_REG_SMS_ENABLE = "sms_enable";

    /**
     * 获取联系客服QQ
     *
     * @return return value description
     */
    public static String getCustomerQQ() {
        return getConfigValue(CONFIG_QQ);
    }


    /**
     * 获取商城名称
     *
     * @return return value description
     */
    public static String getStoreName() {
        return getConfigValue(CONFIG_STORE_NAME);
    }


    /**
     * 获取积分抵扣金额
     *
     * @return return value description
     */
    public static String getPointRate() {
        return getConfigValue(CONFIG_POINT_RATE);
    }

    /**
     * 根据名称获取配置的值
     *
     * @param name name description
     * @return return value description
     */
    public static String getConfigValue(String name) {

        /**List<ServiceConfig> serviceConfigs = MobileApplication.getInstance().getServiceConfigs();

         if (serviceConfigs != null && serviceConfigs.size() > 0) {
         for (ServiceConfig config : serviceConfigs) {
         if (name.equals(config.getName())) {
         return config.getValue();
         }
         }
         }*/
        return name;
    }

    /**
     * 上班时间
     *
     * @return return value description
     */
    public static String getWorkTime() {
        String worktime = getConfigValue(CONFIG_WORK_TIME);
        if (SPStringUtils.isEmpty(worktime)) {
            worktime = "(周一致周五) 08:00-19:00 (周六日) 休息";
        }
        return worktime;
    }


    /**
     * 售后收货地址
     *
     * @return return value description
     */
    public static String getAddress() {
        return getConfigValue(CONFIG_ADDRESS);
    }

    /**
     * 售后客服电话
     *
     * @return return value description
     */
    public static String getServicePhone() {
        return getConfigValue(CONFIG_PHONE);
    }


    /**
     * 搜索关键词
     *
     * @return return value description
     */
    public static List<String> getHotKeyword() {
        List<String> hotwords = null;
        String hotword = getConfigValue(CONFIG_HOT_KEYWORDS);
        if (!SPStringUtils.isEmpty(hotword)) {
            hotwords = SPStringUtils.stringToList(hotword, "|");
        }
        return hotwords;
    }


    /**
     * 注册短信, 超时时间(单位:s)
     *
     * @return
     */
    public static int getSmsTimeOut() {

        String timeout = getConfigValue(CONFIG_KEY_SMS_TIME_OUT);
        if (!SPStringUtils.isEmpty(timeout)) {
            return Integer.valueOf(timeout);
        }
        return 0;
    }

    /**
     * 是否启用短信验证码
     *
     * @return
     */
    public static boolean enableSmsCheckCode() {
        String smsEnable = getConfigValue(CONFIG_KEY_REG_SMS_ENABLE);
        if (!SPStringUtils.isEmpty(smsEnable)) {
            return Boolean.valueOf(smsEnable);
        }
        return false;
    }
}

