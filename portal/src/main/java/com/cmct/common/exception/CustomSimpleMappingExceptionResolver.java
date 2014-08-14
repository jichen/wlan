package com.cmct.common.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.cmct.common.util.ui.DwzAJaxRsp;
import com.cmct.common.util.ResourceDef;


/**
 * @title 		
 * @description	处理全局异常
 * @usage		
 * @copyright	Copyright 2011  SHCMCT Corporation. All rights reserved.
 * @company		上海中移通信技术工程有限公司
 * @author		John.Yao
 * @create		2013-1-9 下午12:41:20
 */ 
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
	
	private static Logger logger = LoggerFactory.getLogger(CustomSimpleMappingExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.error("catched exception :",ex);
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
					.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf(
					"XMLHttpRequest") > -1))) {
				//jsp 返回
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);
			} else {
				//ajax 请求，返回json
				DwzAJaxRsp rsp = new DwzAJaxRsp();
				rsp.setStatusCode(ResourceDef.OPERATE_STATUS_CODE_ERROR);
				rsp.setMessage(ResourceDef.EXCEPTION_ERROR_MESSAGE);
				try {
					PrintWriter writer = response.getWriter();
					writer.write(rsp.toJSON());
					writer.flush();
					
				} catch (IOException e) {
					logger.error("output exception error :", e);
				}
				return null;
			}

		} else {
			return null;
		}

	}
	
}
