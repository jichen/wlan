package com.cmct.portal.dao;

import org.springframework.stereotype.Repository;

import com.cmct.portal.po.APPO;

@Repository("aPDao")
public class APPODao extends HibernateDao<APPO,Integer> {

}