package com.familytraval.http;

import com.familytraval.bean.HomeBanners;
import com.familytraval.bean.HomeCategory;
import com.familytraval.bean.Plugin;
import com.familytraval.bean.Product;
import com.familytraval.bean.ServiceConfig;
import com.familytraval.callback.FailuredListener;
import com.familytraval.callback.SuccessListener;
import com.familytraval.common.MobileConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.soubao.tpshop.utils.SPJsonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by dings on 2016/10/26.
 */

public class HomeRequest {

    private static String TAG = "SPHomeRequest";

    /**
     * 查询系统配置信息
     * 使用万能SQL: index.php?m=Api&c=Index&a=getConfig
     *
     * @param successListener  success description
     * @param failuredListener failure description
     */
    public static void getServiceConfig(final SuccessListener successListener, final FailuredListener failuredListener) {
        assert (successListener != null);
        assert (failuredListener != null);

        String url = MobileHttptRequest.getRequestUrl("Index", "getConfig");

        try {
            MobileHttptRequest.post(url, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        String msg = (String) response.getString(MobileConstants.Response.MSG);
                        int status = response.getInt(MobileConstants.Response.STATUS);
                        if (status > 0) {
                            JSONArray resultArray = (JSONArray) response.getJSONArray(MobileConstants.Response.RESULT);
                            List<ServiceConfig> serviceConfigs = SPJsonUtil.fromJsonArrayToList(resultArray, ServiceConfig.class);
                            successListener.onRespone("success", serviceConfigs);
                        } else {
                            failuredListener.onRespone(msg, -1);
                        }
                    } catch (Exception e) {
                        failuredListener.onRespone(e.getMessage(), -1);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    failuredListener.onRespone(throwable.getMessage(), statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    failuredListener.onRespone(throwable.getMessage(), statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    failuredListener.onRespone(throwable.getMessage(), statusCode);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询插件配置信息
     * 使用万能SQL: index.php?m=Api&c=Index&a=getPluginConfig
     *
     * @param successListener  success description
     * @param failuredListener failure description
     */
    public static void getServicePlugin(final SuccessListener successListener, final FailuredListener failuredListener) {
        assert (successListener != null);
        assert (failuredListener != null);

        String url = MobileHttptRequest.getRequestUrl("Index", "getPluginConfig");

        try {
            MobileHttptRequest.get(url, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        String msg = (String) response.getString(MobileConstants.Response.MSG);

                        int status = response.getInt(MobileConstants.Response.STATUS);
                        if (status > 0) {
                            JSONObject resultJson = (JSONObject) response.getJSONObject(MobileConstants.Response.RESULT);
                            List<Plugin> servicePlugins = SPJsonUtil.fromJsonArrayToList(resultJson.getJSONArray("payment"), Plugin.class);
                            Map<String, Plugin> pluginMap = new HashMap<String, Plugin>();
                            if (servicePlugins != null) {
                                for (Plugin plugin : servicePlugins) {
                                    //插件安装后才可使用
                                    if (plugin.getStatus().equals("1")) {
                                        String key = plugin.getCode();
                                        pluginMap.put(key, plugin);
                                    }
                                }
                            }

                            successListener.onRespone("success", pluginMap);
                        } else {
                            failuredListener.onRespone(msg, -1);
                        }
                    } catch (Exception e) {
                        failuredListener.onRespone(e.getMessage(), -1);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    failuredListener.onRespone(throwable.getMessage(), statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    failuredListener.onRespone(throwable.getMessage(), statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    failuredListener.onRespone(throwable.getMessage(), statusCode);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getHomeData(final SuccessListener successListener, final FailuredListener failuredListener) {
        assert (successListener != null);
        assert (failuredListener != null);
        String url = MobileHttptRequest.getRequestUrl("Index", "home");
        try {
            MobileHttptRequest.post(url, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        String msg = (String) response.getString(MobileConstants.Response.MSG);
                        JSONObject resultJson = (JSONObject) response.getJSONObject(MobileConstants.Response.RESULT);
                        JSONObject dataJson = new JSONObject();

                        if (resultJson != null) {
                            //商品列表
                            if (!resultJson.isNull("goods")) {
                                JSONArray goods = resultJson.getJSONArray("goods");

                                if (goods != null) {
                                    List<HomeCategory> homeCategories = SPJsonUtil.fromJsonArrayToList(goods, HomeCategory.class);
                                    for (int i = 0; i < goods.length(); i++) {
                                        JSONObject entityObj = goods.getJSONObject(i);
                                        JSONArray products = entityObj.getJSONArray("goods_list");
                                        List<Product> pros = SPJsonUtil.fromJsonArrayToList(products, Product.class);
                                        homeCategories.get(i).setGoodsList(pros);
                                    }
                                    dataJson.put("homeCategories", homeCategories);
                                }
                            }
                            //ad
                            if (!resultJson.isNull("ad")) {
                                JSONArray ads = resultJson.getJSONArray("ad");
                                if (ads != null) {
                                    List<HomeBanners> banners = SPJsonUtil.fromJsonArrayToList(ads, HomeBanners.class);
                                    dataJson.put("banners", banners);
                                }
                            }
                            successListener.onRespone("success", dataJson);
                        } else {
                            failuredListener.onRespone(msg, -1);
                        }
                    } catch (Exception e) {
                        failuredListener.onRespone(e.getMessage(), -1);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    failuredListener.onRespone(throwable.getMessage(), statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    failuredListener.onRespone(throwable.getMessage(), statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    failuredListener.onRespone(throwable.getMessage(), statusCode);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

