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
import com.cmct.portal.po.CustomerPO;
import com.cmct.portal.po.UserPO;
import com.cmct.portal.service.CustomerService;


@Controller
@RequestMapping("/sysmanagement/customer")
public class CustomerController extends AbstractController {
	
	
	private static String REL_ID = "customerList";
	
	@Autowired
	private CustomerService customerService;

	
	/**
	 *输出列表 
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page,PageFormModel pageForm,HttpServletRequest req) throws Exception {		
		
		Collection<CustomerPO> list=null;
		List<CustomerPO> customers=new ArrayList<CustomerPO>();
		CustomerPO customer;
		Map propertiesMap =new HashMap();
		//设置显示数量
		Integer start = 0;
		Integer limit=page.getNumPerPage();
		if(pageForm.getPageNum()>0){
			start=(Integer.valueOf(pageForm.getPageNum())-1)*page.getNumPerPage();
		}
		
		//查询语句

		String sql="from CustomerPO where 1=1 ";
		String sqlCount="select count(*) from CustomerPO where 1=1 ";
		String where_sql="";
		if(pageForm.getCust_name()!=null){
			if(pageForm.getCust_name().trim().length()>0){
				propertiesMap.put("cust_name", "%"+pageForm.getCust_name().trim()+"%");
				where_sql=where_sql+" and cust_name like :cust_name";
			}
		}
		if(pageForm.getLocation()!=null){
			if(pageForm.getLocation().trim().length()>0){
				propertiesMap.put("location", pageForm.getLocation().trim());
				where_sql=where_sql+" and address= :location";
			}
		}
		if(pageForm.getIsdelete()!=null){
			if(pageForm.getIsdelete().trim().length()>0){
				propertiesMap.put("isdelete", pageForm.getIsdelete().trim());
				where_sql=where_sql+" and isdelete= :isdelete";
			}
		}
		if(pageForm.getContact()!=null){
			if(pageForm.getContact().trim().length()>0){
				propertiesMap.put("contact", "%"+pageForm.getContact().trim()+"%");
				where_sql=where_sql+" and contact like :contact";
			}
		}
		if(pageForm.getPhone()!=null){
			if(pageForm.getPhone().trim().length()>0){
				propertiesMap.put("phone", "%"+pageForm.getPhone().trim()+"%");
				where_sql=where_sql+" and phone like :phone";
			}
		}

		sql=sql+where_sql;
		sqlCount=sqlCount+where_sql;
		
		//获取列表
		list=customerService.findPages_sql(sql,propertiesMap, start, limit);

		//计算符合条件的行数，分页需要
		page.setTotalCount(customerService.getTotalCount_where(sqlCount, propertiesMap));		
		

		for(CustomerPO customerPO:list){
			customer=new CustomerPO();
			customer.setId(customerPO.getId());
			customer.setCust_name(customerPO.getCust_name());
			customer.setAddress(customerPO.getAddress());
			customer.setContact(customerPO.getContact());
			customer.setPhone(customerPO.getPhone());
			customer.setIsdelete(customerPO.getIsdelete());
			customer.setRemark(customerPO.getRemark());
			customers.add(customer);
		}
		Map mp=new HashMap();
		mp.put("list",customers);
		mp.put("page",page);
		mp.put("pageForm",pageForm);
		return new ModelAndView("/pages/view/sysmanagement/customer/list",mp);
	}

	/*
	 * 预加载
	 */	
	@RequestMapping(value = "/preadd")
	public ModelAndView preAdd() throws Exception {
		ModelAndView mav = new ModelAndView("pages/view/sysmanagement/customer/add");
		return mav;
	}

	/*
	 *添加操作
	 *
	 */
	@Log(module = Constants.MODULE_CUSTOMER, function = Constants.Funtion_Add)
	@RequestMapping(value = "/add")
	@ResponseBody
	public String add(CustomerPO bean,HttpServletRequest request){
		bean.setCreatetime(new Date());
		UserPO user =(UserPO) WebUtils.getSessionAttribute(request, Constants.PORTAL_LOGIN_USER);
		bean.setCreateusername(user.getUsername());
		bean.setIsdelete("N");
		customerService.saveCustomer(bean);
		return ajaxDoneSuccess(" " , true , REL_ID);
	}	
	
