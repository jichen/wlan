<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp" %>
<%
	
	if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
			.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf(
			"XMLHttpRequest") > -1))){ //以jsp页面的方式返回

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>401 - 页面不存在</title>
	<link rel="stylesheet" href="${ctx}/static/error/style.css"/>
	<link rel="stylesheet" href="${ctx}/static/error/error.css"/>
</head>

<body>
<div id="error">
  <div class="leftbar"><img src="${ctx}/static/error/images/404.png"/></div>
  <div class="error_word">
    <p>很抱歉此页面没有服务到您请选择其他栏目继续探索</p>
    <div class="button">
       <a href="${ctx}">返回首页</a>
    </div>
  </div>
</div>   
</body>
</html>
<%
	}else {
		response.setStatus(200);
		response.setHeader("session-status","pagenotfind");
		String message = "{\"statusCode\":\"300\" ,\"message\":\"您访问的资源不存在0！\"}";
		response.getWriter().write(message) ;
	}
%>