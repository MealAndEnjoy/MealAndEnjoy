package com.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.NumberrDao;

@Service
@Transactional
public class NumberrService {
	@Autowired
	private NumberrDao numberrDao;
}
