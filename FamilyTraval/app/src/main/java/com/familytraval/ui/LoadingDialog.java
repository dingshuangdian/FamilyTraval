package com.familytraval.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.familytraval.R;

/**
 * Created by dings on 2016/10/24.
 */

public class LoadingDialog extends Dialog {


    private TextView tv;
    public String mTitle;

    public LoadingDialog(Context context, String title) {
        super(context, R.style.loadingDialogStyle);
        mTitle = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);
        tv = (TextView) findViewById(R.id.tv);
        if (mTitle == null) {
            mTitle = "正在加载数据, 请稍后";
        }
        tv.setText(mTitle);
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.LinearLayout);
        linearLayout.getBackground().setAlpha(210);
    }

}

