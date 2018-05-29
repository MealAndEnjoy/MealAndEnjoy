package com.entity;

import java.util.List;

//店铺的队列集合
public class QueueList {

	private int queueListId;
	private final int count = 6;//每个店铺设置的排队队列数不能超过6个
	
	private Shop shop;
	private List<Queue> queueList;
	public int getQueueListId() {
		return queueListId;
	}
	public void setQueueListId(int queueListId) {
		this.queueListId = queueListId;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public List<Queue> getQueueList() {
		return queueList;
	}
	public void setQueueList(List<Queue> queueList) {
		this.queueList = queueList;
	}
	public int getCount() {
		return count;
	}
	
	
	
}
