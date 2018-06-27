package com.entity;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="businesscircle")
public class BusinessCircle {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int businesscircleid;
	private String businesscirclename;
	private String imgurl;
	private double lat;
	private double lng;

	
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public int getBusinesscircleid() {
		return businesscircleid;
	}
	public void setBusinesscircleid(int businesscircleid) {
		this.businesscircleid = businesscircleid;
	}
	public String getBusinesscirclename() {
		return businesscirclename;
	}
	public void setBusinesscirclename(String businesscirclename) {
		this.businesscirclename = businesscirclename;
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
