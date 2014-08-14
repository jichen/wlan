package com.cmct.portal.dao;

import org.springframework.stereotype.Repository;

import com.cmct.portal.po.BlacklistPO;

@Repository("blacklistDao")
public class BlacklistPODao extends HibernateDao<BlacklistPO,Integer> {

}