package com.rongyan.aikanvideo.search;

import android.content.Context;
import android.support.v4.util.Pair;

import com.rongyan.aikanvideo.OnSearchCompleteListener;
import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ApiService;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpResult;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpUtil;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.NetworkApi;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ProgressSubscriber;
import com.rongyan.rongyanlibrary.util.LogUtils;
import com.rongyan.rongyanlibrary.util.ToastUtils;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by XRY on 2017/5/16.
 */

public class SearchPresenter implements SearchContract.Presenter {
    public static final int PER_PAGE = 20;
    private int sPage = 1;
    private final String TAG;
    private final Context mContext;
    private final SearchContract.View mView;
    private final PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject;
    private OnSearchCompleteListener mOnSearchCompleteListener;
    private String qurey;

    public SearchPresenter(Context mContext, SearchContract.View mView, String tag, PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject) {
        this.mContext = mContext;
        this.mView = mView;
        this.lifeCycleEventPublishSubject = lifeCycleEventPublishSubject;
        TAG = tag;
        mView.setPresenter(this);
    }

    public void setOnSearchCompleteListener(OnSearchCompleteListener listener) {
        this.mOnSearchCompleteListener = listener;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void submitQuery(String text) {
        qurey = text;
        ApiService apiService = new ApiService();
        Observable<HttpResult<List<Video>>> videoInfo = apiService
                .getService(NetworkApi.class).getVideoInfoByTag(PER_PAGE, sPage++, text, TAG);
        HttpUtil.getInstance().toSubscribe(videoInfo, new ProgressSubscriber<List<Video>>(mContext) {
            @Override
            protected void _onNext(List<Video> list) {
                if (list.size() <= 0) {
                    sPage--;
                    mView.setText(mContext.getResources().getString(R.string.no_more));
                }
                TeleplayFilter filter = new TeleplayFilter(mContext, lifeCycleEventPublishSubject, list);
                filter.setOnCompleteListener(new TeleplayFilter.OnCompleteListener() {
                    @Override
                    public void onComplete(Pair<Map<Integer, List<Video>>, List<Video>> pair) {
                        mView.getList(pair.second);
                        LogUtils.d(TAG, "onComplete", pair.second.toString());
                        if (mOnSearchCompleteListener != null) {
                            mOnSearchCompleteListener.onComplete();
                        }
                    }
                });
                filter.filter();
            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShort(mContext, message);
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleEventPublishSubject, false, false, true);
    }
}
