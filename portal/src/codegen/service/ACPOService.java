package com.cmct.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.ACPODao;


@Service
public class ACPOService {
	@Autowired
	private ACPODao aCDao;
}