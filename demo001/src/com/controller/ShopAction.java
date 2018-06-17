package com.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Comment;
import com.entity.Numberr;
import com.entity.Shop;
import com.entity.ShopCollection;
import com.entity.ShopDemo;
import com.entity.ShopImg;
import com.google.gson.Gson;
import com.service.ShopImgService;
import com.service.ShopService;

@Controller
@RequestMapping("/shop")
public class ShopAction {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopImgService shopImgService;
	
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
	
	//获取首页listview数据
		@RequestMapping("/homelist")
		public void getHomelist(Model model,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {
			System.out.println("有post请求");
			httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
			httpServletResponse.setCharacterEncoding("UTF-8");
			PrintWriter writer = httpServletResponse.getWriter();
			InputStream in = httpServletRequest.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String str = null;
			StringBuffer buff = new StringBuffer();
			while(null !=(str = reader.readLine())) {
				buff.append(str);
			}
			System.out.println(buff.toString());
			List<Shop> shoplist = shopService.selectall();
			List<ShopDemo> shoplistt = new ArrayList<ShopDemo>();
			for(Shop sp : shoplist) {
				ShopDemo shopdemo = new ShopDemo();
				shopdemo.setShopdId(sp.getShopId());
				shopdemo.setShopdName(sp.getShopName());
				shopdemo.setShopimg(sp.getCover());
				shopdemo.setLat(sp.getLat());;
				shopdemo.setLng(sp.getLng());
				shoplistt.add(shopdemo);
			}
			Gson gson = new Gson();
			String jshoplist = gson.toJson(shoplistt);
			System.out.println(jshoplist);
			writer.println(jshoplist);
		}
		
		//获取美食页商店列表
		@RequestMapping("/getDelishops")
		public void getDelishops(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {
			System.out.println("美食页列表有post请求");
			httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
			httpServletResponse.setCharacterEncoding("UTF-8");
			PrintWriter writer = httpServletResponse.getWriter();
			InputStream in = httpServletRequest.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String str = null;
			StringBuffer buff = new StringBuffer();
			while(null !=(str = reader.readLine())) {
				buff.append(str);
			}
			System.out.println(buff.toString());
			List<ShopDemo> shoplistt = new ArrayList<ShopDemo>();
			List<Shop> shoplist = shopService.selectFood();
			for(Shop sp : shoplist) {
				ShopDemo shopdemo = new ShopDemo();
				shopdemo.setShopdId(sp.getShopId());
				shopdemo.setShopdName(sp.getShopName());
				shopdemo.setShopimg(sp.getCover());
				shopdemo.setLat(sp.getLat());;
				shopdemo.setLng(sp.getLng());
				shoplistt.add(shopdemo);
			}
			Gson gson = new Gson();
			String jshoplist = gson.toJson(shoplistt);
			System.out.println(jshoplist);
			writer.println(jshoplist);
		}
//		//获取娱乐页商店列表
 		@RequestMapping("/getEntertainshops")
			public void getEntertainshops(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {
				System.out.println("娱乐页列表有post请求");
				httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
			httpServletResponse.setCharacterEncoding("UTF-8");
			PrintWriter writer = httpServletResponse.getWriter();
				InputStream in = httpServletRequest.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
				String str = null;
				StringBuffer buff = new StringBuffer();
				while(null !=(str = reader.readLine())) {
					buff.append(str);
				}
				System.out.println(buff.toString());
				List<ShopDemo> shoplistt = new ArrayList<ShopDemo>();
				List<Shop> shoplist = shopService.selectEntermain();
				for(Shop sp : shoplist) {
					ShopDemo shopdemo = new ShopDemo();
					shopdemo.setShopdId(sp.getShopId());
					shopdemo.setShopdName(sp.getShopName());
					shopdemo.setShopimg(sp.getCover());
					shopdemo.setLat(sp.getLat());;
					shopdemo.setLng(sp.getLng());
					shoplistt.add(shopdemo);
				}
				Gson gson = new Gson();
				String jshoplist = gson.toJson(shoplistt);
			System.out.println(jshoplist);
				writer.println(jshoplist);
			}
 		
 		@RequestMapping("/getshopimg")
		public void getshopimg(Model model,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {
			InputStream in = httpServletRequest.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String str = null;
			StringBuffer buff = new StringBuffer();
			while(null !=(str = reader.readLine())) {
				buff.append(str);
			}
			String str1 = buff.toString();
			//String str2 = httpServletRequest.getRealPath("/img/demo01.png");
			//System.out.println(str2);
			InputStream is = new FileInputStream(httpServletRequest.getRealPath(str1));
			OutputStream out = httpServletResponse.getOutputStream();
			int b = -1;
			while((b = is.read()) != -1) {
				out.write(b);
				out.flush();
			}
			is.close();
			out.close();
		}
 		
//	        //获取商店grid图片
//	        @RequestMapping("/getshopgrid")
//		public void getshopgrid(Model model,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {
//			System.out.println("有post请求");
//			httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
//			httpServletResponse.setCharacterEncoding("UTF-8");
//			PrintWriter writer = httpServletResponse.getWriter();
//			InputStream in = httpServletRequest.getInputStream();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
//			String str = null;
//			StringBuffer buff = new StringBuffer();
//			while(null !=(str = reader.readLine())) {
//				buff.append(str);
//			}
//			String reString = buff.toString();
//			//System.out.println(buff.toString());
//			int x = Integer.valueOf(reString);
//			List<String> silist = new ArrayList<String>();
//			silist = shopImgService.selectall(x,silist);
//			Gson gson = new Gson();
//			String jsilist = gson.toJson(silist);
//			writer.print(jsilist);
//		}
		
//  }
	
}
