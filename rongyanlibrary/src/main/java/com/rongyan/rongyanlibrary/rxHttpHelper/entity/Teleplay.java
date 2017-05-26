package com.rongyan.rongyanlibrary.rxHttpHelper.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by XRY on 2017/5/24.
 */

public class Teleplay {
    @SerializedName("")
    private List<List<Video>> lists;

    public List<List<Video>> getLists() {
        return lists;
    }

    public void setLists(List<List<Video>> lists) {
        this.lists = lists;
    }
}
