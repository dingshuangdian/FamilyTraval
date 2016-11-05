package com.familytraval.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dings on 2016/10/15.
 */
public class DBUtils {
    Context context;
    DBHelper dbHelper;

    public DBUtils(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context, "sql.db", null, 1);
    }

    //     往sql表中  插入数据方法
    public void insert(String num, String password) {
//        获取数据库写的权限 一般都是更新
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "insert into sqlite(numphone,password)values(?,?)";
        sqLiteDatabase.execSQL(sql, new String[]{num, password});
//        ContentValues values=new ContentValues();
//        values.put("num",num);
//        values.put("name",name);
//        sqLiteDatabase.insert("student",null,values);
//        sqLiteDatabase.close();
    }

    //    查询数据库方法,  使用数据库读数据库权限的时候，不能调用sqLiteDatabase.close();
    public Cursor query(String num, String name) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select*from sqlite where numphone=?and password=?";
        return sqLiteDatabase.rawQuery(sql, new String[]{num, name});
    }

}
