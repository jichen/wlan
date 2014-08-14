package com.cmct.portal.dao;

import org.springframework.stereotype.Repository;

import com.cmct.portal.po.RolePO;

@Repository("roleDao")
public class RolePODao extends HibernateDao<RolePO,Integer> {

}