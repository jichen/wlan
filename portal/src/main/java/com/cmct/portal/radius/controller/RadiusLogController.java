package com.cmct.portal.radius.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.cmct.common.Json.JSONUtil;
import com.cmct.common.util.DateUtil;
import com.cmct.common.util.PropertyConfigureHandler;
import com.cmct.common.util.ui.Page;
import com.cmct.common.util.ui.PageFormModel;
import com.cmct.portal.po.RadPostAuthVO;


@Controller
@RequestMapping("/radiusLog")
public class RadiusLogController {
	private static Logger logger = LoggerFactory.getLogger(RadiusLogController.class);
	private static String REL_ID = "radiusLogList";
	
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page,PageFormModel pageForm,HttpServletRequest req) throws Exception {
		HttpClient client = new HttpClient();
		String path = (String)PropertyConfigureHandler.getCtxPropertiesMap().get("RadiusLogToPortal");
		PostMethod pm = new PostMethod(path);
		pm.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		
		String time1="",time2="",username="";
		Map mp=new HashMap();
		List<RadPostAuthVO> list=new ArrayList<RadPostAuthVO>();
		
		if(pageForm.getName()!=null){
			if(pageForm.getName().trim()!=""){
				username=pageForm.getName().trim();
			}
		}		
		if(pageForm.getStartTime1()!=null){
			if(pageForm.getStartTime1()!=""){
				mp.put("startTime",pageForm.getStartTime1());
				time1=pageForm.getStartTime1();
			}
		}
		if(pageForm.getEndTime1()!=null){
			if(pageForm.getEndTime1()!=""){
				mp.put("endTime",pageForm.getEndTime1());
				time2=pageForm.getEndTime1();
			}
		}

		Integer start = 0;
		Integer limit=page.getNumPerPage();
		if(pageForm.getPageNum()>0){
			start=(Integer.valueOf(pageForm.getPageNum())-1)*page.getNumPerPage();
		}
		
		NameValuePair[] vp = {
				new NameValuePair("username",username),
				new NameValuePair("time1",time1),
				new NameValuePair("time2",time2),
				new NameValuePair("start",start+""),
				new NameValuePair("limit",limit+"")
			};
		
		pm.addParameters(vp);
		
		//获取返回值
		int Status=client.executeMethod(pm);
		
		//获取json格式的返回值
		String result=new String(pm.getResponseBodyAsString().getBytes("utf-8"));
		
		System.out.println(result);
		int num=result.indexOf("+");
		String count=result.substring(num+1);
		result=result.substring(0,num);

		if(result.length()>4){
			String[] str=result.split("},");			
			for(int i=0;i<str.length;i++){
				if(str[i]!=null && str[i]!=""){
					if(i==0){
						str[i]=str[i].substring(1)+"}";
					}else if(i==(str.length-1)){
						str[i]=str[i].substring(0,str[i].length()-1);
					}else{
						str[i]=str[i]+"}";
					}
					
				}
			}
			for(int i=0;i<str.length;i++){
				RadPostAuthVO vo=JSONUtil.fromJSON(str[i], RadPostAuthVO.class);
				list.add(vo);
			}
		}
		page.setTotalCount(Integer.valueOf(count));
		mp.put("list",list);
		mp.put("page",page);
		mp.put("pageForm",pageForm);
		return new ModelAndView("/pages/view/radius/radiuslog/radiusloglist",mp);
		
	}

	

}
