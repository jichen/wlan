package com.cmct.portal.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmct.portal.dao.PhoneAndMacDao;
import com.cmct.portal.po.PhoneAndMac;

@Service("phoneAndMacService")
public class PhoneAndMacService  {
	@Autowired
	private PhoneAndMacDao phoneMacDao;
	
	public List<PhoneAndMac> getAll(){
		return phoneMacDao.findAll();
		
	}
	
	public void savePM(PhoneAndMac entity){
		phoneMacDao.save(entity);
	} 
	
	public void updatePM(PhoneAndMac entity){
		phoneMacDao.update(entity);
	} 
	
	public Integer countRow() {
		String sql=" from PhoneAndMac ";
		return phoneMacDao.getCount(sql);

	} 
	
	public PhoneAndMac findOne(Integer id) {
		return phoneMacDao.get(id);

	} 
	
	public PhoneAndMac find(Map<String, Object> propertiesMap) {
		List<PhoneAndMac> list=phoneMacDao.find(propertiesMap);
		if(list!=null && list.size()>0){
			PhoneAndMac pm=new PhoneAndMac();
			pm=list.get(0);
			return pm;
		}
		return null;
	}
	
	public List<PhoneAndMac> finALLWhere(Map<String, Object> propertiesMap) {
		return phoneMacDao.find(propertiesMap);
	}
	
	public List<PhoneAndMac> findPages(Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<PhoneAndMac> list=phoneMacDao.find(propertiesMap, start, limit);
		return list;
	}
	
	

	public List<PhoneAndMac> findPages_sql(String sql,Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<PhoneAndMac> list=phoneMacDao.pageQuery(sql, propertiesMap, start, limit);
		return list;
	}	
		
	
	public Integer getTotalCount_where(String sql, Map<String,Object> propertiesMap) {
		return phoneMacDao.getTotalCount(sql, propertiesMap);
	} 
	
}
