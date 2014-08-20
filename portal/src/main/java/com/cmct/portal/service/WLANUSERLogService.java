package com.cmct.portal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.WLANUSERLogDao;
import com.cmct.portal.po.WLANUSERLogPO;


@Service
public class WLANUSERLogService {
	@Autowired
	private WLANUSERLogDao wUSERLogDao;
	
	public void save(WLANUSERLogPO entity){
		wUSERLogDao.save(entity);
	}
	
	public void update(WLANUSERLogPO entity){
		wUSERLogDao.update(entity);
	}
	
	public List<WLANUSERLogPO> pageQuery(String sql,Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<WLANUSERLogPO> list=wUSERLogDao.pageQuery(sql, propertiesMap, start, limit);
		return list;
	}
	
	public Integer getTotalCount_where(String sql, Map<String,Object> propertiesMap) {
		return wUSERLogDao.getTotalCount(sql, propertiesMap);
	} 
	
	
	
	public WLANUSERLogPO findNewOne(String username,String userip){
		//根据id排序倒序
		List<String[]> orderBies = new ArrayList<String[]>();
		String[] orderBy = new String[] { "id", "desc" };//根据id
		orderBies.add(orderBy);	
		Map<String,Object> propertiesMap =new HashMap<String,Object>();
		propertiesMap.put("username",username);
		propertiesMap.put("userip",userip);
		List<WLANUSERLogPO> list=wUSERLogDao.find(propertiesMap, orderBies);
		if(null != list  && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}