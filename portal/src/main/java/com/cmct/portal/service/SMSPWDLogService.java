package com.cmct.portal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.SMSPWDLogDao;
import com.cmct.portal.po.SMSPWDLogPO;


@Service
public class SMSPWDLogService {
	@Autowired
	private SMSPWDLogDao sMSPWDLogDao;
	
	
	public void save(SMSPWDLogPO entity){
		sMSPWDLogDao.save(entity);
	}
	
	
	public SMSPWDLogPO findNewOne(String username){
		//根据id排序倒序
		List<String[]> orderBies = new ArrayList<String[]>();
		String[] orderBy = new String[] { "id", "desc" };//根据id
		orderBies.add(orderBy);	
		Map<String,Object> propertiesMap =new HashMap<String,Object>();
		propertiesMap.put("username",username);
		List<SMSPWDLogPO> list=sMSPWDLogDao.find(propertiesMap, orderBies);
		if(null != list  && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}