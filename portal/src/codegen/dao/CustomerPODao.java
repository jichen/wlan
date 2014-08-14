package com.cmct.portal.dao;

import org.springframework.stereotype.Repository;

import com.cmct.portal.po.CustomerPO;

@Repository("customerDao")
public class CustomerPODao extends HibernateDao<CustomerPO,Integer> {

}