package com.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//收藏夹类
@Entity
@Table(name="shopcollection")
public class ShopCollection {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int shopCollectionId;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userId")
	private User user;
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinTable(name="shop_shopcollection", 
	    joinColumns=@JoinColumn(name="shopCollectionId"),
	    inverseJoinColumns=@JoinColumn(name="shopId"))
    private Set<Shop> shopList = new HashSet<Shop>();
	
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
	public Set<Shop> getShopList() {
		return shopList;
	}
	public void setShopList(Set<Shop> shopList) {
		this.shopList = shopList;
	}
	

	
	
	
}
