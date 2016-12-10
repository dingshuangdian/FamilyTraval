package com.kingtangdata.inventoryassis.http.business;

import android.content.Context;

import com.kingtangdata.inventoryassis.http.HttpConstants;
import com.kingtangdata.inventoryassis.http.HttpManager;
import com.kingtangdata.inventoryassis.http.domain.BaseReq;
import com.kingtangdata.inventoryassis.http.domain.BaseRes;
import com.kingtangdata.inventoryassis.http.domain.DeptRes;
import com.kingtangdata.inventoryassis.http.domain.GetPicRes;
import com.kingtangdata.inventoryassis.http.domain.PlanReq;
import com.kingtangdata.inventoryassis.http.domain.PlanRes;
import com.kingtangdata.inventoryassis.http.domain.TaskRes;
import com.kingtangdata.inventoryassis.http.domain.UploadReq;
import com.kingtangdata.inventoryassis.http.domain.UserRes;
import com.kingtangdata.inventoryassis.http.domain.VersionRes;
import com.kingtangdata.inventoryassis.util.LogUtils;

/**
 * 用户
 * 
 * @author liyang
 *
 */
public class SystemProvider extends HttpManager {
	
	private static SystemProvider instance;
	/**
	 * @param context
	 */
	protected SystemProvider(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	public static synchronized SystemProvider getInstance(Context context) {
		
		if (instance == null) {
			instance = new SystemProvider(context);
		}
		return instance;
	}
	
	
	/**
	 *获取用户
	 * @param request
	 * @return
	 */
	public UserRes getUser(BaseReq request){
		String httpAddr = getHttpIpAddress() + HttpConstants.USER_ACTION;
		LogUtils.logd(getClass(), "url = " + httpAddr);
		
		UserRes response = (UserRes) doGet(request, UserRes.class, httpAddr);
		return response;
	}
	
	
	/**
	 * 获取部门
	 * @param request
	 * @return
	 */
	public DeptRes getDept(BaseReq request){
		String httpAddr = getHttpIpAddress() + HttpConstants.DEPT_ACTION;
		LogUtils.logd(getClass(), "url = " + httpAddr);
		
		DeptRes response = (DeptRes) doGet(request, DeptRes.class, httpAddr);
		return response;
	}
	
	
	/**
	 * 获取任务
	 * @param request
	 * @return
	 */
	public PlanRes getPlan(PlanReq request){
		String httpAddr = getHttpIpAddress() + HttpConstants.PLAN_ACTION + "&check_id=" + request.getCheckId();
		LogUtils.logd(getClass(), "url = " + httpAddr);
		
		PlanRes response = (PlanRes) doGet(request, PlanRes.class, httpAddr);
		return response;
	}
	
	
	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	public TaskRes getTask(BaseReq request){
		String httpAddr = getHttpIpAddress() + HttpConstants.TASK_ACTION;
		LogUtils.logd(getClass(), "url = " + httpAddr);
		
		TaskRes response = (TaskRes) doGet(request, TaskRes.class, httpAddr);
		return response;
	}
	
	
	/**
	 * 上传任务
	 * @param request
	 * @return
	 */
	public BaseRes uoloadPlan(UploadReq request) {
		String httpAddr = getHttpIpAddress() + HttpConstants.UPLOAD_ACTION;
		BaseRes response = (BaseRes) doPost(request, BaseRes.class, httpAddr);
		return response;
	}
	
	/**
	 * 获取背景
	 * @param request
	 * @return
	 */
	public GetPicRes getPic(BaseReq request) {
		String httpAddr = getHttpIpAddress() + HttpConstants.BG_ACTION;
		GetPicRes response = (GetPicRes) doPost(request, GetPicRes.class, httpAddr);
		return response;
	}
	
	/**
	 * 检查版本
	 * 
	 * @param request
	 * @return
	 */
	public VersionRes checkVersion(BaseReq request) {
		String httpAddr = getHttpIpAddress() + HttpConstants.VERSION_ACTION;
		//String httpAddr = "http://160.0.0.61:8080/web/getCategories";
		VersionRes response = (VersionRes) doGet(request, VersionRes.class, httpAddr);
		return response;
	}
}
