package com.cmct.radius.interfaces.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmct.common.util.DateUtil;
import com.cmct.common.util.Json.JSONUtil;
import com.cmct.radius.po.RadPostAuthPO;
import com.cmct.radius.service.RadPostAuthService;



@Controller
@RequestMapping(value = "/RLogToP")
public class RadiusLogToPortalController {
	@Autowired
	private RadPostAuthService radPostAuthService;
	
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public String radiusLog(String username,String time1,String time2,String start, String limit,
			HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> propertiesMap =new HashMap<String, Object>();
		String sql="from RadPostAuthPO where 1=1 ";
		String where_sql="";
		String orderby=" order by authdate desc,username asc";	
		
		if(username!=null && username.length()>1){
			propertiesMap.put("username",username);
			where_sql=where_sql+" and username >= :username";
		}		
		
		//登录时间
		if(time1!=null && time1.length()>1){
			propertiesMap.put("authdate1",DateUtil.getDate(time1+" 00:00:00",DateUtil.DATE_FORMAT_TIME));
			where_sql=where_sql+" and authdate >= :authdate1";
		}
		if(time2!=null && time1.length()>1){
			//结束时间是当日的晚上23:59:59
			propertiesMap.put("authdate2",new Date(DateUtil.getDate(time2+" 00:00:00",DateUtil.DATE_FORMAT_TIME).getTime()+86400000-1000));
			where_sql=where_sql+" and authdate <= :authdate2 ";
		}
		sql=sql+where_sql+orderby;
		String sqlCount="select count(*) from RadPostAuthPO where 1=1 " + where_sql;
		List<RadPostAuthPO> list = radPostAuthService.pageQuery(sql, propertiesMap, Integer.valueOf(start), Integer.valueOf(limit));
		Integer count=radPostAuthService.getTotalCount_where(sqlCount, propertiesMap);
		
		String toJson=JSONUtil.toJSON(list) ;
		toJson=toJson+"+"+count;
		return toJson;
	}
	

	
}
