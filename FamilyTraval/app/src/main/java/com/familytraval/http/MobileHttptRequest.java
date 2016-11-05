package com.familytraval.http;

import android.content.Context;

import com.familytraval.bean.User;
import com.familytraval.common.MobileApplication;
import com.familytraval.common.MobileConstants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.soubao.tpshop.utils.SPCommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by dings on 2016/10/25.
 */

public class MobileHttptRequest {

    private static String TAG = "SouLeopardHttptRequest";

    public static void init(Context context) {

    }


    private MobileHttptRequest() {

    }

    /**
     * @param url    GET 请求URL
     * @param params 请求参数
     * @return void    返回类型
     * @throws
     * @Description: 回调
     */
    public static void get(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {

        if (params == null) {
            params = new RequestParams();
        }

        if (MobileApplication.getInstance().isLogined) {
            User user = MobileApplication.getInstance().getLoginUser();
            params.put("user_id", user.getUserID());
            params.put("token", user.getToken());
        }

        if (MobileApplication.getInstance().getDeviceId() != null) {
            String imei = MobileApplication.getInstance().getDeviceId();
            params.put("unique_id", imei);
        }

        try {
            configSign(params, url);
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, params, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
            responseHandler.onFailure(-1, new Header[]{}, new Throwable(e.getMessage()), new JSONObject());
        }

    }

    /**
     * @param url    请求URL
     * @param params 请求参数
     * @return void    返回类型
     * @throws
     * @Description: POST回调
     */
    public static void post(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();

        if (params == null) {
            params = new RequestParams();
        }

        if (MobileApplication.getInstance().isLogined) {
            User user = MobileApplication.getInstance().getLoginUser();
            params.put("user_id", user.getUserID());
            params.put("token", user.getToken());//
        } else {
            if (MobileConstants.DevTest) {
                params.put("user_id", 1);
            }
        }

        if (MobileApplication.getInstance().getDeviceId() != null) {
            String imei = MobileApplication.getInstance().getDeviceId();
            params.put("unique_id", imei);
        }

        try {
            configSign(params, url);
            client.post(url, params, responseHandler);

        } catch (Exception e) {
            e.printStackTrace();
            responseHandler.onFailure(-1, new Header[]{}, new Throwable(e.getMessage()), new JSONObject());
        }
    }

    /**
     * 根据控制器和action组装请求URL
     *
     * @param c
     * @param action
     * @return
     */
    public static String getRequestUrl(String c, String action) {
        return MobileConstants.BASE_URL + "&c=" + c + "&a=" + action;
    }

	/*public interface SuccessListener{
        public void onRespone(String msg , Object response);
	}

	public interface FailuredListener{
		public void onRespone(String msg , int errorCode);
	}*/


    /**
     * 每一个访问接口都要调用改函数进行签名
     * 具体签名方法, 参考: tpshop 开发手册 -> 手机api接口 -> 公共接口
     *
     * @param params
     */
    public static void configSign(RequestParams params, String url) {

        long locaTime = SPCommonUtils.getCurrentTime();//本地时间
        long cutTime = MobileApplication.getInstance().getCutServiceTime();
        String time = String.valueOf(locaTime + cutTime);
        Map<String, String> paramsMap = convertRequestParamsToMap(params);
        String signFmt = SPCommonUtils.signParameter(paramsMap, time, MobileConstants.SP_SIGN_PRIVATGE_KEY, url);
        params.put("sign", signFmt);
        params.put("time", time);
    }

    public static Map<String, String> convertRequestParamsToMap(RequestParams params) {

        Map<String, String> paramsMap = new HashMap<String, String>();
        if (params != null) {
            try {
                String[] items = params.toString().split("&");
                if (items != null) {
                    for (String keyValue : items) {
                        String[] keyValues = keyValue.split("=");
                        if (keyValues != null && keyValues.length == 2) {
                            paramsMap.put(keyValues[0], keyValues[1]);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return paramsMap;
    }

    public static List<String> convertJsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> itemList = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String item = jsonArray.getString(i);
            itemList.add(item);
        }
        return itemList;
    }

}

