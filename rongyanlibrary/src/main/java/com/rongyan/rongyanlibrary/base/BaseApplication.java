package com.rongyan.rongyanlibrary.base;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

/**
 * Created by XRY on 2017/4/14.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
    }
}
