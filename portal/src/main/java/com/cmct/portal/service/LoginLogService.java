package com.cmct.portal.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmct.portal.dao.LoginLogDao;
import com.cmct.portal.po.LoginLogPO;



@Service
public class LoginLogService {
	@Autowired
	private LoginLogDao loginLogDao;
	
	public void save(LoginLogPO entity){
		loginLogDao.save(entity);
	} 
	
	
	public List<LoginLogPO> findPages(Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<LoginLogPO> list=loginLogDao.find(propertiesMap, start, limit);
		return list;
	}
	
	public List<LoginLogPO> pageQuery(String sql,Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<LoginLogPO> list=loginLogDao.pageQuery(sql, propertiesMap, start, limit);
		return list;
	}
	
	
	public Integer getTotalCount_where(String sql, Map<String,Object> propertiesMap) {
		return loginLogDao.getTotalCount(sql, propertiesMap);
	} 
	
}
