package com.cmct.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.AuthRulePODao;


@Service
public class AuthRulePOService {
	@Autowired
	private AuthRulePODao authRuleDao;
}