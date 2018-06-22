package entity;

/**
 * Created by lenovo on 2018/6/17.
 */

public class Numberr {
    private int numberrId;
    private String state;//状态
    private String date;//日期
    private String qName; //队列名称
    private String userphone;
    private int userid;
    private int shopid;

    public int getNumberrId() {
        return numberrId;
    }

    public void setNumberrId(int numberrId) {
        this.numberrId = numberrId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getqName() {
        return qName;
    }

    public void setqName(String qName) {
        this.qName = qName;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }
}
