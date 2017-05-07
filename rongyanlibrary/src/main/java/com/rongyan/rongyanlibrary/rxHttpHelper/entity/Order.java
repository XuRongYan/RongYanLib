package com.rongyan.rongyanlibrary.rxHttpHelper.entity;

import com.rongyan.rongyanlibrary.base.BaseEntity;

/**
 * Created by XRY on 2017/4/29.
 */

public class Order extends BaseEntity{
    private String title;
    private String img;
    private String content;
    private boolean isSubscribe = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSubscribe() {
        return isSubscribe;
    }

    public void setSubscribe(boolean subscribe) {
        isSubscribe = subscribe;
    }
}
