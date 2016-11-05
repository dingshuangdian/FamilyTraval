package com.familytraval.common;

/**
 * Created by dings on 2016/10/26.
 */

public class TableConstanct {

    public final static String TABLE_NAME_ADDRESS = "sp_address";
    public final static String CREATE_TABLE_ADDRESS = "CREATE TABLE IF NOT EXISTS sp_address(" +
            "id integer, " +
            "name text NOT NULL, " +
            "parent_id integer NOT NULL , " +
            "level integer NOT NULL) ";

    public final static String TABLE_NAME_CATEGORY = "tp_goods_category";
    public final static String CREATE_TABLE_CATEGORY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CATEGORY + "(" +
            "id integer, " +
            "name STRING NOT NULL, " +
            "parent_id INTEGER NOT NULL , " +
            "level INTEGER NOT NULL ," +
            "image STRING ," +
            "is_hot INTEGER , " +
            "sort_order INTEGER) ";
}


