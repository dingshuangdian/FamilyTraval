package com.kingtangdata.inventoryassis.http;

import java.io.File;
import java.lang.reflect.Field;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kingtangdata.inventoryassis.http.domain.BaseReq;
import com.kingtangdata.inventoryassis.http.domain.BaseRes;
import com.kingtangdata.inventoryassis.util.LogUtils;
import com.kingtangdata.inventoryassis.util.StorageUtils;
/**
 * 客户端数据访问支持类
 * 
 * @author leo
 */
public abstract class HttpManager {

	private static final String root= "response"; // 传回来的
																	
	protected Context context;

	protected HttpManager(Context context) {
			this.context = context;
	}

	/**
	 * 获取IP地址
	 * @return
	 */
	public String getHttpIpAddress(){
		//设置默认IP
		String ip_address = StorageUtils.getString(StorageUtils.IP_ADDRESS, context, HttpConstants.DEFAULT_IP);
		String port       = StorageUtils.getString(StorageUtils.PORT, context, HttpConstants.DEFAULT_PORT);
		String app		  = StorageUtils.getString(StorageUtils.APP, context, HttpConstants.DEFAULT_APP);

		return "http://" + ip_address + ":" + port + "/" + app;
	}
	

	/**
	 * 把对象中每个字段名和值放到参数map中
	 * 
	 * @param object
	 * @param params
	 */
	protected void buildParams(BaseReq request, Map<String, Object> params) {
		buildParams(request.getClass(), request, params);
	}

	/**
	 * 通过反射把对象中每个字段名和值放到参数map中
	 * 
	 * @param params
	 */
	private void buildParams(Class<? extends BaseReq> clz, Object object,
			Map<String, Object> params) {

		Field[] fields = clz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {

			Field fieldObj = fields[i];
			fieldObj.setAccessible(true);

			String field = fieldObj.getName();
			try {
				params.put(field, fields[i].get(object));
			} catch (Exception e) {
				LogUtils.loge(getClass(), LogUtils.getStackTrace(e));
				return;
			}

		}

		Class superClz = clz.getSuperclass();
		if (superClz != null) {
			buildParams(superClz, object, params);
		}

	}

	/**
	 * 把对象中每个字段名和值放到List<BasicNameValuePair>中
	 * 
	 * @param object
	 * @param params
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	protected void buildParams(BaseReq request, List<BasicNameValuePair> params)
			throws Exception {
		buildParams(request.getClass(), request, params);
	}

	/**
	 * 通过反射把对象中每个字段名和值放到List<BasicNameValuePair>中
	 * 
	 * @param params
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void buildParams(Class<? extends BaseReq> clz, Object object,
			List<BasicNameValuePair> params) throws Exception {

		Field[] fields = clz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {

			Field fieldObj = fields[i];
			fieldObj.setAccessible(true);

			String field = fieldObj.getName();
			Object fieldVal = fields[i].get(object);
			
			LogUtils.logd(getClass(), "field = " + field);
			LogUtils.logd(getClass(), "fieldVal = " + fieldVal);
			
			if (fieldVal != null) {
				// 不使用强制转换，而是使用valueOf，这样int字段也能转换成请求参数
				params.add(new BasicNameValuePair(field, String.valueOf(fieldVal)));
			}
		}

		Class superClz = clz.getSuperclass();
		if (superClz != null) {
			buildParams(superClz, object, params);
		}

	}

	/**
	 * 解析JSON字符串，并转换成response对象
	 * 
	 * @param httpRes
	 * @param clz
	 * @return
	 * @throws Exception
	 */
	protected BaseRes parseJsonResponse(HttpResponse httpRes, Class clz)
			throws Exception {

		// 处理JSON返回结果

		String jsonStr =EntityUtils.toString(httpRes.getEntity()) ;
		
		if(LogUtils.D){
			StringBuilder sb = new StringBuilder();
			String[] jsonItems = jsonStr.split(",");
			sb.append("parseJsonResponse - start - " );
			sb.append("\n");
			for (String string : jsonItems) {
				sb.append("  ");
				sb.append(string);
				sb.append("\n");
			}
			sb.append(" parseJsonResponse - end - " );
			sb.append("\n");
			LogUtils.logi(getClass(),sb.toString());
		}
		
		/**
		 * 显示json数据 -end
		 * 
		 */

		JSONObject jsonObject = new JSONObject(jsonStr);

		// 取json中response节点中的字串
		String responseStr = jsonObject.getString(root);

		// 转换器
		GsonBuilder builder = new GsonBuilder();

		// 不转换没有 @Expose 注解的字段
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();

		// 从JSON串转成JAVA对象
		BaseRes response = gson.fromJson(responseStr, clz);

		return response;
	}

