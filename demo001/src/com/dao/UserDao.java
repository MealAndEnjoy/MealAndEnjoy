package com.dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
