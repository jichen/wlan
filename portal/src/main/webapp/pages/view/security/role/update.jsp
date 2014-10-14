<%@page import="com.cmct.portal.po.RolePO"%>
<%@page import="com.cmct.portal.po.ModulePO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/include.inc.jsp"%>
<script type="text/javascript">
</script>
<h2 class="contentTitle">修改角色</h2>
<form method="post" action="${ctx }/security/role/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="id" value="${role.id }">
	<div class="pageFormContent" layoutH="97">
	<dl>
		<dt>角色编号：</dt>
		<dd>
			<input type="text" name="code" class="required" size="30" maxlength="32" alt="请输入角色编号" readonly="readonly" value="${role.code}"/>
		</dd>
	</dl>
	<dl>
		<dt>角色名称：</dt>
		<dd>
			<input type="text" name="name" class="required" size="30" maxlength="32" alt="请输入角色名称" readonly="readonly" value="${role.name}"/>
		</dd>
	</dl>
	<dl class="nowrap">
		<dt>角色权限点描述：</dt>
		<dd>
			<textarea name="description" cols="60" rows="2">${role.description}</textarea>
		</dd>
	</dl>
		<div style=" float:left; display:block; margin:10px; overflow:auto; width:400px; height:300px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
		请选择该角色所具有的权限
		<ul class="tree treeFolder treeCheck expand">
			<c:set var="ind" value="${0}"/>
			<li><a  tname="permissionList[${ind  }]" tvalue="${module.sn }" checked="true">${module.name }</a>
			<ul>
			<c:forEach var="m1" items="${module.children }" varStatus="m1ind">
			<c:set var="ind" value="${0}"/>
				<li><a  tname="permissionList[${ind }]" tvalue="${m1.sn }"  <c:if test="${fn:contains(rolePermissionList, m1.sn) }">checked="true"</c:if>>${m1.name }</a>
					<ul>
						<c:forEach var="m2" items="${m1.children }" varStatus="m2ind">
						<c:set var="ind" value="${ind + 1 }"/>
						<c:choose>
							<c:when test="${m2.priority} == 50">
								<li><a tname="permissionList[${ind }]" tvalue="${m2.sn }" <c:if test="${fn:contains(rolePermissionList, m2.sn) }">checked="true"</c:if>>${m2.name }</a></li>
							</c:when>
							<c:otherwise>
								<li><a tname="permissionList[${ind }]" tvalue="${m2.sn }" <c:if test="${fn:contains(rolePermissionList, m2.sn) }">checked="true"</c:if>>${m2.name }</a>
							<ul>
								<c:forEach var="m3" items="${m2.children }" varStatus="m3ind">
								<c:set var="ind" value="${ind + 2 }"/>
								<c:choose>
									<c:when test="${m3.priority == 50}">
										<li><a tname="permissionList[${ind }]" tvalue="${m3.sn }" <c:if test="${fn:contains(rolePermissionList, m3.sn) }">checked="true"</c:if>>${m3.name }</a></li>
									</c:when>
									<c:otherwise>
										<li><a tname="permissionList[${ind }]" tvalue="${m3.sn }" <c:if test="${fn:contains(rolePermissionList, m3.sn) }">checked="true"</c:if>>${m3.name }</a>
										<ul>
											<c:forEach var="m4" items="${m3.children }" varStatus="m4ind">
											<c:set var="ind" value="${ind + 3 }"/>
												<c:choose>
													<c:when test="${m4.priority == 50}">
														<li> <a tname="permissionList[${ind }]" tvalue="${m4.sn }" <c:if test="${fn:contains(rolePermissionList, m4.sn) }">checked="true"</c:if>>${m4.name }</a></li>
													</c:when>
													<c:otherwise>
														<li> <a tname="permissionList[${ind }]" tvalue="${m4.sn }" <c:if test="${fn:contains(rolePermissionList, m4.sn) }">checked="true"</c:if>>${m4.name }</a> 
															<ul>
																<c:forEach var="m5" items="${m4.children }" varStatus="m5ind">
																<c:set var="ind" value="${ind + 4 }"/>
																<li> <a tname="permissionList[${ind }]" tvalue="${m5.sn }" <c:if test="${fn:contains(rolePermissionList, m5.sn) }">checked="true"</c:if>>${m5.name }</a></li>
																</c:forEach>
															</ul>
														</li>
													</c:otherwise>
												</c:choose>
										
											</c:forEach>										
										</ul>
									</li>
									</c:otherwise>
								</c:choose>
									
								</c:forEach>
								
							</ul>
						</li>
							</c:otherwise>
						</c:choose>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>
			</ul>
			</li>
		</ul>
	</div>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>