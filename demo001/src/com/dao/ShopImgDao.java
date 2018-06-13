package com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.ShopImg;

@Repository
public class ShopImgDao {
	@Autowired
	private SessionFactory sessionFactory;
	//根据店铺ID查找图片
	public List<ShopImg> getById(int shopId){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ShopImg si where si.shop.shopId=?");
		query.setParameter(0,shopId);
		List<ShopImg> sList = query.list();
		return sList;
	}
}
