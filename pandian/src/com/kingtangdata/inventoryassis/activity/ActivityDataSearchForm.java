package com.kingtangdata.inventoryassis.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.kingtangdata.inventoryassis.R;
import com.kingtangdata.inventoryassis.base.BaseActivity;
import com.kingtangdata.inventoryassis.bean.Plan;

/**
 * 
 * 盘点信息查询表单
 * @author liyang
 *
 */
public class ActivityDataSearchForm extends BaseActivity{
	
	private TextView etMarks;
	private TextView etMeanscode;
	private TextView etDevicecode;
	private TextView etDevicename;
	private TextView etXinghao;
	private TextView etGuige;
	private TextView etChuchangcode;
	private TextView etChuchangdate;
	private TextView etBuildaddress;
	private TextView etRemarks;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_data_search_from);
		super.setTopLabel("设备信息");
		super.setLeftButtonText("返回");
		
		//控件初始化
		etMarks = (TextView)findViewById(R.id.et_marks);
		etMeanscode = (TextView)findViewById(R.id.et_meanscode);
		etDevicecode = (TextView)findViewById(R.id.et_devicecode);
		etDevicename = (TextView)findViewById(R.id.et_devicename);
		etXinghao = (TextView)findViewById(R.id.et_xinghao);
		etGuige = (TextView)findViewById(R.id.et_guige);
		etChuchangcode = (TextView)findViewById(R.id.et_chuchangcode);
		etChuchangdate = (TextView)findViewById(R.id.et_chuchangdate);
		etBuildaddress = (TextView)findViewById(R.id.et_buildaddress);
		etRemarks = (TextView)findViewById(R.id.et_remarks);
		
		Plan plan = (Plan) getIntent().getSerializableExtra("plan");
		
		if(plan != null){
			setPlanText(plan);
		}
	}
	
	/**
	 * 根据plan对象给当前界面赋值
	 * @param plan
	 */
	public void setPlanText(Plan plan){
		etMarks.setText(plan.getLabel_code());
		etMeanscode .setText(plan.getAssetno());
		etDevicecode .setText(plan.getDevice_code());
		etDevicename .setText(plan.getDevice_name());
		etXinghao.setText(plan.getDevice_type());
		etGuige.setText(plan.getDevice_size());
		etChuchangcode .setText(plan.getOutno());
		etChuchangdate .setText(plan.getOutdate());
		etBuildaddress .setText(plan.getInstall_place());
		etRemarks .setText(plan.getMemo());
	}
}
