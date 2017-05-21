package com.rongyan.rongyanlibrary.rxHttpHelper.entity;

import com.rongyan.rongyanlibrary.base.BaseEntity;

/**
 * Created by XRY on 2017/4/14.
 */

public class User extends BaseEntity{
    private int userid;
    private String cellphone;
    private String nickname;
    private String email;
    private String userstatus;
    private String headimg;
    private String age;
    private String career;
    private String gender;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", cellphone='" + cellphone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", userstatus='" + userstatus + '\'' +
                ", headimg='" + headimg + '\'' +
                ", age='" + age + '\'' +
                ", career='" + career + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
