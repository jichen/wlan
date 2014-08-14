package com.cmct.portal.dao;

import org.springframework.stereotype.Repository;

import com.cmct.portal.po.StatisticsVo;

@Repository("statisticsDao")
public class StatisticsVoDao extends HibernateDao<StatisticsVo,Integer> {

}