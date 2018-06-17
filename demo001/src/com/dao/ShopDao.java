package com.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.Shop;
import com.entity.ShopDemo;

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
	
//	//获取商店列表
//		public List<ShopDemo> selectall(List<ShopDemo> shoplist) {
//			Session session = sessionFactory.getCurrentSession();
//			String hql = "from ShopDemo";
//			Query query = session.createQuery(hql);
//			shoplist = query.list();
//			return shoplist;
//		}
	
	//根据类别查找店铺
		public List<Shop> selectTypeShop(String type){
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from Shop s where s.type.typeName=?");
			query.setParameter(0, type);
			List<Shop> shoplist = query.list();
			return shoplist;
		}
		
	//获取所有店铺
	public List<Shop> selectall(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Shop");
		List<Shop> shoplist = query.list();
		return shoplist ;
	}
	
	//获取所有属于美食的店铺
	public List<Shop> selectFood(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Shop where type.typeId=1 or type.typeId=2 or type.typeId=3 or type.typeId=4 or type.typeId=5 or type.typeId=6 or type.typeId=7 or type.typeId=8");
		List<Shop> foodlist = query.list();
		return foodlist;
	}
	
	//获取所有属于娱乐的店铺
		public List<Shop> selectEntermain(){
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from Shop where type.typeId=9 or type.typeId=10 or type.typeId=11");
			List<Shop> enlist = query.list();
			return enlist;
		}
} 
