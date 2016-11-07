package com.familytraval.activity;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends FragmentActivity {
    private TextView lg_rg;
    private Button btnSure;
    private EditText phone;
    private EditText password;
    private DBUtils dbUtils;
    private boolean flag;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


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
        //从共享参数获取数据
        autoLogin();

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
                        flag = true;
                        //登陆成功后将flag设置为ture存入共享参数中
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("login2", flag);
                        saveMsg("login", map);
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

    /**
     * 将数据存储进入共享参数
     */
    public boolean saveMsg(String fileName, Map<String, Object> map) {
        boolean flag = false;
        preferences = getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        editor = preferences.edit();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object object = entry.getValue();
            // 根据值得不同类型，添加
            if (object instanceof Boolean) {
                Boolean new_name = (Boolean) object;
                editor.putBoolean(key, new_name);
            } else if (object instanceof Integer) {
                Integer integer = (Integer) object;
                editor.putInt(key, integer);
            } else if (object instanceof Float) {
                Float f = (Float) object;
                editor.putFloat(key, f);
            } else if (object instanceof Long) {
                Long l = (Long) object;
                editor.putLong(key, l);
            } else if (object instanceof String) {
                String s = (String) object;
                editor.putString(key, s);
            }
        }
        flag = editor.commit();
        return flag;
    }

    /**
     * 读取数据
     */
    public Map<String, ?> getMsg(String fileName) {
        Map<String, ?> map = null;
        // 读取数据用不到edit
        preferences = getSharedPreferences(fileName,
                Context.MODE_APPEND);
        //Context.MODE_APPEND可以对已存在的值进行修改
        map = preferences.getAll();
        return map;
    }

    /**
     * 自动登陆
     */
    public void autoLogin() {
        HashMap<String, Object> map = (HashMap<String, Object>) getMsg("login");
        if (map != null && !map.isEmpty()) {
            if ((Boolean) map.get("login2")) {
                //若值为true,用户无需输入密码，直接跳转进入操作界面
                UIHelper.showHome(LoginActivity.this);
            }
        }
    }
}
