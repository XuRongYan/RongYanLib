package com.rongyan.rongyanlibrary.rxHttpHelper.http;

import android.util.Log;

import com.rongyan.rongyanlibrary.util.LogUtils;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by XRY on 2017/4/14.
 */

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtils.v("OKHttp", "request", request.toString());
        long t1 = System.nanoTime(); //请求发起时间
        Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        Log.v("OKHttp", String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        LogUtils.v("OKHttp", "response" ,content);
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }
}
