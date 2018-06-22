package entity;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/5/25.
 */

public class ShopDemo implements Serializable {
    private int shopdId;
    private String shopdName;
    private String shopimg;
    private double lat;
    private double lng;
    private String address;


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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}