package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.User;
import com.service.UserService;

@Controller
@RequestMapping("/user")
public class UserAction{
	@Autowired
    private UserService userService;
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

}
