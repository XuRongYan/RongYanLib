package com.rongyan.aikanvideo.rank;

import android.content.Context;

import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ApiService;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpResult;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpUtil;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.NetworkApi;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ProgressSubscriber;
import com.rongyan.rongyanlibrary.util.LogUtils;

import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by XRY on 2017/5/7.
 */

public class RankPresenter implements RankContract.Presenter {
    private static final String TAG = "RankPresenter";
    private final RankContract.View mView;
    private final Context context;
    private final PublishSubject<ActivityLifeCycleEvent> subject;

    public RankPresenter(RankContract.View mView, Context context, PublishSubject<ActivityLifeCycleEvent> subject) {
        this.mView = mView;
        this.context = context;
        this.subject = subject;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getRank() {
        ApiService apiService = new ApiService();
        Observable<HttpResult<List<Video>>> rank = apiService.getService(NetworkApi.class).getRank(10);
        HttpUtil.getInstance().toSubscribe(rank, new ProgressSubscriber<List<Video>>(context) {

            @Override
            protected void _onNext(List<Video> list) {
                mView.getList(list);
            }

            @Override
            protected void _onError(String message) {
                LogUtils.e(TAG, "getRank", message);
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, subject, false, false, true);
    }

    @Override
    public void refresh() {
        ApiService apiService = new ApiService();
        Observable<HttpResult<List<Video>>> rank = apiService.getService(NetworkApi.class).getRank(10);
        HttpUtil.getInstance().toSubscribe(rank, new ProgressSubscriber<List<Video>>(context) {

            @Override
            protected void _onNext(List<Video> list) {
                mView.refresh(list);
            }

            @Override
            protected void _onError(String message) {
                LogUtils.e(TAG, "getRank", message);
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, subject, false, false, false);
    }
}
