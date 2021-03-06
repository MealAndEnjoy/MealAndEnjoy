﻿package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ShopImgDao;
import com.entity.ShopImg;

@Service
@Transactional
public class ShopImgService {
	@Autowired
	private ShopImgDao shopImgDao;
	//根据店铺id查找图片
	public List<ShopImg> getById(int shopId){
		return shopImgDao.getById(shopId);
	}
        //根据商店id查图片url
        public List<String> selectall(int shopid,List<String> silist){
		silist = shopImgDao.selectall(shopid);
	
		return silist;
	}
}
