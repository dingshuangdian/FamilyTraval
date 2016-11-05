package com.familytraval.Model;

import com.familytraval.callback.Model;

/**
 * Created by dings on 2016/10/26.
 */

public class RegionModel implements Model {

    //地区ID
    String regionID;
    //父地区ID
    String parentID;
    String name;
    String level;//1: 省份 , 2: 城市 , 3: 地区

    @Override
    public String[] replaceKeyFromPropertyName() {
        return new String[]{
                "regionID", "id",
                "parentID", "parent_id"
        };
    }


    public String getRegionID() {
        return regionID;
    }

    public void setRegionID(String regionID) {
        this.regionID = regionID;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

