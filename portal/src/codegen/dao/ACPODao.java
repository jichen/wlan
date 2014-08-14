package com.cmct.portal.dao;

import org.springframework.stereotype.Repository;

import com.cmct.portal.po.ACPO;

@Repository("aCDao")
public class ACPODao extends HibernateDao<ACPO,Integer> {

}