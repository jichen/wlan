package com.cmct.${moduleName}.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmct.portal.dao.${classname}Dao;


@Service
public class ${classname}Service {
	@Autowired
	private ${classname}Dao ${name}Dao;
}