package com.cmct.portal.dao;

import org.springframework.stereotype.Repository;

import com.cmct.portal.po.AuthRulePO;

@Repository("authRuleDao")
public class AuthRulePODao extends HibernateDao<AuthRulePO,Integer> {

}