package com.rongyan.rongyanlibrary.rxHttpHelper.entity;

import com.rongyan.rongyanlibrary.base.BaseEntity;

/**
 * Created by XRY on 2017/5/18.
 */

public class Comment extends BaseEntity {
    private int videoid;
    private String username;
    private String comdatetime;
    private String comments;

    public Comment(int videoid, String username, String comdatetime, String comments) {
        this.videoid = videoid;
        this.username = username;
        this.comdatetime = comdatetime;
        this.comments = comments;
    }

    public int getVideoid() {
        return videoid;
    }

    public void setVideoid(int videoid) {
        this.videoid = videoid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComdatetime() {
        return comdatetime;
    }

    public void setComdatetime(String comdatetime) {
        this.comdatetime = comdatetime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
