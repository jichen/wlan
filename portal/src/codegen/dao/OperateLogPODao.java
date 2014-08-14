package com.cmct.portal.dao;

import org.springframework.stereotype.Repository;

import com.cmct.portal.po.OperateLogPO;

@Repository("operateLogDao")
public class OperateLogPODao extends HibernateDao<OperateLogPO,Integer> {

}