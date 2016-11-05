package com.familytraval.bean;

import com.familytraval.callback.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dings on 2016/10/26.
 */

public class HomeCategory implements Model, Serializable {

    private int id;                //分类ID
    private String name;            //分类名称
    private List<Product> goodsList;            //该分类下的商品


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Product> parentId) {
        this.goodsList = parentId;
    }

    @Override
    public String[] replaceKeyFromPropertyName() {
        return new String[]{
                "goodsList", "goods_list",
        };
    }


}
