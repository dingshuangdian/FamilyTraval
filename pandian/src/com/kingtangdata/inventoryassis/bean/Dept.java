package com.kingtangdata.inventoryassis.bean;

import com.google.gson.annotations.Expose;

/**
 * 部门实体类
 * 
 * @author leo
 * 
 */
public class Dept {
	@Expose
	private String dept_id;
	@Expose
	private String dept_code;
	@Expose
	private String dept_name;
	@Expose
	private String dept_level;
	@Expose
	private String leader;
	@Expose
	private String telephone;
	@Expose
	private String netaddress;
	@Expose
	private String email;
	@Expose
	private String dept_function;
	@Expose
	private String memo;
	@Expose
	private boolean isOld;
	@Expose
	private boolean isExamine;
	@Expose
	private String add_date;
	@Expose
	private String add_userid;
	@Expose
	private String modify_date;
	@Expose
	private String modify_userid;
	@Expose
	private boolean isProd;

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getDept_level() {
		return dept_level;
	}

	public void setDept_level(String dept_level) {
		this.dept_level = dept_level;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getNetaddress() {
		return netaddress;
	}

	public void setNetaddress(String netaddress) {
		this.netaddress = netaddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDept_function() {
		return dept_function;
	}

	public void setDept_function(String dept_function) {
		this.dept_function = dept_function;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAdd_date() {
		return add_date;
	}

	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}

	public String getAdd_userid() {
		return add_userid;
	}

	public void setAdd_userid(String add_userid) {
		this.add_userid = add_userid;
	}

	public String getModify_date() {
		return modify_date;
	}

	public void setModify_date(String modify_date) {
		this.modify_date = modify_date;
	}

	public String getModify_userid() {
		return modify_userid;
	}

	public void setModify_userid(String modify_userid) {
		this.modify_userid = modify_userid;
	}

	public boolean isOld() {
		return isOld;
	}

	public void setOld(boolean isOld) {
		this.isOld = isOld;
	}

	public boolean isExamine() {
		return isExamine;
	}

	public void setExamine(boolean isExamine) {
		this.isExamine = isExamine;
	}

	public boolean isProd() {
		return isProd;
	}

	public void setProd(boolean isProd) {
		this.isProd = isProd;
	}
}