package com.familytraval.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.familytraval.R;
import com.familytraval.activity.LoginActivity;
import com.familytraval.common.IViewController;
import com.familytraval.ui.LoadingDialog;
import com.familytraval.utils.ConfirmDialog;
import com.familytraval.utils.DialogUtils;
import com.familytraval.utils.MobileLog;

import org.json.JSONObject;

/**
 * Created by dings on 2016/10/26.
 */

public abstract class BaseFragment extends Fragment implements IViewController {

    LoadingDialog mLoadingDialog;

    JSONObject mDataJson;

    /**
     * 跳转登录界面
     */
    public void gotoLogin() {

    }

    public void init(View view) {
        initSubView(view);
        initEvent();
        initData();
    }

    /**
     * 取消网络请求
     *
     * @param obj 设定文件
     * @return void    返回类型
     * @throws
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public void cancelRequest(Object obj) {

    }

    public void showToast(String msg) {
        DialogUtils.showToast(getActivity(), msg);
    }

    public void showToastUnLogin() {
        showToast(getString(R.string.toast_person_unlogin));
    }

    public void toLoginPage() {
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(loginIntent);
    }

    public void showLoadingToast() {
        showLoadingToast(null);
    }

    public void showLoadingToast(String title) {
        mLoadingDialog = new LoadingDialog(getActivity(), title);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.show();
    }

    public void hideLoadingToast() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }


    public void showConfirmDialog(String message, String title, final ConfirmDialog.ConfirmDialogListener confirmDialogListener, final int actionType) {
        ConfirmDialog.Builder builder = new ConfirmDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                if (confirmDialogListener != null) confirmDialogListener.clickOk(actionType);
            }
        });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    /**
     * @param view 设定文件
     * @return void    返回类型
     * @throws
     * @Description: 初始化子类视图
     */
    public abstract void initSubView(View view);

    public abstract void initEvent();

    public abstract void initData();

    @Override
    public void gotoLoginPage() {
        /*if (!SPStringUtils.isEmpty(msg)){
            showToast(msg);
		}*/
        MobileLog.i("SPBaseFragment", "gotoLoginPage : " + this);
        toLoginPage();

    }
}

