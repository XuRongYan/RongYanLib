package com.rongyan.rongyanlibrary.rxHttpHelper.entity;

/**
 * Created by XRY on 2017/5/20.
 */

public class History {
    private int userid;
    private int videoid;
    private String title;
    private String titlenew;

    public History(int userid, int videoid, String title, String titlenew) {
        this.userid = userid;
        this.videoid = videoid;
        this.title = title;
        this.titlenew = titlenew;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getVideoid() {
        return videoid;
    }

    public void setVideoid(int videoid) {
        this.videoid = videoid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitlenew() {
        return titlenew;
    }

    public void setTitlenew(String titlenew) {
        this.titlenew = titlenew;
    }
}
