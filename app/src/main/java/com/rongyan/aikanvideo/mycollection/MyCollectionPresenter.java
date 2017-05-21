package com.rongyan.aikanvideo.mycollection;

import android.content.Context;

import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;

import rx.subjects.PublishSubject;

/**
 * Created by XRY on 2017/5/20.
 */

public class MyCollectionPresenter implements MyCollectionContract.Presenter {
    private final Context context;
    private final MyCollectionContract.View mView;
    private final PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject;

    public MyCollectionPresenter(Context context, MyCollectionContract.View mView, PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject) {
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
