package com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Num;
import com.entity.Numberr;
import com.entity.Numberst;
import com.google.gson.Gson;
import com.service.NumberrService;

@Controller
@RequestMapping("/numberr")
public class NumberrAction {
	@Autowired
	private NumberrService numberrService;

	// 获取每种桌型正在等待桌数
	@RequestMapping("/getNum")
	public void getNum(HttpServletResponse response, HttpServletRequest request) throws IOException {
		PrintWriter writer = response.getWriter();
		String shopid = request.getParameter("sId");
		int shopId = Integer.parseInt(shopid);
		//获取当前时间
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String numTime = format.format(date);
		Num n = new Num();

		int num1 = numberrService.getLittle(shopId, numTime);
		int num2 = numberrService.getMiddle(shopId, numTime);
		int num3 = numberrService.getLarge(shopId, numTime);
		n.setLittleNum(num1);
		n.setMiddleNum(num2);
		n.setLargeNum(num3);
		Gson gson = new Gson();
		String numm = gson.toJson(n);
		writer.println(numm);
	}

	// 判断某一用户该日是否在本商铺存在已取但属于未叫号的订单，若存在返回qName
	@RequestMapping("/judgeNum")
	public void judgeNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		// 获取服务器端传来数据
		String shopid = request.getParameter("sId");
		String userid = request.getParameter("userId");
		int shopId = Integer.parseInt(shopid);
		int userId = Integer.parseInt(userid);
		// 获取当前时间
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String numTime = format.format(date);
		// 判断该用户当天是否在该店铺拥有未叫号的号码
		// 查询出该用户在该商店所有“未叫”号码
		List<Numberr> nlist = numberrService.getNumList(shopId, userId);
		String qName = null;
		if (nlist.size() == 0) {
		} else {
			int res = 0;
			for (Numberr nb : nlist) {
				if (nb.getDate().equals(numTime)) {
					res = 1;
					qName = nb.getqName();
				}
			}
			if (res == 0) {

			} else {
				System.out.println(qName);
				writer.write(qName);
			}
		}
	}

	// 取某一店铺一小桌号码
	@RequestMapping("/getLittle")
	public void getLittle(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		PrintWriter writer = response.getWriter();
		// 获取当前时间
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String numTime = format.format(date);
		// 获取安卓端传来的数据
		String shopid = request.getParameter("sId");
		String userid = request.getParameter("userId");
		String phone = request.getParameter("phone");
		int shopId = Integer.parseInt(shopid);
		int userId = Integer.parseInt(userid);
		Numberr numberr = new Numberr();
		numberr.setqName("小桌S");
		numberr.setState("未叫");
		numberr.setUserphone(phone);
		numberr.setDate(numTime);
		boolean result = numberrService.rowNum(numberr, shopId, userId);
		if (result)
			System.out.println("成功取到小桌号");

	}

	// 取某一店铺一中桌号码
	@RequestMapping("/getMiddle")
	public void getMiddle(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		Numberr numberr = new Numberr();
		String shopid = request.getParameter("sId");
		String userid = request.getParameter("userId");
		String phone = request.getParameter("phone");
		int shopId = Integer.parseInt(shopid);
		int userId = Integer.parseInt(userid);
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String numTime = format.format(date);
		Date dat = format.parse(numTime);
		numberr.setqName("中桌M");
		numberr.setState("未叫");
		numberr.setUserphone(phone);
		numberr.setDate(numTime);
		boolean result = numberrService.rowNum(numberr, shopId, userId);
		if (result)
			System.out.println("成功取到中桌号");
	}

	// 取某一店铺一大桌号码
	@RequestMapping("/getLarge")
	public void getLarge(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		Numberr numberr = new Numberr();
		String shopid = request.getParameter("sId");
		String userid = request.getParameter("userId");
		String phone = request.getParameter("phone");
		int shopId = Integer.parseInt(shopid);
		int userId = Integer.parseInt(userid);
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String numTime = format.format(date);
		Date dat = format.parse(numTime);
		numberr.setqName("大桌B");
		numberr.setState("未叫");
		numberr.setUserphone(phone);
		numberr.setDate(numTime);
		boolean result = numberrService.rowNum(numberr, shopId, userId);
		if (result)
			System.out.println("成功取到大桌号");
	}

	// 取消排号
	@RequestMapping("/cancelNum")
	public void cancelLittle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		// 获取当前时间
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String currentTime = format.format(date);
		// 获取安卓端传来的数据
		String shopid = request.getParameter("sId");
		String userid = request.getParameter("userId");
		int shopId = Integer.parseInt(shopid);
		int userId = Integer.parseInt(userid);
		// 取消排号
		boolean result = numberrService.cancelNum(shopId, userId, currentTime);
		if (result)
			System.out.println("已成功取消排号");
	}

	// 获取当前排号队列
	@RequestMapping("/getcurrentlist")
	public void getcurrentlist(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws IOException {
		System.out.println("有post请求");
		httpServletResponse.setContentType("text/html;charset=utf-8");
		httpServletResponse.setCharacterEncoding("utf-8");
		httpServletResponse.setCharacterEncoding("utf-8");
		PrintWriter writer = httpServletResponse.getWriter();
		String shopid = httpServletRequest.getParameter("sId");
		int shopId = Integer.parseInt(shopid);
		// 获取当前时间
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String currentTime = format.format(date);
		
		List<Numberr> nrlist = new ArrayList<Numberr>();
		nrlist = numberrService.selectbyuserid(shopId,currentTime);
		List<Numberst> nstlist = new ArrayList<Numberst>();
		for (int i = 0; i < nrlist.size(); i++) {
			Numberst numberst = new Numberst();
			numberst.setUserid(nrlist.get(i).getUser().getUserId());
			numberst.setNumberrId(nrlist.get(i).getNumberrId());
			numberst.setDate(nrlist.get(i).getDate());
			numberst.setqName(nrlist.get(i).getqName());
			numberst.setShopid(nrlist.get(i).getShop().getShopId());
			numberst.setState(nrlist.get(i).getState());
			numberst.setUserphone(nrlist.get(i).getUserphone());
			nstlist.add(numberst);
		}
		Gson gson = new Gson();
		String jnrlist = gson.toJson(nstlist);
		writer.print(jnrlist);
	}

	@RequestMapping("/big")
	public void bigchange(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws IOException {
		System.out.println("有big请求");
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
		String reString = buff.toString();
		// System.out.println(buff.toString());
		int x = Integer.valueOf(reString);
		if (numberrService.changestatebyId(x)) {
			writer.println("success");
		}
		writer.println("error");
	}
}
