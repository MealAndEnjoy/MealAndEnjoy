package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserDao;
import com.entity.User;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserDao userDao;
	
	 //注册新用户
    public boolean register(User user) {
   	 return userDao.insert(user);
    }
    
    //通过获取到的全部用户名字符串数组判断某用户名是否已存在
    public boolean judgeName(String username) {
   	 List<String> nameList = userDao.getAllusername();
   	 if(nameList.contains(username)) {
   		 return true;
   	 }else {
   		 return false;
   	   }
    }
    
	//根据用户名查找某一用户是否存在
	public boolean isUser(String userName){
		boolean flag=userDao.isUser(userName);
		return flag;
	}
	
	//根据用户名查找用户
	public User getUser(String userName) {
		User user=userDao.getUser(userName);
		return user;
	}
	
}
