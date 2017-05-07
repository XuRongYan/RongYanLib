package com.rongyan.aikanvideo.register;

import android.os.Handler;

import com.rongyan.rongyanlibrary.base.BasePresenter;
import com.rongyan.rongyanlibrary.base.BaseView;

/**
 * Created by XRY on 2017/5/5.
 */

public interface RegisterContract {
    interface Presenter extends BasePresenter {
        void register();
        void getVerificationCode();
    }

    interface View extends BaseView<Presenter> {
        //用户名是否合法
        boolean isUserNameValid();

        //密码是否合法
        boolean isPasswordValid();

        //设置出错文本
        boolean setError(String error);

        boolean setUsernameError(String error);

        boolean setPswError(String error);

        boolean setPswConfirmError(String error);

        boolean setVerificationCodeError(String error);

        String getUsername();

        String getPsw();

        String getPswConfirm();

        String getVerificationCode();

        void onRegisterSuc();

        Handler getHandler();
    }
}
