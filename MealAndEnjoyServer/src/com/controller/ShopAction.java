package com.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.service.NumberrService;
import com.service.ShopImgService;
import com.service.ShopService;

@Controller
@RequestMapping("/shop")
public class ShopAction {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopImgService shopImgService;
	@Autowired
	private NumberrService numberrService;
	
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
			//获取当前时间
			Date date = new Date();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String numTime = format.format(date);
			List<Shop> shoplist = shopService.selectall();
			List<ShopDemo> shoplistt = new ArrayList<ShopDemo>();
			for(Shop sp : shoplist) {
				ShopDemo shopdemo = new ShopDemo();
				int num1 = numberrService.getLittle(sp.getShopId(), numTime);
				int num2 = numberrService.getMiddle(sp.getShopId(), numTime);
				int num3 = numberrService.getLarge(sp.getShopId(), numTime);
				int allnum = num1 + num2 + num3 ;
				String allNum = String.valueOf(allnum);
				shopdemo.setShopdId(sp.getShopId());
				shopdemo.setShopdName(sp.getShopName());
				shopdemo.setShopimg(sp.getCover());
				shopdemo.setLat(sp.getLat());;
				shopdemo.setLng(sp.getLng());
				shopdemo.setAddress(sp.getAddress().getAddress());
				shopdemo.setAllNum(allNum);
				shopdemo.setAvgCost(String.valueOf(sp.getAvgCost()));
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
			//获取当前时间
			Date date = new Date();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String numTime = format.format(date);
			for(Shop sp : shoplist) {
				ShopDemo shopdemo = new ShopDemo();
				int num1 = numberrService.getLittle(sp.getShopId(), numTime);
				int num2 = numberrService.getMiddle(sp.getShopId(), numTime);
				int num3 = numberrService.getLarge(sp.getShopId(), numTime);
				int allnum = num1 + num2 + num3 ;
				String allNum = String.valueOf(allnum);
				shopdemo.setShopdId(sp.getShopId());
				shopdemo.setShopdName(sp.getShopName());
				shopdemo.setShopimg(sp.getCover());
				shopdemo.setLat(sp.getLat());;
				shopdemo.setLng(sp.getLng());
				shopdemo.setAddress(sp.getAddress().getAddress());
				shopdemo.setAllNum(allNum);
				shopdemo.setAvgCost(String.valueOf(sp.getAvgCost()));
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
				//获取当前时间
				Date date = new Date();
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String numTime = format.format(date);
				for(Shop sp : shoplist) {
					ShopDemo shopdemo = new ShopDemo();
					int num1 = numberrService.getLittle(sp.getShopId(), numTime);
					int num2 = numberrService.getMiddle(sp.getShopId(), numTime);
					int num3 = numberrService.getLarge(sp.getShopId(), numTime);
					int allnum = num1 + num2 + num3 ;
					String allNum = String.valueOf(allnum);
					shopdemo.setShopdId(sp.getShopId());
					shopdemo.setShopdName(sp.getShopName());
					shopdemo.setShopimg(sp.getCover());
					shopdemo.setLat(sp.getLat());;
					shopdemo.setLng(sp.getLng());
					shopdemo.setAddress(sp.getAddress().getAddress());
					shopdemo.setAllNum(allNum);
					shopdemo.setAvgCost(String.valueOf(sp.getAvgCost()));
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
 		
	        //获取商店grid图片
	        @RequestMapping("/getshopgrid")
		public void getshopgrid(Model model,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {
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
			String reString = buff.toString();
			//System.out.println(buff.toString());
			int x = Integer.valueOf(reString);
			List<String> silist = new ArrayList<String>();
			silist = shopImgService.selectall(x,silist);
			Gson gson = new Gson();
		    String jsilist = gson.toJson(silist);
			writer.print(jsilist);
		}
	        @RequestMapping("/updatestate")
	    	//商家更新状态的业务逻辑
	    	public void updateState(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    		System.out.println("收到客户端请求");
	    	    response.setContentType("text/html;charset=utf-8");
	    	    response.setCharacterEncoding("utf-8");
	    		request.setCharacterEncoding("utf-8");
	    		PrintWriter writer = response.getWriter();
	    		String shopid=request.getParameter("shopId");
	    		int shopId=Integer.parseInt(shopid);
	    		System.out.println(shopid);
	    		String state=request.getParameter("state");
	    		System.out.println(state);
	    		//改变shop的state属性，并更新数据库
	    		boolean flag=shopService.updateState(shopId, state);
	    		if(flag) {
	    		//返回新的shop对象给客户端
	    		Shop shop1=shopService.getShop(shopId);
	    		ShopDemo shop=new ShopDemo();
	    		shop.setLat(shop1.getLat());
	    		shop.setLng(shop1.getLng());
	    		shop.setShopdId(shop1.getShopId());
	    		shop.setShopdName(shop1.getShopName());
	    		shop.setShopimg(shop1.getCover());
	    		shop.setShopIntroduce(shop1.getShopIntroduce());
	    		shop.setState(shop1.getState());
	    		shop.setWaitTime(shop1.getWaitTime());
	    		Gson gson=new Gson();
	    		String JShop=gson.toJson(shop);
	    		System.out.println(JShop);
	    		writer.write(JShop);
	    		}else {
	    			writer.write("未更新成功，请稍候再试");
	    		}
	    	}
	    	
	        @RequestMapping("/gethotshop")
	    	public void gethotshop(Model model, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest)
	    			throws IOException {
	    		System.out.println("有post请求");
	    		httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
	    		httpServletResponse.setCharacterEncoding("UTF-8");
	    		PrintWriter writer = httpServletResponse.getWriter();
	    		InputStream in = httpServletRequest.getInputStream();
	    		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
	    		String str = null;
	    		StringBuffer buff = new StringBuffer();
	    		while (null != (str = reader.readLine())) {
	    			buff.append(str);
	    		}
	    		System.out.println(buff.toString());
	    		List<Shop> hotshoplist = shopService.selecthotshop();
	    		List<ShopDemo> hotshoplistt = new ArrayList<ShopDemo>();
	    		for (Shop sp : hotshoplist) {
	    			ShopDemo shopdemo = new ShopDemo();
	    			shopdemo.setShopdId(sp.getShopId());
	    			shopdemo.setShopdName(sp.getShopName());
	    			shopdemo.setShopimg(sp.getCover());
	    			shopdemo.setLat(sp.getLat());
	    			;
	    			shopdemo.setLng(sp.getLng());
	    			hotshoplistt.add(shopdemo);
	    		}
	    		Gson gson = new Gson();
	    		String jshoplist = gson.toJson(hotshoplistt);
	    		System.out.println(jshoplist);
	    		writer.println(jshoplist);
	    	}
	        @RequestMapping("/type")
		      public void type1(HttpServletResponse response, HttpServletRequest request) throws IOException {
		    	  response.setContentType("text/html;charset=utf-8");
		    	    response.setCharacterEncoding("utf-8");
		    		request.setCharacterEncoding("utf-8");
		    		PrintWriter writer = response.getWriter();
		    	  String typeid = request.getParameter("typeID");
		    	  int typeId = Integer.parseInt(typeid);
		    	  List<Shop> shopList = shopService.getTypeShop(typeId);
		    	  List<ShopDemo> shoplistt = new ArrayList<ShopDemo>();
					//获取当前时间
					Date date = new Date();
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String numTime = format.format(date);
					for(Shop sp : shopList) {
						ShopDemo shopdemo = new ShopDemo();
						int num1 = numberrService.getLittle(sp.getShopId(), numTime);
						int num2 = numberrService.getMiddle(sp.getShopId(), numTime);
						int num3 = numberrService.getLarge(sp.getShopId(), numTime);
						int allnum = num1 + num2 + num3 ;
						String allNum = String.valueOf(allnum);
						shopdemo.setShopdId(sp.getShopId());
						shopdemo.setShopdName(sp.getShopName());
						shopdemo.setShopimg(sp.getCover());
						shopdemo.setLat(sp.getLat());;
						shopdemo.setLng(sp.getLng());
						shopdemo.setAddress(sp.getAddress().getAddress());
						shopdemo.setAllNum(allNum);
						shopdemo.setAvgCost(String.valueOf(sp.getAvgCost()));
						shoplistt.add(shopdemo);
					}
					Gson gson = new Gson();
					String jshoplist = gson.toJson(shoplistt);
					writer.write(jshoplist);
		      }
	        
	        @RequestMapping("/bcdetail")
	    	public void getbcdetail(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {
	    		System.out.println("有post请求");
	    		httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
	    		httpServletResponse.setCharacterEncoding("UTF-8");
	    		PrintWriter writer = httpServletResponse.getWriter();
	    		InputStream in = httpServletRequest.getInputStream();
	    		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
	    		String str = null;
	    		StringBuffer buff = new StringBuffer();
	    		while (null != (str = reader.readLine())) {
	    			buff.append(str);
	    		}
	    		//获取当前时间
				Date date = new Date();
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String numTime = format.format(date);
	    		List<Shop> bcshoplist = shopService.getbcdetail(buff.toString());
	    		
	    		List<ShopDemo> bcdetail = new ArrayList<ShopDemo>();
	    		for (Shop sp : bcshoplist) {
	    			
	    				ShopDemo shopdemo = new ShopDemo();
	    				int num1 = numberrService.getLittle(sp.getShopId(), numTime);
	    				int num2 = numberrService.getMiddle(sp.getShopId(), numTime);
	    				int num3 = numberrService.getLarge(sp.getShopId(), numTime);
	    				int allnum = num1 + num2 + num3 ;
	    				String allNum = String.valueOf(allnum);
	    				shopdemo.setShopdId(sp.getShopId());
	    				shopdemo.setShopdName(sp.getShopName());
	    				shopdemo.setShopimg(sp.getCover());
	    				shopdemo.setLat(sp.getLat());;
	    				shopdemo.setLng(sp.getLng());
	    				shopdemo.setAddress(sp.getAddress().getAddress());
	    				shopdemo.setAllNum(allNum);
	    				shopdemo.setAvgCost(String.valueOf(sp.getAvgCost()));
	    				bcdetail.add(shopdemo);
	    			
	    		}
	    		Gson gson = new Gson();
	    		String jshoplist = gson.toJson(bcdetail);
	    		System.out.println(jshoplist);
	    		writer.println(jshoplist);
	    	}


 }
	
