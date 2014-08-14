package com.cmct.portal.dao;

import org.springframework.stereotype.Repository;

import com.cmct.portal.po.RoleVo;

@Repository("roleDao")
public class RoleVoDao extends HibernateDao<RoleVo,Integer> {

}