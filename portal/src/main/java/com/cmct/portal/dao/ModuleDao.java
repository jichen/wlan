package com.cmct.portal.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.cmct.portal.po.ModulePO;


@SuppressWarnings("all")
@Repository("moduleDao")
public class ModuleDao extends HibernateDao<ModulePO,Integer> {

	
}
