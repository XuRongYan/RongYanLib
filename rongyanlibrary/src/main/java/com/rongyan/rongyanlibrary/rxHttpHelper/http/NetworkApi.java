package com.rongyan.rongyanlibrary.rxHttpHelper.http;

import com.rongyan.rongyanlibrary.rxHttpHelper.postEntity.LoginPost;
import com.rongyan.rongyanlibrary.rxHttpHelper.postEntity.RegisterPost;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by XRY on 2017/4/14.
 */

public interface NetworkApi {

    @POST("CheckLogin.aspx")
    Observable<HttpResult> login(@Body LoginPost loginPost);

    @POST("UserRegister.aspx")
    Observable<HttpResult> register(@Body RegisterPost registerPost);

    @FormUrlEncoded
    @POST
    Observable<HttpResult> loginForm(@Field("username") String username, @Field("password") String password);


}
