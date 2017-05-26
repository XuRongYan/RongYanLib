package com.rongyan.aikanvideo.forgetpsw;

import android.content.Context;
import android.os.Handler;

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

import static com.rongyan.aikanvideo.register.RegisterFragment.CLICKABLE;
import static com.rongyan.aikanvideo.register.RegisterFragment.UNCLICKABLE;

/**
 * Created by XRY on 2017/5/23.
 */

public class ForgetPswPresenter implements ForgetPswContract.Presenter {
    private static final String TAG = "ForgetPswPresenter";
    private final Context context;
    private final ForgetPswContract.View mView;
    private final PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject;

    public ForgetPswPresenter(Context context, ForgetPswContract.View mView, PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject) {
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
    public void submit(final String cellphone, String psw, String verificationCode) {
        boolean isCancel = false;

        if (cellphone == null) {
            isCancel = mView.setUsernameError(context.getString(R.string.string_username_required));
        } else if (!mView.isUserNameValid()) {
            isCancel = mView.setUsernameError(context.getString(R.string.string_invalid_username));
        } else if (psw == null) {
            isCancel = mView.setPswError(context.getString(R.string.string_psw_required));
        } else if (verificationCode == null) {
            isCancel = mView.setVerificationCodeError(context.getString(R.string.string_verification_required));
        }

        if (!isCancel) {
            ApiService apiService = new ApiService();
            Observable<HttpResult> observable = apiService.getService(NetworkApi.class).forgetPsw(cellphone, psw, verificationCode);
            HttpUtil.getInstance().toSubscribe(observable, new ProgressSubscriber(context) {
                @Override
                protected void _onNext(Object o) {
                    ToastUtils.showShort(context, context.getString(R.string.psw_update_success));
                    mView.onSubmit();
                }

                @Override
                protected void _onError(String message) {
                    LogUtils.d("ForgetPswPresenter", "submit", message);
                }

                @Override
                protected void _onCompleted() {
                }
            }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleEventPublishSubject, false, false, false);
        }
    }

    @Override
    public void getVerification(final String cellphone) {
        boolean isCancel = false;

        if (cellphone == null) {
            isCancel = mView.setUsernameError(context.getString(R.string.string_username_required));
        } else if (!mView.isUserNameValid()) {
            isCancel = mView.setUsernameError(context.getString(R.string.string_invalid_username));
        }

        if (!isCancel) {
            final Handler handler = mView.getHandler();
            if (handler == null) {
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 90; i++) {
                        handler.sendEmptyMessage(UNCLICKABLE);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendEmptyMessage(CLICKABLE);
                }
            }).start();
            ApiService apiService = new ApiService();
            Observable<HttpResult> verification = apiService.getService(NetworkApi.class).getVerification(cellphone);
            HttpUtil.getInstance().toSubscribe(verification, new ProgressSubscriber(context) {
                @Override
                protected void _onNext(Object o) {
                    ToastUtils.showShort(context, context.getString(R.string.string_verification_already_send));
                }

                @Override
                protected void _onError(String message) {
                    ToastUtils.showShort(context, message);
                    LogUtils.e(TAG, "getVerification", message);
                }

                @Override
                protected void _onCompleted() {

                }
            }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleEventPublishSubject, false, false, false);
        }
    }
}
