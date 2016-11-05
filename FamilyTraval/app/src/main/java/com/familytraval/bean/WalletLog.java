package com.familytraval.bean;

import com.familytraval.callback.Model;

/**
 * Created by dings on 2016/10/26.
 */

public class WalletLog implements Model {

    //交易余额
    String userMoney;

    //交易积分
    String payPoints;

    //交易描述
    String desc;

    //交易时间
    String changeTime;

    //订单SN
    String orderSN;

    @Override
    public String[] replaceKeyFromPropertyName() {
        return new String[]{
                "userMoney", "user_money",
                "payPoints", "pay_points",
                "changeTime", "change_time",
                "orderSN", "order_sn",
        };
    }

    public String getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(String userMoney) {
        this.userMoney = userMoney;
    }

    public String getPayPoints() {
        return payPoints;
    }

    public void setPayPoints(String payPoints) {
        this.payPoints = payPoints;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getOrderSN() {
        return orderSN;
    }

    public void setOrderSN(String orderSN) {
        this.orderSN = orderSN;
    }
}

