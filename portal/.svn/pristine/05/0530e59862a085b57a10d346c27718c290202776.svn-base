package com.cmct.portal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmct.portal.dao.ModuleDao;
import com.cmct.portal.po.ACPO;
import com.cmct.portal.po.ModulePO;


@Service
public class ModuleService {
	@Autowired
	private ModuleDao moduleDao;
	
	
	public ModulePO get(Integer id){
		return moduleDao.get(id);
	}
		
	public void save(ModulePO entity){
		moduleDao.save(entity);
	}
	
	public void update(ModulePO entity){
		moduleDao.update(entity);
	}

	public void delete(Integer id) {
		moduleDao.delete(id);		
	}

	/**
	 * 判断是否是根模块.
	 */
	private boolean isRoot(Integer id) {
		return id == 1;
	}

	/**
	 * 得到树
	 * @return
	 */
	public ModulePO getTree() {
		List<ModulePO> list = moduleDao.findAll();
		List<ModulePO> rootList = makeTree(list);
				if(null!=rootList&&rootList.size()>=1){
					return rootList.get(0);
				}
				return null;
	}

	private List<ModulePO> makeTree(List<ModulePO> list) {
		List<ModulePO> parent = new ArrayList<ModulePO>();
		// get parentId = null;
		for (ModulePO e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<ModulePO>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);
		
		makeChildren(parent, list);
		
		return parent;
	}
	
	private void makeChildren(List<ModulePO> parent, List<ModulePO> children) {
		if (children.isEmpty()) {
			return ;
		}
		
		List<ModulePO> tmp = new ArrayList<ModulePO>();
		for (ModulePO c1 : parent) {
			for (ModulePO c2 : children) {
				c2.setChildren(new ArrayList<ModulePO>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}
		
		children.removeAll(tmp);
		
		makeChildren(tmp, children);
	}
	

	public List<ModulePO> findPages(Map propertiesMap,Integer start,Integer limit){
		List<ModulePO> list=moduleDao.find(propertiesMap, start, limit);
		return list;
	}

	public List<ModulePO> findPages_sql(String sql,Map propertiesMap,Integer start,Integer limit){
		List<ModulePO> list=moduleDao.pageQuery(sql, propertiesMap, start, limit);
		return list;
	}	
	
	
	public Integer getTotalCount_where(String sql, Map propertiesMap) {
		return moduleDao.getTotalCount(sql, propertiesMap);
	} 
	
}