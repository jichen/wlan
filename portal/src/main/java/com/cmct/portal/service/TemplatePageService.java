package com.cmct.portal.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmct.portal.dao.TemplatePageDAO;
import com.cmct.portal.po.TemplatePagePO;


@Service
public class TemplatePageService {
	@Autowired
	private TemplatePageDAO templatePageDAO;
	
	
	public List<TemplatePagePO> getAll(){
		return templatePageDAO.findAll();
	}
	
	public void save(TemplatePagePO entity){
		templatePageDAO.save(entity);
	}

	public void update(TemplatePagePO entity){
		templatePageDAO.update(entity);
	}
	
	public TemplatePagePO findOne(Integer id) {
		return templatePageDAO.get(id);

	} 
	
	public List<TemplatePagePO> findPages(Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<TemplatePagePO> list=templatePageDAO.find(propertiesMap, start, limit);
		return list;
	}
	
	
	public Integer getTotalCount_where(String sql, Map<String,Object> propertiesMap) {
		return templatePageDAO.getTotalCount(sql, propertiesMap);
	} 
}
