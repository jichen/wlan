package com.cmct.radius.dao;

import org.springframework.stereotype.Repository;

import com.cmct.radius.po.RadCheckPO;

@Repository("radCheckDao")
public class RadCheckDao extends HibernateDao<RadCheckPO, Integer> {

}
