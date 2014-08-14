package com.cmct.radius.interfaces.controller;


import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmct.common.util.Constants;
import com.cmct.common.util.Passwordmd5;
import com.cmct.radius.service.RadUserService;
import com.cmct.radius.service.SMSLogService;




@Controller
@RequestMapping(value = "/dinamicPwd")
public class DinamicPwdController {
	
	@Autowired
	private RadUserService radUserService;

	@Autowired
	private SMSLogService smsLogService;
	
	
	@RequestMapping(value = "/fetch")
	@ResponseBody
	public String fetch(HttpServletResponse rsp,String username,String validtime) {
		try {
			/**
			 * 计算失效时间
			 */
			Integer time=Integer.valueOf(validtime)*60;
			String onTimePassword=radUserService.generateOnTimePassword(username,time);
			System.out.println("时间："+new Date()+"    密码:"+onTimePassword);
			
			String content="账号："+username+"，获取动态短信密码为："+onTimePassword+"，该密码有效期为："+validtime+"分钟。";
			
			
			String pwd = Passwordmd5.MD5(Constants.SMS_PWD);
		
			//发送短信给用户
			HttpClient client = new HttpClient();
			PostMethod pm = new PostMethod(Constants.SMS_URL);
			pm.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
			
			NameValuePair[] vp = {
					new NameValuePair("uid",Constants.SMS_USER),
					new NameValuePair("pwd",pwd),
					new NameValuePair("time",""),
					new NameValuePair("encode","UTF-8"),
					new NameValuePair("mobile",username),
					new NameValuePair("content",content)
				};
			
			pm.addParameters(vp);
			
			//获取返回值
			int Status=client.executeMethod(pm);
			

			 
			//获取json格式的返回值
			String sms_result=new String(pm.getResponseBodyAsString().getBytes("utf-8"));
			//System.out.println(sms_result);
			JsonFactory jf=new MappingJsonFactory();
			JsonParser jp=jf.createJsonParser(sms_result);
			jp.nextToken();
			HashMap<String,String> map=new HashMap<String,String>();
			while(jp.nextToken()!=JsonToken.END_OBJECT){
				map.put(jp.getCurrentName(), jp.getText());
				//System.out.println(jp.getCurrentName()+"======"+jp.getText());
			}
			//获取后保存至数据库中username，date，result，text
			//System.out.println(map.get("result"));
			//System.out.println(map.get("text"));
			
			//记录是否发生成功
			if(map.get("result").toString().equals("100")){
				smsLogService.saveSMSLOG(username,map.get("result"));
			}else{
				rsp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				smsLogService.saveSMSLOG(username,map.get("result"));
			}
			
			return "";
		} catch(Exception e) {
			e.printStackTrace();
			rsp.setStatus(HttpServletResponse.SC_OK);
			return "";
		}
	}
	
	
	@RequestMapping(value = "/isoverdue")
	@ResponseBody
	public String isoverdue(HttpServletResponse rsp,String username,String validtime) {
		try {
			String result=radUserService.isoverDuePassword(username, validtime);
			if(result=="effective"){
				rsp.setStatus(HttpServletResponse.SC_OK);
			}else{
				rsp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			return "";
		} catch(Exception e) {
			e.printStackTrace();
			rsp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return "";
		}
	}
}
