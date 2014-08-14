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
import com.cmct.portal.po.APPO;
import com.cmct.portal.po.UserPO;
import com.cmct.portal.service.APService;


@Controller
@RequestMapping("/sysmanagement/ap")
public class APController extends AbstractController {
	
	private static String REL_ID = "apList";
	
	@Autowired
	private APService apService;



	@RequestMapping(value = "/preadd")
	public ModelAndView preAdd() throws Exception {
		ModelAndView mav = new ModelAndView("pages/view/sysmanagement/ap/add");
		return mav;
	}


	@Log(module = Constants.MODULE_AP, function = Constants.Funtion_Add)
	@RequestMapping(value = "/add")
	@ResponseBody
	public String add(APPO bean,HttpServletRequest request){
		String ac_id = request.getParameter("ac.ac_id");		
		bean.setAc_id(Integer.valueOf(ac_id));		
		bean.setCreatetime(new Date());
		UserPO user =(UserPO) WebUtils.getSessionAttribute(request, Constants.PORTAL_LOGIN_USER);
		bean.setCreateusername(user.getUsername());
		bean.setIsdelete("N");
		apService.saveAP(bean);
		return ajaxDoneSuccess(" ",true,REL_ID);
	}


	
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page,PageFormModel pageForm,HttpServletRequest req) throws Exception {
		Collection<APPO> list=null;
		List<APPO> aps=new ArrayList<APPO>();
		APPO ap;
		
		Map propertiesMap =new HashMap();
		Integer start = 0;
		Integer limit=page.getNumPerPage();
		String sqlCount="select count(*) from APPO where 1=1 ";
		String sql=" from APPO where 1=1 ";
		String where_sql="";
		
		if(pageForm.getPageNum()>0){
			start=(Integer.valueOf(pageForm.getPageNum())-1)*page.getNumPerPage();
		}
		if(pageForm.getAc_id()!=null){
			if(pageForm.getAc_id().trim().length()>0){
				propertiesMap.put("ac_name", "%"+pageForm.getAc_id().trim()+"%");
				where_sql=where_sql+" and ac_id in ( select id from ACPO where ac_name like :ac_name) ";
			}
		}
		if(pageForm.getName()!=null){
			if(pageForm.getName().trim().length()>0){
				propertiesMap.put("name", "%"+pageForm.getName().trim()+"%");
				where_sql=where_sql+" and ap_name like :name";
			}
		}
		if(pageForm.getLocation()!=null){
			if(pageForm.getLocation().trim().length()>0){
				propertiesMap.put("location", "%"+pageForm.getLocation().trim()+"%" );
				where_sql=where_sql+" and location like :location";
			}
		}
		if(pageForm.getIsdelete()!=null){
			if(pageForm.getIsdelete().trim().length()>0){
				propertiesMap.put("isdelete", pageForm.getIsdelete().trim());
				where_sql=where_sql+" and isdelete= :isdelete";
			}
		}

		sql=sql+where_sql;
		sqlCount=sqlCount+where_sql;
		
		list=apService.findPages_sql(sql,propertiesMap, start, limit);

		page.setTotalCount(apService.getTotalCount_where(sqlCount, propertiesMap));
		

		
		for(APPO apPO:list){
			ap=new APPO();
			ap.setId(apPO.getId());
			ap.setAp_name(apPO.getAp_name());
			ap.setNas_port_id(apPO.getNas_port_id());
			ap.setLocation(apPO.getLocation());
			ap.setAc_name(apPO.getAc_name());
			ap.setAc_id(apPO.getAc_id());
			ap.setRemark(apPO.getRemark());
			ap.setIsdelete(apPO.getIsdelete());
			aps.add(ap);
		}
		
		Map mp=new HashMap();
		mp.put("list",aps);
		mp.put("page",page);
		mp.put("pageForm",pageForm);
		return new ModelAndView("/pages/view/sysmanagement/ap/list",mp);
	}


	
	@RequestMapping(value = "/preUpdate/{id}")
	public ModelAndView preUpdate(@PathVariable("id") Integer id) throws Exception {
		APPO bean = apService.findOne(id);
		ModelAndView mav = new ModelAndView("/pages/view/sysmanagement/ap/update");
		mav.addObject("bean", bean);
		return mav;
	}
	


	@Log(module = Constants.MODULE_AP, function = Constants.Funtion_Update)	
	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(APPO bean,HttpServletRequest request) throws Exception {
		String ac_id = request.getParameter("ac.ac_id");		
		bean.setAc_id(Integer.valueOf(ac_id));	
		if(bean.getIsdelete()==null || bean.getIsdelete()==""){
			bean.setIsdelete("N");
		}
		bean.setUpdatetime(new Date());
		UserPO user =(UserPO) WebUtils.getSessionAttribute(request, Constants.PORTAL_LOGIN_USER);
		bean.setUpdateusername(user.getUsername());
		apService.updateAP(bean);
		return ajaxDoneSuccess(" ",true,REL_ID);
	}
	

	@Log(module = Constants.MODULE_AP, function = Constants.Funtion_Delete)
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id,HttpServletRequest request) throws Exception {
		APPO bean = apService.findOne(id);
		bean.setIsdelete("Y");
		bean.setUpdatetime(new Date());
		UserPO user =(UserPO) WebUtils.getSessionAttribute(request, Constants.PORTAL_LOGIN_USER);
		bean.setUpdateusername(user.getUsername());
		apService.updateAP(bean);
		return ajaxDoneSuccess("删除成功", false, REL_ID);
	}
}