	/**
	 * 根据对象转换成JSON字符串
	 * 
	 * @param obj
	 *            对象
	 * @return
	 */
	protected String formatBeanToJson(Object obj) {

		// 转换器
		GsonBuilder builder = new GsonBuilder();

		// 不转换没有 @Expose 注解的字段
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();

		return gson.toJson(obj);
	}

	/**
	 * POST提交数据
	 * 
	 * @param request
	 *            请求对象
	 * @return 应答对象
	 */
	protected BaseRes doPost(BaseReq request, Class<? extends BaseRes> clz,
			String httpAddr) {
		
		BaseRes response = null;
		HttpCoreClient coreHttpClient = null;

		try {

			response = clz.newInstance();

			// 验证request对象, 如果有错误, 将错误信息设置到response中
			if (!request.validate(response)) {
				return response;
			}

			// 把对象中每个字段名和值放到List<BasicNameValuePair>中
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			try {
				buildParams(request, params);
			} catch (Exception e) {
				response.setCode(HttpResponseCode.SYSTEM_EXCEPTION);
				response.setDesc("请求数据转换失败,请稍后再试");
				return response;
			}
			
			LogUtils.logd(getClass(), "1 =======================" );
			
			// http client 核心类
			coreHttpClient = new HttpCoreClient();
			HttpResponse httpResponse = null;

			// POST提交数据至服务器
			httpResponse = coreHttpClient.post(httpAddr, params);
			if (httpResponse == null) {
				LogUtils.loge(getClass(), "http response is null.");
				response.setCode(HttpResponseCode.SYSTEM_EXCEPTION);
				response.setDesc("网络通信异常,请稍后再试");
				return response;
			}

			// 解析JSON字符串，并转换成response对象
			response = parseJsonResponse(httpResponse, response.getClass());
			

            /**************************************************
             * 6,分析服务器的response,判断 token ， tgt 的 有效性 
             **************************************************/

            if (response != null) {
                if (TextUtils.isEmpty(response.getCode())) {
                	response.setDesc("客户端获取的服务器响应代码为空");
                    return response;
                } 
            } else {
                response = buildResponseForServerNullResponse(clz);
                return response;
            }
		} catch (Exception e) {
			setExceptionResponse(e, response);
		} finally {
			if (coreHttpClient != null) {
				coreHttpClient.shutdownHttpClient();
			}
		}

		return response;
	}
	
	
	/**
	 * POST提交数据
	 * 
	 * @param request
	 *            请求对象
	 * @return 应答对象
	 */
	protected BaseRes doGet(BaseReq request, Class<? extends BaseRes> clz,
			String httpAddr) {
		
		BaseRes response = null;
		HttpCoreClient coreHttpClient = null;

		try {

			response = clz.newInstance();

			// 验证request对象, 如果有错误, 将错误信息设置到response中
			if (!request.validate(response)) {
				return response;
			}

			// 把对象中每个字段名和值放到List<BasicNameValuePair>中
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			try {
				buildParams(request, params);
			} catch (Exception e) {
				response.setCode(HttpResponseCode.SYSTEM_EXCEPTION);
				response.setDesc("请求数据转换失败,请稍后再试");
				return response;
			}
			
			LogUtils.logd(getClass(), "1 =======================" );
			
			// http client 核心类
			coreHttpClient = new HttpCoreClient();
			HttpResponse httpResponse = null;

			// POST提交数据至服务器
			httpResponse = coreHttpClient.get(httpAddr, params);
			if (httpResponse == null) {
				LogUtils.loge(getClass(), "http response is null.");
				response.setCode(HttpResponseCode.SYSTEM_EXCEPTION);
				response.setDesc("网络通信异常,请稍后再试");
				return response;
			}

			// 解析JSON字符串，并转换成response对象
			response = parseJsonResponse(httpResponse, response.getClass());

            /**************************************************
             * 6,分析服务器的response,判断 token ， tgt 的 有效性 
             **************************************************/

            if (response != null) {
                if (TextUtils.isEmpty(response.getCode())) {
                	response.setDesc("客户端获取的服务器响应代码为空");
                    return response;
                } 
            } else {
                response = buildResponseForServerNullResponse(clz);
                return response;
            }
		} catch (Exception e) {
			LogUtils.logd(getClass(), "异常信息:" + e.getMessage());
			setExceptionResponse(e, response);
		} finally {
			if (coreHttpClient != null) {
				coreHttpClient.shutdownHttpClient();
			}
		}

		return response;
	}
	
	
	
