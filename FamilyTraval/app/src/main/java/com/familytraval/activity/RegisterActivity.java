package com.familytraval.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.familytraval.R;
import com.familytraval.ui.UIHelper;

import cn.smssdk.SMSSDK;

public class RegisterActivity extends FragmentActivity {
    private EditText registerPhone;
    private Button btnNext;
    private TextView rg_back;
    private String cellNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerPhone = (EditText) findViewById(R.id.registerPhone);
        rg_back = (TextView) findViewById(R.id.rg_back);
        btnNext = (Button) findViewById(R.id.btnNext);
        rg_back.setOnClickListener(rgClickListener);
        btnNext.setOnClickListener(rgClickListener);
    }

    private View.OnClickListener rgClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnNext:
                    cellNumber = registerPhone.getText().toString().trim();
                    //发送短信，传入国家号和电话---使用SMSSDK核心类之前一定要在MyApplication中初始化，否则不能使用
                    if ((TextUtils.isEmpty(cellNumber))) {
                        Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    } else {
                        SMSSDK.getVerificationCode("+86", cellNumber);
                        Toast.makeText(RegisterActivity.this, "短信已发送！", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = RegisterActivity.this.getSharedPreferences("phoneNumber", Context.MODE_PRIVATE);
                        sp.edit().putString("cellNumber", cellNumber).commit();
                        UIHelper.GetSecurityActivity(RegisterActivity.this);
                    }
                    break;
                case R.id.rg_back:
                    UIHelper.showLogin(RegisterActivity.this);
                    break;
            }
        }
    };

    public void getCode() {
        SMSSDK.getVerificationCode("+86", cellNumber);

    }
}
