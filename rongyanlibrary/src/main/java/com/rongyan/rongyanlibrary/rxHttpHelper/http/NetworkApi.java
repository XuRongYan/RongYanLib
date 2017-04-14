package com.rongyan.rongyanlibrary.rxHttpHelper.http;

import org.json.JSONObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by XRY on 2017/4/14.
 */

public interface NetworkApi {
    @FormUrlEncoded
    @POST("Users_CheckLogin.aspx")
    Observable<HttpResult> login(@Field("")JSONObject jsonObject);


}
