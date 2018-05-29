package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
     //判断某用户名是否已存在
     public boolean judgeName(String username) {
    	 List<String> nameList = userDao.getAllusername();
    	 if(nameList.contains(username)) {
    		 return true;
    	 }else {
    		 return false;
    	 }
     }
}
