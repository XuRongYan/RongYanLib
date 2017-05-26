package com.rongyan.rongyanlibrary.rxHttpHelper.http;

import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Comment;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.History;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Plots;
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
    Observable<HttpResult> addHistory(@Field("userid") int userId, @Field("videoid") int videoId, @Field("title")String title, @Field("titlenew") String titleNew, @Field("startpoint") long startpoint, @Field("stoppoint") long stoppoint, @Field("timelast") long timelast);

    @FormUrlEncoded
    @POST("AddVideoCollect.aspx")
    Observable<HttpResult> addCollection(@Field("userid") int userId, @Field("videoid") int videoId);

    @FormUrlEncoded
    @POST("ReturnCollectByUserid.aspx")
    Observable<HttpResult<List<Video>>> getCollection(@Field("pageSize") int pageSize, @Field("pageNo") int pageNo, @Field("userid") int userId);

    @FormUrlEncoded
    @POST("returnVideoKandian.aspx")
    Observable<HttpResult<List<Plots>>> getPlots(@Field("videoid") int videoid);

    @FormUrlEncoded
    @POST("addZan.aspx")
    Observable<HttpResult> addLike(@Field("userid") int userid, @Field("videoid") int videoid);

    @FormUrlEncoded
    @POST("getTopVideo.aspx")
    Observable<HttpResult<List<Video>>> getRank(@Field("size") int size);

    @FormUrlEncoded
    @POST("returnPassword.aspx")
    Observable<HttpResult> forgetPsw(@Field("cellphone") String cellPhone, @Field("newpassword") String psw, @Field("yzmCode") String verification);

    @FormUrlEncoded
    @POST("GetVideoRecommand.aspx")
    Observable<HttpResult<List<Video>>> getRecommend(@Field("videoid") int videoid, @Field("size") int size);

    @FormUrlEncoded
    @POST("getVideoData.aspx")
    Observable<HttpResult<List<List<Video>>>> getTeleplay(@Field("pageSize") int pageSize, @Field("pageNo") int page, @Field("selectStr") String selectStr, @Field("catagory") String catalog);

    @FormUrlEncoded
    @POST("GuessYouLike.aspx")
    Observable<HttpResult<List<Video>>> getMainLike(@Field("userid") int userid, @Field("size") int size);

    @FormUrlEncoded
    @POST("GetADRecommand.aspx")
    Observable<HttpResult<Video>> getAdv(@Field("videoid") int videoid, @Field("userid") int userid);

    @FormUrlEncoded
    @POST("addUserPreference.aspx")
    Observable<HttpResult> sendUserPreference(@Field("userid") int userId, @Field("preference") String preference, @Field("labels") String labels);

    @FormUrlEncoded
    @POST("fullUserInfo.aspx")
    Observable<HttpResult> fullUserInfo(@Field("userid") int userid
            , @Field("nickname") String nickname
            , @Field("email") String email
            , @Field("age") int age
            , @Field("gender") String gender
            , @Field("career") String career);

    @FormUrlEncoded
    @POST("addComment.aspx")
    Observable<HttpResult> addComment(@Field("videoid")int videoid, @Field("username") String username, @Field("comments") String comments);
}
