package com.kingtangdata.inventoryassis.bean;

import com.google.gson.annotations.Expose;


/**
 * 用户实体类
 * @author leo
 *
 */
public class User {
	@Expose
	private String user_id;
	@Expose
	private String user_code;
	@Expose
	private String user_name;
	@Expose
	private String user_pass;
	@Expose
	private String dept_id;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_pass() {
		return user_pass;
	}

	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("user_id=").append(user_id);
		sb.append(", ").append("user_code=").append(user_code);
		sb.append(", ").append("user_name=").append(user_name);
		sb.append(", ").append("user_pass=").append(user_pass);
		sb.append(", ").append("dept_id=").append(dept_id);
		return sb.toString();
	}
}