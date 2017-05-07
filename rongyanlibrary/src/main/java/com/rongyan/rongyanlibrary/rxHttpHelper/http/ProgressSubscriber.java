package com.rongyan.rongyanlibrary.rxHttpHelper.http;

import android.content.Context;

import com.rongyan.rongyanlibrary.util.AppUtils;
import com.rongyan.rongyanlibrary.util.LogUtils;
import com.rongyan.rongyanlibrary.widget.SimpleLoadDialog;

import rx.Subscriber;

/**
 * Created by XRY on 2017/4/14.
 */

public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    private SimpleLoadDialog mDialogHandler;
    private Context context;

    public ProgressSubscriber(Context context) {
        mDialogHandler = new SimpleLoadDialog(context, this, true);
        this.context = context;
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();

    }

    public void showProgressDialog() {
        if (mDialogHandler != null) {
            mDialogHandler.show();
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    public void dismissProgressDialog() {
        if (mDialogHandler != null) {
            mDialogHandler.dismiss();
            mDialogHandler = null;
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (!AppUtils.isNetWorkConnected(context)) {
            _onError("网络不可用");
        } else if (e instanceof ApiException) {
            _onError(e.getMessage());
        } else {
            _onError("请求失败，请稍后再试...\n" + "异常：" + e.toString());
            LogUtils.d("OKHttp", "exception", e.toString());
        }
        dismissProgressDialog();
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    protected abstract void _onNext(T t);
    protected abstract void _onError(String message);
    protected abstract void _onCompleted();
}
