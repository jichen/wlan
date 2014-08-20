package com.cmct.portal.security.controller;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmct.portal.po.UserPO;
import com.cmct.portal.service.UserService;



@Controller
@RequestMapping("/security/employee")
public class EmployeeController {
	private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	private static String REL_ID = "employeeList";
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "/list")
	public String employee_List(HttpServletRequest request) {
		
		Collection<UserPO> list=userService.getAll();
		List<UserPO> users=new ArrayList<UserPO>();
		UserPO user;
		for(UserPO userPO:list){
			user=new UserPO();
			user.setUserid(userPO.getUserid());
			user.setUsername(userPO.getUsername());
			user.setPwd(userPO.getPwd());
			users.add(user);
		}
		Map<String,Object> mp=new HashMap<String,Object>();
		mp.put("list",users);
		return "/pages/view/security/employee/list";
		
	}




}
