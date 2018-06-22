package entity;

/**
 * Created by 张刘洋 on 2018/5/28.
 */

public class BusinessCircle {
    private String name;
    private int imgurl;
    private double juli;//距离

    public BusinessCircle(String name, int imgurl, double juli) {
        this.name = name;
        this.imgurl = imgurl;
        this.juli = juli;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgurl() {
        return imgurl;
    }

    public void setImgurl(int imgurl) {
        this.imgurl = imgurl;
    }

    public double getJuli() {
        return juli;
    }

    public void setJuli(double juli) {
        this.juli = juli;
    }
}
