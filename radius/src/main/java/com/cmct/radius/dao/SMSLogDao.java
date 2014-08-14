package com.cmct.radius.dao;

import org.springframework.stereotype.Repository;

import com.cmct.radius.po.SMSLogPO;

@Repository("smsLogDao")
public class SMSLogDao extends HibernateDao<SMSLogPO, Integer> {

}
