package com.familytraval.http;

import com.familytraval.bean.Category;
import com.familytraval.callback.FailuredListener;
import com.familytraval.callback.SuccessListener;
import com.familytraval.common.MobileConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.soubao.tpshop.utils.SPJsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by dings on 2016/10/26.
 */

public class CategoryRequest {

    private static String TAG = "SPCategoryRequest";

    /**
     * @param parentID         如果parent < 1 则返回时的左边分类, 如果parent > 0 获取的是右边的分类
     * @param failuredListener
     * @return void    返回类型
     * @throws JSONException 设定文件
     * @throws
     * @Description: 获取分类
     */
    public static void getCategory(int parentID, final SuccessListener successListener, final FailuredListener failuredListener) {

        assert (successListener != null);
        assert (failuredListener != null);

        String url = MobileHttptRequest.getRequestUrl("Goods", "goodsCategoryList");

        try {
            RequestParams params = new RequestParams();
            if (parentID >= 0) {
                params.put("parent_id", parentID);
            }

            MobileHttptRequest.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray data = (JSONArray) response.getJSONArray(MobileConstants.Response.RESULT);
                        List<Category> categorys = SPJsonUtil.fromJsonArrayToList(data, Category.class);
                        successListener.onRespone("success", categorys);
                        printCategory(categorys);
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
     * @param parentID         如果parent < 1 则返回时的左边分类, 如果parent > 0 获取的是右边的分类
     * @param failuredListener
     * @return void    返回类型
     * @throws JSONException 设定文件
     * @throws
     * @Description: 根据一级分类获取对应的二三级分类
     */
    public static void goodsSecAndThirdCategoryList(int parentID, final SuccessListener successListener, final FailuredListener failuredListener) {

        assert (successListener != null);
        assert (failuredListener != null);
        String url = MobileHttptRequest.getRequestUrl("Goods", "goodsSecAndThirdCategoryList");
        try {
            RequestParams params = new RequestParams();
            if (parentID >= 0) {
                params.put("parent_id", parentID);
            }

            MobileHttptRequest.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray data = (JSONArray) response.getJSONArray(MobileConstants.Response.RESULT);
                        List<Category> categorys = SPJsonUtil.fromJsonArrayToList(data, Category.class);
                        for (Category category : categorys) {
                            JSONArray array = category.getSubCategoryArray();
                            if (array != null) {
                                List<Category> subCategorys = null;
                                try {
                                    subCategorys = SPJsonUtil.fromJsonArrayToList(category.getSubCategoryArray(), Category.class);
                                } catch (Exception e) {
                                }
                                category.setSubCategory(subCategorys);
                            }
                        }
                        successListener.onRespone("success", categorys);
                        printCategory(categorys);
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
     * @param successListener
     * @param failuredListener
     * @return void    返回类型
     * @throws JSONException 设定文件
     * @throws
     * @Description: 获取所有分类
     */
    public static void getAllCategory(final SuccessListener successListener, final FailuredListener failuredListener) {

        assert (successListener != null);
        assert (failuredListener != null);

        String url = MobileHttptRequest.getRequestUrl("Goods", "goodsAllCategoryList");
        try {

            MobileHttptRequest.post(url, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray data = (JSONArray) response.getJSONArray(MobileConstants.Response.RESULT);
                        List<Category> categorys = SPJsonUtil.fromJsonArrayToList(data, Category.class);
                        successListener.onRespone("success", categorys);
                        printCategory(categorys);
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

    public static void printCategory(List<Category> categorys) {
        if (categorys != null) {
            for (int i = 0; i < categorys.size(); i++) {
                Category category = categorys.get(i);
            }
        }
    }

}

