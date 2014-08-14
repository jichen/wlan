package com.cmct.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import com.cmct.portal.po.UserPO;


@SuppressWarnings("all")
@Repository("userDao")
public class UserDao extends HibernateDao<UserPO,Integer> {

	public Collection<UserPO> doquery(){
		return this.findAll();
	}
	
	public List<UserPO> doqueryLogin(Map login){
		return this.find(login);
	}
	
}
