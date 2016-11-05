package com.familytraval.callback;

/**
 * Created by dings on 2016/10/25.
 */

import com.familytraval.common.IViewController;
import com.familytraval.common.MobileConstants;

/**
 * 首先会验证token是否过期/失效,
 * 如果过期/失效会进入登录页面登录
 */

public abstract class FailuredListener {

    private IViewController viewController;

    public FailuredListener() {
    }

    public IViewController getViewController() {
        return viewController;
    }

    public FailuredListener(IViewController pViewController) {
        viewController = pViewController;
    }

    /**
     * 预处理,
     *
     * @param msg
     * @param errorCode
     */
    public void handleResponse(String msg, int errorCode) {
        boolean isNeedLogin = preRespone(msg, errorCode);
        if (isNeedLogin) {
            //去登陆
            if (viewController != null) {
                viewController.gotoLoginPage();
                onRespone(msg, errorCode);
            } else {
                onRespone(msg, errorCode);
            }
        } else {
            onRespone(msg, errorCode);
        }
    }

    public abstract void onRespone(String msg, int errorCode);

    public boolean preRespone(String msg, int errorCode) {
        if (errorCode == MobileConstants.Response.RESPONSE_CODE_TOEKN_EXPIRE
                || errorCode == MobileConstants.Response.RESPONSE_CODE_TOEKN_INVALID
                || errorCode == MobileConstants.Response.RESPONSE_CODE_TOEKN_EMPTY) {
            return true;
        }
        return false;
    }

}

