package com.cmct.portal.dao;

import org.springframework.stereotype.Repository;

import com.cmct.portal.po.TemplatePagePO;

@Repository("templatePageDao")
public class TemplatePagePODao extends HibernateDao<TemplatePagePO,Integer> {

}