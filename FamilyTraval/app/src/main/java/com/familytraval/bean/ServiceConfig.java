package com.familytraval.bean;

import com.familytraval.callback.Model;

/**
 * Created by dings on 2016/10/25.
 */

public class ServiceConfig implements Model {

    private String configID;

    private String name;

    private String value;

    @Override
    public String[] replaceKeyFromPropertyName() {
        return new String[]{
                "configID", "id"
        };
    }

    public String getConfigID() {
        return configID;
    }

    public void setConfigID(String configID) {
        this.configID = configID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

