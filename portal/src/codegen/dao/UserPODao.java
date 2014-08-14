package com.cmct.portal.dao;

import org.springframework.stereotype.Repository;

import com.cmct.portal.po.UserPO;

@Repository("userDao")
public class UserPODao extends HibernateDao<UserPO,Integer> {

}