	/*
	 * 预加载
	 */	
	@RequestMapping(value = "/preUpdate/{id}")
	public ModelAndView preUpdate(@PathVariable("id") Integer id) throws Exception {
		CustomerPO bean = customerService.findOne(id);
		ModelAndView mav = new ModelAndView("/pages/view/sysmanagement/customer/update");
		mav.addObject("bean", bean);
		return mav;
	}

	/*
	 * 更新操作
	 */

	@Log(module = Constants.MODULE_CUSTOMER, function = Constants.Funtion_Update)
	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(CustomerPO bean,HttpServletRequest request) throws Exception {
		if(bean.getIsdelete()==null || bean.getIsdelete()==""){
			bean.setIsdelete("N");
		}
		bean.setUpdatetime(new Date());
		UserPO user =(UserPO) WebUtils.getSessionAttribute(request, Constants.PORTAL_LOGIN_USER);
		bean.setUpdateusername(user.getUsername());
		customerService.updateCustomer(bean);
		return ajaxDoneSuccess(" ",true,REL_ID);
	}
	
	/*
	 * 删除（删除只是将数据更新）
	 */
	@Log(module = Constants.MODULE_CUSTOMER, function = Constants.Funtion_Delete)
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id,HttpServletRequest request) throws Exception {
		CustomerPO bean = customerService.findOne(id);
		bean.setIsdelete("Y");
		bean.setUpdatetime(new Date());
		UserPO user =(UserPO) WebUtils.getSessionAttribute(request, Constants.PORTAL_LOGIN_USER);
		bean.setUpdateusername(user.getUsername());
		customerService.updateCustomer(bean);
		return ajaxDoneSuccess("删除成功", false, REL_ID);
	}

	/*
	 * 显示选择列表
	 */
	@RequestMapping(value = "/lookUp")
	public ModelAndView custList(Page page,PageFormModel pageForm,HttpServletRequest req) throws Exception {
		Collection<CustomerPO> list=null;
		List<CustomerPO> customers=new ArrayList<CustomerPO>();
		CustomerPO customer;
		
		Map propertiesMap =new HashMap();
		Integer start = 0;
		Integer limit=page.getNumPerPage();
		if(pageForm.getPageNum()>0){
			start=(pageForm.getPageNum()-1)*page.getNumPerPage();
		}
		
		String sqlCount="select count(*) from CustomerPO where 1=1 ";
		
		//默认删除的不显示
		propertiesMap.put("isdelete", "N");
		sqlCount=sqlCount+" and isdelete= :isdelete";
		


		if(pageForm.getCust_name()!=null && pageForm.getCust_name().trim().length()>0){
			propertiesMap.put("cust_name", "%"+pageForm.getCust_name().trim()+"%");
			sqlCount=sqlCount+" and cust_name like :cust_name";
		}
		if(pageForm.getLocation()!=null && pageForm.getLocation().trim().length()>0){
			propertiesMap.put("location", "%"+pageForm.getLocation().trim()+"%");
			sqlCount=sqlCount+" and address like :location";
		}
		if(pageForm.getContact()!=null && pageForm.getContact().trim().length()>0){
			propertiesMap.put("contact", "%"+pageForm.getContact().trim()+"%");
			sqlCount=sqlCount+" and contact like :contact";
		}
		if(pageForm.getPhone()!=null && pageForm.getPhone().trim().length()>0){
			propertiesMap.put("phone", "%"+pageForm.getPhone().trim()+"%");
			sqlCount=sqlCount+" and phone like :phone";
		}
		
		
		list=customerService.findPages(propertiesMap, start, limit);

		page.setTotalCount(customerService.getTotalCount_where(sqlCount, propertiesMap));		
		
		for(CustomerPO customerPO:list){
			customer=new CustomerPO();
			customer.setId(customerPO.getId());
			customer.setCust_name(customerPO.getCust_name());
			customer.setAddress(customerPO.getAddress());
			customer.setContact(customerPO.getContact());
			customer.setPhone(customerPO.getPhone());
			customer.setIsdelete(customerPO.getIsdelete());
			customer.setRemark(customerPO.getRemark());
			customers.add(customer);
		}
		Map mp=new HashMap();
		mp.put("list",customers);
		mp.put("page",page);
		mp.put("pageForm",pageForm);
		return new ModelAndView("/pages/view/sysmanagement/customer/customerLookUp",mp);
	}
	
}
