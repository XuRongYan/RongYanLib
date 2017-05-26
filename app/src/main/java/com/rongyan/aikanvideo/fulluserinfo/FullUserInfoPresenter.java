package com.rongyan.aikanvideo.fulluserinfo;

import android.content.Context;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ApiService;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpResult;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpUtil;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.NetworkApi;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ProgressSubscriber;
import com.rongyan.rongyanlibrary.util.LogUtils;
import com.rongyan.rongyanlibrary.util.ToastUtils;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by XRY on 2017/5/24.
 */

public class FullUserInfoPresenter implements FullUserInfoContract.Presenter {
    private static final String TAG = "FullUserInfoPresenter";
    private final Context context;
    private final FullUserInfoContract.View mView;
    private final PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject;

    public FullUserInfoPresenter(Context context, FullUserInfoContract.View mView, PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject) {
        this.context = context;
        this.mView = mView;
        this.lifeCycleEventPublishSubject = lifeCycleEventPublishSubject;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }


    @Override
    public void submit(int userId) {
        String nickname = mView.getNickname();
        String email = mView.getEmail();
        String age = mView.getAge();
        String gender = mView.getGender();
        final String career = mView.getCareer();

        boolean isCancel = false;

        if (nickname == null || nickname.equals("")) {
            isCancel = mView.setNicknameError("昵称不能为空");
        } else if (email == null || email.equals("")) {
            isCancel = mView.setEmailError("邮箱不得为空");
        } else if (!mView.isEmailValid()) {
            isCancel = mView.setEmailError("邮箱地址不合法");
        } else if (age == null || age.equals("")) {
            isCancel = mView.setAgeError("年龄不得为空");
        } else if (career == null || career.equals("")) {
            isCancel = mView.setError("职业不得为空");
        }

        if (!isCancel) {
            ApiService apiService = new ApiService();
            Observable<HttpResult> info = apiService.getService(NetworkApi.class).fullUserInfo(userId, nickname, email, Integer.parseInt(age), gender, career);
            HttpUtil.getInstance().toSubscribe(info, new ProgressSubscriber(context) {
                @Override
                protected void _onNext(Object o) {
                    ToastUtils.showShort(context, context.getString(R.string.full_info_success));
                    mView.finish();
                }

                @Override
                protected void _onError(String message) {
                    LogUtils.e(TAG, "submit", message);
                    ToastUtils.showShort(context, context.getString(R.string.sunmit_fail));
                }

                @Override
                protected void _onCompleted() {

                }
            }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleEventPublishSubject, false, false, false);
        }
    }
}
