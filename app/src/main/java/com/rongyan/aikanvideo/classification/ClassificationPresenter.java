package com.rongyan.aikanvideo.classification;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

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
import rx.subscriptions.CompositeSubscription;

/**
 *
 * Created by XRY on 2017/4/30.
 */

public class ClassificationPresenter implements ClassificationContract.Presenter {
    public static final int PER_PAGE = 20;
    private final ClassificationContract.View mView;
    private final Context mContext;
    public final PublishSubject<ActivityLifeCycleEvent> lifeCycleSubject;
    private boolean isMore = true;
    private CompositeSubscription mSubscriptions;
    private static int page = 1;

    public ClassificationPresenter(ClassificationContract.View mView, Context mContext, PublishSubject<ActivityLifeCycleEvent> lifeCycleSubject) {
        this.mView = mView;
        this.mContext = mContext;
        this.lifeCycleSubject = lifeCycleSubject;
        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getList(String key) {
        ApiService apiService = new ApiService();
        Observable<HttpResult<List<Video>>> videoInfo = apiService.getService(NetworkApi.class).getVideoInfo(PER_PAGE, page++, key);
        HttpUtil.getInstance().toSubscribe(videoInfo, new ProgressSubscriber<List<Video>>(mContext) {

            @Override
            protected void _onNext(List<Video> list) {
                if (list.size() < PER_PAGE - 1) {
                    mView.setText(mContext.getString(R.string.no_more));
                    page--;
                }
                mView.getList(list);
            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShort(mContext, message);
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleSubject, false, false, true);

    }

    @Override
    public void refresh(String key, SwipeRefreshLayout layout) {
        page = 1;
        mView.setText(mContext.getString(R.string.load_more));
        ApiService apiService = new ApiService();
        Observable<HttpResult<List<Video>>> videoInfo = apiService.getService(NetworkApi.class).getVideoInfo(20, page++, key);
        HttpUtil.getInstance().toSubscribe(videoInfo, new ProgressSubscriber<List<Video>>(mContext) {

            @Override
            protected void _onNext(List<Video> list) {

                isMore = false;

                mView.refreshList(list);
            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShort(mContext, message);
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleSubject, false, false, true);

        layout.setRefreshing(false);
    }
}
