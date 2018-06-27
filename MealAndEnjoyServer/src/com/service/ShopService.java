package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ShopDao;
import com.entity.Shop;

@Service
@Transactional
public class ShopService {
	@Autowired
	private ShopDao shopDao;

	// 根据店铺ID查找店铺
	public Shop getShop(int shopId) {
		Shop shop = shopDao.getShop(shopId);
		return shop;
	}

	// 获取所有商店列表
	public List<Shop> selectall() {
		List<Shop> shoplist = shopDao.selectall();
		return shoplist;
	}

	// 根据类别获取商店列表
	public List<Shop> getTypeShop(int typeId) {
		List<Shop> shoplist = shopDao.selectTypeShop(typeId);
		return shoplist;
	}

	// 获取所有属于美食的店铺
	public List<Shop> selectFood() {
		return shopDao.selectFood();
	}

	// 获取所有属于娱乐的店铺
	public List<Shop> selectEntermain() {
		return shopDao.selectEntermain();
	}

	// 修改店铺信息，并更新数据库
	public boolean updateState(int shopId, String state) {
		boolean flag = shopDao.updateState(shopId, state);
		if (flag) {
			return true;
		} else {
			return false;
		}
	}
	// 获取热门店铺
		public List<Shop> selecthotshop() {
			return shopDao.selecthot();
		}
		//
		//根据商圈查店铺
		public List<Shop> getbcdetail(String id) {
			return shopDao.getbcdetail(id);
		}
}
