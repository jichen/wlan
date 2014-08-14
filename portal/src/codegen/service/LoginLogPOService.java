package com.cmct.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.LoginLogPODao;


@Service
public class LoginLogPOService {
	@Autowired
	private LoginLogPODao loginLogDao;
}