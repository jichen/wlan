package com.cmct.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.WLANUSERLogPODao;


@Service
public class WLANUSERLogPOService {
	@Autowired
	private WLANUSERLogPODao wLANUSERLogDao;
}