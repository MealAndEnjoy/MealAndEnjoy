package com.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.Numberr;
import com.entity.Shop;
import com.entity.User;

@Repository
public class NumberrDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	//查询某一商店小桌排号数量
	public int getLittle(int shopId,String currentTime) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Numberr where shop.shopId=? and qName=? and state=? and date=?");
		query.setParameter(0, shopId);
		query.setParameter(1, "小桌S");
		query.setParameter(2, "未叫");
		query.setParameter(3, currentTime);
		return query.list().size();
	}
//查询某一商店中桌排号数量
	public int getMiddle(int shopId,String currentTime) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Numberr where shop.shopId=? and qName=? and state=? and date=?");
		query.setParameter(0, shopId);
		query.setParameter(1, "中桌M");
		query.setParameter(2, "未叫");
		query.setParameter(3, currentTime);
		return query.list().size();
	}
//查询某一商店大桌排号数量
	public int getLarge(int shopId,String currentTime) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Numberr where shop.shopId=? and qName=? and state=? and date=?");
		query.setParameter(0, shopId);
		query.setParameter(1, "大桌B");
		query.setParameter(2, "未叫");
		query.setParameter(3, currentTime);
		return query.list().size();
	}
	//取某一商店排号
	public boolean rowNum(Numberr numberr,int shopId,int userId) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, new Integer(userId));
		Shop shop = session.get(Shop.class,new Integer(shopId));
		numberr.setShop(shop);
		numberr.setUser(user);
		session.save(numberr);
		return true;
	}
	//查询某一用户在某一商店的取号信息
	public List<Numberr> getNumList(int shopId,int userId){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Numberr where user.userId=? and shop.shopId=? and state=?");
		query.setParameter(0, userId);
		query.setParameter(1, shopId);
		query.setParameter(2, "未叫");
		List<Numberr> numl = query.list();
		return numl ;
	}
    //用户取消在某一商店的当天排号
    public boolean cancelNum(int shopId,int userId,String currentTime) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete from Numberr where user.userId=? and shop.shopId=? and date=? and state=?");
		query.setParameter(0, userId);
		query.setParameter(1, shopId);
		query.setParameter(2, currentTime);
		query.setParameter(3, "未叫");
		query.executeUpdate();
		return true;
	}
			
	public List<Numberr> selectbyuserid(int userid,String date){
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Numberr nr where nr.shop.shopId=?0 and nr.date=?1";
		Query query = session.createQuery(hql);
		query.setParameter("0", userid);
		query.setParameter("1", date);
		List<Numberr> nrlist = query.list();
		return nrlist;
	}
			
	public Numberr selectbydate(String riqi) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Numberr nr where nr.date=?0";
		Query query = session.createQuery(hql);
		query.setParameter("0", riqi);
		Numberr nrd = (Numberr)query.uniqueResult();
		return nrd;
	}
	//获得当天排号
	public List<Numberr> getcurrentqueue(String date, int shopid){
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Numberr nr where nr.shop.shopId=?0 and nr.date=?1";
		Query query = session.createQuery(hql);
		query.setParameter("0",shopid);
		query.setParameter("1", date);
		List<Numberr> nrlist = query.list();
		return nrlist;			}
	//修改某一号码状态
	public boolean changestatebyId(int id) {
		Session session = sessionFactory.getCurrentSession();
		try {
				Numberr numberr = new Numberr();
				numberr = session.get(Numberr.class, id);
				numberr.setState("已叫");
				session.save(numberr);
				session.flush();
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
		}
				return true;
		}
	//得到当前叫号
		public int getNowNum(int shopId){
			Session session = sessionFactory.getCurrentSession();
	 	   	Query query = session.createQuery("select max(n.numberrId) from Numberr n where n.state=? and n.shop.shopId=?");
	 	    query.setParameter(0, "已叫");
	 	   	query.setParameter(1, shopId);
	 	    int num;
	 	   	if(query.uniqueResult() == null) {
	 	   		num=0;
	 	   	}else {
	 	   		num=(int)query.uniqueResult();
	 	   	}
	 	  
	        return num;

		}

	//根据numberrId查询出numberr对象
	public Numberr getNumberById(int numberrId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Numberr n where n.numberrId=?");
		query.setParameter(0, numberrId);
		Numberr num=(Numberr)query.uniqueResult();
		return num;
	}
	//根据user,num移除num
	public boolean deleteNum(User user,Numberr num){
		Session session = sessionFactory.getCurrentSession();
		Set<Numberr> numberSet=user.getNumberrSet();
		numberSet.remove(num);
		try {
			session.update(user);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
				
	}
	//根据userId,num移除num
		public boolean deleteNum(int userId,int numId){
			Session session = sessionFactory.getCurrentSession();
			User user=session.get(User.class, userId);
			Numberr num=session.get(Numberr.class, numId);
			Set<Numberr> numberSet=user.getNumberrSet();
			numberSet.remove(num);
			Query query = session.createQuery("delete Numberr n where n.numberrId=?");
			query.setParameter(0, numId);
			try {
				session.save(user);
				query.executeUpdate();
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
					
		}


}
