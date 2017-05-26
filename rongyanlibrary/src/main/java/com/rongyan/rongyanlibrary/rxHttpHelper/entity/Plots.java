package com.rongyan.rongyanlibrary.rxHttpHelper.entity;

import com.rongyan.rongyanlibrary.base.BaseEntity;

/**
 * Created by XRY on 2017/5/17.
 */

public class Plots extends BaseEntity {
    private int videoid;
    private long position;
    private String title;
    private double ratio;


    public Plots(String title) {
        this.title = title;
    }

    public Plots(int videoid, long position, String title, double ratio) {
        this.videoid = videoid;
        this.position = position;
        this.title = title;
        this.ratio = ratio;
    }

    public int getVideoid() {
        return videoid;
    }

    public void setVideoid(int videoid) {
        this.videoid = videoid;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
