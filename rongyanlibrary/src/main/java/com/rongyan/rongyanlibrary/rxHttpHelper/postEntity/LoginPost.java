package com.rongyan.rongyanlibrary.rxHttpHelper.postEntity;

import com.rongyan.rongyanlibrary.base.BaseEntity;

/**
 * Created by XRY on 2017/4/14.
 */

public class LoginPost extends BaseEntity {
    private String username;
    private String password;

    public LoginPost(String username, String password) {
        this.username = username;
        this.password = password;
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
}
