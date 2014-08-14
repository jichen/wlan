package com.cmct.radius.dao;

import org.springframework.stereotype.Repository;

import com.cmct.radius.po.RadPostAuthPO;

@Repository("radPostAuthDao")
public class RadPostAuthDao extends HibernateDao<RadPostAuthPO, Integer> {

}
