package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.NumDemo;
import com.entity.Numberr;
import com.entity.Shop;
import com.entity.ShopDemo;
import com.entity.User;
import com.entity.UserDemo;
import com.google.gson.Gson;
import com.service.NumberrService;
import com.service.ShopService;
import com.service.UserService;

@Controller
@RequestMapping("/user")
public class UserAction {
	@Autowired
	private UserService userService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private NumberrService numberrService;
	//用户登录
	@RequestMapping("/logintext")
	public void userLoginText(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("收到客户端请求");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		User user=new User();
		/*String param = request.getParameter("user");*/
		String userName = request.getParameter("userName");
		String password=request.getParameter("password");
		/*String str="这是一条测试语句";*/
		user.setUsername(userName);
		user.setPassword(password);
		System.out.println(userName+","+password);
		if(user.getUsername()!=null && user.getPassword() != null && !"".equals(user.getUsername()) && !"".equals(user.getPassword())) {
	    	 boolean result = userService.isUser(user.getUsername());
	    	 User user1= userService.getUser(user.getUsername());
	    		 if(result  && (user.getPassword()).equals(user1.getPassword())) {
	    			 UserDemo user2=new UserDemo();
	    			 user2.setUserId(user1.getUserId());
	    			 user2.setUsername(user1.getUsername());
	    			 user2.setPassword(user1.getPassword());
	    			 user2.setImgUrl(user1.getImgUrl());
	    			 user2.setPhone(user1.getPhone());
	    			 user2.setRole(user1.getRole());
	    			 user2.setState(user1.getState());
	    			 Gson gson=new Gson();
	    			 String jUser=gson.toJson(user2);
	    			 System.out.println(jUser);
	    			 writer.write(jUser);
	    		 }else {
	    			 writer.write("您输入的用户名或密码不正确");
		         }
	    }else {
	    	writer.write("用户名或密码不能为空");
	    }
	/*	writer.write(str);*/
	}


	//用户注册
    @RequestMapping("/register")
    public void register(HttpSession session,HttpServletResponse response,HttpServletRequest request ) throws IOException {
    	PrintWriter writer = response.getWriter();
        //获取安卓端数据
		String username = request.getParameter("username");
		String password=request.getParameter("password");
		String phone = request.getParameter("phone");
		//实例化一个User对象
		User user=new User();
		//判断该用户名是否已被注册
		boolean result1 = userService.judgeName(username);
		if(result1) {
			writer.write("0");
		}else {
			user.setUsername(username);
			user.setPassword(password);
			user.setPhone(phone);
			user.setState("正常");
			boolean result2 = userService.register(user);
			writer.write("yes");
		}
		return;
    }
    
    
    //测试
    @RequestMapping("/aaa")
    public void a() {
    	User user  = userService.getUser("zhangsans");
    	String nnn = user.getShop().getShopName();
    	int a = user.getShopCollection().getShopCollectionId();
    	String a2 = String.valueOf(a);
//    	Set<Comment> s = user.getCommentSet();
//    	int i  = 0;
//    	for(Comment c : s) {
//    		System.out.println(c.getComment());
//    	}
    	
    	Set<Numberr> numbers = user.getNumberrSet();
        System.out.println(numbers.size());	
    	for(Numberr n : numbers) {
		  System.out.println(n.getNumberrId());
	}
    }
  
