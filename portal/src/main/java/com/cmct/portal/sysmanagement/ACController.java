package com.cmct.portal.sysmanagement;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.cmct.common.Constants;
import com.cmct.common.annotation.Log;
import com.cmct.common.util.AbstractController;
import com.cmct.common.util.ui.PageFormModel;
import com.cmct.common.util.ui.Page;
import com.cmct.portal.po.ACPO;
import com.cmct.portal.po.UserPO;
import com.cmct.portal.service.ACService;


@Controller
@RequestMapping("/sysmanagement/ac")
public class ACController extends AbstractController {
	
	private static String REL_ID = "acList";
	
	@Autowired
	private ACService acService;


	@RequestMapping(value = "/preadd")
	public ModelAndView preAdd() throws Exception {
		ModelAndView mav = new ModelAndView("pages/view/sysmanagement/ac/add");
		return mav;
	}

	@Log(module = Constants.MODULE_AC, function = Constants.Funtion_Add)
	@RequestMapping(value = "/add")
	@ResponseBody
	public String add(ACPO bean,HttpServletRequest request){
		String cust_id = request.getParameter("cust.cust_id");
		bean.setCust_id(Integer.valueOf(cust_id));
		bean.setIsdelete("N");
		bean.setCreatetime(new Date());
		UserPO user =(UserPO) WebUtils.getSessionAttribute(request, Constants.PORTAL_LOGIN_USER);
		bean.setCreateusername(user.getUsername());
		acService.saveAC(bean);
		return ajaxDoneSuccess(" ",true,REL_ID);
	}


	@RequestMapping(value = "/preUpdate/{id}")
	public ModelAndView preUpdate(@PathVariable("id") Integer id) throws Exception {
		ACPO bean = acService.findOne(id);
		ModelAndView mav = new ModelAndView("/pages/view/sysmanagement/ac/update");
		mav.addObject("bean", bean);
		return mav;
	}

	@Log(module = Constants.MODULE_AC, function = Constants.Funtion_Update)
	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(ACPO bean,HttpServletRequest request) throws Exception {
		String cust_id = request.getParameter("cust.cust_id");
		bean.setCust_id(Integer.valueOf(cust_id));
		if(bean.getIsdelete()==null || bean.getIsdelete()==""){
			bean.setIsdelete("N");
		}
		bean.setUpdatetime(new Date());
		UserPO user =(UserPO) WebUtils.getSessionAttribute(request, Constants.PORTAL_LOGIN_USER);
		bean.setUpdateusername(user.getUsername());
		acService.updateAC(bean);
		return ajaxDoneSuccess(" ",true,REL_ID);
	}
	

