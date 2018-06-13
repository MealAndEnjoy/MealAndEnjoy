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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.ShopDemo;
import com.google.gson.Gson;
import com.service.ShopDemoService;

@Controller
@RequestMapping("/ShopDemo")
public class ShopDemoAction {
	
	@Autowired
	private ShopDemoService shopDemoService;
	//测试连接方法
	@RequestMapping("/printInfo")
	public void printInfo(Model model,HttpServletRequest httpServletRequest) {
		ShopDemo shopDemo = new ShopDemo();
		Gson gson = new Gson();
		shopDemo = shopDemoService.printInfo(shopDemo);
		String jString = gson.toJson(shopDemo);
		System.out.println(jString);
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
		List<ShopDemo> shoplist = new ArrayList<ShopDemo>();
		shoplist = shopDemoService.selectall(shoplist);
		Gson gson = new Gson();
		String jshoplist = gson.toJson(shoplist);
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
		List<ShopDemo> shoplist = new ArrayList<ShopDemo>();
		shoplist = shopDemoService.getDelishops("美食");
		Gson gson = new Gson();
		String jshoplist = gson.toJson(shoplist);
		System.out.println(jshoplist);
		writer.println(jshoplist);
	}
	//获取娱乐页商店列表
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
			List<ShopDemo> shoplist = new ArrayList<ShopDemo>();
			shoplist = shopDemoService.getDelishops("娱乐");
			Gson gson = new Gson();
			String jshoplist = gson.toJson(shoplist);
			System.out.println(jshoplist);
			writer.println(jshoplist);
		}
	

}
