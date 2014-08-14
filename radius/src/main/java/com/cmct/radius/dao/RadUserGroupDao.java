package com.cmct.radius.dao;

import org.springframework.stereotype.Repository;

import com.cmct.radius.po.RadUserGroupPO;

@Repository("radUserGroupDao")
public class RadUserGroupDao extends HibernateDao<RadUserGroupPO, Integer> {

}
