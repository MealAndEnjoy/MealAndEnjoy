package com.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.Address;
@Repository
public class AddressDao {

	@Autowired
	private SessionFactory sessionFactory;
	public void add(Address a) {
		Session session = sessionFactory.getCurrentSession();
		session.save(a);
		session.clear();	
	}

}
