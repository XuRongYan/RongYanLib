package com.rongyan.aikanvideo.setting;

import android.content.Context;

import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;

import rx.subjects.PublishSubject;

/**
 * Created by XRY on 2017/5/23.
 */

public class SettingPresenter implements SettingContract.Presenter {
    private final Context context;
    private final SettingContract.View mView;
    private final PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject;


    public SettingPresenter(Context context, SettingContract.View mView, PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject) {
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
}