  //根据返回的用户名更新用户信息，并将更新过后的用户信息返回给客户端
    @RequestMapping("/changeUsername")
    public void updateByUsername(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	System.out.println("收到客户端修改用户名请求");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		//获取客户端请求参数
		String priUsername=request.getParameter("priUsername");
		String changeUsername=request.getParameter("changeUsername");
		User user1=userService.getUser(changeUsername);
		if(changeUsername.equals(priUsername) || user1 == null) {
			boolean flag=userService.updateByUsername(priUsername, changeUsername);
			if(flag){
				User user=userService.getUser(changeUsername);
				UserDemo user2=new UserDemo();
				user2.setUserId(user.getUserId());
   			 	user2.setUsername(user.getUsername());
   			 	user2.setPassword(user.getPassword());
   			 	user2.setImgUrl(user.getImgUrl());
   			 	user2.setPhone(user.getPhone());
   			 	user2.setRole(user.getRole());
   			 	user2.setState(user.getState());
				Gson gson=new Gson();
				String JUser=gson.toJson(user2);
				System.out.println(JUser);
				writer.write(JUser);
			}
		}else{
			writer.write("该用户名已存在，请换一个吧~");
		}
		
    }
    //根据传输的用户名更新该用户的电话号码
    @RequestMapping("/changePhone")
    public void updatePhone(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	System.out.println("收到客户端修改手机号请求");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter writer=response.getWriter();
		//获取客户端请求参数
		String username=request.getParameter("username");
		String changePhone=request.getParameter("changePhone");
		boolean flag=userService.updatephone(username, changePhone);
		if(flag) {
			User user=userService.getUser(username);
			UserDemo user2=new UserDemo();
			user2.setUserId(user.getUserId());
			user2.setUsername(user.getUsername());
			user2.setPassword(user.getPassword());
			user2.setImgUrl(user.getImgUrl());
			user2.setPhone(user.getPhone());
			user2.setRole(user.getRole());
			user2.setState(user.getState());
			Gson gson=new Gson();
			String JUser=gson.toJson(user2);
			writer.write(JUser);
		}else {
			writer.write("未修改成功，请稍后再试~");
		}
		
    }
    @RequestMapping("/changePassword")
    //根据传输的数据修改用户密码
    public void updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	System.out.println("收到客户端修改密码请求");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter writer=response.getWriter();
		//获取客户端请求参数
		String username=request.getParameter("username");
		String changePassword=request.getParameter("changePassword");
		boolean flag=userService.updatePassword(username, changePassword);
		if(flag) {
			User user=userService.getUser(username);
			UserDemo user2=new UserDemo();
			user2.setUserId(user.getUserId());
			user2.setUsername(user.getUsername());
			user2.setPassword(user.getPassword());
			user2.setImgUrl(user.getImgUrl());
			user2.setPhone(user.getPhone());
			user2.setRole(user.getRole());
			user2.setState(user.getState());
			Gson gson=new Gson();
			String JUser=gson.toJson(user2);
			writer.write(JUser);
		}else {
			writer.write("未修改成功，请稍后再试~");
		}
		
    }

  //获取用户取到的号码
    @RequestMapping("/getusernum")
    public void getUserNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	System.out.println("收到客户端请求");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		//获取传过来的userId
    	String userid=request.getParameter("userId");
    	int userId=Integer.valueOf(userid);
    	//获取User对象
    	User user=userService.getUserById(userId);
    	Set<Numberr> numberSet=user.getNumberrSet();
    	List<NumDemo> numberList=new ArrayList<NumDemo>();
    	for(Numberr number:numberSet) {
    		NumDemo numDemo=new NumDemo();
    		int num=numberrService.getNowNum(number.getShop().getShopId());
    		/*if(! num) {
    			numDemo.setNowNum(0);
    		}else {
    			numDemo.setNowNum(num);
    		}*/
    		numDemo.setNowNum(num);
    		numDemo.setMyNum(number.getNumberrId());
    		numDemo.setNumState(number.getState());
    		numDemo.setShopId(number.getShop().getShopId());
    		numDemo.setShopName(number.getShop().getShopName());
    		numDemo.setGetNumtime(number.getDate());
    		numberList.add(numDemo);
    	}
    	Gson gson=new Gson();
    	String JNumList=gson.toJson(numberList);
    	System.out.println(JNumList);
    	writer.write(JNumList);
    	
    }


    //根据传过来的userId和shopId来把要收藏的商店存到数据库中
    @RequestMapping("/collectshop")
    public void collectShop(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	System.out.println("收到客户端请求");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		//获取传过来的userId
    	String userid=request.getParameter("userId");
    	int userId=Integer.valueOf(userid);
    	//获取传过来的shopId
    	String shopid=request.getParameter("shopId");
    	int shopId=Integer.valueOf(shopid);
    	//获取User对象
    	User user=userService.getUserById(userId);
    	//获取shop对象
    	Shop shop=shopService.getShop(shopId);
    	//添加到收藏夹
    	boolean flag=userService.collectShop(userId, shopId);
    	if(flag){
    		writer.write("您已收藏成功~");
    	}else {
    		writer.write("收藏失败，请稍候再试~");
    	}
    }

  
    //根据传过来的数据取消收藏
    @RequestMapping("/cancelcollectshop")
    public void cancelCollectShop(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	System.out.println("收到客户端请求");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		//获取传过来的userId
    	String userid=request.getParameter("userId");
    	int userId=Integer.valueOf(userid);
    	//获取传过来的shopId
    	String shopid=request.getParameter("shopId");
    	int shopId=Integer.valueOf(shopid);
    	//获取User对象
    	User user=userService.getUserById(userId);
    	//获取shop对象
    	Shop shop=shopService.getShop(shopId);
    	//从收藏夹内删除店铺
    	boolean flag=userService.cancelCollectShop(userId, shopId);
    	if(flag){
    		writer.write("您已取消收藏~");
    	}else {
    		writer.write("取消收藏失败，请稍候再试~");
    	}
    }
    
    //判断该用户是否收藏某一商店
    @RequestMapping("/jugCollect")
    public void jugCollect(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	PrintWriter writer = response.getWriter();
    	//获取传过来的userId
    	String userid=request.getParameter("userId");
    	int userId=Integer.valueOf(userid);
    	//获取传过来的shopId
    	String shopid=request.getParameter("sId");
    	int shopId=Integer.valueOf(shopid);
    	//获取User对象
    	User user=userService.getUserById(userId);
    	Set<Shop> shopSet = user.getShopCollection().getShopList();
    	int i = 0 ;
    	for(Shop ss : shopSet) {
    		if(ss.getShopId() == shopId) {
    			i = 1;
    		}
    	}
    	System.out.println(i+"9999999999999");
    	writer.write(String.valueOf(i));
    }
    
    @RequestMapping("/shoplogin")
    //商家登录的业务逻辑
    public void shopLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	System.out.println("收到客户端请求");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		String shopUsername=request.getParameter("shopUsername");
		String shopPassword=request.getParameter("shopPassword");
		User user=userService.getUser(shopUsername);
		if(user == null) {
			writer.write("用户名或密码不正确");
		}else {
			if((user.getPassword()).equals(shopPassword) && user.getRole().equals("shop")){
				System.out.println("登录成功");
				Shop shop1=user.getShop();
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
			}else{
				writer.write("用户名或密码不正确");
			}
		}
    }
  //获取用户收藏夹数据
    @RequestMapping("/getcollection")
    public void getShopCollection(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	System.out.println("收到客户端请求");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		//获取当前时间
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String numTime = format.format(date);
    	//获取传过来的userId
    	String userid=request.getParameter("userId");
    	int userId=Integer.valueOf(userid);
    	//获取User对象
    	User user=userService.getUserById(userId);
    	Set<Shop> shopList=user.getShopCollection().getShopList();
    	List<ShopDemo> shopList1=new ArrayList<ShopDemo>();
    	for(Shop shop:shopList) {
    		ShopDemo shop1=new ShopDemo();
    		int num1 = numberrService.getLittle(shop.getShopId(), numTime);
			int num2 = numberrService.getMiddle(shop.getShopId(), numTime);
			int num3 = numberrService.getLarge(shop.getShopId(), numTime);
			int allnum = num1 + num2 + num3 ;
    		shop1.setLat(shop.getLat());
    		shop1.setLng(shop.getLng());
    		shop1.setShopdId(shop.getShopId());
    		shop1.setShopdName(shop.getShopName());
    		shop1.setShopimg(shop.getCover());
    		shop1.setShopIntroduce(shop.getShopIntroduce());
    		shop1.setState(shop.getState());
    		shop1.setWaitTime(shop.getWaitTime());  
    		shop1.setAllNum(String.valueOf(allnum));
    		shop1.setAvgCost(String.valueOf(shop.getAvgCost()));
    		shopList1.add(shop1);
    	}
    	Gson gson=new Gson();
    	String JShopList=gson.toJson(shopList1);
    	System.out.println(JShopList);
    	writer.write(JShopList);
    	
    }
  //删除用户的某个号码
    @RequestMapping("/deleteusernum")
    public void deleteUserNum(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	System.out.println("收到客户端请求");
response.setContentType("text/html;charset=utf-8");
response.setCharacterEncoding("utf-8");
request.setCharacterEncoding("utf-8");
PrintWriter writer = response.getWriter();
//获取传过来的userId
    	String userid=request.getParameter("userId");
    	int userId=Integer.valueOf(userid);
    	//获取传过来的numid
    	String numid=request.getParameter("numId");
    	int numberrId=Integer.valueOf(numid);
    	//获取User对象
    	User user=userService.getUserById(userId);
    	//查出要删除的number
    	boolean flag=numberrService.deleteNum(userId, numberrId);
    	if(flag){
    	
    	 User user1 = userService.getUserById(userId);
    	 Set<Numberr> numberSet1=user1.getNumberrSet();
    	 List<NumDemo> numberList2=new ArrayList<NumDemo>();
        	for(Numberr number:numberSet1) {
        	 NumDemo numDemo=new NumDemo();
        	 int num1=numberrService.getNowNum(number.getShop().getShopId());
        	 /*if(num1 == null) {
        	 numDemo.setNowNum(0);
        	 }else {
        	 numDemo.setNowNum(num1.getNumberrId());
        	 }*/
        	 numDemo.setNowNum(num1);
        	 numDemo.setMyNum(number.getNumberrId());
        	 numDemo.setNumState(number.getState());
        	 numDemo.setShopId(number.getShop().getShopId());
        	 numDemo.setShopName(number.getShop().getShopName());
        	 numDemo.setGetNumtime(number.getDate());
        	 numberList2.add(numDemo);
        	}
        	if(numberList2 == null) {
        	 writer.write("您当前无取号");
        	}else {
        	 Gson gson=new Gson();
            	String JNumList=gson.toJson(numberList2);
            	System.out.println(JNumList);
            	writer.write(JNumList);
        	}
        	
    	}else{
    	 writer.write("未删除成功,请稍候再试~");
    	}
    }
    

}
