package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.entity.Address;
import com.service.AddressService;


@Controller
@RequestMapping("/address")
public class AddressAction {

	@Autowired
	private AddressService addressService;
	
		@RequestMapping("/addAddress")
		public void addAddress(Model model, HttpServletRequest request) {
			Address a = new Address();
			a.setAddress("河北省");
			a.setLatitude(2.22);
			a.setLongitude(2.22);
			addressService.add(a);

		}

}
