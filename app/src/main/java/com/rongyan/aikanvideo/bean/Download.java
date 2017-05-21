package com.rongyan.aikanvideo.bean;

/**
 * Created by XRY on 2017/5/20.
 */

public class Download {
    private String title;
    private String path;
    private String titlenews;



    public Download(String title, String path) {
        this.title = title;
        this.path = path;
    }

    public String getTitlenews() {
        return titlenews;
    }

    public void setTitlenews(String titlenews) {
        this.titlenews = titlenews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

