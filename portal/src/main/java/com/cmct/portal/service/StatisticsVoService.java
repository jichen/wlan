package com.cmct.portal.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.StatisticsVoDao;

@Service
public class StatisticsVoService {
	@Autowired
	private StatisticsVoDao statisticsDao;
	
	
	public List<Object> findALL(){
		return statisticsDao.findAll();
	}
	
	public List<Object> find_ALL(String sql,  int start, int limit){
		return statisticsDao.pageQuery(sql,  start, limit);
	}
	
	public List<Object> CountWlanList(String sql, Map propertiesMap,int start, int limit){
		return statisticsDao.pageQuery(sql, propertiesMap, start, limit);
	}
	
	public Integer getTotalCount_where(String sql, Map propertiesMap) {
		return statisticsDao.getTotalCount(sql, propertiesMap);
	}
}