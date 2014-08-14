<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ include file="/include.inc.jsp"%>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%
	Throwable ex = null;
	if (request.getAttribute("javax.servlet.error.exception") != null) {
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
	} else {
		ex = exception;
	}

	//记录日志
	if (ex != null) {
		Logger logger = LoggerFactory.getLogger("500.jsp");
		logger.error(ex.getMessage(), ex);	
	}
%>
<%
	
	if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
			.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf(
			"XMLHttpRequest") > -1))){ //以jsp页面的方式返回

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>重新登录</title>
	<link rel="stylesheet" href="${ctx}/static/error/style.css"/>
	<link rel="stylesheet" href="${ctx}/static/error/error.css"/>
</head>

<body>
<div id="error">
  <div class="leftbar"><img src="${ctx}/static/error/images/500.png"/></div>
  <div class="error_word">
	<p>无法找到改页面<br/>
                       该页面可能数据库出错或者链接出错
    </p>
    <div class="button">
       <a href="${ctx}/login">返回首页</a>
    </div>
  </div>
</div>   
</body>
</html>
<%
	}else {
		response.setStatus(200);
		response.setHeader("session-status","pagenotfind");
		String message = "{\"statusCode\":\"300\" ,\"message\":\"您访问的资源不存在2！\"}";
		response.getWriter().write(message) ;
	}
%>
