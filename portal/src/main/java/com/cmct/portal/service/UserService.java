package com.cmct.portal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmct.portal.dao.UserDao;
import com.cmct.portal.po.UserPO;


@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	
	public UserPO ByLogin(Map<String,Object> propertiesMap){
		//用户状态
		propertiesMap.put("status","N");
		List<UserPO> list=userDao.find(propertiesMap);
		if(null != list  && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}		
	}
	
	
	
	public List<UserPO> getAll(){
		return userDao.findAll();
		
	} 

	public void saveUser(UserPO entity){
		userDao.save(entity);
	} 
	
	
	public void update(UserPO user){
		userDao.update(user);
	}

	public void delete(Integer id){
		userDao.delete(id);
	}
	
	
	public UserPO findOne(String username){
		Map<String,Object> propertiesMap = new HashMap<String,Object>();
		propertiesMap.put("username",username);
		//使用中的
		propertiesMap.put("status","N");
		List<UserPO> list=userDao.find(propertiesMap);
		if(null != list  && list.size()>0){
			return list.get(0);
		}else{
			return null;	
		}
	}
	
	public UserPO onlyOne_Username(String username){
		Map<String,Object> propertiesMap = new HashMap<String,Object>();
		propertiesMap.put("username",username);
		propertiesMap.put("status","N");
		List<UserPO> list=userDao.find(propertiesMap);
		if(null != list  && list.size()>0){
			return list.get(0);
		}else{
			return null;	
		}	
	}
	
	
	public UserPO findOne(Integer userid){
		Map<String,Object> propertiesMap = new HashMap<String,Object>();
		propertiesMap.put("userid",userid);
		List<UserPO> list=userDao.find(propertiesMap);
		if(null != list  && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	

	
	public List<UserPO> findPages(Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<UserPO> list=userDao.find(propertiesMap, start, limit);
		return list;
	}
	
	public List<UserPO> findPages_sql(String sql,Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<UserPO> list=userDao.pageQuery(sql, propertiesMap, start, limit);
		return list;
	}
	
	
	public Integer getTotalCount_where(String sql, Map<String,Object> propertiesMap) {
		return userDao.getTotalCount(sql, propertiesMap);
	} 

}
