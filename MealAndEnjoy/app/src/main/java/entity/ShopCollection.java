package entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

//收藏夹类
public class ShopCollection implements Serializable {
	private int shopCollectionId;
	private User user;
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
