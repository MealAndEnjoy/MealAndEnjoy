package com.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//号码类
@Entity
@Table(name="numberr")
public class Numberr {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int numberrId;
	private String state;//状态
	private Date date;//日期
	private String qName; //队列名称
	private String userphone;
	 @ManyToOne
	 @JoinColumn(name="userId")
	private User user;
	 @ManyToOne
	 @JoinColumn(name="shopId")
	private Shop shop;

	public String getState() {
		return state;
	}
	public int getNumberrId() {
		return numberrId;
	}
	public void setNumberrId(int numberrId) {
		this.numberrId = numberrId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getqName() {
		return qName;
	}
	public void setqName(String qName) {
		this.qName = qName;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public String getUserphone() {
		return userphone;
	}
	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}

}
