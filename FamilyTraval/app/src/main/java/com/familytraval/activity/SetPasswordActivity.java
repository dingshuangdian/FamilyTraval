package com.familytraval.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.familytraval.R;
import com.familytraval.ui.UIHelper;
import com.familytraval.utils.DBUtils;
import com.familytraval.utils.MD5Utils;

public class SetPasswordActivity extends FragmentActivity {
    private Button btnSure;
    private EditText sure_password;
    private EditText confirm_password;
    private DBUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpassword);
        btnSure = (Button) findViewById(R.id.btnSure);
        btnSure.setOnClickListener(clickListener);
        sure_password = (EditText) findViewById(R.id.sure_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        dbUtils = new DBUtils(this);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSure:
                    SharedPreferences sp = SetPasswordActivity.this.getSharedPreferences("phoneNumber", Context.MODE_PRIVATE);
                    String phoneNum = sp.getString("cellNumber", "");
                    String password = sure_password.getText().toString();
                    String confirnPassword = confirm_password.getText().toString();
                    if (password.equals(confirnPassword)) {
                        if (password.matches("^[a-zA-Z]\\w{5,17}$")) {
                            password = MD5Utils.getMD5(password);
                            dbUtils.insert(phoneNum, password);
                            Toast.makeText(SetPasswordActivity.this, "密码设置成功", Toast.LENGTH_SHORT).show();
                            UIHelper.SetMessageActivity(SetPasswordActivity.this);
                        } else {
                            Toast.makeText(SetPasswordActivity.this, "密码不能过于简单", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SetPasswordActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };
}
