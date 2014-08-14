package com.cmct.portal.login.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.cmct.common.Constants;
import com.cmct.common.annotation.Log;
import com.cmct.common.shiro.URLAuthorizationFilter;
import com.cmct.common.util.AbstractController;
import com.cmct.portal.po.TemplateMapPO;
import com.cmct.portal.po.TemplatePagePO;
import com.cmct.portal.po.UserPO;
import com.cmct.portal.service.TemplateMapService;
import com.cmct.portal.service.TemplatePageService;
import com.cmct.portal.service.UserService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
public class LoginController extends AbstractController {
	

	private static Logger logger = LoggerFactory.getLogger(URLAuthorizationFilter.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TemplatePageService templatePageService;

	@Autowired
	private TemplateMapService templateMapService;
	
	/**
	 * 
	 * @param request
	 * @param resp
	 * @throws IOException
	 * @throws TemplateException
	 * wlan用户登录推送处理
	 * 
	 */
	
	@RequestMapping(value = "/")
	public void createPage(HttpServletRequest request,HttpServletResponse response) throws IOException, TemplateException {
		//判断用户的组别来确认是否需要推送页面和手动认证
		
		//获取URL中的参数
		String userip=request.getParameter("wlanuserip");
		//String ssid=request.getParameter("ssid");
		//判断访问的终端类型
		String type="pc";
		String userAgent = request.getHeader("user-agent").toLowerCase();
		System.out.println("userAgent==="+userAgent);
		if (userAgent.contains("iphone") || userAgent.contains("ipad") || userAgent.contains("android")) {		
			type="mobile";
			//return "/pages/authenticate/mobileAuth";			
		} else {
			type="pc";
		}		
		//登录的AP位置，决定使用那套模板页面

		String clientMac="14:9f:e8:03:63:f7";
//		SnmpClientofAP obj=apMIBService.findAtoCByRunInfo(clientMac);
//		System.out.println(obj.getApMac());
//		String apMac=obj.getApMac().toString();
//		
		
		//获取模板页面
		int id=1;
		TemplatePagePO bean = templatePageService.findOne(id);
		Configuration cfg = new Configuration();	   
		//确定模板
		cfg.setServletContextForTemplateLoading(request.getSession().getServletContext(), "/");
		cfg.setDefaultEncoding("UTF-8");
		Template t = cfg.getTemplate(bean.getTemplatelocal()+"/"+bean.getPagename());
		t.setEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Map<String,Object> propertiesMap = new HashMap<String,Object>();
		
		//根据终端显示模板内容
		propertiesMap.put("template_id",id);
		propertiesMap.put("teminal_type",type);
		List<TemplateMapPO> list=templateMapService.findList(propertiesMap);
		//实际模板缺损，使用另一终端模板内容
		if(list.size()<1){
			//缺损判断
			if(type.equals("pc")){
				type="mobile";
			}else{
				type="pc";
			}
			list=null;
			propertiesMap.put("template_id",id);
			propertiesMap.remove("teminal_type");
			propertiesMap.put("teminal_type",type);
			list=templateMapService.findList(propertiesMap);
		}
		
		//获取显示数量
		Integer textnum=bean.getTextcount();
		Integer cssnum=bean.getCsscount();
		Integer imagenum=bean.getImagecount();
		//获取相应的显示内容和对应模板的标签名称
		for(int i=1;i<textnum+1;i++){
			for(TemplateMapPO map:list){
				if(map.getData_type().equals("text")){
					if(map.getValue_name().equals("text_"+i)){
						//System.out.println("text_"+i+"====="+map.getValue());
						dataMap.put("text_"+i,map.getValue());
					}	
				}
			}
		}
		for(int i=1;i<cssnum+1;i++){
			for(TemplateMapPO map:list){
				if(map.getData_type().equals("css")){
					if(map.getValue_name().equals("css_"+i+"_"+type)){
						//System.out.println("css_"+i+"====="+map.getValue());
						dataMap.put("css_"+i,map.getValue());
					}
				}
			}
		}
		for(int i=1;i<imagenum+1;i++){
			for(TemplateMapPO map:list){
				if(map.getData_type().equals("image")){
					if(map.getValue_name().equals("image_"+i+"_"+type)){
						////System.out.println("image_"+i+"====="+map.getValue());
						dataMap.put("image_"+i,map.getValue());
						dataMap.put("image_url_"+i,map.getUrl());
				   	}
			   	}
		   	}
		}
		//System.out.println("userip=="+userip);
		//System.out.println("dataMap=="+dataMap.toString());
		
		/*
		 * 固定需要的参数不进行修改
		 * 
		 */

		//String wlanurl=request.getParameter("wlanuserfirsturl");
		dataMap.put("userip",userip);
		dataMap.put("clientMac",clientMac);
		//dataMap.put("wlanurl",wlanurl);
		dataMap.put("ctx", request.getContextPath());
		PrintWriter out = response.getWriter();
		t.process(dataMap, out);
	}
	
	
	/**
	 * 登录页面
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("/login");
	}
	
		
	@Log(module = Constants.MODULE_LOGIN_SYSTEM, function = Constants.Funtion_Login, isSign = true)
	@RequestMapping(value = "/submit")
	@ResponseBody
	public Map<String,Object> submit(@RequestParam String username, @RequestParam String password,
			@RequestParam String check,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		Map<String,Object> rs = new HashMap<String,Object>();
		logger.debug("***************************************");
		logger.debug("submit...");
		logger.debug("***************************************");
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(check)) {
			rs.put("status", 0);
			rs.put("msg", "用户密，密码不能为空");
			return rs;
		}
		String verify = (String) request.getSession().getAttribute("verify");
		request.getSession().removeAttribute("verify");
		if (!check.equals(verify)) {
			rs.put("status", 0);
			rs.put("msg", "输入的验证码不匹配");
			return rs;
		}
		UserPO user = userService.findOne(username);
		if(user==null){
			rs.put("status", 0);
			rs.put("msg", "帐号不存在");
			return rs;
		}else if(!user.getPwd().equals(password)){
			rs.put("status", 0);
			rs.put("msg", "密码错误");
			return rs;
		}

		WebUtils.setSessionAttribute(request, Constants.PORTAL_LOGIN_USER,user);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		subject.login(token);
		session.setAttribute("_screenName", user.getUsername());
		rs.put("status", 1);
		return rs;
	}
	
	/**
	 * 登录引导页
	 * @return
	 */
	@RequestMapping(value = "/index")
	@ResponseBody
	public ModelAndView index(HttpSession session,HttpServletRequest request) {
		//跳转主页面
		ModelAndView mav = new ModelAndView("pages/view/index");
		// 返回
		return mav;
	}
	
	/**
	 * 登录引导页
	 * @return
	 */
	@RequestMapping(value = "/verify")
	public ModelAndView verify() {
		ModelAndView mav = new ModelAndView("pages/login/verify");
		return mav;
	}

	@RequestMapping(value = "/exit")
	public ModelAndView exit(HttpSession session) {
		SecurityUtils.getSubject().logout();
		return new ModelAndView("/login");
	}
	
	@RequestMapping("/timeout")
	@ResponseBody
	public String timeout() {
		return ajaxDoneTimeout();
	}

}
