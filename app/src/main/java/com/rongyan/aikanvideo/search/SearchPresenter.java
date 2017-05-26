package com.rongyan.aikanvideo.search;

import android.content.Context;

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

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by XRY on 2017/5/16.
 */

public class SearchPresenter implements SearchContract.Presenter {
    private static final String TAG_LOG = "SearchPresenter";
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
        LogUtils.d(TAG_LOG, "submitQuery", "getTelePlay");
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
                mView.getList(list);
                mView.closeSearchView();
            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShort(mContext, message);
                mView.closeSearchView();
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleEventPublishSubject, false, false, true);
    }

    @Override
    public void getTelePlay(String key) {
        LogUtils.d(TAG_LOG, "getTelePlay", "getTelePlay");
        ApiService apiService = new ApiService();
        Observable<HttpResult<List<List<Video>>>> teleplay = apiService.getService(NetworkApi.class).getTeleplay(PER_PAGE, sPage++, key, "电视剧");
        HttpUtil.getInstance().toSubscribe(teleplay, new ProgressSubscriber<List<List<Video>>>(mContext) {

            @Override
            protected void _onNext(List<List<Video>> lists) {
                if (lists.size() < PER_PAGE - 1) {
                    mView.setText(mContext.getString(R.string.no_more));
                    if (lists.size() <= 0) {
                        sPage--;
                    }
                }
                mView.getTeleList(lists);
                mView.closeSearchView();
            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShort(mContext, "电视剧：" + message);
                mView.closeSearchView();
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleEventPublishSubject, false, false, true);
    }
}
