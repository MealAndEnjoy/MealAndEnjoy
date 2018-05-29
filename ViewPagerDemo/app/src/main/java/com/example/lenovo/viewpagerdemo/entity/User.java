package com.example.lenovo.viewpagerdemo.entity;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/5/27.
 */

public class User implements Serializable {
    private int userId;
    private String userName;
    private String password;
    private String imgUrl;
    private String state;//用户账号状态
    private String role;//用户角色  （商家还是用户）
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    public User(){

    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
