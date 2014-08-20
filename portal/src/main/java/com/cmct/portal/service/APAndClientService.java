package com.cmct.portal.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmct.portal.dao.APAndClientDao;
import com.cmct.portal.po.APAndClient;

@Service("apAndClientService")
public class APAndClientService  {
	@Autowired
	private APAndClientDao apClientDao;
	
	public List<APAndClient> getAll(){
		return apClientDao.findAll();
		
	}
	
	public void saveAPClient(APAndClient entity){
		apClientDao.save(entity);
	} 
	
	public void updateAPClient(APAndClient entity){
		apClientDao.update(entity);
	}
	
	public void updateList(List<APAndClient> list){
		if(list!=null && list.size()>0){
			for(APAndClient entity:list){
				apClientDao.update(entity);
			}
		}
	} 
	
	public Integer countRow() {
		String sql=" from APAndClient ";
		return apClientDao.getCount(sql);

	} 
	
	public APAndClient findOne(Integer id) {
		return apClientDao.get(id);
	} 
	
	public List<APAndClient> findAllWhere(Map<String,Object> propertiesMap){
		return apClientDao.find(propertiesMap);
	}
	
	public APAndClient find_Newest(Map<String,Object> propertiesMap){
		List<APAndClient> list=apClientDao.find(propertiesMap);
		if(list!=null && list.size()>0){
			return list.get(list.size()-1);
		}
		return null;
	}
	
	public List<APAndClient> findPages(Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<APAndClient> list=apClientDao.find(propertiesMap, start, limit);
		return list;
	}

	public List<APAndClient> findPages_sql(String sql,Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<APAndClient> list=apClientDao.pageQuery(sql, propertiesMap, start, limit);
		return list;
	}	
	
	
	public Integer getTotalCount_where(String sql, Map<String,Object> propertiesMap) {
		return apClientDao.getTotalCount(sql, propertiesMap);
	} 
	
}
