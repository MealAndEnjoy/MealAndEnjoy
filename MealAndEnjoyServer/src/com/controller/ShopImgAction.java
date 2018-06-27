package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.ShopImg;
import com.service.ShopImgService;

@Controller
@RequestMapping("/shopimg")
public class ShopImgAction {
	@Autowired
	private ShopImgService shopImgService;
	
	@RequestMapping("ccc")
	public void ceshi() {
		int shopId = 1;
		List<ShopImg> ls = shopImgService.getById(shopId);
		for(ShopImg si : ls) {
			System.out.println(si.getShopImgUrl());
		}
	}
}
