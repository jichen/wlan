package com.cmct.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import com.cmct.portal.po.AuthRulePO;

@SuppressWarnings("all")
@Repository("authRuleDao")
public class AuthRuleDao extends HibernateDao<AuthRulePO,Integer> {

	public Collection<AuthRulePO> doquery(){
		return this.findAll();
	}
	
	public List<AuthRulePO> doqueryLogin(Map login){
		return this.find(login);
	}
	
}
