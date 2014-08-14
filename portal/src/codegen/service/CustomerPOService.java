package com.cmct.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.CustomerPODao;


@Service
public class CustomerPOService {
	@Autowired
	private CustomerPODao customerDao;
}