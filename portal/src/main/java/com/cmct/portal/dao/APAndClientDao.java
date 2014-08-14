package com.cmct.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.cmct.portal.po.APAndClient;

@SuppressWarnings("all")
@Repository("apClientDao")
public class APAndClientDao extends HibernateDao<APAndClient,Integer> {

	public Collection<APAndClient> doquery(){
		return this.findAll();
	}
	
	public List<APAndClient> doqueryLogin(Map login){
		return this.find(login);
	}
	
}
