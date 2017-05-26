package com.rongyan.aikanvideo.mycollection;

import android.content.Context;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ApiService;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpResult;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpUtil;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.NetworkApi;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ProgressSubscriber;
import com.rongyan.rongyanlibrary.util.ToastUtils;

import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by XRY on 2017/5/20.
 */

public class MyCollectionPresenter implements MyCollectionContract.Presenter {
    public static final int PER_PAGE = 20;
    private final Context context;
    private final MyCollectionContract.View mView;
    private final PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject;
    private int page = 1;

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


    @Override
    public void getCollection() {
        int userId = mView.getUserId();
        if (userId == -1) {
            ToastUtils.showShort(context, "请先登录");
            return;
        }
        ApiService apiService = new ApiService();
        final Observable<HttpResult<List<Video>>> collection = apiService.getService(NetworkApi.class).getCollection(PER_PAGE, page++, userId);
        HttpUtil.getInstance().toSubscribe(collection, new ProgressSubscriber<List<Video>>(context) {

            @Override
            protected void _onNext(List<Video> list) {
                if (list.size() < PER_PAGE - 1) {
                    mView.setText(context.getResources().getString(R.string.no_more));
                    if (list.size() == 0) {
                        page--;
                    }
                }

                mView.getList(list);
            }


            @Override
            protected void _onError(String message) {

            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleEventPublishSubject, false, false, true);
    }
}
