package entity;

import java.io.Serializable;

/**
 * Created by ll on 2018/6/15.
 */

public class Shop implements Serializable {
    private int shopdId;
    private String shopdName;
    private String shopIntroduce;//店铺介绍信息
    private String state;//店铺状态信息（营业中-暂不需要排队；营业中-正在排队；打烊了；暂停营业）
    private String waitTime;
    private String shopimg;
    private double lat;
    private double lng;

    public String getShopimg() {
        return shopimg;
    }

    public void setShopimg(String shopimg) {
        this.shopimg = shopimg;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getShopdId() {
        return shopdId;
    }

    public void setShopdId(int shopdId) {
        this.shopdId = shopdId;
    }

    public String getShopdName() {
        return shopdName;
    }

    public void setShopdName(String shopdName) {
        this.shopdName = shopdName;
    }

    public String getShopIntroduce() {
        return shopIntroduce;
    }

    public void setShopIntroduce(String shopIntroduce) {
        this.shopIntroduce = shopIntroduce;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }

}
