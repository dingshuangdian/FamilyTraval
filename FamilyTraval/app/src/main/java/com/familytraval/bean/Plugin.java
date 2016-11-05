package com.familytraval.bean;

import com.familytraval.callback.Model;

import org.json.JSONObject;

/**
 * Created by dings on 2016/10/25.
 */

public class Plugin implements Model {

    String  code;
    String  name;
    String  icon;
    JSONObject configValue;
    String  status;
    String  type;

    @Override
    public String[] replaceKeyFromPropertyName() {
        return new String[]{"configValue","config_value"};
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public JSONObject getConfigValue() {
        return configValue;
    }

    public void setConfigValue(JSONObject configValue) {
        this.configValue = configValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
