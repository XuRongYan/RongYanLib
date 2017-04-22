package com.rongyan.rongyanlibrary.rxHttpHelper.http;

import com.rongyan.rongyanlibrary.rxHttpHelper.entity.User;
import com.rongyan.rongyanlibrary.rxHttpHelper.postEntity.RegisterPost;

import org.json.JSONObject;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by XRY on 2017/4/14.
 */

public interface NetworkApi {
    @FormUrlEncoded
    @POST("CheckLogin.aspx")
    Observable<HttpResult<User>> login(@Field("")JSONObject jsonObject);

    @POST("UserRegister.aspx")
    Observable<HttpResult> register(@Body RegisterPost registerPost);

    @FormUrlEncoded
    @POST
    Observable<HttpResult> loginForm(@Field("username") String username, @Field("password") String password);


}
