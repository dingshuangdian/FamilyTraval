package com.familytraval.common;

import java.io.File;
import java.util.List;

/**
 * Created by dings on 2016/10/26.
 */

public class CommentCondition {

    String goodsID; //商品ID
    String orderID; //订单ID
    String comment; //评论ID
    Integer goodsRank;
    Integer serviceRank;
    Integer expressRank;
    List<File> images;
    String specKey; //商品规格

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getGoodsRank() {
        return goodsRank;
    }

    public void setGoodsRank(Integer goodsRank) {
        this.goodsRank = goodsRank;
    }

    public Integer getServiceRank() {
        return serviceRank;
    }

    public void setServiceRank(Integer serviceRank) {
        this.serviceRank = serviceRank;
    }

    public Integer getExpressRank() {
        return expressRank;
    }

    public void setExpressRank(Integer expressRank) {
        this.expressRank = expressRank;
    }

    public List<File> getImages() {
        return images;
    }

    public void setImages(List<File> images) {
        this.images = images;
    }

    public String getSpecKey() {
        return specKey;
    }

    public void setSpecKey(String specKey) {
        this.specKey = specKey;
    }
}

