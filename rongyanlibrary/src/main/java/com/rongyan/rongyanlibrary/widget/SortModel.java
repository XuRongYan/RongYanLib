package com.rongyan.rongyanlibrary.widget;

/**
 * 实体类，里面包含了ListView的name，另外一个就是显示name的首字母。
 * Created by XRY on 2016/11/1.
 */

public class SortModel {

    private String name; //显示的数据

    private String sortLetters; //显示数据的拼音字母

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
