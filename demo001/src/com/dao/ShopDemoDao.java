package com.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.ShopDemo;


@Repository
public class ShopDemoDao {
	
	@Autowired
	private SessionFactory sessionFactory;
     //测试连接
	public ShopDemo printInfo(ShopDemo shopDemo) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "select d from ShopDemo d where d.shopdId=?0";
		Query query = session.createQuery(hql);
		query.setParameter("0", 1);
		shopDemo = (ShopDemo)query.uniqueResult();
		return shopDemo;
	}
	//获取商店列表
	public List<ShopDemo> selectall(List<ShopDemo> shoplist) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from ShopDemo";
		Query query = session.createQuery(hql);
		shoplist = query.list();
		return shoplist;
	}
	//根据类别查找店铺
	public List<ShopDemo> selectShop(String type){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ShopDemo where type=?");
		query.setParameter(0, type);
		List<ShopDemo> shoplist = query.list();
		return shoplist;
	}

}
