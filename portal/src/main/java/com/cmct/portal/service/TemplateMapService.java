package com.cmct.portal.service;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmct.portal.dao.TemplateMapDAO;
import com.cmct.portal.po.TemplateMapPO;


@Service
@Transactional
public class TemplateMapService {
	@Autowired
	private TemplateMapDAO templateMapDAO;
	
	public void save(TemplateMapPO entity){
		templateMapDAO.save(entity);
	}

	public void delete(TemplateMapPO entity){
		templateMapDAO.delete(entity);
	}
	
	public List<TemplateMapPO> findList(Map propertiesMap){
		return templateMapDAO.find(propertiesMap);		
	}

	public List<TemplateMapPO> findList(Map propertiesMap,List<String[]> order){
		return templateMapDAO.find(propertiesMap,order);		
	}

	
	
}
