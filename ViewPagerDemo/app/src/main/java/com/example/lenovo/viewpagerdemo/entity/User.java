package com.example.lenovo.viewpagerdemo.entity;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/5/27.
 */

public class User implements Serializable {
    private int userId;
    private String username;
    private String password;
    private String imgUrl;
    private String state;//用户账号状态
    private String role;//用户角色  （商家还是用户）
    private String phone;//手机号码

    public User(int userId, String username, String password, String imgUrl, String state, String role, String phone) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.imgUrl = imgUrl;
        this.state = state;
        this.role = role;
        this.phone = phone;
    }

    public User(){

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
