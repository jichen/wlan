package com.cmct.radius.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cmct.radius.dao.RadPostAuthDao;
import com.cmct.radius.po.RadPostAuthPO;

@Service
public class RadPostAuthService {
	@Autowired
    private RadPostAuthDao radPostAuthDao;
	
	public List<RadPostAuthPO> pageQuery(String sql, Map<String, Object> propertiesMap, int start, int limit){
		List<RadPostAuthPO> list=radPostAuthDao.pageQuery(sql, propertiesMap, start, limit);
		return list;
	}
	
	
	public Integer getTotalCount_where(String sql, Map propertiesMap) {
		return radPostAuthDao.getTotalCount(sql, propertiesMap);
	} 
}
