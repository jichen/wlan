package com.cmct.portal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmct.portal.dao.APDao;
import com.cmct.portal.po.APPO;


@Service
public class APService  {
	@Autowired
	private APDao aPDao;
	
	public List<APPO> getAll(){
		return aPDao.findAll();
		
	} 
	
	public void saveAP(APPO entity){
		aPDao.save(entity);
	} 
	
	public void updateAP(APPO entity){
		aPDao.update(entity);
	} 


	public Integer countRow(String sql) {
		if(sql==null || sql==""){
			sql=" from APPO ";
		}
		return aPDao.getCount(sql);

	} 
	
	
	public APPO findOne(Integer id) {
		return aPDao.get(id);

	}
	

	public List<APPO> findPages_sql(String sql,Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<APPO> list=aPDao.pageQuery(sql, propertiesMap, start, limit);
		return list;
	}	
	
	
	public List<APPO> findPages(Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<APPO> list=aPDao.find(propertiesMap, start, limit);
		return list;
	}
	
	public APPO findLoginAp1(String apMac){
		Map<String,Object> propertiesMap=new HashMap<String, Object>();
		propertiesMap.put("mac", apMac);
		propertiesMap.put("isdelete", 'N');
		List<APPO> list =aPDao.find(propertiesMap, 0, 10);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public APPO findLoginAp(String apMac){
		Map<String,Object> propertiesMap=new HashMap<String, Object>();
		propertiesMap.put("mac", apMac);
		List<APPO> list =aPDao.find(propertiesMap, 0, 10);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public APPO findAPname(String apName){
		Map<String,Object> propertiesMap=new HashMap<String, Object>();
		propertiesMap.put("ap_name", apName);
		propertiesMap.put("isdelete", "N");
		List<APPO> list =aPDao.find(propertiesMap, 0, 10);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	
	public Integer getTotalCount_where(String sql, Map<String,Object> propertiesMap) {
		return aPDao.getTotalCount(sql, propertiesMap);
	} 

	
	
}
