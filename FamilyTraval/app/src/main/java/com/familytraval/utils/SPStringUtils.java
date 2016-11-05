package com.familytraval.utils;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dings on 2016/10/24.
 */

public class SPStringUtils {
    public SPStringUtils() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() < 1 || "null".equals(str.trim());
    }

    public static boolean isEditEmpty(EditText text) {
        return text == null || "".equals(text.getText().toString().trim());
    }

    public static String listToString(List list, String separator) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); ++i) {
            sb.append(list.get(i)).append(separator);
        }

        return separator.equals("") ? sb.toString() : sb.toString().substring(0, sb.toString().length() - 1);
    }

    public static List<String> stringToList(String string, String separator) {
        new StringBuilder();
        if (isEmpty(string)) {
            return null;
        } else {
            String[] substrs = string.split(separator);
            if ("|".equals(separator)) {
                substrs = string.split("\\|");
            }

            ArrayList substrList = new ArrayList();
            if (substrs != null) {
                String[] var8 = substrs;
                int var7 = substrs.length;

                for (int var6 = 0; var6 < var7; ++var6) {
                    String sbustr = var8[var6];
                    substrList.add(sbustr);
                }
            }

            return substrList;
        }
    }

    public static String collectToString(Collection<String> collect, String separator) {
        ArrayList list = new ArrayList();
        Iterator var4 = collect.iterator();

        while (var4.hasNext()) {
            String id = (String) var4.next();
            list.add(id);
        }

        return listToString(list, separator);
    }
}

