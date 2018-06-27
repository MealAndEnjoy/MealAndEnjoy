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
import javax.persistence.Table;

//类别类
@Entity
@Table(name="type")
public class Type {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int typeId;
	private String typeName;//类型名称
	@OneToMany(mappedBy="type", targetEntity=Shop.class,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Shop> shopSet = new HashSet<>();
	
	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Set<Shop> getShopSet() {
		return shopSet;
	}

	public void setShopSet(Set<Shop> shopSet) {
		this.shopSet = shopSet;
	}

}
