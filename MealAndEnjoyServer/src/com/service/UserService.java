package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserDao;
import com.entity.Shop;
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
	//根据用户id查找用户
	public User getUserById(int userId) {
		return userDao.getUserById(userId);
	}
	//根据用户名更新数据库
	public boolean updateByUsername(String priUsername,String changeUsername){
		boolean flag=userDao.updateByUsername(priUsername, changeUsername);
		return flag;
	}
	//根据传输的用户名修改该用户的手机号码
	public boolean updatephone(String username,String changePhone){
		boolean flag=userDao.updatePhone(username, changePhone);
		return flag;
	}
	//根据传输的数据修改用户密码
	public boolean updatePassword(String username,String changePassword){
		boolean flag=userDao.updatePassword(username, changePassword);
		return flag;
	}
	//将shop存放到user的collection里面,更新数据库
	public boolean collectShop(int userId,int shopId){
		boolean flag=userDao.collectShop(userId, shopId);	
		return flag;
	}
	//将shop从user的collection里面移出,更新数据库
	public boolean cancelCollectShop(int userId,int shopId){
		boolean flag=userDao.cancelCollectShop(userId, shopId);
		return flag;
	}

}
