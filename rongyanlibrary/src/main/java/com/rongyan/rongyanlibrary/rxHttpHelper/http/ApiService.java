package com.rongyan.rongyanlibrary.rxHttpHelper.http;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 初始化retrofit
 * Created by XRY on 2017/4/13.
 */

public class ApiService {
    /**
     * 请求超时时间
     */
    private static final int DEFAULT_TIMEOUT = 10000;
    private HashMap<String, Object> mServiceMap;

    public ApiService() {
        mServiceMap = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <S> S getService(Class<S> serviceClass) {
        if (!mServiceMap.containsKey(serviceClass.getName())) {
            S service = createService(serviceClass);
            mServiceMap.put(serviceClass.getName(), service);
            return service;
        } else {
            return (S) mServiceMap.get(serviceClass.getName());
        }
    }

    private <S> S createService(Class<S> serviceClass) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }
}
