package com.rongyan.aikanvideo.guessYouLike;

import android.content.Context;

import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;

import rx.subjects.PublishSubject;

/**
 * Created by XRY on 2017/5/7.
 */

public class GuessYouLikePresenter implements GuessYouLikeContract.Presenter {
    private final GuessYouLikeContract.View mView;
    private final Context mContext;
    private final PublishSubject<ActivityLifeCycleEvent> lifeCycleSubject;

    public GuessYouLikePresenter(GuessYouLikeContract.View mView, Context mContext, PublishSubject<ActivityLifeCycleEvent> lifeCycleSubject) {
        this.mView = mView;
        this.mContext = mContext;
        this.lifeCycleSubject = lifeCycleSubject;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
