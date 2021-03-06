package com.cmct.portal.statistics.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.cmct.portal.po.StatisticsVo;
import com.cmct.portal.service.StatisticsVoService;


@Controller
@RequestMapping("/statistics/wlanUserLog")
public class WUserInterLogController extends AbstractController {
	
	@Autowired
	private StatisticsVoService statisticsVoService;

	/**
	 * 
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page,PageFormModel pageForm,HttpServletRequest req) throws Exception {
		Map<String,Object> mp=new HashMap<String,Object>();
		String type=pageForm.getDatetype();
		String time1=req.getParameter("startTime");
		String time2=req.getParameter("endTime");
		mp.put("page",page);
		mp.put("pageForm",pageForm);
		if(type==null && time1==null && time2==null){
			return new ModelAndView("/pages/view/statistics/userwlanlog/userwlanlist",mp);
		}else{
			Map<String,Object> propertiesMap =new HashMap<String,Object>();
			Integer start = 0;
			Integer limit=page.getNumPerPage();
			String sql_time="";
			//根据不同的维度，构造不同的sql
			if(type.equals("m")){
				sql_time=" DATE_FORMAT(authtime,'%Y-%m') ";
			}else if(type.equals("d")){
				sql_time=" DATE_FORMAT(authtime,'%Y-%m-%d') ";				
			}else if(type.equals("h")){
				sql_time=" DATE_FORMAT(authtime,'%Y-%m-%d %H') ";
			}
			
			
			String sql="select "+sql_time+" as time , count(id) as num from WLANUSERLogPO where 1=1 ";
			String where_sql="";
			if(pageForm.getPageNum()>0){
				start=(Integer.valueOf(pageForm.getPageNum())-1)*page.getNumPerPage();
			}
			//登录时间
			if(pageForm.getStartTime()!=null){
				propertiesMap.put("time1", DateUtil.getDate(time1+":00", DateUtil.DATE_FORMAT_TIME));
				where_sql=where_sql+" and authtime >= :time1";
				mp.put("startTime",time1);
			}
			if(pageForm.getEndTime()!=null){
				propertiesMap.put("time2", DateUtil.getDate(time2+":59", DateUtil.DATE_FORMAT_TIME));
				where_sql=where_sql+" and authtime <= :time2 ";
				mp.put("endTime",time2);
			}			
			String group_sql=" group by "+sql_time;
			String orderby=" order by authtime desc";
			
			sql=sql+where_sql+group_sql+orderby;
			

			//String sqlCount="select count(*) from ( "+sql+" ) ";
			
			List<Object> list=statisticsVoService.CountWlanList(sql, propertiesMap, start, limit);
			
			page.setTotalCount(list.size());
			//sql=select  '2013-01-01' as time, count(id) as num from WLANUSERLogPO where 1=1 group by authresult
			//String Sql="select DATE_FORMAT(authtime,'%Y-%m-%d %H') as time, count(id) as num from WLANUSERLogPO where 1=1 group by DATE_FORMAT(authtime,'%Y-%m-%d %H') " ;
			//List<Object> list=statisticsVoService.find_ALL(Sql, 0, 10);
			
			Iterator<Object> i=list.iterator();
			List<StatisticsVo> volist =new ArrayList<StatisticsVo>();
			while(i.hasNext()){
				StatisticsVo vo=new StatisticsVo();
				Object[] obj=(Object[])i.next();
				vo.setTime(obj[0].toString());
				vo.setNum(Integer.valueOf(obj[1].toString()));
				volist.add(vo);	
			}
			mp.put("list", volist);
			mp.put("page",page);
			mp.put("pageForm",pageForm);
			return new ModelAndView("/pages/view/statistics/userwlanlog/userwlanlist",mp);
		}
	}
	
	
	
	
}
