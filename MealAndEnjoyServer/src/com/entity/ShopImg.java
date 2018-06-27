package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="shopimg")
public class ShopImg {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int shopImgId;
    private String shopImgUrl;
     @ManyToOne
	 @JoinColumn(name="shopId")
    private Shop shop;
	public int getShopImgId() {
		return shopImgId;
	}
	public void setShopImgId(int shopImgId) {
		this.shopImgId = shopImgId;
	}
	public String getShopImgUrl() {
		return shopImgUrl;
	}
	public void setShopImgUrl(String shopImgUrl) {
		this.shopImgUrl = shopImgUrl;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
    
	
	
    
}
