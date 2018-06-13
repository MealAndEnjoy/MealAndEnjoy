package com.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Comment;
import com.entity.Numberr;
import com.entity.Shop;
import com.entity.ShopCollection;
import com.entity.ShopImg;
import com.service.ShopService;

@Controller
@RequestMapping("/shop")
public class ShopAction {
	@Autowired
	private ShopService shopService;
	
	//shop类关联测试
	@RequestMapping("/bbb")
   public void getS() {
	   int shopId = 1;
	   Shop shop = shopService.getShop(shopId);
	   Set<ShopCollection> sc = shop.getShopCollectionSet();
 	   Set<Comment> cmt = shop.getCommentSet();
 	   Set<ShopImg> ss = shop.getShopImgSet();
 	   Set<Numberr> nu = shop.getNumberSet();
	   int i = sc.size();
	   String a3 = String.valueOf(i);
//	   for(ShopCollection sc1 : sc) {
//		   System.out.println(sc1.getUser().getUserId());
//	   }
//	   for(Comment co : cmt) {
//		   System.out.println(co.getComment());
//	   }
//	   System.out.println(shop.getAddress().getAddress());
//	   System.out.println(shop.getType().getTypeName());
	   for(ShopImg si : ss) {
		   System.out.println(si.getShopImgUrl());
	   }
	   for(Numberr nb : nu) {
		   System.out.println(nb.getqName());
	   }
   }
	
	
}
