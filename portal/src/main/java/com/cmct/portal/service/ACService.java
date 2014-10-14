package com.cmct.portal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmct.portal.dao.ACDao;
import com.cmct.portal.po.ACPO;

@Service
public class ACService  {
	@Autowired
	private ACDao aCDao;
	
	public List<ACPO> getAll(){
		return aCDao.findAll();
		
	}
	
	public void saveAC(ACPO entity){
		aCDao.save(entity);
	} 
	
	public void updateAC(ACPO entity){
		aCDao.update(entity);
	} 
	
	public Integer countRow() {
		String sql=" from ACPO ";
		return aCDao.getCount(sql);

	} 
	
	public ACPO findOne(Integer id) {
		return aCDao.get(id);

	} 
	
	public List<ACPO> findPages(Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<ACPO> list=aCDao.find(propertiesMap, start, limit);
		return list;
	}

	public List<ACPO> findPages_sql(String sql,Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<ACPO> list=aCDao.pageQuery(sql, propertiesMap, start, limit);
		return list;
	}	
	
	public ACPO findACip(String ip){
		Map<String,Object> propertiesMap=new HashMap<String, Object>();
		propertiesMap.put("ip", ip);
		propertiesMap.put("isdelete", "N");
		List<ACPO> list =aCDao.find(propertiesMap, 0, 5);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public ACPO findACname(String acName){
		Map<String,Object> propertiesMap=new HashMap<String, Object>();
		propertiesMap.put("ac_name", acName);
		propertiesMap.put("isdelete", "N");
		List<ACPO> list =aCDao.find(propertiesMap, 0, 5);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	
	
	public Integer getTotalCount_where(String sql, Map<String,Object> propertiesMap) {
		return aCDao.getTotalCount(sql, propertiesMap);
	} 
	
}
