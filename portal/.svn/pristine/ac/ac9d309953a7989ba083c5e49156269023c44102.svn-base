package com.cmct.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.cmct.portal.po.BlacklistPO;

@SuppressWarnings("all")
@Repository("blackListDao")
public class BlackListDao extends HibernateDao<BlacklistPO,Integer> {

	public Collection<BlacklistPO> doquery(){
		return this.findAll();
	}
	

	
}
