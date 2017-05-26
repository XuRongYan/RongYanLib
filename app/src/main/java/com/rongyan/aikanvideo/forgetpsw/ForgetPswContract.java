package com.rongyan.aikanvideo.forgetpsw;

import android.os.Handler;

import com.rongyan.rongyanlibrary.base.BasePresenter;
import com.rongyan.rongyanlibrary.base.BaseView;

/**
 *
 * Created by XRY on 2017/5/23.
 */

public interface ForgetPswContract {
    interface Presenter extends BasePresenter {
        void submit(String cellphone, String psw, String verificationCode);
        void getVerification(String cellphone);
    }

    interface View extends BaseView<Presenter> {
        Handler getHandler();
        void onSubmit();
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
    }
}
