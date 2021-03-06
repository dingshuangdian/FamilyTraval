package com.kingtangdata.inventoryassis.http.domain;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.kingtangdata.inventoryassis.bean.User;

public class UserRes extends BaseRes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private List<User> datas;

	public List<User> getDatas() {
		return datas;
	}

	public void setDatas(List<User> datas) {
		this.datas = datas;
	}

}
