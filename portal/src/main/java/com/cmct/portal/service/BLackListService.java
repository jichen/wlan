package com.cmct.portal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmct.portal.dao.BlackListDao;
import com.cmct.portal.po.BlacklistPO;

@Service
public class BLackListService  {
	@Autowired
	private BlackListDao blackListDao;
	
	public List<BlacklistPO> getAll(){
		return blackListDao.findAll();
		
	}
	
	public void saveBL(BlacklistPO entity){
		blackListDao.save(entity);
	} 
	

	public void deleteBL(BlacklistPO entity){
		blackListDao.delete(entity);
	} 
	
	
	
	public Boolean findOneofUsername(String username){
		Map<String,Object> propertiesMap = new HashMap<String,Object>();
		propertiesMap.put("username",username);
		List<BlacklistPO> list=blackListDao.find(propertiesMap);
		if(null != list  && list.size()>0){
			return true;
		}else{
			return false;	
		}
	}

}
