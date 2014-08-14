package com.cmct.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.cmct.portal.po.PhoneAndMac;


@SuppressWarnings("all")
@Repository("phoneMacDao")
public class PhoneAndMacDao extends HibernateDao<PhoneAndMac,Integer> {

	public Collection<PhoneAndMac> doquery(){
		return this.findAll();
	}
	
	public List<PhoneAndMac> doqueryLogin(Map login){
		return this.find(login);
	}
	
}
