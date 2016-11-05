/**
 * shopmobile for tpshop
 * ============================================================================
 * 版权所有 2015-2099 深圳搜豹网络科技有限公司，并保留所有权利。
 * 网站地址: http://www.tp-shop.cn
 * ——————————————————————————————————————
 * 这不是一个自由软件！您只能在不用于商业目的的前提下对程序代码进行修改和使用 .
 * 不允许对程序代码以任何形式任何目的的再发布。
 * ============================================================================
 * Author: 飞龙  wangqh01292@163.com
 * Date: @date 2015年10月28日 下午9:31:20
 * Description: 商城相关数据接口
 *
 * @version V1.0
 */
package com.familytraval.http;

import com.familytraval.Model.FilterItem;
import com.familytraval.Model.SPFilter;
import com.familytraval.Model.ShopOrder;
import com.familytraval.bean.Product;
import com.familytraval.bean.ProductAttribute;
import com.familytraval.bean.ProductCondition;
import com.familytraval.bean.ProductSpec;
import com.familytraval.callback.FailuredListener;
import com.familytraval.callback.SuccessListener;
import com.familytraval.common.ConsigneeAddress;
import com.familytraval.common.Coupon;
import com.familytraval.common.GoodsComment;
import com.familytraval.common.MobileApplication;
import com.familytraval.common.MobileConstants;
import com.familytraval.utils.MobileLog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.soubao.tpshop.utils.SPCommonUtils;
import com.soubao.tpshop.utils.SPJsonUtil;
import com.soubao.tpshop.utils.SPStringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * @author 飞龙
 */
public class SPShopRequest {

    private static String TAG = "SPShopRequest";

