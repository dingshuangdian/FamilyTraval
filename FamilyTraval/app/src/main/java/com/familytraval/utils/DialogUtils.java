package com.familytraval.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by dings on 2016/10/24.
 */

public class DialogUtils {


    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}
