package com.rongyan.rongyanlibrary.base;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.orhanobut.hawk.Hawk;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.Url;
import com.rongyan.rongyanlibrary.util.LogUtils;

/**
 *
 * Created by XRY on 2017/4/14.
 */

public class BaseApplication extends Application {
    public static final int PER_PAGE = 20;
    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
        /**
         * 初始化Hawk数据库
         */
        Hawk.init(this).build();

        /**
         * 从Manifest获取meta-data
         */
        try {
            ApplicationInfo applicationInfo = this.getPackageManager()
                    .getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            String baseUrl = applicationInfo.metaData.getString("BaseUrl");
            LogUtils.e("Url", "BaseUrl",baseUrl);
            Url.BASE_URL = baseUrl;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}
