package entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

//类别类
public class Type implements Serializable {
	private int typeId;
	private String typeName;//类型名称
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
