package com.rongyan.aikanvideo.watchhistory;

import android.content.Context;

import com.linxiao.commondevlib.util.PreferencesUtil;
import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.History;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.User;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ApiService;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpResult;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpUtil;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.NetworkApi;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ProgressSubscriber;

import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by XRY on 2017/5/20.
 */

public class WatchHistoryPresenter implements WatchHistoryContract.Presenter {
    private final Context context;
    private final WatchHistoryContract.View mView;
    private final PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject;
    public static final int PER_PAGE = 20;
    private final User user;
    private int page = 1;


    public WatchHistoryPresenter(Context context, WatchHistoryContract.View mView, PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject) {
        this.context = context;
        this.mView = mView;
        this.lifeCycleEventPublishSubject = lifeCycleEventPublishSubject;
        user = PreferencesUtil.getSerializable(context, "user");
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getHistory(){
        ApiService apiService = new ApiService();
        Observable<HttpResult<List<History>>> history =
                apiService.getService(NetworkApi.class).getHistory(PER_PAGE, page++, user.getUserid());

        HttpUtil.getInstance().toSubscribe(history, new ProgressSubscriber<List<History>>(context) {

            @Override
            protected void _onNext(List<History> list) {
                if (list.size() < PER_PAGE - 1) {
                    mView.setText(context.getResources().getString(R.string.no_more));
                    if (list.size() <= 0) {
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
