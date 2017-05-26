package com.rongyan.aikanvideo.register;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

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
import rx.subscriptions.CompositeSubscription;

import static com.rongyan.aikanvideo.register.RegisterFragment.CLICKABLE;
import static com.rongyan.aikanvideo.register.RegisterFragment.UNCLICKABLE;

/**
 * Created by XRY on 2017/5/5.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private static final String TAG = "RegisterPresenter";
    private final RegisterContract.View mView;
    private final Context mContext;
    private final PublishSubject<ActivityLifeCycleEvent> activityLifeCycleEventPublishSubject;
    private CompositeSubscription subscription;
    private String phone;
    private String psw;
    private String pswConfirm;
    private String verificationCode;
    private Handler handler;

    public RegisterPresenter(RegisterContract.View mView, Context mContext, PublishSubject<ActivityLifeCycleEvent> activityLifeCycleEventPublishSubject) {
        this.mView = mView;
        this.mContext = mContext;
        this.activityLifeCycleEventPublishSubject = activityLifeCycleEventPublishSubject;

        mView.setPresenter(this);

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (subscription != null) {
            subscription.clear();
        }
    }

    @Override
    public void register() {
        boolean isCancel = false;
        phone = mView.getUsername();
        psw = mView.getPsw();
        pswConfirm = mView.getPswConfirm();
        verificationCode = mView.getVerificationCode();

        if (TextUtils.isEmpty(phone)) {
            isCancel = mView.setUsernameError(mContext.getString(R.string.string_username_required));
        } else if (!mView.isUserNameValid()) {
            isCancel = mView.setUsernameError(mContext.getString(R.string.string_invalid_username));
        } else if (TextUtils.isEmpty(psw)) {
            isCancel = mView.setPswError(mContext.getString(R.string.string_psw_required));
        } else if (TextUtils.isEmpty(pswConfirm)) {
            isCancel = mView.setPswConfirmError(mContext.getString(R.string.string_psw_confirm));
        } else if (!pswConfirm.equals(psw)) {
            isCancel = mView.setPswConfirmError(mContext.getString(R.string.string_different_psw));
        } else if (TextUtils.isEmpty(verificationCode)) {
            isCancel = mView.setVerificationCodeError(mContext.getString(R.string.string_verification_required));
        }

        if (!isCancel) {
            ApiService apiService = new ApiService();
            Observable<HttpResult> observable = apiService.getService(NetworkApi.class)
                    .register(phone, psw, verificationCode);
            HttpUtil.getInstance().toSubscribe(observable, new ProgressSubscriber(mContext) {
                @Override
                protected void _onNext(Object o) {
                    LogUtils.e(TAG, "register", "success");
                    mView.onRegisterSuc();
                }

                @Override
                protected void _onError(String message) {
                    LogUtils.e(TAG, "register error", message);
                    ToastUtils.showShort(mContext, message);
                }

                @Override
                protected void _onCompleted() {

                }
            }, null, ActivityLifeCycleEvent.PAUSE, activityLifeCycleEventPublishSubject, false, false, true);
        }
    }

    @Override
    public void getVerificationCode() {
        boolean isCancel = false;
        phone = mView.getUsername();
        if (TextUtils.isEmpty(phone)) {
            isCancel = mView.setUsernameError(mContext.getString(R.string.string_username_required));
        } else if (!mView.isUserNameValid()) {
            isCancel = mView.setUsernameError(mContext.getString(R.string.string_invalid_username));
        }

        if (!isCancel) {
            handler = mView.getHandler();
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
            tryGetVerification();
        }
    }

    private void tryGetVerification() {
        ApiService apiService = new ApiService();
        Observable<HttpResult> observable = apiService.getService(NetworkApi.class).getVerification(phone);
        HttpUtil.getInstance().toSubscribe(observable, new ProgressSubscriber(mContext) {

            @Override
            protected void _onNext(Object o) {
                ToastUtils.showShort(mContext, mContext.getString(R.string.string_verification_already_send));
            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShort(mContext, mContext.getString(R.string.string_verification_fail));
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, activityLifeCycleEventPublishSubject, false, false, true);
    }
}
