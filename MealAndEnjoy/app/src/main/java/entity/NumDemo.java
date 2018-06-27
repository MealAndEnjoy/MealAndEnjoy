package entity;

import java.io.Serializable;

public class NumDemo implements Serializable {
	private int nowNum;
	private int myNum;
	private String getNumtime;
	private String numState;
	private String shopName;
	private int shopId;
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public int getNowNum() {
		return nowNum;
	}
	public void setNowNum(int nowNum) {
		this.nowNum = nowNum;
	}
	public int getMyNum() {
		return myNum;
	}
	public void setMyNum(int myNum) {
		this.myNum = myNum;
	}
	public String getGetNumtime() {
		return getNumtime;
	}
	public void setGetNumtime(String getNumtime) {
		this.getNumtime = getNumtime;
	}
	public String getNumState() {
		return numState;
	}
	public void setNumState(String numState) {
		this.numState = numState;
	}

	
}
