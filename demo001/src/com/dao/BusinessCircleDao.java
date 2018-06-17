package com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.BusinessCircle;

@Repository
public class BusinessCircleDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	public List<BusinessCircle> selectall(List<BusinessCircle> bclist){
		Session session = sessionFactory.getCurrentSession();
		String hql = "from BusinessCircle";
		Query query = session.createQuery(hql);
		bclist = query.list();
		return bclist;
	}
	
}
