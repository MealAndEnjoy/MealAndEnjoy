package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ShopDemoDao;
import com.entity.ShopDemo;

@Service
@Transactional
public class ShopDemoService {
	
	@Autowired
	private ShopDemoDao shopDemoDao;
	//测试连接方法
	public ShopDemo printInfo(ShopDemo shopDemo) {
		shopDemo = shopDemoDao.printInfo(shopDemo);
		return shopDemo;
	}
    //获取商店列表
	public List<ShopDemo> selectall(List<ShopDemo> shoplist){
		shoplist = shopDemoDao.selectall(shoplist);
		return shoplist;
	}
	 //根据类别获取商店列表
		public List<ShopDemo> getDelishops(String type) {
			List<ShopDemo> shoplist  = shopDemoDao.selectShop(type);
			return shoplist;
		}
}
