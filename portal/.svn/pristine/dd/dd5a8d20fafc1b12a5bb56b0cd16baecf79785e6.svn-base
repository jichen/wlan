package com.cmct.common.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLAuthorizationFilter extends AuthorizationFilter {
	// 日志
	private static Logger logger = LoggerFactory.getLogger(URLAuthorizationFilter.class);
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object weaving)
			throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		// 得到URI
		String ctxPath = req.getContextPath();
		String url = req.getRequestURI().replaceAll("//", "/");
		if(!ctxPath.equals("/")){
			url = url.replaceFirst(ctxPath, StringUtils.EMPTY);
		}
		// 有没有?号
		int index1 = url.indexOf("?");
		if(index1>=0){
			url = url.substring(0,index1);
		}
		// 将数字替换成*
		String URL = url.substring(url.lastIndexOf("/") + 1);
		if (URL.matches("\\d*")) {
			url = url.substring(0, url.lastIndexOf("/")) + "/*";
		}
		
		// 权限验证
		Subject subject = SecurityUtils.getSubject();
		boolean flag = subject.isPermitted(url);
		// 输出访问URL
		logger.debug("*****************************************");
		logger.debug("url :" + url);
		logger.debug("flag:" + String.valueOf(flag));
		logger.debug("*****************************************");
		return flag;
	}
}