package com.familytraval.utils;

import com.familytraval.bean.Collect;
import com.familytraval.common.MobileApplication;
import com.soubao.tpshop.utils.*;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by dings on 2016/10/26.
 */

public class ShopUtils {

    /**
     * 判断某个商品是否收藏
     *
     * @return
     */
    public static boolean isCollected(String goodsID) {

        if (com.soubao.tpshop.utils.SPStringUtils.isEmpty(goodsID) || MobileApplication.getInstance() == null)
            return false;

        List<Collect> collects = MobileApplication.getInstance().collects;
        if (collects == null || collects.size() < 1) {
            return false;
        }

        for (Collect collect : collects) {
            if (collect != null && goodsID.equals(collect.getGoodsID())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对规格ID按升序排序之后, 组成的key获取对应的价格和库存
     *
     * @return
     */
    public static String getPricekey(Collection<String> keys) {

        List<Integer> idlist = new ArrayList<Integer>();
        for (String id : keys) {
            idlist.add(Integer.valueOf(id));
        }
        return getPricekey(idlist);
    }

    /**
     * 对规格ID按升序排序之后, 组成的key获取对应的价格和库存
     *
     * @param spceids
     * @return
     */
    public static String getPricekey(List<Integer> spceids) {

        String priceKey = null;
        if (spceids != null && spceids.size() > 0) {
            Collections.sort(spceids);//对id原始顺序排序
            priceKey = com.soubao.tpshop.utils.SPStringUtils.listToString(spceids, "_");
        }
        return priceKey;
    }


    /**
     * 根据规格获取相关价格
     *
     * @param priceJson
     * @param keys
     * @return
     */
    public static String getShopprice(JSONObject priceJson, Collection<String> keys) {

        if (keys == null || keys.size() < 1) return null;

        String pricekey = getPricekey(keys);

        return getShopprice(priceJson, pricekey);

    }

    /**
     * 根据规格获取相关价格
     *
     * @param priceJson
     * @param keys
     * @return
     */
    public static int getShopStoreCount(JSONObject priceJson, Collection<String> keys) {

        if (keys == null || keys.size() < 1) return 0;

        String pricekey = getPricekey(keys);

        return getShopStoreCount(priceJson, pricekey);

    }

    /**
     * 根据规格获取库存数量
     *
     * @param priceJson
     * @param key
     * @return
     */
    public static int getShopStoreCount(JSONObject priceJson, String key) {

        if (priceJson == null || key == null) {
            return 0;
        }
        try {
            if (priceJson.has(key)) {
                JSONObject specMap = priceJson.getJSONObject(key);
                if (specMap == null) {
                    return 0;
                }
                if (specMap.has("store_count")) {
                    return specMap.getInt("store_count");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据规格获取相关价格
     *
     * @param priceJson
     * @param key
     * @return
     */
    public static String getShopprice(JSONObject priceJson, String key) {

        if (priceJson == null || key == null) {
            return null;
        }
        try {
            if (priceJson.has(key)) {
                JSONObject specMap = priceJson.getJSONObject(key);
                if (specMap == null) {
                    return null;
                }
                return specMap.getString("price");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

