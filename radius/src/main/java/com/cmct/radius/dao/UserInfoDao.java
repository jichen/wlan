package com.cmct.radius.dao;

import org.springframework.stereotype.Repository;

import com.cmct.radius.po.UserInfoPO;

@Repository("userInfoDao")
public class UserInfoDao extends HibernateDao<UserInfoPO, Integer> {

}
