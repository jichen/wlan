/**
 * 
 */
package com.cmct.common.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @title 		
 * @description	session timeout hanlder for general http request and ajax 
 * @usage		
 * @copyright	Copyright 2011  SHCMCT Corporation. All rights reserved.
 * @company		上海中移通信技术工程有限公司
 * @author		John.Yao
 * @create		2013-1-15 下午6:05:50
 */
public class SessionCheckFilter extends UserFilter {
	
	private static Logger logger = LoggerFactory.getLogger(SessionCheckFilter.class);
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
		if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {
			logger.debug("it is not an ajax request.");
			saveRequestAndRedirectToLogin(request, response);
		} else {
			logger.debug("it's an ajax request.");
			httpServletResponse.addHeader("WWW-Authentication", "ACME-AUTH");
			httpServletRequest.getRequestDispatcher("/timeout").forward(httpServletRequest, httpServletResponse);
		}
		return false;
	}

}
