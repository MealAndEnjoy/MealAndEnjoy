package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.BusinessCircleDao;
import com.entity.BusinessCircle;

@Service
@Transactional
public class BusinessCircleService {
	
	@Autowired
	private BusinessCircleDao businessCircleDao;
	
	public List<BusinessCircle> selectall(List<BusinessCircle> bclist){
		bclist = businessCircleDao.selectall(bclist);
		return bclist;
	}
}
