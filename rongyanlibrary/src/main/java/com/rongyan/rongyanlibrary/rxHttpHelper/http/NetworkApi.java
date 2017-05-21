package com.rongyan.rongyanlibrary.rxHttpHelper.http;

import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Comment;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.History;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.User;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import java.util.List;

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

    @FormUrlEncoded
    @POST("getFirstPage.aspx")
    Observable<HttpResult<List<Video>>> getMainPage(@Field("") String s);

    @FormUrlEncoded
    @POST("getVideoData.aspx")
    Observable<HttpResult<List<Video>>> getVideoInfo(@Field("pageSize") int pageSize, @Field("pageNo") int pageNo, @Field("selectStr") String selectStr);

    @FormUrlEncoded
    @POST("ReturnCommentByVideoid.aspx")
    Observable<HttpResult<List<Comment>>> getVideoCommentById(@Field("pageSize") int pageSize, @Field("pageNo") int pageNo, @Field("videoid") int videoid);

    @FormUrlEncoded
    @POST("getVideoData.aspx")
    Observable<HttpResult<List<Video>>> getVideoInfoByTag(@Field("pageSize") int pageSize, @Field("pageNo") int pageNo, @Field("selectStr") String selectStr, @Field("catagory") String catagory);


    @FormUrlEncoded
    @POST("getTVData.aspx")
    Observable<HttpResult<List<Video>>> getTVData(@Field("videoid") int id);

    @FormUrlEncoded
    @POST("returnHistoryRecord.aspx")
    Observable<HttpResult<List<History>>> getHistory(@Field("pageSize") int pageSize, @Field("pageNo") int pageNo, @Field("userid") int userId);

    @FormUrlEncoded
    @POST("addHistoryRecord.aspx")
    Observable<HttpResult> addHistory(@Field("userid") int userId, @Field("videoid") int videoId, @Field("title")String title, @Field("titlenew") String titleNew);
}
