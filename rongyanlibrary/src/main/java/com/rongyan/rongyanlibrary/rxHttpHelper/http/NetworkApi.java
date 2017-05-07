package com.rongyan.rongyanlibrary.rxHttpHelper.http;

import com.rongyan.rongyanlibrary.rxHttpHelper.entity.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by XRY on 2017/4/14.
 */

public interface NetworkApi {


    @FormUrlEncoded
    @POST("checkLogin.aspx")
    Observable<HttpResult<User>> login(@Field("cellphone") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("GetYZM.aspx")
    Observable<HttpResult> getVerification(@Field("phonenum") String phoneNum);

    @FormUrlEncoded
    @POST("userRegister.aspx")
    Observable<HttpResult> register(@Field("cellphone") String cellPhone, @Field("password") String psw, @Field("yzmCode") String verificationCode);
}
