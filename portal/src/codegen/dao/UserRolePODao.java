package com.cmct.portal.dao;

import org.springframework.stereotype.Repository;

import com.cmct.portal.po.UserRolePO;

@Repository("userRoleDao")
public class UserRolePODao extends HibernateDao<UserRolePO,Integer> {

}