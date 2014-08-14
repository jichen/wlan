package com.cmct.portal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmct.portal.dao.RoleDao;
import com.cmct.portal.po.RolePO;

@Service
public class RoleService {
	@Autowired
	private RoleDao roleDao;
	
	public void save(RolePO entity){
		roleDao.save(entity);
	}
	
	public void update(RolePO entity){
		roleDao.update(entity);
	}
	
	public void delete(Integer id){
		roleDao.delete(id);
	}
	
	
	public List<RolePO> findAll(){
		return roleDao.findAll();
	}
	
	
	/*
	 * 根据id查询
	 * 
	 */
	
	public RolePO findOne(Integer roleid){
		Map<String,Object> propertiesMap = new HashMap<String,Object>();
		propertiesMap.put("id",roleid);	
		List<RolePO> roles=roleDao.find(propertiesMap);
		if(null!=roles && roles.size()>0){

			return roles.get(0);
		}else{
			return null;
		}
		
	}
	
	public Map<String,Object> findOne_update(Integer roleid){
		Map<String,Object> propertiesMap = new HashMap<String,Object>();
		propertiesMap.put("id",roleid);	
		List<RolePO> roles=roleDao.find(propertiesMap);
		propertiesMap.clear();
		if(null!=roles && roles.size()>0){
			RolePO role=roles.get(0);
			String rolePermissionList=role.getPermissionList().toString();
			propertiesMap.put("role",role);	
			propertiesMap.put("rolePermissionList",rolePermissionList);	
		}else{
		}
		return propertiesMap;
	}
	
	
	/*
	 * 根据name查询
	 * 
	 */
	public RolePO findOne(String name){
		Map<String,Object> propertiesMap = new HashMap<String,Object>();
		propertiesMap.put("name",name);	
		List<RolePO> roles=roleDao.find(propertiesMap);
		if(null!=roles && roles.size()>0){
			return roles.get(0);
		}else{
			return null;
		}
	}
	
	
	/*
	 * 根据name返回数据
	 * 
	 */
	public List<RolePO> findByRoleName(String rolename){
		Map<String,Object> propertiesMap = new HashMap<String,Object>();
		propertiesMap.put("name",rolename);	
		return roleDao.find(propertiesMap);
	}
	
	/*
	 * 根据code返回数据
	 * 
	 */
	public List<RolePO> findByCode(String rolecode){
		Map<String,Object> propertiesMap = new HashMap<String,Object>();
		propertiesMap.put("code",rolecode);	
		return roleDao.find(propertiesMap);
	}
	
	
	public List<RolePO> findPages_sql(String sql,Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<RolePO> list=roleDao.pageQuery(sql, propertiesMap, start, limit);
		return list;
	}	
	
	
	public List<RolePO> findPages(Map<String,Object> propertiesMap,Integer start,Integer limit){
		List<RolePO> list=roleDao.find(propertiesMap, start, limit);
		return list;
	}
	
	public Integer getTotalCount_where(String sql, Map<String,Object> propertiesMap) {
		return roleDao.getTotalCount(sql, propertiesMap);
	} 
	
}