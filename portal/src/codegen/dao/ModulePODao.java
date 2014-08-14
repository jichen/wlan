package com.cmct.portal.dao;

import org.springframework.stereotype.Repository;

import com.cmct.portal.po.ModulePO;

@Repository("moduleDao")
public class ModulePODao extends HibernateDao<ModulePO,Integer> {

}