	@Log(module = Constants.MODULE_AC, function = Constants.Funtion_Delete)
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id,HttpServletRequest request) throws Exception {
		ACPO bean = acService.findOne(id);
		bean.setIsdelete("Y");
		bean.setUpdatetime(new Date());
		UserPO user =(UserPO) WebUtils.getSessionAttribute(request, Constants.PORTAL_LOGIN_USER);
		bean.setUpdateusername(user.getUsername());
		acService.updateAC(bean);
		return ajaxDoneSuccess("删除成功", false, REL_ID);
	}
	
	/**
	 * 
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page,PageFormModel pageForm,HttpServletRequest req) throws Exception {
		Collection<ACPO> list=null;
		List<ACPO> acs=new ArrayList<ACPO>();
		ACPO ac;
		Map<String,Object> propertiesMap =new HashMap<String,Object>();
		Integer start = 0;
		Integer limit=page.getNumPerPage();
		String sqlCount="select count(*) from ACPO where 1=1 ";
		String sql=" from ACPO where 1=1 ";
		String where_sql="";
		if(pageForm.getPageNum()>0){
			start=(Integer.valueOf(pageForm.getPageNum())-1)*page.getNumPerPage();
		}

		if(pageForm.getName()!=null){
			if(pageForm.getName().trim().length()>0){
				propertiesMap.put("ac_name", "%"+pageForm.getName().trim()+"%");
				where_sql=where_sql+" and ac_name like :ac_name";
			}
		}
		if(pageForm.getLocation()!=null){
			if(pageForm.getLocation().trim().length()>0){
				propertiesMap.put("location", "%"+pageForm.getLocation()+"%");
				where_sql=where_sql+" and location like :location";
			}
		}
		if(pageForm.getIsdelete()!=null){
			if(pageForm.getIsdelete().trim().length()>0){
				propertiesMap.put("isdelete", pageForm.getIsdelete().trim());
				where_sql=where_sql+" and isdelete= :isdelete";
			}
		}
		if(pageForm.getIp()!=null){
			if(pageForm.getIp().trim().length()>0){
				propertiesMap.put("ip", pageForm.getIp());
				where_sql=where_sql+" and ip= :ip";
			}
		}
		if(pageForm.getCust_name()!=null){
			if(pageForm.getCust_name().trim().length()>0){
				propertiesMap.put("cust_name", "%"+pageForm.getCust_name().trim()+"%");
				where_sql=where_sql+" and cust_id = (select id from CustomerPO where cust_name like :cust_name)";
			}
		}	

		sql=sql+where_sql;
		sqlCount=sqlCount+where_sql;
		list=acService.findPages_sql(sql ,propertiesMap, start, limit);

		page.setTotalCount(acService.getTotalCount_where(sqlCount, propertiesMap));	

		
		for(ACPO acPO:list){
			ac=new ACPO();
			ac.setId(acPO.getId());
			ac.setAc_name(acPO.getAc_name());
			ac.setCust_name(acPO.getCust_name());
			ac.setCust_id(acPO.getCust_id());
			ac.setIp(acPO.getIp());
			ac.setLocation(acPO.getLocation());
			ac.setRemark(acPO.getRemark());
			ac.setIsdelete(acPO.getIsdelete());
			acs.add(ac);
		}
		Map<String,Object> mp=new HashMap<String,Object>();
		mp.put("list",acs);
		mp.put("page",page);
		mp.put("pageForm",pageForm);
		return new ModelAndView("/pages/view/sysmanagement/ac/list",mp);
	}
	
	
	@RequestMapping(value = "/lookUp")
	public ModelAndView aclist(Page page,PageFormModel pageForm,HttpServletRequest req) throws Exception {
		Collection<ACPO> list=null;
		List<ACPO> acs=new ArrayList<ACPO>();
		ACPO ac;
		Map<String,Object> propertiesMap =new HashMap<String,Object>();
		Integer start = 0;
		Integer limit=page.getNumPerPage();
		String sqlCount="select count(*) from ACPO where 1=1 ";
		String sql=" from ACPO where 1=1 ";
		String where_sql="";
		if(pageForm.getPageNum()>0){
			start=(Integer.valueOf(pageForm.getPageNum())-1)*page.getNumPerPage();
		}
		page.setPageNum(pageForm.getPageNum());
		
		if(pageForm.getName()!=null){
			if(pageForm.getName().trim().length()>0){
				propertiesMap.put("ac_name", pageForm.getName().trim());
				where_sql=where_sql+" and ac_name= :ac_name";
			}
		}
		if(pageForm.getLocation()!=null){
			if(pageForm.getLocation().trim().length()>0){
				propertiesMap.put("location", pageForm.getLocation());
				where_sql=where_sql+" and location= :location";
			}
		}
		if(pageForm.getIp()!=null){
			if(pageForm.getIp().trim().length()>0){
				propertiesMap.put("ip", pageForm.getIp().trim());
				where_sql=where_sql+" and ip= :ip";
			}
		}
		if(pageForm.getCust_name()!=null){
			if(pageForm.getCust_name().trim().length()>0){
				propertiesMap.put("cust_name", "%"+pageForm.getCust_name().trim()+"%");
				where_sql=where_sql+" and cust_id in (select id from CustomerPO where cust_name = :cust_name)";
			}
		}	

		sql=sql+where_sql;
		sqlCount=sqlCount+where_sql;
		list=acService.findPages_sql(sql ,propertiesMap, start, limit);
		page.setTotalCount(acService.getTotalCount_where(sqlCount, propertiesMap));	
		
		for(ACPO acPO:list){
			ac=new ACPO();
			ac.setId(acPO.getId());
			ac.setAc_name(acPO.getAc_name());
			ac.setCust_name(acPO.getCust_name());
			ac.setCust_id(acPO.getCust_id());
			ac.setIp(acPO.getIp());
			ac.setLocation(acPO.getLocation());
			ac.setRemark(acPO.getRemark());
			ac.setIsdelete(acPO.getIsdelete());
			acs.add(ac);
		}
		Map<String,Object> mp=new HashMap<String,Object>();
		mp.put("list",acs);
		mp.put("page",page);
		mp.put("pageForm",pageForm);
		return new ModelAndView("/pages/view/sysmanagement/ac/acLookUp",mp);
	}	
	
	
	
	
}