	 /**
     * 客户端获取的服务器响应为空
     * @param clz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private BaseRes buildResponseForServerNullResponse(Class<? extends BaseRes> clz)
            throws IllegalAccessException, InstantiationException {
        BaseRes response;
        response = clz.newInstance();
        response.setDesc("客户端获取的服务器响应为空");
        return response;
    }

	/**
	 * 设置异常返回信息
	 * 
	 * @param response
	 */
	private void setExceptionResponse(Exception e, BaseRes response) {

		String result = null;
		String desc = null;

		if (e instanceof SocketException) {
			/*
			 * This SocketException may be thrown during socket creation or
			 * setting options, and is the superclass of all other socket
			 * related exceptions.
			 */
			result = HttpResponseCode.HTTP_SOKET_EXCEPTION;
			desc = "网络连接超时,请确定网络正常后重试";
		} else if (e instanceof SocketTimeoutException) {
			/*
			 * This exception is thrown when a timeout expired on a socket read
			 * or accept operation.
			 */
			result = HttpResponseCode.HTTP_SOKET_TIMEOUT;
			desc = "网络通信超时,请确定网络正常后重试";
		} else if (e instanceof HttpException) {
			result = HttpResponseCode.HTTP_ERROR;
			HttpException httpEx = (HttpException) e;
			desc = "网络通信失败,请稍后再试";
			if (!(httpEx.getStatusCode() == -1)) {
				desc = desc + "-" + httpEx.getStatusCode();
			}
			LogUtils.loge(getClass(), LogUtils.getStackTrace(e));
		} else {
			result = HttpResponseCode.SYSTEM_EXCEPTION; // 系统异常
			desc = "系统处理失败,请稍后再试";
			LogUtils.loge(getClass(), LogUtils.getStackTrace(e));
		}

		response.setCode(result);
		response.setDesc(desc);

	}
	
	
	/**
	 * POST提交数据
	 * 
	 * @param request
	 *            请求对象
	 * @return 应答对象
	 */
	protected BaseRes postData(BaseReq request, Class<? extends BaseRes> clz,
			String httpAddr,File file) {

		BaseRes response = null;
		HttpCoreClient coreHttpClient = null;

		try {

			response = clz.newInstance();

			// 验证request对象, 如果有错误, 将错误信息设置到response中
			if (!request.validate(response)) {
				return response;
			}

			// 把对象中每个字段名和值放到List<BasicNameValuePair>中
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			
			try {
				buildParams(request, params);
			} catch (Exception e) {
				response.setCode(HttpResponseCode.SYSTEM_EXCEPTION);
				response.setDesc("请求数据转换失败,请稍后再试");
				return response;
			}

			// http client 核心类
			coreHttpClient = new HttpCoreClient();
			HttpResponse httpResponse = null;
			
			if(file == null ){
				return response ;
			}
			
			LogUtils.logd(getClass(), "file size = "+file.length());
			// POST提交数据至服务器
			httpResponse = coreHttpClient.post(httpAddr, file, params);
			
			if (httpResponse == null) {
				LogUtils.loge(getClass(), "http response is null.");
				response.setCode(HttpResponseCode.SYSTEM_EXCEPTION);
				response.setDesc("网络通信异常,请稍后再试");
				return response;
			}

			// 解析JSON字符串，并转换成response对象
			response = parseJsonResponse(httpResponse, response.getClass());
			
		} catch (Exception e) {
			setExceptionResponse(e, response);
		} finally {
			if (coreHttpClient != null) {
				coreHttpClient.shutdownHttpClient();
			}
		}

		return response;
	}

}
