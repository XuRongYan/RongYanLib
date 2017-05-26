package com.rongyan.aikanvideo.login;

import android.content.Context;
import android.text.TextUtils;

import com.linxiao.commondevlib.util.PreferencesUtil;
import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.User;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ApiService;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpUtil;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.NetworkApi;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ProgressSubscriber;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by XRY on 2017/4/22.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View mView;
    private final Context context;
    public final PublishSubject<ActivityLifeCycleEvent> lifeCycleSubject;
    private CompositeSubscription mSubscriptions;

    public LoginPresenter(LoginContract.View mView, Context context, PublishSubject<ActivityLifeCycleEvent> lifeCycleSubject) {
        this.mView = mView;
        this.context = context;
        this.lifeCycleSubject = lifeCycleSubject;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }

    }

    @Override
    public void login() {
        final String username = mView.getUsername();
        String psw = mView.getPsw();

        boolean cancel = false;

        if (TextUtils.isEmpty(username)) {
            cancel = mView.setUsernameError(context.getString(R.string.string_username_required));
        }
//        } else if (!mView.isUserNameValid()) {
//            cancel = mView.setUsernameError(context.getString(R.string.string_invalid_username));
//        }
        else if (TextUtils.isEmpty(psw)) {
            cancel = mView.setPswError(context.getString(R.string.string_psw_required));
        } else if (!mView.isPasswordValid()) {
            cancel = mView.setPswError(context.getString(R.string.string_invalid_psw));
        }

        if (!cancel) {

            ApiService apiService = new ApiService();
            Observable observable = apiService.getService(NetworkApi.class).login(username, psw);

            HttpUtil.getInstance().toSubscribe(observable, new ProgressSubscriber<User>(context) {
                @Override
                protected void _onNext(User result) {

                    PreferencesUtil.reset(context);
                    PreferencesUtil.put(context, "user", result);
                    mView.onLoginSuc();
                }

                @Override
                protected void _onError(String message) {
                    mView.setError(message);
                }

                @Override
                protected void _onCompleted() {

                }
            }, "user", ActivityLifeCycleEvent.PAUSE, lifeCycleSubject, true, true, true);
        }
    }
}
