package com.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AddressDao;
import com.entity.Address;

@Service
@Transactional
public class AddressService {

	@Autowired
	private AddressDao addressDao;

	public void add(Address a) {
		// TODO Auto-generated method stub
		addressDao.add(a);
	}
	
}
