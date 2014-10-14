<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>WLAN</title>

<link href="${ctx}/static/themes/default/style.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="${ctx}/static/themes/css/core.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="${ctx}/static/themes/css/print.css" rel="stylesheet"
	type="text/css" media="print" />
<link href="${ctx}/static/themes/css/extends.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="${ctx}/static/uploadify/css/uploadify.css" rel="stylesheet"
	type="text/css" media="screen" />
<!--[if IE]>
<link href="${ctx}/static/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->
</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav my_header">
				<ul class="nav">
					<li style="background: none;"><a href="javascript:void(0);">欢迎您，<b>${_screenName}</b></a></li>
					<li><a href="${ctx}/security/user/prePassword" rel="upload"
						target="dialog" mask="true" width="450" height="190">修改密码</a></li>
					<li><a href="${ctx}/exit">退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li style="color: #B9CCDA;"><fmt:formatDate
							value="<%=new java.util.Date()%>" pattern="yyyy年MM月dd日  EEEE" /></li>
				</ul>
			</div>
			<!-- navMenu -->
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>主菜单</h2>
					<div>收缩</div>
				</div>
				<!-- 新增模块  -->
				<div class="accordion" fillSpace="sidebar">
					<shiro:hasPermission name="AUTHRN">
					<!-- 鉴权接入管理模块 -->
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>鉴权接入管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<!-- 一期不开发 -->
							<!-- 
							<li><a href="${ctx}/security/employee/list" target="navTab"
								rel="employeeList">NAS配置</a></li>
							<li><a href="${ctx}/security/role/list" target="navTab"
								rel="roleList">服务状态</a></li>
							<li><a href="<%=request.getContextPath() %>/radiusLog/list" target="navTab"
								rel="userList">登录用户管理</a></li>
							-->
							<shiro:hasPermission name="AUTHRN_RADIUS_LOG">
							<li><a href="${ctx}/radiusLog/list" target="navTab"
								rel="radiusLogList">Radius日志 </a></li>
							</shiro:hasPermission>
						</ul>
					</div>	
					</shiro:hasPermission>				
					<!-- Portal管理模块 -->
					<shiro:hasPermission name="PORTAL">
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>Portal管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<shiro:hasPermission name="PORTAL_AUTH_ROLE">
							<li><a href="${ctx}/portalmanagement/rules/preadd" target="navTab"
								rel="ruleadd">认证规则设置</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="PORTAL_AUTH_PAGE">
							<li><a href="${ctx}/templatepage/authpage/list" target="navTab"
								rel="templatepageList">认证页面设置</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="PORTAL_LOG">
							<li><a href="${ctx}/portallog/list" target="navTab"
								rel="rulelog">Portal日志 </a></li>
									 	
							</shiro:hasPermission>
						</ul>
					</div>
					</shiro:hasPermission>
					<!-- 统计分析模块 -->
					<shiro:hasPermission name="STATISTICS">
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>统计分析
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">						
							<shiro:hasPermission name="STATISTICS_USER">
							<li><a href="${ctx}/statistics/wlanUserLog/list" target="navTab"
								rel="employeeList">上网用户统计分析</a></li>
							</shiro:hasPermission>								
							<shiro:hasPermission name="STATISTICS_LOGIN">
							<li><a href="${ctx}/statistics/wlanUserLogin/list" target="navTab"
								rel="wUserLoginLoglist">用户登录日志查询</a></li>
							</shiro:hasPermission>
						</ul>
					</div>
					</shiro:hasPermission>
					<!-- 系统管理模块 642990-->
					<shiro:hasPermission name="SYS">
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>系统管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<shiro:hasPermission name="SYS_AP">
							<li><a href="${ctx}/sysmanagement/ap/list" target="navTab"
								rel="apList">AP位置信息管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="SYS_AC">
							<li><a href="${ctx}/sysmanagement/ac/list" target="navTab"
								rel="acList">AC管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="SYS_CUSTOMER">
							<li><a href="${ctx}/sysmanagement/customer/list" target="navTab"
								rel="customerList">企业管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="SYS_MODULE">
							<li><a href="${ctx}/security/module/tree" target="navTab"
								rel="moduleList">系统模块管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="SYS_USER">
							<li><a href="${ctx}/security/user/list" target="navTab"
								rel="userList">系统用户管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="SYS_ROLE">
							<li><a href="${ctx}/security/role/list" target="navTab"
								rel="userRoleList">角色权限管理</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="SYS_LOG">
							<li><a href=#>系统日志查询</a>
								<ul>
									<shiro:hasPermission name="SYS_LOG_LOGIN">
									<li><a href="${ctx}/security/log/loginlist" target="navTab"
										rel="loginList">系统登录日志</a></li>
									</shiro:hasPermission>			
									<shiro:hasPermission name="SYS_LOG_OPERATION">
									<li><a href="${ctx}/security/log/operationlist" target="navTab"
										rel="operationList">系统操作日志</a></li>
									</shiro:hasPermission>
								</ul>
							</li>
							</shiro:hasPermission>
						</ul>
					</div>
					</shiro:hasPermission>
				</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span
										class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<%@include file="/index.jsp"%>
					</div>

				</div>
			</div>
		</div>

	</div>

	<div id="footer">
		中移通信 2013 WLAN管理系统 
	</div>

</body>
<%@include file="include.inc.jsp"%>
<script type="text/javascript">
	$(function() {
		DWZ.init("${ctx}/static/js/dwz/dwz.frag.xml", {
			loginUrl : "${ctx}/timeout",
			loginTitle : "登录", // 弹出登录对话框
			//		loginUrl:"login.html",	// 跳到登录页面
			statusCode : {
				ok : 200,
				error : 300,
				timeout : 403
			}, //【可选】
			debug : false, // 调试模式 【true|false】
			callback : function() {
				initEnv();
				//	$("#themeList").theme({themeBase:"themes"}); // themeBase 相对于index页面的主题base路径
			}
		});
	});
</script>

</html>

