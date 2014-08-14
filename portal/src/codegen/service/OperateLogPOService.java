package com.cmct.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.OperateLogPODao;


@Service
public class OperateLogPOService {
	@Autowired
	private OperateLogPODao operateLogDao;
}