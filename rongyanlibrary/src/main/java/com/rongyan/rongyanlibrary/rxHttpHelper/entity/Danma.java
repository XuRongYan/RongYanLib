package com.rongyan.rongyanlibrary.rxHttpHelper.entity;

/**
 * Created by XRY on 2017/5/18.
 */

public class Danma {
    private String c;
    private String m;

    public Danma(String c, String m) {
        this.c = c;
        this.m = m;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    @Override
    public String toString() {
        return "Danma{" +
                "c='" + c + '\'' +
                ", m='" + m + '\'' +
                '}';
    }
}
