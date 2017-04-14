package com.rongyan.rongyanlibrary.rxHttpHelper.http;

import rx.Observable;
import rx.functions.Action0;
import rx.subjects.PublishSubject;

/**
 * Created by XRY on 2017/4/14.
 */

public class HttpUtil {
    private HttpUtil() {
    }

    private static class SingletonHolder {
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    public static HttpUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public void toSubscribe(Observable ob, final ProgressSubscriber subscriber, String cacheKey,
                            final ActivityLifeCycleEvent event,
                            final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject,
                            boolean isSave, boolean forceRefresh) {
        //数据预处理
        Observable.Transformer<HttpResult<Object>, Object> result = RxHelper.handleResult(event, lifecycleSubject);
        Observable observable = ob.compose(result)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        subscriber.showProgressDialog();
                    }
                });
        RetrofitCache.load(cacheKey, observable, isSave, forceRefresh).subscribe(subscriber);
    }
}
