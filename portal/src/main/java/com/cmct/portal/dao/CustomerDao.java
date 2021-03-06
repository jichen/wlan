package com.cmct.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.cmct.portal.po.CustomerPO;

@SuppressWarnings("all")
@Repository("customerDao")
public class CustomerDao extends HibernateDao<CustomerPO,Integer> {

	public Collection<CustomerPO> doquery(){
		return this.findAll();
	}
	
	public List<CustomerPO> doqueryLogin(Map login){
		return this.find(login);
	}
	
}
