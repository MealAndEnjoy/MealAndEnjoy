package com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.BusinessCircle;
import com.google.gson.Gson;
import com.service.BusinessCircleService;

@Controller
@RequestMapping("/BusinessCircle")
public class BusinessCircleAction {
	
	@Autowired
	private BusinessCircleService businessCircleService;
	
	@RequestMapping("/bclist")
	public void getbclist(Model model,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {
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
		List<BusinessCircle> bclist = new ArrayList<BusinessCircle>();
		bclist = businessCircleService.selectall(bclist);
		Gson gson = new Gson();
		String jbclist = gson.toJson(bclist);
		System.out.println(jbclist);
		writer.println(jbclist);
	}

}
