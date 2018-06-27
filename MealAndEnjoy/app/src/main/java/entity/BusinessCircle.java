package entity;

/**
 * Created by lenovo on 2018/6/6.
 */

public class BusinessCircle {
    private int businesscircleid;
    private String businesscirclename;
    private String imgurl;
    private double lat;
    private double lng;

    public String getimgurl() {
        return imgurl;
    }

    public void setimgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getBusinesscircleid() {
        return businesscircleid;
    }

    public void setBusinesscircleid(int businesscircleid) {
        this.businesscircleid = businesscircleid;
    }

    public String getBusinesscirclename() {
        return businesscirclename;
    }

    public void setBusinesscirclename(String businesscirclename) {
        this.businesscirclename = businesscirclename;
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
}
