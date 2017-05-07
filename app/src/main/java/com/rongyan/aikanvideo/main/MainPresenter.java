package com.rongyan.aikanvideo.main;

import android.content.Context;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by XRY on 2017/4/23.
 */

public class MainPresenter implements MainContract.Presenter {
    private final MainContract.View mView;
    private final Context mContext;
    private CompositeSubscription mSubscriptions;

    public MainPresenter(MainContract.View mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
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
}
