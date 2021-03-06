package com.kingtangdata.inventoryassis.http;

public class HttpConstants {
	
	/**用户数据接口*/
	public static String USER_ACTION = "/PdInterface?f=getUser";
	
	/**用户数据接口*/
	public static String DEPT_ACTION = "/PdInterface?f=getDept";
	
	/**任务接口*/
	public static String TASK_ACTION = "/PdInterface?f=getPlanList";
	
	/**计划接口*/
	public static String PLAN_ACTION = "/PdInterface?f=getPlan";
	
	/**计划接口*/
	public static String UPLOAD_ACTION = "/PdInterface?f=sendJson";
	
	/**测试接口*/
	public static String TEST_ACTION = "/PdInterface?f=getTest";
	
	/**获取背景接口*/
	public static String BG_ACTION = "/PdInterface?f=getWallpaper";
	
	/**检测版本接口*/
	public static String VERSION_ACTION = "/PdInterface?f=getVersion";

	/**服务器默认ip*/
	public static final String DEFAULT_IP = "160.0.0.85";
	
	/**端口号*/
	public static final String DEFAULT_PORT = "8888";
	
	/**应用名*/
	public static final String DEFAULT_APP = "eam10";
	
	//网络请求超时时间
	public static final int CONNECT_TIME_OUT = 3000 ;
	public static final int READ_TIME_OUT = 3000 ;
	
}
