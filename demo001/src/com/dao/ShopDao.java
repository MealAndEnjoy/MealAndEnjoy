package com.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.Shop;

@Repository
public class ShopDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	//根据店铺ID查找店铺
	public Shop getShop(int shopId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Shop s where s.shopId=?");
		query.setParameter(0, shopId);
		Shop shop = (Shop)query.uniqueResult();
		return shop;
	}
}
