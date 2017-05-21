package com.rongyan.aikanvideo.main;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ApiService;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpResult;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpUtil;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.NetworkApi;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ProgressSubscriber;

import java.io.Serializable;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by XRY on 2017/4/23.
 */

public class MainPresenter implements MainContract.Presenter, Serializable{
    private final MainContract.View mView;
    private final Context mContext;
    public final PublishSubject<ActivityLifeCycleEvent> lifeCycleSubject;
    private CompositeSubscription mSubscriptions;

    public MainPresenter(MainContract.View mView, Context mContext, PublishSubject<ActivityLifeCycleEvent> lifeCycleSubject) {
        this.mView = mView;
        this.mContext = mContext;
        this.lifeCycleSubject = lifeCycleSubject;
        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getFirstPage();
    }

    @Override
    public void unsubscribe() {
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }
    }


    @Override
    public void getFirstPage() {
        ApiService apiService = new ApiService();
        Observable<HttpResult<List<Video>>> mainPage = apiService.getService(NetworkApi.class).getMainPage(null);
        HttpUtil.getInstance().toSubscribe(mainPage, new ProgressSubscriber<List<Video>>(mContext) {

            @Override
            protected void _onNext(List<Video> videos) {
                mView.getList(videos);
            }

            @Override
            protected void _onError(String message) {

            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleSubject, false, false, true);
    }

    @Override
    public void refresh(final SwipeRefreshLayout swipeRefreshLayout) {
        ApiService apiService = new ApiService();
        Observable<HttpResult<List<Video>>> mainPage = apiService.getService(NetworkApi.class).getMainPage(null);
        HttpUtil.getInstance().toSubscribe(mainPage, new ProgressSubscriber<List<Video>>(mContext) {

            @Override
            protected void _onNext(List<Video> videos) {
                mView.getList(videos);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            protected void _onError(String message) {

            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleSubject, false, false, false);
    }
}
