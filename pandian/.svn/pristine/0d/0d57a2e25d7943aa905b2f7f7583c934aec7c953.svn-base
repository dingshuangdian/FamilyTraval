package com.kingtangdata.inventoryassis.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kingtangdata.inventoryassis.bean.User;
import com.kingtangdata.inventoryassis.util.LogUtils;



/**
 * 用户数据管理类
 * @author leo
 * 
 * 对用户数据的增删改查操作
 */
public class UserManager {

	private DBHelper dbhelper = null;
	
	private static UserManager instance = null;
	
	public UserManager(Context context){
		dbhelper = DBHelper.getInstance(context);
	}
	
	public static UserManager getInstance(Context context){
		if(instance == null){
			instance = new UserManager(context);
		}
		return instance;
	}
	

	/**
	 * 增加一条用户记录
	 * 
	 * @return 是否添加成功
	 */
	public boolean addUser(User user, SQLiteDatabase db ) {
		ContentValues contentValue = buildContentValue(user);
		try {
			if(db == null){
				db = dbhelper.getWritableDatabase();
			}
			
			long rowId = db.insert("users", "",contentValue);
			if (rowId == -1) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		
		LogUtils.logd(getClass(), "插入用户" + user.getUser_name() + "成功");
		return true;
	}
	
	
	/**
	 * 增加一条用户记录
	 * 
	 * @return 是否添加成功
	 */
	public boolean addUser(User user) {
		return  addUser(user,null);
	}
	
	/**
	 * 增加一批用户记录
	 * @param mList
	 * @return 是否添加成功
	 */
	public boolean addUsers(List<User> mList) {
		if (mList == null) {
			return false;
		}

		SQLiteDatabase db = dbhelper.getWritableDatabase();
		try {
			//开启事务
			db.beginTransaction();
			int size = mList.size();
			for (int i = 0; i < size; i++) {
				User user = mList.get(i);
				addUser(user,db); 
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
	 * 删除所有用户   不能删除sbd用户 需要过滤
	 */
	public boolean deleteUsers(SQLiteDatabase db) {
		try {
			if(db == null){
				db = dbhelper.getWritableDatabase();
			}
			long rowId = db.delete("users"," user_code != ?", new String[]{"sbd"});
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
	 * 删除所有用户   不能删除sbd用户 需要过滤
	 */
	public boolean deleteUsers() {
		return deleteUsers(null);
	}
	
	
	/**
	 * 根据code 检查用户名和密码
	 * @param code
	 * @return
	 */
	public User checkUser(String userName,String userPass){
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = dbhelper.getReadableDatabase();
			StringBuffer sb = new StringBuffer(100);
			sb.append(" select  *  from  users where user_code = ? and user_pass=?");
			cursor = db.rawQuery(sb.toString(), new String[] { userName,userPass });
			while (cursor.moveToNext()) {
				User user = new User();
				user.setUser_id(cursor.getString(1));
				user.setUser_code(cursor.getString(2));
				user.setUser_name(cursor.getString(3));
				user.setUser_pass(cursor.getString(4));
				user.setDept_id(cursor.getString(5));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(db != null){
				db.close();
			}
			
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
	
	
	/**
	 * @param User
	 *            对象
	 * @return 根据对象凑成ContentValues
	 */
	private ContentValues buildContentValue(User user) {
		ContentValues contentValue = new ContentValues();
		contentValue.put("user_id", user.getUser_id());
		contentValue.put("user_code", user.getUser_code());
		contentValue.put("user_name", user.getUser_name());
		contentValue.put("user_pass", user.getUser_pass());
		contentValue.put("dept_id", user.getDept_id());
		return contentValue;
	}
}
