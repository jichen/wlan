package com.cmct.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import com.cmct.portal.po.APPO;

@SuppressWarnings("all")
@Repository("aPDao")
public class APDao extends HibernateDao<APPO,Integer> {

	public Collection<APPO> doquery(){
		return this.findAll();
	}
	
	public List<APPO> doqueryLogin(Map login){
		return this.find(login);
	}
	
}
