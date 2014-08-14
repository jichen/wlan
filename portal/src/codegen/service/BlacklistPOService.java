package com.cmct.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.BlacklistPODao;


@Service
public class BlacklistPOService {
	@Autowired
	private BlacklistPODao blacklistDao;
}