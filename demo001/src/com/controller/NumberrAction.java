package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.service.NumberrService;


@Controller
@RequestMapping("/numberr")
public class NumberrAction {
	@Autowired
	private NumberrService numberrService;
}
