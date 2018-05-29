package com.entity;

import java.util.List;

//收藏夹类
public class ShopCollection {

	private int shopCollectionId;
	
	private User user;
	private List<Shop>shops;
	
	public int getShopCollectionId() {
		return shopCollectionId;
	}
	public void setShopCollectionId(int shopCollectionId) {
		this.shopCollectionId = shopCollectionId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Shop> getShops() {
		return shops;
	}
	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}
	
	
	
}
