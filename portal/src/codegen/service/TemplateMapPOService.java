package com.cmct.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.TemplateMapPODao;


@Service
public class TemplateMapPOService {
	@Autowired
	private TemplateMapPODao templateMapDao;
}