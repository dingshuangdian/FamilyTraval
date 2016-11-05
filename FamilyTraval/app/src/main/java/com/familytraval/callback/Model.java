package com.familytraval.callback;

/**
 * Created by dings on 2016/10/25.
 */

public interface Model {

    /**
     * @return String[] 属性i为实体属性, i+i 为json对应的属性
     * @throws
     * @Description: 返回用实体属性与之对于的json属性
     */
    public String[] replaceKeyFromPropertyName();
}
