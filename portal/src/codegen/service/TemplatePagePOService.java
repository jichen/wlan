package com.cmct.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.TemplatePagePODao;


@Service
public class TemplatePagePOService {
	@Autowired
	private TemplatePagePODao templatePageDao;
}