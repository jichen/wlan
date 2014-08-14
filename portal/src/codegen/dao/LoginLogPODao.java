package com.cmct.portal.dao;

import org.springframework.stereotype.Repository;

import com.cmct.portal.po.LoginLogPO;

@Repository("loginLogDao")
public class LoginLogPODao extends HibernateDao<LoginLogPO,Integer> {

}