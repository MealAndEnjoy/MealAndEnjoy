package com.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
//用户类
@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;
	private String username;
	private String password;
	private String imgUrl;
	private String state;//用户账号状态
	private String role;//用户角色  （商家还是用户）
	private String phone;  //手机号码
	@OneToOne(mappedBy="user")
	private Shop shop;
	@OneToOne(mappedBy="user")
	private ShopCollection shopCollection;
	@OneToMany(mappedBy="user", targetEntity=Comment.class,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set CommentSet = new HashSet<Comment>();
	@OneToMany(mappedBy="user", targetEntity=Numberr.class,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set numberrSet = new HashSet<Numberr>();
	
	
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public ShopCollection getShopCollection() {
		return shopCollection;
	}
	public void setShopCollection(ShopCollection shopCollection) {
		this.shopCollection = shopCollection;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Set getCommentSet() {
		return CommentSet;
	}
	public void setCommentSet(Set commentSet) {
		CommentSet = commentSet;
	}
	public Set getNumberrSet() {
		return numberrSet;
	}
	public void setNumberrSet(Set numberrSet) {
		this.numberrSet = numberrSet;
	}
	

	
}
