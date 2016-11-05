package com.familytraval.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dings on 2016/10/25.
 */

public class SPJsonUtil {
    private static String TAG = "SPJsonUtil";

    public SPJsonUtil() {
    }

    public static <T> Object fromJsonToModel(JSONObject json, Class<T> pojo) throws Exception {
        HashMap propertyMap = null;
        Object objs = null;
        Method m = null;

        try {
            m = pojo.getDeclaredMethod("replaceKeyFromPropertyName", new Class[0]);
        } catch (NoSuchMethodException var14) {
            ;
        }

        try {
            if (m != null) {
                propertyMap = new HashMap();
                String[] fields = (String[]) m.invoke(pojo.newInstance(), (Object[]) objs);
                if (fields != null && fields.length % 2 == 0) {
                    for (int t = 0; t < fields.length; t += 2) {
                        propertyMap.put(fields[t], fields[t + 1]);
                    }
                }
            }
        } catch (SecurityException var16) {
            var16.printStackTrace();
        } catch (IllegalAccessException var17) {
            var17.printStackTrace();
        } catch (IllegalArgumentException var18) {
            var18.printStackTrace();
        } catch (InvocationTargetException var19) {
            var19.printStackTrace();
        } catch (InstantiationException var20) {
            var20.printStackTrace();
        }

        Field[] var21 = pojo.getDeclaredFields();
        Object var22 = pojo.newInstance();
        Field[] var10 = var21;
        int var9 = var21.length;

        for (int var8 = 0; var8 < var9; ++var8) {
            Field field = var10[var8];
            field.setAccessible(true);
            String name = field.getName();
            if (propertyMap != null && propertyMap.keySet().contains(name)) {
                name = (String) propertyMap.get(name);
            }

            try {
                json.get(name);
            } catch (Exception var15) {
                continue;
            }

            if (json.get(name) != null && !"".equals(json.getString(name))) {
                if (!field.getType().equals(Long.class) && !field.getType().equals(Long.TYPE)) {
                    if (field.getType().equals(String.class)) {
                        field.set(var22, json.getString(name));
                    } else if (!field.getType().equals(Float.class) && !field.getType().equals(Float.TYPE)) {
                        if (!field.getType().equals(Double.class) && !field.getType().equals(Double.TYPE)) {
                            if (!field.getType().equals(Integer.class) && !field.getType().equals(Integer.TYPE)) {
                                if (field.getType().equals(Date.class)) {
                                    field.set(var22, Long.valueOf(Date.parse(json.getString(name))));
                                } else if (field.getType().equals(JSONArray.class)) {
                                    try {
                                        if (!SPStringUtils.isEmpty(json.getString(name))) {
                                            field.set(var22, new JSONArray(json.getString(name)));
                                        }
                                    } catch (Exception var13) {
                                        ;
                                    }
                                } else if (field.getType().equals(JSONObject.class)) {
                                    field.set(var22, json.getJSONObject(name));
                                }
                            } else {
                                field.set(var22, Integer.valueOf(Integer.parseInt(json.getString(name))));
                            }
                        } else {
                            field.set(var22, Double.valueOf(Double.parseDouble(json.getString(name))));
                        }
                    } else {
                        field.set(var22, Float.valueOf(Float.parseFloat(json.getString(name))));
                    }
                } else {
                    field.set(var22, Long.valueOf(Long.parseLong(json.getString(name))));
                }
            }
        }

        return var22;
    }

    public static <T> List<T> fromJsonArrayToList(JSONArray jsonArr, Class<T> pojo) throws Exception {
        if (jsonArr == null) {
            Log.w(TAG, " fromJsonToModel jsonArr is null");
            return null;
        } else {
            int len = jsonArr.length();
            ArrayList list = new ArrayList(len);

            for (int i = 0; i < len; ++i) {
                JSONObject json = jsonArr.getJSONObject(i);
                Object t = fromJsonToModel(json, pojo);
                list.add(t);
            }

            return list;
        }
    }

    public static <T> List<T> fromJsonArrayToList(List jsonArr, Class<T> pojo) throws Exception {
        if (jsonArr == null) {
            Log.w(TAG, " fromJsonToModel jsonArr is null");
            return null;
        } else {
            int len = jsonArr.size();
            ArrayList list = new ArrayList(len);

            for (int i = 0; i < len; ++i) {
                JSONObject json = (JSONObject) jsonArr.get(i);
                Object t = fromJsonToModel(json, pojo);
                list.add(t);
            }

            return list;
        }
    }
}

