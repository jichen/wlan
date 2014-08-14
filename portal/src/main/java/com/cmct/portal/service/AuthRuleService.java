package com.cmct.portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmct.portal.dao.AuthRuleDao;
import com.cmct.portal.po.AuthRulePO;


@Service
public class AuthRuleService  {
	@Autowired
	private AuthRuleDao authRuleDao;
	
	public List<AuthRulePO> getAll(){
		return authRuleDao.findAll();
		
	}
	
	public void saveAuthRule(AuthRulePO entity){
		authRuleDao.save(entity);
	} 
	
	public void updateAuthRule(AuthRulePO entity){
		authRuleDao.update(entity);
	} 


	public Integer countRow() {
		String sql=" from AuthRulePO ";
		return authRuleDao.getCount(sql);

	} 
	
}
