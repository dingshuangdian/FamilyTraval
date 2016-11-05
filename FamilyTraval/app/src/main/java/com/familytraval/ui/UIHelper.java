package com.familytraval.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.familytraval.activity.CitySelectActivity;
import com.familytraval.activity.GetSecurityActivity;
import com.familytraval.activity.LoginActivity;
import com.familytraval.activity.MainActivity;
import com.familytraval.activity.MessageActivity;
import com.familytraval.activity.ProductActivity;
import com.familytraval.activity.ProductDetailActivity;
import com.familytraval.activity.RegisterActivity;
import com.familytraval.activity.SetPasswordActivity;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 */
public class UIHelper {

    public final static String TAG = "UIHelper";

    public final static int RESULT_OK = 0x00;
    public final static int REQUEST_CODE = 0x01;

    public static void ToastMessage(Context cont, String msg) {
        if (cont == null || msg == null) {
            return;
        }
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(Context cont, int msg) {
        if (cont == null || msg <= 0) {
            return;
        }
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(Context cont, String msg, int time) {
        if (cont == null || msg == null) {
            return;
        }
        Toast.makeText(cont, msg, time).show();
    }

    public static void showHome(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void showLogin(Activity context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void RegisterActivity(Activity context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    public static void GetSecurityActivity(Activity context) {
        Intent intent = new Intent(context, GetSecurityActivity.class);
        context.startActivity(intent);

    }

    public static void SetPasswordActivity(Activity context) {
        Intent intent = new Intent(context, SetPasswordActivity.class);
        context.startActivity(intent);

    }

    public static void SetMessageActivity(Activity context) {
        Intent intent = new Intent(context, MessageActivity.class);
        context.startActivity(intent);

    }

    public static void CitySelectActivity(Activity context) {
        Intent intent = new Intent(context, CitySelectActivity.class);
        context.startActivity(intent);

    }

    public static void ProductDetailActivity(Activity context) {
        Intent intent = new Intent(context, ProductActivity.class);
        context.startActivity(intent);
    }


}
