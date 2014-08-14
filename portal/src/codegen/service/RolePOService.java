package com.cmct.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.RolePODao;


@Service
public class RolePOService {
	@Autowired
	private RolePODao roleDao;
}