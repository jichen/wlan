package com.cmct.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.cmct.portal.po.ACPO;

@SuppressWarnings("all")
@Repository("aCDao")
public class ACDao extends HibernateDao<ACPO,Integer> {

	public Collection<ACPO> doquery(){
		return this.findAll();
	}
	
	public List<ACPO> doqueryLogin(Map login){
		return this.find(login);
	}
	
}
