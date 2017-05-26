package com.rongyan.rongyanlibrary.rxHttpHelper.entity;

/**
 * Created by XRY on 2017/5/20.
 */

public class History {
    private int userid;
    private int videoid;
    private String watchtime;
    private String title;
    private String titlenew;
    private String videoURL;
    private String imageURL;
    private long timelast;
    private long startpoint;
    private long stoppoint;
    private double startrate;
    private double stoprate;



    public History(int userid, int videoid, String watchtime, String title, String titlenew, String videoURL, String imageURL, long timelast, long startpoint, long stoppoint, double startrate, double stoprate) {
        this.userid = userid;
        this.videoid = videoid;
        this.watchtime = watchtime;
        this.title = title;
        this.titlenew = titlenew;
        this.videoURL = videoURL;
        this.imageURL = imageURL;
        this.timelast = timelast;
        this.startpoint = startpoint;
        this.stoppoint = stoppoint;
        this.startrate = startrate;
        this.stoprate = stoprate;
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

    public String getWatchtime() {
        return watchtime;
    }

    public void setWatchtime(String watchtime) {
        this.watchtime = watchtime;
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

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public long getTimelast() {
        return timelast;
    }

    public void setTimelast(long timelast) {
        this.timelast = timelast;
    }

    public long getStartpoint() {
        return startpoint;
    }

    public void setStartpoint(long startpoint) {
        this.startpoint = startpoint;
    }

    public long getStoppoint() {
        return stoppoint;
    }

    public void setStoppoint(long stoppoint) {
        this.stoppoint = stoppoint;
    }

    public double getStartrate() {
        return startrate;
    }

    public void setStartrate(double startrate) {
        this.startrate = startrate;
    }

    public double getStoprate() {
        return stoprate;
    }

    public void setStoprate(double stoprate) {
        this.stoprate = stoprate;
    }
}
