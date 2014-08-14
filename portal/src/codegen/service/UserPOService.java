package com.cmct.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.UserPODao;


@Service
public class UserPOService {
	@Autowired
	private UserPODao userDao;
}