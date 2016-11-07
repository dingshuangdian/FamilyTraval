package com.familytraval.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.familytraval.R;
import com.familytraval.ui.UIHelper;
import com.familytraval.utils.MyCountDownTimer;
import com.familytraval.utils.SharedPreferences;

import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class GetSecurityActivity extends FragmentActivity {
    private TextView tv_getNumber;
    private TextView rg_back;
    private Button btnGetCode;
    private String postSecurity;
    private String realPhone;
    private EditText code;
    private ProgressDialog dialog;
    RegisterActivity registerActivity;
    // 短信注册，随机产生头像
    private static final String[] AVATARS = {
            "http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
            "http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
            "http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
            "http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
            "http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
            "http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
            "http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
            "http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
            "http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
            "http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getcode);
        btnGetCode = (Button) findViewById(R.id.btnGetCode);
        tv_getNumber = (TextView) findViewById(R.id.tv_getNumber);
        code = (EditText) findViewById(R.id.code);
        rg_back = (TextView) findViewById(R.id.rg_back);
        registerActivity = new RegisterActivity();
        time();
        getText();
        rg_back.setOnClickListener(clickListener);
        btnGetCode.setOnClickListener(clickListener);
        SMSSDK.registerEventHandler(ev); //注册短信回调监听

    }

    private void getText() {
        realPhone = SharedPreferences.getInstance().getString("cellphone", "");
        tv_getNumber.setText("我们已经给您的手机号码" + realPhone + "发送了一条验证短信,请稍等。");

    }

    /**
     * 短信验证的回调监听
     */
    private EventHandler ev = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) { //回调完成
                //提交验证码成功,如果验证成功会在data里返回数据。data数据类型为HashMap<number,code>
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Log.e("TAG", "提交验证码成功" + data.toString());
                    HashMap<String, Object> mData = (HashMap<String, Object>) data;
                    String country = (String) mData.get("country");//返回的国家编号
                    String phone = (String) mData.get("phone");//返回用户注册的手机号
                    //提交用户信息
                    registerUser(country, phone);
                    if (phone.equals(realPhone)) {
                        runOnUiThread(new Runnable() {//更改ui的操作要放在主线程，实际可以发送hander
                            @Override
                            public void run() {
                                Toast.makeText(GetSecurityActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                UIHelper.SetPasswordActivity(GetSecurityActivity.this);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showDailog("验证码错误");
                                dialog.dismiss();
                            }
                        });
                    }
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//获取验证码成功
                    Log.e("TAG", "获取验证码成功");
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {//返回支持发送验证码的国家列表

                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

    /**
     * 向服务器提交验证码，在监听回调中判断是否通过验证
     *
     * @param v
     */
    public void postSecurity(View v) {
        postSecurity = code.getText().toString();
        if (!TextUtils.isEmpty(postSecurity)) {
            dialog = ProgressDialog.show(this, null, "正在验证...", false, true);
            //提交短信验证码
            SMSSDK.submitVerificationCode("+86", realPhone, postSecurity);//国家号，手机号码，验证码
        } else {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDailog(String text) {
        new AlertDialog.Builder(this)
                .setTitle(text)
                .setPositiveButton("确定", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //要在activity销毁时反注册，否侧会造成内存泄漏问题
        SMSSDK.unregisterAllEventHandler();
    }

    // 提交用户信息
    private void registerUser(String country, String phone) {
        Random rnd = new Random();
        int id = Math.abs(rnd.nextInt());
        String uid = String.valueOf(id);
        String nickName = "SmsSDK_User_" + uid;
        String avatar = AVATARS[id % 12];
        SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rg_back:
                    UIHelper.showLogin(GetSecurityActivity.this);
                    break;
                case R.id.btnGetCode:
                    registerActivity.getCode();
                    time();
                    break;
            }
        }
    };

    private void time() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyCountDownTimer mCountDownTimer = new MyCountDownTimer(btnGetCode, 60000, 1000);
                mCountDownTimer.start();
            }
        });
    }

}
