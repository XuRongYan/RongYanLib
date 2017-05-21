package com.rongyan.aikanvideo.download;

import android.content.Context;

import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;

import rx.subjects.PublishSubject;

/**
 * Created by XRY on 2017/5/20.
 */

public class DownloadPresenter implements DownloadContract.Presenter {
    private final Context context;
    private final DownloadContract.View mView;
    private final PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject;

    public DownloadPresenter(Context context, DownloadContract.View mView, PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject) {
        this.context = context;
        this.mView = mView;
        this.lifeCycleEventPublishSubject = lifeCycleEventPublishSubject;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
