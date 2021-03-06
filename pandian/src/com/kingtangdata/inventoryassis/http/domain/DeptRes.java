package com.kingtangdata.inventoryassis.http.domain;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.kingtangdata.inventoryassis.bean.Dept;

public class DeptRes extends BaseRes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private List<Dept> datas;

	public List<Dept> getDatas() {
		return datas;
	}

	public void setDatas(List<Dept> datas) {
		this.datas = datas;
	}

}