    /**
     * @param productCondition
     * @param failuredListener
     * @return void    返回类型
     * @throws
     * @Description: 获取商品列表
     * @ 设定文件
     */
    public static void getProductList(ProductCondition productCondition, final SuccessListener successListener, final FailuredListener failuredListener) {

        assert (successListener != null);
        assert (failuredListener != null);
        String url = MobileHttptRequest.getRequestUrl("Goods", "goodsList");

        RequestParams params = new RequestParams();
        params.put("p", productCondition.page);
        params.put("pagesize", MobileConstants.SizeOfPage);

        if (productCondition.href != null) {
            url = MobileConstants.BASE_HOST + productCondition.href;
        }

        /** 商品分类*/
        if (productCondition.categoryID > 0) {
            params.put("id", productCondition.categoryID);
        }

        /** 按照 productCondition.sort 字段进行 order 排序*/
        if (!SPStringUtils.isEmpty(productCondition.orderdesc) && !SPStringUtils.isEmpty(productCondition.orderby)) {
            params.put("orderby", productCondition.orderby);
            params.put("orderdesc", productCondition.orderdesc);
        }

        MobileHttptRequest.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                /** 针对返回的业务数据会重新包装一遍再返回到View */
                try {
                    String msg = (String) response.get(MobileConstants.Response.MSG);
                    JSONObject resultJson = (JSONObject) response.getJSONObject(MobileConstants.Response.RESULT);
                    JSONObject dataJson = new JSONObject();
                    List<SPFilter> filters = new ArrayList<SPFilter>();

                    if (resultJson != null) {
                        //排序URL
                        ShopOrder shopOrder = SPJsonUtil.fromJsonToModel(resultJson, ShopOrder.class);
                        if (shopOrder != null) {
                            dataJson.put("order", shopOrder);
                        }

                        //商品列表
                        if (!resultJson.isNull("goods_list")) {
                            JSONArray goodsList = resultJson.getJSONArray("goods_list");
                            if (goodsList != null) {
                                List<Product> products = SPJsonUtil.fromJsonArrayToList(goodsList, Product.class);
                                dataJson.put("product", products);
                            }
                        }

                        //选中菜单
                        if (!resultJson.isNull("filter_menu")) {
                            JSONArray menuJson = resultJson.getJSONArray("filter_menu");
                            if (menuJson != null) {
                                List<FilterItem> menus = SPJsonUtil.fromJsonArrayToList(menuJson, FilterItem.class);
                                for (FilterItem item : menus) {
                                    item.setIsHighLight(true);
                                }
                                SPFilter menuFilter = new SPFilter(1, "1", "选择分类", menus);
                                dataJson.put("menu", menuFilter);
                            }
                        }

                        //规格
                        if (!resultJson.isNull("filter_spec")) {
                            JSONArray filterSpecJson = resultJson.getJSONArray("filter_spec");
                            if (filterSpecJson != null) {
                                List<SPFilter> specs = SPJsonUtil.fromJsonArrayToList(filterSpecJson, SPFilter.class);
                                for (SPFilter spec : specs) {
                                    spec.setItems(SPJsonUtil.fromJsonArrayToList(spec.getItemJsonArray(), FilterItem.class));
                                    ;
                                }
                                filters.addAll(specs);
                            }
                        }

                        //属性
                        if (!resultJson.isNull("filter_attr")) {
                            JSONArray attrJson = resultJson.getJSONArray("filter_attr");
                            if (attrJson != null) {
                                List<SPFilter> attrs = SPJsonUtil.fromJsonArrayToList(attrJson, SPFilter.class);
                                for (SPFilter attr : attrs) {
                                    attr.setItems(SPJsonUtil.fromJsonArrayToList(attr.getItemJsonArray(), FilterItem.class));
                                    ;
                                }
                                filters.addAll(attrs);
                            }
                        }


                        //品牌
                        if (!resultJson.isNull("filter_brand")) {
                            JSONArray brandJson = resultJson.getJSONArray("filter_brand");
                            if (brandJson != null) {
                                List<FilterItem> brands = SPJsonUtil.fromJsonArrayToList(brandJson, FilterItem.class);
                                SPFilter brandFilter = new SPFilter(4, "4", "品牌", brands);
                                filters.add(brandFilter);
                            }
                        }

                        //价格
                        if (!resultJson.isNull("filter_price")) {
                            JSONArray priceJson = resultJson.getJSONArray("filter_price");
                            if (priceJson != null) {
                                List<FilterItem> prices = SPJsonUtil.fromJsonArrayToList(priceJson, FilterItem.class);
                                SPFilter priceFilter = new SPFilter(5, "5", "价格", prices);
                                filters.add(priceFilter);
                            }
                        }
                        dataJson.put("filter", filters);
                        successListener.onRespone(msg, dataJson);
                    } else {
                        failuredListener.onRespone("not found data", -1);
                    }
                } catch (JSONException e) {
                    failuredListener.onRespone(e.getMessage(), -1);
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    failuredListener.onRespone(e.getMessage(), -1);
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
    }


    /**
     * @param success success description
     * @param failure failure description
     * @Description: 搜索列表 -> 搜索结果
     * @url Api/Goods/search/q/相机
     */
    public static void searchResultProductListWithPage(int page, String searchKey, String href, final SuccessListener successListener, final FailuredListener failuredListener) {

        assert (successListener != null);
        assert (failuredListener != null);

        String url = MobileHttptRequest.getRequestUrl("Goods", "search");
        if (!SPStringUtils.isEmpty(href)) {
            url = MobileConstants.BASE_HOST + href;
        }

        RequestParams params = new RequestParams();
        params.put("p", page);
        params.put("pagesize", MobileConstants.SizeOfPage);

        if (!SPStringUtils.isEmpty(searchKey)) {
            params.put("q", searchKey);
        }


        MobileHttptRequest.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                /** 针对返回的业务数据会重新包装一遍再返回到View */
                try {
                    String msg = (String) response.get(MobileConstants.Response.MSG);
                    JSONObject resultJson = (JSONObject) response.getJSONObject(MobileConstants.Response.RESULT);
                    JSONObject dataJson = new JSONObject();
                    List<SPFilter> filters = new ArrayList<SPFilter>();

                    if (resultJson != null) {
                        //排序URL
                        ShopOrder shopOrder = SPJsonUtil.fromJsonToModel(resultJson, ShopOrder.class);
                        if (shopOrder != null) {
                            dataJson.put("order", shopOrder);
                        }

                        //商品列表
                        if (!resultJson.isNull("goods_list")) {
                            JSONArray goodsList = resultJson.getJSONArray("goods_list");
                            if (goodsList != null) {
                                List<Product> products = SPJsonUtil.fromJsonArrayToList(goodsList, Product.class);
                                dataJson.put("product", products);
                            }
                        }

                        //选中菜单
                        if (!resultJson.isNull("filter_menu")) {
                            JSONArray menuJson = resultJson.getJSONArray("filter_menu");
                            if (menuJson != null) {
                                List<FilterItem> menus = SPJsonUtil.fromJsonArrayToList(menuJson, FilterItem.class);
                                for (FilterItem item : menus) {
                                    item.setIsHighLight(true);
                                }
                                SPFilter menuFilter = new SPFilter(1, "1", "选择分类", menus);
                                dataJson.put("menu", menuFilter);
                            }
                        }

                        //规格
                        if (!resultJson.isNull("filter_spec")) {
                            JSONArray filterSpecJson = resultJson.getJSONArray("filter_spec");
                            if (filterSpecJson != null) {
                                List<SPFilter> specs = SPJsonUtil.fromJsonArrayToList(filterSpecJson, SPFilter.class);
                                for (SPFilter spec : specs) {
                                    spec.setItems(SPJsonUtil.fromJsonArrayToList(spec.getItemJsonArray(), FilterItem.class));
                                    ;
                                }
                                filters.addAll(specs);
                            }
                        }

                        //属性
                        if (!resultJson.isNull("filter_attr")) {
                            JSONArray attrJson = resultJson.getJSONArray("filter_attr");
                            if (attrJson != null) {
                                List<SPFilter> attrs = SPJsonUtil.fromJsonArrayToList(attrJson, SPFilter.class);
                                for (SPFilter attr : attrs) {
                                    attr.setItems(SPJsonUtil.fromJsonArrayToList(attr.getItemJsonArray(), FilterItem.class));
                                    ;
                                }
                                filters.addAll(attrs);
                            }
                        }


                        //品牌
                        if (!resultJson.isNull("filter_brand")) {
                            JSONArray brandJson = resultJson.getJSONArray("filter_brand");
                            if (brandJson != null) {
                                List<FilterItem> brands = SPJsonUtil.fromJsonArrayToList(brandJson, FilterItem.class);
                                SPFilter brandFilter = new SPFilter(4, "4", "品牌", brands);
                                filters.add(brandFilter);
                            }
                        }

                        //价格
                        if (!resultJson.isNull("filter_price")) {
                            JSONArray priceJson = resultJson.getJSONArray("filter_price");
                            if (priceJson != null) {
                                List<FilterItem> prices = SPJsonUtil.fromJsonArrayToList(priceJson, FilterItem.class);
                                SPFilter priceFilter = new SPFilter(5, "5", "价格", prices);
                                filters.add(priceFilter);
                            }
                        }
                        dataJson.put("filter", filters);
                        successListener.onRespone(msg, dataJson);
                    } else {
                        failuredListener.onRespone("not found data", -1);
                    }
                } catch (JSONException e) {
                    failuredListener.onRespone(e.getMessage(), -1);
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    failuredListener.onRespone(e.getMessage(), -1);
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
    }


    /**
     * @param productCondition 参数(包含goodsID)
     * @param failuredListener
     * @return void    返回类型
     * @throws
     * @Description: 获取商品
     * @ 设定文件
     */
    public static void getProductByID(ProductCondition productCondition, final SuccessListener successListener, final FailuredListener failuredListener) {
        assert (successListener != null);
        assert (failuredListener != null);
        String url = MobileHttptRequest.getRequestUrl("Goods", "goodsInfo");

        RequestParams params = new RequestParams();
        if (productCondition.goodsID > 0) {
            params.put("id", productCondition.goodsID);
        }
        MobileLog.i(TAG, "getProductByID -> url : " + url + "&" + params.toString());
        MobileHttptRequest.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject dataJson = new JSONObject();
                    /** 针对返回的业务数据会重新包装一遍再返回到View */
                    String msg = (String) response.get(MobileConstants.Response.MSG);
                    JSONObject result = (JSONObject) response.getJSONObject(MobileConstants.Response.RESULT);
                    Product product = null;
                    if (result.has("goods")) {

                        //属性
                        product = SPJsonUtil.fromJsonToModel(result.getJSONObject("goods"), Product.class);
                        if (product.getAttrJsonArray() != null) {
                            product.setAttrArr(SPJsonUtil.fromJsonArrayToList(product.getAttrJsonArray(), ProductAttribute.class));
                        }
                        //规格
                        if (product.getSpecJsonArray() != null) {
                            product.setSpecArr(SPJsonUtil.fromJsonArrayToList(product.getSpecJsonArray(), ProductSpec.class));
                        }
                    }

                    if (result.has("comment")) {
                        Object obj = result.get("comment");
                        List<GoodsComment> comments = SPJsonUtil.fromJsonArrayToList(result.getJSONArray("comment"), GoodsComment.class);
                        dataJson.put("comments", comments);
                    }

                    if (product != null && result.has("gallery")) {
                        JSONArray jsonGarrys = result.getJSONArray("gallery");
                        dataJson.put("gallery", jsonGarrys);
                    }

                    if (product != null) {
                        dataJson.put("product", product);
                    }

                    if (result.has("spec_goods_price") && !SPStringUtils.isEmpty(result.getString("spec_goods_price"))) {
                        Object obj = result.get("spec_goods_price");
                        JSONObject priceJson = result.getJSONObject("spec_goods_price");
                        dataJson.put("price", priceJson);
                    }

                    successListener.onRespone(msg, dataJson);
                } catch (JSONException e) {
                    failuredListener.onRespone(e.getMessage(), -1);
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    failuredListener.onRespone(e.getMessage(), -1);
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
    }

    /**
     * @throws
     * @Description: 获取购物车商品列表 同一个接口(购物车所有商品数量)
     * @URL index.php/Api/Cart/cartList
     */
    public static void getShopCartNumber(final SuccessListener successListener, final FailuredListener failuredListener) {
        assert (successListener != null);
        assert (failuredListener != null);
        String url = MobileHttptRequest.getRequestUrl("Cart", "cartList");
        MobileHttptRequest.post(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject dataJson = new JSONObject();
                    /** 针对返回的业务数据会重新包装一遍再返回到View */
                    String msg = (String) response.getString(MobileConstants.Response.MSG);
                    int status = response.getInt(MobileConstants.Response.STATUS);
                    if (status > 0) {
                        JSONObject resultJson = response.getJSONObject("result");
                        JSONObject feeJson = resultJson.getJSONObject("total_price");
                        int count = 0;
                        if (feeJson.has("num")) {
                            count = feeJson.getInt("num");
                        }

                        successListener.onRespone(msg, count);
                    }
                } catch (Exception e) {
                    successListener.onRespone(e.getMessage(), -1);
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
    }


    /**
     * 添加商品到购物车（对购物车商品数量操作，数量增加或减少）
     *
     * @param goodsID          商品id
     * @param specs            商品规格id
     * @param number           数量
     * @param successListener  success description
     * @param failuredListener failure description
     * @URL Api/Cart/addCart?goods_spec[尺码]=3&goods_spec[颜色]=4&goods_num=2&goods_id=1
     */
    public static void shopCartGoodsOperation(String goodsID, String specs, int number, final SuccessListener successListener, final FailuredListener failuredListener) {
        assert (successListener != null);
        assert (failuredListener != null);
        String url = MobileHttptRequest.getRequestUrl("Cart", "addCart");
        RequestParams params = new RequestParams();
        params.put("goods_num", number);
        params.put("goods_id", goodsID);
        if (!SPStringUtils.isEmpty(specs)) {
            params.put("goods_spec", "[" + specs + "]");
        }

        MobileHttptRequest.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject dataJson = new JSONObject();
                    /** 针对返回的业务数据会重新包装一遍再返回到View */
                    String msg = (String) response.getString(MobileConstants.Response.MSG);
                    int status = response.getInt(MobileConstants.Response.STATUS);
                    if (status > 0) {
                        int count = response.getInt(MobileConstants.Response.RESULT);
                        successListener.onRespone(msg, count);
                    } else {
                        failuredListener.handleResponse(msg, status);
                    }
                } catch (Exception e) {
                    failuredListener.onRespone(e.getMessage(), -1);
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
    }


    /**
     * @URL index.php/Api/Cart/cartList
     * 获取购物车商品列表
     */
    public static void getShopCartList(JSONArray formDataArray, final SuccessListener successListener, final FailuredListener failuredListener) {

        assert (successListener != null);
        assert (failuredListener != null);
        String url = MobileHttptRequest.getRequestUrl("Cart", "cartList");

        RequestParams params = new RequestParams();

        String formData = formDataArray.toString();

        if (formDataArray != null && formDataArray.length() > 0) {
            //表单数据: POST提交
            params.put("cart_form_data", formData);
        }
        params.put("test", "1");


        MobileHttptRequest.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    /** 针对返回的业务数据会重新包装一遍再返回到View */
                    String msg = (String) response.getString(MobileConstants.Response.MSG);
                    int status = response.getInt(MobileConstants.Response.STATUS);
                    if (status > 0) {
                        JSONObject jsonObject = new JSONObject();
                        if (response.has("result")) {
                            JSONObject resultJson = response.getJSONObject("result");
                            if (resultJson.has("cartList")) {
                                List<Product> products = SPJsonUtil.fromJsonArrayToList(resultJson.getJSONArray("cartList"), Product.class);
                                jsonObject.put("products", products);
                            }

                            if (resultJson.has("total_price")) {
                                JSONObject feeJson = resultJson.getJSONObject("total_price");
                                if (feeJson != null && feeJson.has("total_fee")) {
                                    double totalFee = feeJson.getDouble("total_fee");
                                    jsonObject.put("totalFee", totalFee);//总金额(需要支付的金额
                                }
                                if (feeJson != null && feeJson.has("cut_fee")) {
                                    double cutFee = feeJson.getDouble("cut_fee");//节省金额
                                    jsonObject.put("cutFee", cutFee);
                                }
                            }
                        }
                        successListener.onRespone(msg, jsonObject);
                    } else {
                        failuredListener.handleResponse(msg, status);
                    }
                } catch (Exception e) {
                    failuredListener.onRespone(e.getMessage(), -1);
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

    }


    /**
     * @URL index.php/Api/Cart/cart2
     * 获取确认订单数据(购物车订单填写页)
     */
    public static void getConfirmOrderData(final SuccessListener successListener, final FailuredListener failuredListener) {

        assert (successListener != null);
        assert (failuredListener != null);
        String url = MobileHttptRequest.getRequestUrl("Cart", "cart2");

        MobileHttptRequest.post(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    /** 针对返回的业务数据会重新包装一遍再返回到View */
                    String msg = (String) response.getString(MobileConstants.Response.MSG);
                    int status = response.getInt(MobileConstants.Response.STATUS);
                    if (status > 0) {
                        JSONObject jsonObject = new JSONObject();
                        if (response.has("result")) {
                            JSONObject resultJson = response.getJSONObject("result");
                            if (resultJson.has("addressList")) {
                                ConsigneeAddress consignees = SPJsonUtil.fromJsonToModel(resultJson.getJSONObject("addressList"), ConsigneeAddress.class);
                                jsonObject.put("consigneeAddress", consignees);
                            }

                            //物流信息
                            if (resultJson.has("shippingList")) {
                                JSONArray delivers = resultJson.getJSONArray("shippingList");
                                jsonObject.put("delivers", delivers);//总金额(需要支付的金额
                            }

                            //商品列表
                            if (resultJson.has("cartList")) {
                                List<Product> products = SPJsonUtil.fromJsonArrayToList(resultJson.getJSONArray("cartList"), Product.class);
                                jsonObject.put("products", products);//总金额(需要支付的金额
                            }

                            //优惠券, 代金券
                            if (resultJson.has("cartList")) {
                                List<Coupon> coupons = SPJsonUtil.fromJsonArrayToList(resultJson.getJSONArray("couponList"), Coupon.class);
                                jsonObject.put("coupons", coupons);
                            }

                            if (resultJson.has("userInfo")) {
                                JSONObject userJson = resultJson.getJSONObject("userInfo");
                                jsonObject.put("userInfo", userJson);
                            }
                        }
                        successListener.onRespone(msg, jsonObject);
                    } else {
                        failuredListener.onRespone(msg, -1);
                    }
                } catch (Exception e) {
                    failuredListener.onRespone(e.getMessage(), -1);
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

    }


    /**
     * @URL index.php?m=Api&c=Cart&a=cart3
     * 根据选择的订单信息查询总价(物流, )
     */
    public static void getOrderTotalFee(RequestParams params, final SuccessListener successListener, final FailuredListener failuredListener) {

        assert (successListener != null);
        assert (failuredListener != null);
        String url = MobileHttptRequest.getRequestUrl("Cart", "cart3");

        MobileHttptRequest.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    /** 针对返回的业务数据会重新包装一遍再返回到View */
                    String msg = (String) response.getString(MobileConstants.Response.MSG);
                    int status = response.getInt(MobileConstants.Response.STATUS);
                    if (status > 0) {
                        JSONObject jsonObject = null;
                        if (response.has("result")) {
                            jsonObject = response.getJSONObject("result");
                        }
                        successListener.onRespone(msg, jsonObject);
                    } else {
                        failuredListener.onRespone(msg, -1);
                    }
                } catch (Exception e) {
                    failuredListener.onRespone(e.getMessage(), -1);
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
    }

    /**
     * @param successListener  success description
     * @param failuredListener failure description
     * @URL index.php?m=Api&c=Cart&a=cart3
     * 提交订单(该方法与getOrderTotalFeeWithParams获取商品价格信息URL参数,
     * URL基本一致, 主要区别在于act参数)
     */
    public static void submitOrder(RequestParams params, final SuccessListener successListener, final FailuredListener failuredListener) {

        assert (successListener != null);
        assert (failuredListener != null);
        String url = MobileHttptRequest.getRequestUrl("Cart", "cart3");

        MobileHttptRequest.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    /** 针对返回的业务数据会重新包装一遍再返回到View */
                    String msg = (String) response.getString(MobileConstants.Response.MSG);
                    int status = response.getInt(MobileConstants.Response.STATUS);
                    if (status > 0) {
                        String orderId = null;
                        if (response.has("result")) {
                            orderId = response.getString("result");
                        }
                        successListener.onRespone(msg, orderId);
                    } else {
                        failuredListener.handleResponse(msg, status);
                    }
                } catch (Exception e) {
                    failuredListener.onRespone(e.getMessage(), -1);
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
    }


    /**
     * @param goodsID          description
     * @param successListener  success description
     * @param failuredListener failure description
     * @URL index.php?m=Api&c=Goods&a=getGoodsComment&goods_id=1
     * 获取商品评论
     */
    public static void getGoodsCommentWithGoodsID(String goodsID, int page, final SuccessListener successListener, final FailuredListener failuredListener) {

        assert (successListener != null);
        assert (failuredListener != null);
        String url = MobileHttptRequest.getRequestUrl("Goods", "getGoodsComment");

        RequestParams params = new RequestParams();
        params.put("goods_id", goodsID);
        params.put("p", page);

        MobileHttptRequest.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    /** 针对返回的业务数据会重新包装一遍再返回到View */
                    String msg = (String) response.getString(MobileConstants.Response.MSG);
                    int status = response.getInt(MobileConstants.Response.STATUS);
                    List<GoodsComment> comments = null;
                    if (status > 0) {
                        String orderId = null;
                        if (response.has("result")) {
                            comments = SPJsonUtil.fromJsonArrayToList(response.getJSONArray("result"), GoodsComment.class);
                            for (GoodsComment goodsComment : comments) {
                                if (goodsComment.getImageArray() == null) {
                                    continue;
                                }
                                List<String> images = MobileHttptRequest.convertJsonArrayToList(goodsComment.getImageArray());
                                goodsComment.setImages(images);
                            }
                        }
                        successListener.onRespone(msg, comments);
                    } else {
                        failuredListener.onRespone(msg, -1);
                    }
                } catch (Exception e) {
                    failuredListener.onRespone(e.getMessage(), -1);
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
    }

    /**
     * 获取服务器时间戳
     *
     * @param successListener  success description
     * @param failuredListener failure description
     * @URL index.php?m=Api&c=Base&a=getServerTime
     */
    public static void refreshServiceTime(final SuccessListener successListener, final FailuredListener failuredListener) {

        assert (successListener != null);
        assert (failuredListener != null);
        String url = MobileHttptRequest.getRequestUrl("Base", "getServerTime");

        MobileHttptRequest.post(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    /** 针对返回的业务数据会重新包装一遍再返回到View */
                    String msg = (String) response.getString(MobileConstants.Response.MSG);
                    int status = response.getInt(MobileConstants.Response.STATUS);
                    if (status > 0) {
                        long serviceTime = response.getLong("result");
                        long localTime = SPCommonUtils.getCurrentTime();
                        long cutServiceTime = serviceTime - localTime;
                        MobileApplication.getInstance().setCutServiceTime(cutServiceTime);
                        successListener.onRespone(msg, cutServiceTime);
                    } else {
                        failuredListener.onRespone(msg, status);
                    }
                } catch (Exception e) {
                    failuredListener.onRespone(e.getMessage(), -1);
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
    }


    /**
     * 删除购物车的商品
     *
     * @param ids              多个商品ID用逗号分隔
     * @param successListener  <#success description#>
     * @param failuredListener <#failure description#>
     * @url index.php?m=Api&c=Cart&a=delCart
     */
    public static void deleteShopCartProductWithIds(String cartIds, final SuccessListener successListener, final FailuredListener failuredListener) {

        assert (successListener != null);
        assert (failuredListener != null);
        String url = MobileHttptRequest.getRequestUrl("Cart", "delCart");

        RequestParams params = new RequestParams();
        params.put("ids", cartIds);

        MobileHttptRequest.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    /** 针对返回的业务数据会重新包装一遍再返回到View */
                    String msg = (String) response.getString(MobileConstants.Response.MSG);
                    int status = response.getInt(MobileConstants.Response.STATUS);
                    if (status > 0) {
                        int count = response.getInt(MobileConstants.Response.RESULT);
                        successListener.onRespone(msg, count);
                    } else {
                        failuredListener.onRespone(msg, -1);
                    }
                } catch (Exception e) {
                    failuredListener.onRespone(e.getMessage(), -1);
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
    }


    /**
     * @param page
     * @param successListener
     * @param failuredListener
     * @return void    返回类型
     * @throws
     * @Description: 猜你喜欢/热门推荐
     * @ 设定文件
     */
    public static void guessYouLike(int page, final SuccessListener successListener, final FailuredListener failuredListener) {

        assert (successListener != null);
        assert (failuredListener != null);
        String url = MobileHttptRequest.getRequestUrl("Goods", "guessYouLike");

        RequestParams params = new RequestParams();
        params.put("p", page);

        MobileHttptRequest.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                /** 针对返回的业务数据会重新包装一遍再返回到View */
                try {
                    String msg = (String) response.get(MobileConstants.Response.MSG);
                    int status = response.getInt(MobileConstants.Response.STATUS);
                    if (status > 0) {
                        JSONArray resulJson = response.getJSONArray(MobileConstants.Response.RESULT);
                        List<Product> products = SPJsonUtil.fromJsonArrayToList(resulJson, Product.class);
                        successListener.onRespone(msg, products);
                    } else {
                        failuredListener.onRespone("not found data", -1);
                    }
                } catch (JSONException e) {
                    failuredListener.onRespone(e.getMessage(), -1);
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    failuredListener.onRespone(e.getMessage(), -1);
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
    }

}
