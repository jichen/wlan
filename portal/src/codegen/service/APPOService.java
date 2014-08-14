package com.cmct.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.APPODao;


@Service
public class APPOService {
	@Autowired
	private APPODao aPDao;
}