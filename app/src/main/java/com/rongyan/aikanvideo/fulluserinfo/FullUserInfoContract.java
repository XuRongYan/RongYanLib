package com.rongyan.aikanvideo.fulluserinfo;

import com.rongyan.rongyanlibrary.base.BasePresenter;
import com.rongyan.rongyanlibrary.base.BaseView;

/**
 * Created by XRY on 2017/5/24.
 */

public interface FullUserInfoContract {
    interface Presenter extends BasePresenter {
        void submit(int userId);
    }

    interface View extends BaseView<Presenter> {
        String getNickname();
        String getEmail();
        String getAge();
        String getGender();
        String getCareer();

        boolean isEmailValid();
        boolean setError(String error);
        boolean setNicknameError(String error);
        boolean setEmailError(String error);
        boolean setAgeError(String error);

        void finish();
    }
}
