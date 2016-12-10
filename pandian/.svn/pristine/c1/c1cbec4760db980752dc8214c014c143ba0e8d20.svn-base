package com.kingtangdata.inventoryassis.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/**
 * 
 * 计划实体类
 * 
 * @author leo
 * 
 */
public class Plan  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2038331197457526103L;
	@Expose
	private String det_id;
	@Expose
	private String check_id;
	@Expose
	private String assetno;
	@Expose
	private String device_code;
	@Expose
	private String device_name;
	@Expose
	private String type_name;
	@Expose
	private String device_type;
	@Expose
	private String device_size;
	@Expose
	private String outdate;
	@Expose
	private String install_place;
	@Expose
	private String check_result;
	@Expose
	private String memo;
	@Expose
	private String devicecard_id;
	@Expose
	private String type_id;
	@Expose
	private String field_name;
	@Expose
	private String if_create;
	@Expose
	private String type_code;
	@Expose
	private String is_upload;
	@Expose
	private String is_normal;
	@Expose
	private String is_bind;
	@Expose
	private String outno;
	@Expose
	private String dept_name;
	@Expose
	private String dept_code;
	@Expose
	private String dept_id;
	@Expose
	private String label_code;
	@Expose
	private String factory;
	@Expose
	private String running;
	@Expose
	private String is_damage;
	
	public String getDet_id() {
		return det_id;
	}

	public void setDet_id(String det_id) {
		this.det_id = det_id;
	}

	public String getCheck_id() {
		return check_id;
	}

	public void setCheck_id(String check_id) {
		this.check_id = check_id;
	}

	public String getAssetno() {
		return assetno;
	}

	public void setAssetno(String assetno) {
		this.assetno = assetno;
	}

	public String getDevice_code() {
		return device_code;
	}

	public void setDevice_code(String device_code) {
		this.device_code = device_code;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public String getDevice_size() {
		return device_size;
	}

	public void setDevice_size(String device_size) {
		this.device_size = device_size;
	}

	public String getOutdate() {
		return outdate;
	}

	public void setOutdate(String outdate) {
		this.outdate = outdate;
	}

	public String getInstall_place() {
		return install_place;
	}

	public void setInstall_place(String install_place) {
		this.install_place = install_place;
	}

	public String getCheck_result() {
		return check_result;
	}

	public void setCheck_result(String check_result) {
		this.check_result = check_result;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDevicecard_id() {
		return devicecard_id;
	}

	public void setDevicecard_id(String devicecard_id) {
		this.devicecard_id = devicecard_id;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public String getOutno() {
		return outno;
	}

	public void setOutno(String outno) {
		this.outno = outno;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getLabel_code() {
		return label_code;
	}

	public void setLabel_code(String label_code) {
		this.label_code = label_code;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}
	
	public String getRunning() {
		return running;
	}

	public void setRunning(String running) {
		this.running = running;
	}

	public String getIs_damage() {
		return is_damage;
	}

	public void setIs_damage(String is_damage) {
		this.is_damage = is_damage;
	}

	public String getIf_create() {
		return if_create;
	}

	public void setIf_create(String if_create) {
		this.if_create = if_create;
	}

	public String getIs_upload() {
		return is_upload;
	}

	public void setIs_upload(String is_upload) {
		this.is_upload = is_upload;
	}

	public String getIs_normal() {
		return is_normal;
	}

	public void setIs_normal(String is_normal) {
		this.is_normal = is_normal;
	}
	
	public String getIs_bind() {
		return is_bind;
	}

	public void setIs_bind(String is_bind) {
		this.is_bind = is_bind;
	}

	public boolean isNormal(){
		return "1".equals(is_normal) ? true : false  || "N".equals(is_normal) ? true : false ;
	}
	
	public boolean isUpload(){
		return "1".equals(is_upload) ? true : false  ;
	}
	
	public boolean isCreate(){
		return "1".equals(if_create) ? true : false  ;
	}
	
	public boolean isBind(){
		return "1".equals(is_bind) ? true : false  ;
	}
	
	//从一个plan对象拷贝值到当前对象
	public void  copyFrom(Plan plan){
		if(plan != null){
			this.setDet_id(plan.getDet_id());
			this.setCheck_id(plan.getCheck_id());
			//this.set
			this.setAssetno(plan.getAssetno());
			this.setDevice_code(plan.getDevice_code());
			this.setDevice_name(plan.getDevice_name());
			this.setDevice_type(plan.getDevice_type());
			this.setDevice_size(plan.getDevice_size());
			this.setOutno(plan.getOutno());
			this.setFactory(plan.getFactory());
			this.setInstall_place(plan.getInstall_place());
			this.setMemo(plan.getMemo());
			this.setLabel_code(plan.getLabel_code());
			this.setIs_upload(plan.getIs_upload());
			this.setIs_bind(plan.getIs_bind());
			this.setIs_damage(plan.getIs_damage());
			this.setRunning(plan.getRunning());
		}
	}
}