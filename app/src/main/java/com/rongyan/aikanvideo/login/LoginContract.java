package com.rongyan.aikanvideo.login;

import com.rongyan.rongyanlibrary.base.BasePresenter;
import com.rongyan.rongyanlibrary.base.BaseView;

/**
 * Created by XRY on 2017/4/22.
 */

public interface LoginContract {
    interface Presenter extends BasePresenter {
        void login();
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

        //登陆成功后跳转页面
        void onLoginSuc();

        String getUsername();

        String getPsw();
    }
}
