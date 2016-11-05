package com.familytraval.bean;

import com.familytraval.callback.Model;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by dings on 2016/10/26.
 */

public class Exchange implements Model {

    String exchangeId;
    String orderId;
    String orderSN;
    String goodsId;
    String goodsName;
    /**
     * 0:退货 ,  1:换货
     */
    String type;

    /**
     * 退换货原因
     */
    String reason;

    /**
     * 申请时间
     */
    String addtime;

    /**
     * 0:申请中, 1:处理中, 2:已完成
     */
    String status;

    /**
     * 客服备注
     */
    String remark;

    //问题图片
    List<String> images;
    JSONArray imageArray;


    @Override
    public String[] replaceKeyFromPropertyName() {
        return new String[]{
                "orderId", "order_id",
                "orderSN", "order_sn",
                "goodsId", "goods_id",
                "exchangeId", "id",
                "goodsName", "goods_name",
                "imageArray", "imgs"
        };
    }


    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderSN() {
        return orderSN;
    }

    public void setOrderSN(String orderSN) {
        this.orderSN = orderSN;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public JSONArray getImageArray() {
        return imageArray;
    }

    public void setImageArray(JSONArray imageArray) {
        this.imageArray = imageArray;
    }
}
