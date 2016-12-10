package com.kingtangdata.inventoryassis.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kingtangdata.inventoryassis.bean.Dept;

/**
 * 部门数据管理类
 * @author leo
 *
 */
public class DeptManager {
	
	private DBHelper dbhelper = null;
	
	private static DeptManager instance = null;
	
	public DeptManager(Context context){
		dbhelper = DBHelper.getInstance(context);
	}
	
	public static DeptManager getInstance(Context context){
		if(instance == null){
			instance = new DeptManager(context);
		}
		return instance;
	}
	

	/**
	 * 增加一条用户记录
	 * 
	 * @return 是否添加成功
	 */
	public boolean addDept(Dept dept, SQLiteDatabase db ) {
		ContentValues contentValue = buildContentValue(dept);
		try {
			if(db == null){
				db = dbhelper.getWritableDatabase();
			}
			
			long rowId = db.insert("depts", "",contentValue);
			if (rowId == -1) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		return true;
	}
	
	
	/**
	 * 增加一条用户记录
	 * 
	 * @return 是否添加成功
	 */
	public boolean addDept(Dept dept) {
		return  addDept(dept,null);
	}
	
	/**
	 * 增加一批用户记录
	 * @param mList
	 * @return 是否添加成功
	 */
	public boolean addDepts(List<Dept> mList) {
		if (mList == null) {
			return false;
		}

		SQLiteDatabase db = dbhelper.getWritableDatabase();
		try {
			//开启事务
			db.beginTransaction();
			int size = mList.size();
			for (int i = 0; i < size; i++) {
				Dept dept = mList.get(i);
				addDept(dept,db); 
			}
			
			//提交事务
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			
			if (db != null) {
				//事务结束，失败自动回滚 
				db.endTransaction();
				db.close();
				db = null;
			}
			if (mList != null) {
				mList.clear();
				mList = null;
			}
		}
		return true;
	}
	
	
	/**
	 * 删除所有用户   不能删除Admin用户 需要过滤
	 */
	public boolean deleteDepts(SQLiteDatabase db) {
		try {
			if(db == null){
				db = dbhelper.getWritableDatabase();
			}
			long rowId = db.delete("Depts",null, null);
			if (rowId == -1) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		return true;
	}
	
	/**
	 * @param User
	 *            对象
	 * @return 根据对象凑成ContentValues
	 */
	private ContentValues buildContentValue(Dept dept) {
		ContentValues contentValue = new ContentValues();
		contentValue.put("dept_id", dept.getDept_id());
		contentValue.put("dept_code", dept.getDept_code());
		contentValue.put("dept_name", dept.getDept_name());
		contentValue.put("dept_level", dept.getDept_level());
		contentValue.put("leader", dept.getLeader());
		contentValue.put("telephone", dept.getTelephone());
		contentValue.put("netaddress", dept.getNetaddress());
		contentValue.put("email", dept.getEmail());
		contentValue.put("dept_function", dept.getDept_function());
		contentValue.put("memo", dept.getMemo());
		contentValue.put("is_old", dept.isOld());
		contentValue.put("is_examine", dept.isExamine());
		contentValue.put("add_date", dept.getAdd_date());
		contentValue.put("add_userid", dept.getAdd_userid());
		contentValue.put("modify_date", dept.getModify_date());
		contentValue.put("modify_userid", dept.getModify_userid());
		contentValue.put("is_prod", dept.isProd());
		return contentValue;
	}
}
