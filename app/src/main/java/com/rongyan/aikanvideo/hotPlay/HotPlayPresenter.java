package com.rongyan.aikanvideo.hotPlay;

import android.content.Context;

import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;

import rx.subjects.PublishSubject;

/**
 * Created by XRY on 2017/5/7.
 */

public class HotPlayPresenter implements HotPlayContract.Presenter {
    private final HotPlayContract.View mView;
    private final Context mContext;
    private final PublishSubject<ActivityLifeCycleEvent> subject;

    public HotPlayPresenter(HotPlayContract.View mView, Context mContext, PublishSubject<ActivityLifeCycleEvent> subject) {
        this.mView = mView;
        this.mContext = mContext;
        this.subject = subject;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
