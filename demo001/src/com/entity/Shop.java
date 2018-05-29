package com.entity;

import java.util.List;

public class Shop {

	private int shopId;
	private String shopName;
	private String shopIntroduce;//店铺介绍信息
	private String state;//店铺状态信息（营业中-暂不需要排队；营业中-正在排队；打烊了；暂停营业）
	private String shopImg;
	private String waitTime;
	
	private User user;
	private List<Comment>commentList;
	private Address address;
	private QueueList queueList;
	private Type type;
	private ShopCollection shopCollection;
	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Comment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public QueueList getQueueList() {
		return queueList;
	}
	public void setQueueList(QueueList queueList) {
		this.queueList = queueList;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public ShopCollection getShopCollection() {
		return shopCollection;
	}
	public void setShopCollection(ShopCollection shopCollection) {
		this.shopCollection = shopCollection;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
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
	public String getShopImg() {
		return shopImg;
	}
	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}
	public String getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}
	
	
	
}
