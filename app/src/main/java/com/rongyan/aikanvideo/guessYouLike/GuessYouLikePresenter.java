package com.rongyan.aikanvideo.guessYouLike;

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

public class GuessYouLikePresenter implements GuessYouLikeContract.Presenter {

    private final GuessYouLikeContract.View mView;
    private final Context mContext;
    private final PublishSubject<ActivityLifeCycleEvent> lifeCycleSubject;

    public GuessYouLikePresenter(GuessYouLikeContract.View mView, Context mContext, PublishSubject<ActivityLifeCycleEvent> lifeCycleSubject) {
        this.mView = mView;
        this.mContext = mContext;
        this.lifeCycleSubject = lifeCycleSubject;
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
        HttpUtil.getInstance().toSubscribe(rank, new ProgressSubscriber<List<Video>>(mContext) {

            @Override
            protected void _onNext(List<Video> list) {
                mView.getList(list);
            }

            @Override
            protected void _onError(String message) {
                LogUtils.e("GuessYouLikePresenter", "getRank", message);
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleSubject, false, false, true);
    }


}
