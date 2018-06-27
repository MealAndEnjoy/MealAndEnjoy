package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.NumberrDao;
import com.entity.Numberr;
import com.entity.User;

@Service
@Transactional
public class NumberrService {
	@Autowired
	private NumberrDao numberrDao;
	
	//查询某一商店小桌排号数量
		public int getLittle(int shopId,String currentTime) {
			return numberrDao.getLittle(shopId,currentTime);
		}
		//查询某一商店中桌排号数量
			public int getMiddle(int shopId,String currentTime) {
				return numberrDao.getMiddle(shopId,currentTime);
			}
		//查询某一商店大桌排号数量
			public int getLarge(int shopId,String currentTime) {
				return numberrDao.getLarge(shopId,currentTime);
			}
	//取某一商店排号
	public boolean rowNum(Numberr numberr,int shopId,int userId) {
		return numberrDao.rowNum(numberr, shopId, userId);
	}
	//查询某一用户在某一商店的取号信息
	public List<Numberr> getNumList(int shopId,int userId){
		return numberrDao.getNumList(shopId, userId);
	}
	//用户取消在某一商店的当天排号
	public boolean cancelNum(int shopId,int userId,String currentTime) {
		return  numberrDao.cancelNum(shopId, userId, currentTime);
	}
	public List<Numberr> selectbyuserid(int userid,String date){
		List<Numberr> nrList = numberrDao.selectbyuserid(userid,date);
		return nrList;
	}
	//
	public Numberr selectbydate(String riqi) {
		Numberr nrd = numberrDao.selectbydate(riqi);
		return nrd;
	}
	public boolean changestatebyId(int id) {
		boolean result = numberrDao.changestatebyId(id);
		return result;
	}
	//获取某一商店当天排号队列
	public List<Numberr> getcurrentqueue(String date, int shopid){
		return numberrDao.getcurrentqueue(date, shopid);
	}
	//得到当前叫号
	public int getNowNum(int shopId){
		int num=numberrDao.getNowNum(shopId);
		return num;
	}
	//根据numberrId查询出numberr对象
	public Numberr getNumberById(int numberrId) {
		Numberr num=numberrDao.getNumberById(numberrId);
		return num;
	}
	//根据user,num移除num
	public boolean deleteNum(User user,Numberr num){
		boolean flag=numberrDao.deleteNum(user, num);
		return flag;
	}
	//根据user,num移除num
		public boolean deleteNum(int userId,int numId){
			boolean flag=numberrDao.deleteNum(userId, numId);
			return flag;
		}


}
