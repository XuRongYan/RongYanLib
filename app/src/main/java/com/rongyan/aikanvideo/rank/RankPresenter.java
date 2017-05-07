package com.rongyan.aikanvideo.rank;

import android.content.Context;

import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;

import rx.subjects.PublishSubject;

/**
 * Created by XRY on 2017/5/7.
 */

public class RankPresenter implements RankContract.Presenter {
    private final RankContract.View mView;
    private final Context context;
    private final PublishSubject<ActivityLifeCycleEvent> subject;

    public RankPresenter(RankContract.View mView, Context context, PublishSubject<ActivityLifeCycleEvent> subject) {
        this.mView = mView;
        this.context = context;
        this.subject = subject;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
