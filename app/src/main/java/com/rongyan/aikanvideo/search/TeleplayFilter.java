package com.rongyan.aikanvideo.search;

import android.content.Context;
import android.support.v4.util.Pair;

import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ApiService;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpResult;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpUtil;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.NetworkApi;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ProgressSubscriber;
import com.rongyan.rongyanlibrary.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * 用于过滤电视剧，返回新的List
 * Created by XRY on 2017/5/19.
 */

public class TeleplayFilter {
    private static final String TAG = "TeleplayFilter";
    private final HttpUtil instance;
    private final Context context;
    private final PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject;
    private OnCompleteListener onCompleteListener;
    private int index = 0;
    List<Video> list = new ArrayList<>();
    Map<Integer, List<Video>> map = new HashMap<>(); //用于存放index对应剧集的键值对
    ApiService apiService = new ApiService();
    Pair<Map<Integer, List<Video>>, List<Video>> pair;


    public TeleplayFilter(Context context, PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject, List<Video> list) {
        this.list = list;
        this.context = context;
        this.lifeCycleEventPublishSubject = lifeCycleEventPublishSubject;
        instance = HttpUtil.getInstance();
        LogUtils.d(TAG, "TeleplayFilter", "get instance");

    }

    public Pair<Map<Integer, List<Video>>, List<Video>> filter() {
        LogUtils.d(TAG, "filter", "filter执行");
        if (list == null) {
            return null;
        }
        int i;
        for (i = index++; i < list.size(); i++) {
            LogUtils.d(TAG, "filter", "index:" + index);
            Video video = list.get(i);
            int belongtovideoid = video.getBelongtovideoid();
            //没有剧集了自然跳出循环不会再调用flag了
            if (belongtovideoid != -1) {
                getTvData(video.getBelongtovideoid(), i);
                break;
            }
        }

        if (i >= list.size()) {
            pair = new Pair<>(map, list);
            return pair;
        } else {
            return null;
        }
    }

    private void getTvData(int id, final int index) {
        LogUtils.d(TAG, "getTvData", "getTvData执行了");
        synchronized (instance) {
            Observable<HttpResult<List<Video>>> tvData = apiService.getService(NetworkApi.class).getTVData(id);
            instance.toSubscribe(tvData, new ProgressSubscriber<List<Video>>(context) {
                @Override
                protected void _onNext(List<Video> list) {
                    LogUtils.d(TAG, "_onNext", "执行了");
                    List<Integer> indexList = new ArrayList<>();
                    map.put(index, list);
                    //遍历寻找出所有与现有list的VideoId相同的video并标记
                    if (list != null) {
                        Video v = list.get(0);
                        List<Video> teleList = TeleplayFilter.this.list;
                        for (int i = 0; i < teleList.size(); i++) {
                            if (teleList.get(i).getBelongtovideoid() == v.getBelongtovideoid()) {
                                indexList.add(i);
                            }

                        }
                        if (indexList.size() > 0) {
                            indexList.remove(0);
                        }
                    }

                    //根据标记删除对应的item
                    for (int i = 0; i < indexList.size(); i++) {
                        TeleplayFilter.this.list.remove(indexList.get(i));
                    }
                    //再次调用filter继续寻找剧集
                    Pair<Map<Integer, List<Video>>, List<Video>> filter = filter();

                    if (filter != null && onCompleteListener != null) {
                        onCompleteListener.onComplete(filter);
                    }
                }

                @Override
                protected void _onError(String message) {
                    LogUtils.d(TAG, "_onError", message);
                }

                @Override
                protected void _onCompleted() {

                }
            }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleEventPublishSubject, false, false, false);
        }
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public interface OnCompleteListener {
        void onComplete(Pair<Map<Integer, List<Video>>, List<Video>> pair);
    }
}
