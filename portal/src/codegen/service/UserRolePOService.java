package com.cmct.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.UserRolePODao;


@Service
public class UserRolePOService {
	@Autowired
	private UserRolePODao userRoleDao;
}