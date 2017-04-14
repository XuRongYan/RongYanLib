package com.rongyan.rongyanlibrary.rxHttpHelper.http;

import com.orhanobut.hawk.Hawk;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by XRY on 2017/4/14.
 */

public class RetrofitCache {
    public static <T> Observable<T> load(final String cachKey,
                                         Observable<T> fromNetwork,
                                         boolean isSave, final boolean forceFresh) {
        Observable<T> fromCache = Observable.unsafeCreate(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                T cache = Hawk.get(cachKey);
                if (cache != null) {
                    subscriber.onNext(cache);
                } else {
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());

        if (isSave) {
            fromNetwork = fromNetwork.map(new Func1<T, T>() {
                @Override
                public T call(T result) {
                    Hawk.put(cachKey, result);
                    return result;
                }
            });
        }

        if (forceFresh) {
            return fromNetwork;
        } else {
            return Observable.concat(fromCache, fromNetwork).takeFirst(new Func1<T, Boolean>() {
                @Override
                public Boolean call(T t) {
                    return t != null;
                }
            });
        }
    }
}
