package com.rongyan.rongyanlibrary.rxHttpHelper.postEntity;

import com.rongyan.rongyanlibrary.base.BaseEntity;

/**
 * Created by XRY on 2017/4/14.
 */

public class RegisterPost extends BaseEntity {
    private String username;
    private String password;
    private String yzmCode;

    public RegisterPost(String username, String password, String yzmCode) {
        this.username = username;
        this.password = password;
        this.yzmCode = yzmCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getYzmCode() {
        return yzmCode;
    }

    public void setYzmCode(String yzmCode) {
        this.yzmCode = yzmCode;
    }
}
