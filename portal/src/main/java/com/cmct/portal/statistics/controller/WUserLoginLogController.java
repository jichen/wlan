package com.cmct.portal.statistics.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmct.common.util.AbstractController;
import com.cmct.common.util.DateUtil;
import com.cmct.common.util.ui.PageFormModel;
import com.cmct.common.util.ui.Page;
import com.cmct.portal.po.WLANUSERLogPO;
import com.cmct.portal.service.WLANUSERLogService;


@Controller
@RequestMapping("/statistics/wlanUserLogin")
public class WUserLoginLogController extends AbstractController {
	
	
	@Autowired
	private WLANUSERLogService wUSERLogService;


	@RequestMapping(value = "/list")
	public ModelAndView list(Page page,PageFormModel pageForm,HttpServletRequest req) throws Exception {
		Map<String,Object> propertiesMap =new HashMap<String,Object>();
		Map<String,Object> mp=new HashMap<String,Object>();
		Integer start = 0;
		Integer limit=page.getNumPerPage();
		String sqlCount="select count(*) from WLANUSERLogPO where 1=1 ";
		String sql=" from WLANUSERLogPO where 1=1 ";
		String where_sql="";
		if(pageForm.getPageNum()>0){
			start=(Integer.valueOf(pageForm.getPageNum())-1)*page.getNumPerPage();
		}

		if(pageForm.getAp_id()!=null){
			if(pageForm.getAp_id().trim().length()>0){
				propertiesMap.put("apid", pageForm.getAp_id());
				where_sql=where_sql+" and apid= :apid";
			}
		}
		//登录时间
		if(pageForm.getAuth_time1()!=null){
			propertiesMap.put("authtime1", pageForm.getAuth_time1());
			where_sql=where_sql+" and authtime >= :authtime1";
			mp.put("authtime1",DateUtil.getFormatStringDate(pageForm.getAuth_time1(), DateUtil.DATE_FORMAT));
		}
		if(pageForm.getAuth_time2()!=null){
			//结束时间是当日的晚上23:59:59
			pageForm.setAuth_time2(new Date(pageForm.getAuth_time2().getTime()+86400000-1000));
			propertiesMap.put("authtime2", pageForm.getAuth_time2());
			where_sql=where_sql+" and authtime <= :authtime2 ";
			mp.put("authtime2",DateUtil.getFormatStringDate(pageForm.getAuth_time2(), DateUtil.DATE_FORMAT));
		}
		//退出时间
		if(pageForm.getExit_time1()!=null){
			propertiesMap.put("exittime1", pageForm.getExit_time1());
			where_sql=where_sql+" and exittime >= :exittime1";
			mp.put("exittime1",DateUtil.getFormatStringDate(pageForm.getExit_time1(), DateUtil.DATE_FORMAT));
		}
		if(pageForm.getExit_time2()!=null){
			//结束时间是当日的晚上23:59:59
			pageForm.setExit_time2(new Date(pageForm.getExit_time2().getTime()+86400000-1000));
			propertiesMap.put("exittime2", pageForm.getExit_time2());
			where_sql=where_sql+" and exittime <= :exittime2 ";
			mp.put("exittime2",DateUtil.getFormatStringDate(pageForm.getExit_time2(), DateUtil.DATE_FORMAT));
		}
		
		if(pageForm.getName()!=null){
			if(pageForm.getName().trim().length()>0){
				propertiesMap.put("username", "%"+pageForm.getName().trim()+"%");
				where_sql=where_sql+" and username like :username";
			}
		}
		//只查看登录的成功日志
		propertiesMap.put("authresult", "OK");
		where_sql=where_sql+" and authresult= :authresult";
		String orderby=" order by authtime desc";
		
		sql=sql+where_sql+orderby ;
		sqlCount=sqlCount+where_sql;
		List<WLANUSERLogPO> list=wUSERLogService.pageQuery(sql ,propertiesMap, start, limit);

		page.setTotalCount(wUSERLogService.getTotalCount_where(sqlCount, propertiesMap));	

		mp.put("list",list);
		mp.put("page",page);
		mp.put("pageForm",pageForm);
		return new ModelAndView("/pages/view/statistics/userloginlog/userloginlist",mp);
	}
	
	
	
	
	
}
