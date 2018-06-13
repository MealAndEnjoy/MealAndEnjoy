package com.service;

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
	
	//根据店铺ID查找店铺
		public Shop getShop(int shopId) {
			Shop shop = shopDao.getShop(shopId);
			return shop;
		}
	
}
