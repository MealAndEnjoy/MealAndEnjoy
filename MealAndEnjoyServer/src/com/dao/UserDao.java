package com.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.Shop;
import com.entity.ShopCollection;
import com.entity.User;
@Repository
public class UserDao {
	@Autowired
	private SessionFactory sessionFactory;
	//添加用户
    public boolean insert(User user) {
 	   Session session = sessionFactory.getCurrentSession();
 	   session.save(user);
 	   return true;
    }
    //查找全部用户名
    public List<String> getAllusername(){
    	Session session = sessionFactory.getCurrentSession();
    	Query query = session.createQuery("select u.username from User u");
    	List<String> nameList = query.list();
    	return nameList;
    }
	//根据用户名查找某一用户是否存在
	public boolean isUser(String userName){
		Session session = sessionFactory.getCurrentSession();
 	   	Query query = session.createQuery("from User u where u.username=?");
 	   	query.setParameter(0,userName);
 	   	User user = (User) query.uniqueResult();
 	   	if(user != null) {
 	   		return true;
 	   	}else {
 	   		return false;
 	   	}
	}
	//根据用户名查找用户
	public User getUser(String userName) {
		Session session = sessionFactory.getCurrentSession();
 	   	Query query = session.createQuery("from User u where u.username=?");
 	   	query.setParameter(0,userName);
        User user =  (User) query.uniqueResult();
        return user;
	}
	//根据用户id查找用户
	public User getUserById(int userId) {
		Session session = sessionFactory.getCurrentSession();
 	   	Query query = session.createQuery("from User u where u.userId=?");
 	   	query.setParameter(0,userId);
        User user =  (User) query.uniqueResult();
        return user;
	}
	//根据用户名更新数据库
	public boolean updateByUsername(String priUsername,String changeUsername) {
		Session session = sessionFactory.getCurrentSession();
		//先根据之前的用户名查出这个用户
		User user=getUser(priUsername);
		user.setUsername(changeUsername);
		try {
			session.update(user);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
			}
	//根据传过来的用户名查找到该用户，并修改用户电话号码，更新数据库
	public boolean updatePhone(String username,String changePhone){
		Session session = sessionFactory.getCurrentSession();
		//先根据用户名查出这个用户
		User user=getUser(username);
		//改变这个user的phone属性值
		user.setPhone(changePhone);
		//更新数据
		try {
			session.update(user);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//根据传过来的用户名查找到用户，并修改用户的密码
	public boolean updatePassword(String username,String changePassword){
		Session session = sessionFactory.getCurrentSession();
		//先根据用户名查出这个用户
		User user=getUser(username);
		//改变这个user的password属性值
		user.setPassword(changePassword);
//		Query query = session.createQuery("update User set password=? where username=?");
//		query.setParameter(0, changePassword);
//		query.setParameter(1, username);
//		int ret  = query.executeUpdate();
//		return true;
		//更新数据
		try {
			session.update(user);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//将shop存放到user的collection里面,更新数据库
	public boolean collectShop(int userId,int shopId){
		Session session = sessionFactory.getCurrentSession();
		try {
			User user=session.get(User.class, userId);
			Shop shop=session.get(Shop.class, shopId);
			if(user.getShopCollection() == null) {
				ShopCollection shopCollection=new ShopCollection();
				Set<Shop> shopList1=new HashSet<Shop>();
				shopList1.add(shop);
				shopCollection.setShopList(shopList1);
				user.setShopCollection(shopCollection);
				shopCollection.setUser(user);
				session.save(user);
				session.save(shopCollection);
			}else {
				Set<Shop> shopList=user.getShopCollection().getShopList();
				shopList.add(shop);
				ShopCollection shopCollection=user.getShopCollection();
				shopCollection.setUser(user);
				session.save(user);
				session.save(shopCollection);
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
			
			
	}
	//将shop从user的collection里面移出,更新数据库
	public boolean cancelCollectShop(int userId,int shopId){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Shop s where s.shopId=?");
		User user=getUserById(userId);
		query.setParameter(0, shopId);
		Shop shop = (Shop)query.uniqueResult();
		Set<Shop> shopList=user.getShopCollection().getShopList();
		shopList.remove(shop);
		ShopCollection shopCollection=user.getShopCollection();
		Set<ShopCollection> shopCollectionSet=shop.getShopCollectionSet();
		shopCollectionSet.remove(shopCollection);
		shop.setShopCollectionSet(shopCollectionSet);
		try {
			session.update(user);
			session.update(shop);		
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
	}


}
