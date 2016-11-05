package com.familytraval.Model;

import com.familytraval.callback.Model;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by dings on 2016/10/25.
 */

public class SPFilter implements Model {
    private String filterId;
    private String name;
    private List<FilterItem> items;
    private JSONArray itemJsonArray;


    public SPFilter() {
    }

    ;

    public SPFilter(int type, String filterId, String name, List<FilterItem> items) {
        this.type = type;
        this.name = name;
        this.filterId = filterId;
        this.items = items;

    }


    /**
     * 类型 type ->  1:选中菜单 , 2:规格 ,3:属性 , 4:品牌 , 5:价格
     */
    private Integer type;

    @Override
    public String[] replaceKeyFromPropertyName() {
        return new String[]{"itemJsonArray", "item"};
    }


    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FilterItem> getItems() {
        return items;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public JSONArray getItemJsonArray() {
        return itemJsonArray;
    }

    public void setItemJsonArray(JSONArray itemJsonArray) {
        this.itemJsonArray = itemJsonArray;
    }

    public void setItems(List<FilterItem> items) {
        this.items = items;
    }
}
