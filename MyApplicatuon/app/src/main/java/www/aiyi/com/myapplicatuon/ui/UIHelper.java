package www.aiyi.com.myapplicatuon.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import www.aiyi.com.myapplicatuon.activity.DetailPointActivity;
import www.aiyi.com.myapplicatuon.activity.MyPointActivity;

/**
 * Created by dings on 2016-11-26.
 */

public class UIHelper {
    public final static String TAG = "UIHelper";
    public final static int RESULT_OK = 0x00;
    public final static int REQUEST_CODE = 0x01;

    public static void ToastMessage(Context context, String msg) {
        if (context == null || msg == null) {
            return;
        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(Context context, String msg, int time) {
        if (context == null || msg == null) {
            return;
        }
        Toast.makeText(context, msg, time).show();
    }

    public static void ToastMessage(Context context, int msg) {
        if (context == null || msg <= 0) {
            return;

        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showMyPoint(Activity context) {
        Intent intent = new Intent(context, MyPointActivity.class);
        context.startActivity(intent);
    }

    public static void showDetailPoint(Activity context) {
        Intent intent = new Intent(context, DetailPointActivity.class);
        context.startActivity(intent);
    }

}
