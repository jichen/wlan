package com.cmct.portal.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmct.portal.dao.OperateLogDao;
import com.cmct.portal.po.OperateLogPO;


@Service
public class OperateLogService {
	@Autowired
	private OperateLogDao operateLogDao;
	
	public void save(OperateLogPO entity){
		operateLogDao.save(entity);
	} 
	
	public List<OperateLogPO> findPages(Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<OperateLogPO> list=operateLogDao.find(propertiesMap, start, limit);
		return list;
	}

	public List<OperateLogPO> pageQuery(String sql,Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<OperateLogPO> list=operateLogDao.pageQuery(sql, propertiesMap, start, limit);
		return list;
	}
	
	public Integer getTotalCount_where(String sql, Map<String,Object> propertiesMap) {
		return operateLogDao.getTotalCount(sql, propertiesMap);
	} 
}