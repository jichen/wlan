package com.cmct.portal.security.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.cmct.common.Constants;
import com.cmct.common.annotation.Log;
import com.cmct.common.exception.ServiceException;
import com.cmct.common.util.AbstractController;
import com.cmct.common.util.ui.Page;
import com.cmct.common.util.ui.PageFormModel;
import com.cmct.portal.po.ModulePO;
import com.cmct.portal.service.ModuleService;


/**
 * 
 * @title 		
 * @description	模块管理
 * @usage		
 * @copyright	Copyright 2011  SHCMCT Corporation. All rights reserved.
 * @company		上海中移通信技术工程有限公司
 * @author		bjc_caixin
 * @create		2013-1-7 上午10:28:45
 */
@Controller
@RequestMapping("security/module")
public class ModuleController extends AbstractController {
	@Autowired
	private ModuleService moduleService;
	
	

	private static String REL_ID = "moduleList";
	private static final String ADD = "pages/view/security/module/add";
	private static final String UPDATE = "pages/view/security/module/update";
	private static final String LIST = "pages/view/security/module/list";
	private static final String TREE = "pages/view/security/module/tree";
	
	@RequestMapping(value="/preAdd")
	public String preAdd() {
		return ADD;
	}
	
	@Log(module = Constants.MODULE_SYS_MODULE, function = Constants.Funtion_Add)
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public  String create(ModulePO module, HttpServletRequest request) {
		module.setParent((ModulePO)request.getSession().getAttribute("parentModule"));
		moduleService.save(module);
		return ajaxDoneSuccess(" ",true,REL_ID);
	}
	
	@RequestMapping(value="/preUpdate/{id}")
	public ModelAndView preUpdate(@PathVariable Integer id) {
		ModulePO module = moduleService.get(id);
		return new ModelAndView(UPDATE,"module", module);
	}
	

	@Log(module = Constants.MODULE_SYS_MODULE, function = Constants.Funtion_Update)
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public  String update(ModulePO module, HttpServletRequest request) {
		moduleService.update(module);
		return ajaxDoneSuccess(" ",true,"moduleList");
	}
	
	@Log(module = Constants.MODULE_SYS_MODULE, function = Constants.Funtion_Delete)
	@RequestMapping(value="/delete/{id}")
	@ResponseBody 
	public String delete(@PathVariable Integer id, HttpServletRequest request) {
		try {
			moduleService.delete(id);		
		} catch (ServiceException e) {
			return ajaxDoneError("模块删除失败：" + e.getMessage());
		}
		
		return ajaxDoneSuccess("模块删除成功！", false, "moduleList");
	}
	
	@RequestMapping(value="/tree")
	public String tree(Map<String, Object> map) {
		ModulePO module = moduleService.getTree();
		map.put("module", module);
		return TREE;
	}
	
	@RequestMapping(value = "/list/{parentId}")
	public ModelAndView list( @PathVariable Integer parentId,Page page,PageFormModel pageForm, HttpServletRequest request) {
		List<ModulePO> list = null;
		Map propertiesMap =new HashMap();
		Integer start = 0;
		Integer limit=page.getNumPerPage();
		String sqlCount="select count(*) from ModulePO where 1=1 ";
		String sql=" from ModulePO where 1=1 ";
		String where_sql="";
		propertiesMap.put("parentid",pageForm.getParentId());
		where_sql=where_sql+" and parentId= :parentid";
		
		if(pageForm.getPageNum()>0){
			start=(Integer.valueOf(pageForm.getPageNum())-1)*page.getNumPerPage();
		}
		if (StringUtils.isNotBlank(pageForm.getName())) {
			propertiesMap.put("name",pageForm.getName().trim());
			where_sql=where_sql+" and name= :name";
		}
		sql=sql+where_sql;
		sqlCount=sqlCount+where_sql;
		
		list=moduleService.findPages_sql(sql,propertiesMap, start, limit);

		page.setTotalCount(moduleService.getTotalCount_where(sqlCount, propertiesMap));
		request.getSession().setAttribute("parentModule", moduleService.get(pageForm.getParentId()));
		
		Map mp=new HashMap();
		mp.put("list",list);
		mp.put("page",page);
		mp.put("pageForm",pageForm);
		return new ModelAndView(LIST,mp);
	}
	
	
}
