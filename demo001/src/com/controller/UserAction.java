package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Numberr;
import com.entity.User;
import com.service.UserService;

@Controller
@RequestMapping("/user")
public class UserAction {
	@Autowired
	private UserService userService;
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
	    			 writer.write("登录成功");
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
  
}
