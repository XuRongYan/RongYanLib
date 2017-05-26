package com.rongyan.rongyanlibrary.rxHttpHelper.http;

import com.rongyan.rongyanlibrary.util.LogUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * 对结果进行预处理
 * Created by XRY on 2017/4/14.
 */

public class RxHelper {
    /**
     * 对结果进行预处理
     * 利用Transformer将一种Observable(rx.Observable<HttpResult<T>>)
     * 转化为另一种Observable(rx.Observable<T>)
     * 这里用flatMap将Observable<HttpResult<T>>转换成Observable<T>
     * 内部对code进行处理，若成功将结果发送给订阅者，否则交给ApiException并返回一个异常
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<HttpResult<T>, T> handleResult(final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        return new Observable.Transformer<HttpResult<T>, T>() {
            @Override
            public Observable<T> call(final Observable<HttpResult<T>> httpResultObservable) {
                Observable<ActivityLifeCycleEvent> compareLifeCycleObservable =
                        lifecycleSubject.takeFirst(new Func1<ActivityLifeCycleEvent, Boolean>() {
                            @Override
                            public Boolean call(ActivityLifeCycleEvent activityLifeCycleEvent) {
                                return activityLifeCycleEvent.equals(event);
                            }
                        });

                return httpResultObservable.flatMap(new Func1<HttpResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(HttpResult<T> tHttpResult) {
                        LogUtils.e(this.getClass().getName(), "RxHelper", tHttpResult.getResultCode() + "");
                        //如果code等于1则成功创建数据，否则交给自定义异常处理
                        if (tHttpResult.getResultCode() == 1 || tHttpResult.getResultCode() == 101 || tHttpResult.getResultCode() == 100) {
                            return createData(tHttpResult.getData(), tHttpResult.getResultCode());
                        } else {
                            return Observable.error(new ApiException(tHttpResult.getResultCode()));
                        }
                    }
                }).takeUntil(compareLifeCycleObservable)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private static <T> Observable<T> createData(final T data, int resutCode) {
        //TODO create方法过时了，试试用官方推荐的unsafeCreate，不知道有什么区别
        return Observable.unsafeCreate(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);

                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
