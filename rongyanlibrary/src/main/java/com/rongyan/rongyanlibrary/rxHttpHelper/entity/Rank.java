package com.rongyan.rongyanlibrary.rxHttpHelper.entity;

import com.rongyan.rongyanlibrary.base.BaseEntity;

/**
 * Created by XRY on 2017/5/7.
 */

public class Rank extends BaseEntity{
    private int rank;
    private String title;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
