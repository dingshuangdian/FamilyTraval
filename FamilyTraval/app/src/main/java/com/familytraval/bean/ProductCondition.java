package com.familytraval.bean;

/**
 * Created by dings on 2016/10/25.
 */

public class ProductCondition extends Condition {
    public int categoryID = -1;    //分类ID
    public String goodsName;    //分类ID
    public String orderdesc;    //排序方式: desc , asc
    public String orderby;        //排序字段
    public int goodsID;            //商品ID
    public String href;            //请求URL
}
