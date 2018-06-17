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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
//商铺类
@Entity
@Table(name="shop")
public class Shop {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int shopId;
	private String shopName;
	private String shopIntroduce;//店铺介绍信息
	private String state;//店铺状态信息（营业中-暂不需要排队；营业中-正在排队；打烊了；暂停营业）
	private String waitTime;
	private double lat;
	private double lng;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userId")
	private User user;
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="shop_shopcollection", 
	    joinColumns=@JoinColumn(name="shopId"),
	    inverseJoinColumns=@JoinColumn(name="shopCollectionId"))
	private Set<ShopCollection> shopCollectionSet = new HashSet<ShopCollection>();
	@OneToMany(mappedBy="shop", targetEntity=Comment.class,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set CommentSet = new HashSet<Comment>();
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="addressId")
	private Address address;
	 @ManyToOne
	 @JoinColumn(name="typeId")
	private Type type;
	 @OneToMany(mappedBy="shop", targetEntity=ShopImg.class,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set shopImgSet  = new HashSet<ShopImg>();
	private String cover; //封面
	@OneToMany(mappedBy="shop", targetEntity=Numberr.class,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set numberSet = new HashSet<Number>();
	
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
	public String getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<ShopCollection> getShopCollectionSet() {
		return shopCollectionSet;
	}
	public void setShopCollectionSet(Set<ShopCollection> shopCollectionSet) {
		this.shopCollectionSet = shopCollectionSet;
	}
	public Set getCommentSet() {
		return CommentSet;
	}
	public void setCommentSet(Set commentSet) {
		CommentSet = commentSet;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public Set getShopImgSet() {
		return shopImgSet;
	}
	public void setShopImgSet(Set shopImgSet) {
		this.shopImgSet = shopImgSet;
	}

	
	public Set getNumberSet() {
		return numberSet;
	}
	public void setNumberSet(Set numberSet) {
		this.numberSet = numberSet;
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
