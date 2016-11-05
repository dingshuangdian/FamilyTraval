package com.familytraval.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.familytraval.R;
import com.familytraval.ui.UIHelper;
import com.familytraval.utils.DBUtils;
import com.familytraval.utils.MD5Utils;

public class LoginActivity extends FragmentActivity {
    private TextView lg_rg;
    private Button btnSure;
    private EditText phone;
    private EditText password;
    private DBUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lg_rg = (TextView) findViewById(R.id.lg_rg);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        btnSure = (Button) findViewById(R.id.btnSure);
        dbUtils = new DBUtils(this);
        lg_rg.setOnClickListener(clickListener);
        btnSure.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSure:
                    String cellphone = phone.getText().toString();
                    String password1 = password.getText().toString();
                    password1 = MD5Utils.getMD5(password1);
                    Cursor cursor = dbUtils.query(cellphone.trim(), password1.trim());
                    if (cursor.moveToFirst()) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        UIHelper.showHome(LoginActivity.this);
                    } else {
                        Toast.makeText(LoginActivity.this, "帐号或密码错误", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.lg_rg:
                    UIHelper.RegisterActivity(LoginActivity.this);
            }
        }
    };
}
