package com.cmct.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.RadPostAuthVODao;


@Service
public class RadPostAuthVOService {
	@Autowired
	private RadPostAuthVODao radPostAuthDao;
}