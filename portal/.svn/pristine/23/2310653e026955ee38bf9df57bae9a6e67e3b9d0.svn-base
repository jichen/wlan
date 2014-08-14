<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp"%>

<form onsubmit="return navTabSearch(this);" rel="pagerForm"  id="pagerForm" action="${ctx}/security/role/list" method="post">
	<c:choose>
		<c:when test="${pageForm.pageNum==null}">
			<input type="hidden" name="pageNum" value="1" />
		</c:when>
		<c:otherwise>
			<input type="hidden" name="pageNum" value="${pageForm.pageNum}" />
		</c:otherwise>
	</c:choose>
	<input type="hidden" name="numPerPage" value="10" />
</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
				<li><a class="add" target="dialog" mask="true" width="600" height="500" href="${ctx }/security/role/preAdd" ><span>添加角色</span></a></li>
				<li><a class="edit" target="dialog" mask="true" width="600" height="500" href="${ctx }/security/role/preUpdate/{slt_uid}"><span>更新角色</span></a></li>
				<!-- 
				<li><a class="delete" target="ajaxTodo" href="${ctx }/security/role/delete/{slt_uid}" title="确认要删除该角色?"><span>删除角色</span></a></li>
				 -->
		</ul>
	</div>
	
	<table class="table"  width="100%" layoutH="74">
		<thead>
			<tr>
				<th width="10%">序号</th>
				<th width="30%">角色编号</th>
				<th width="30%">角色名称</th>
				<th width="30%">角色权限点描述</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${list}" varStatus="itemStatus">
			<tr target="slt_uid" rel="${item.id}">
				<td>${itemStatus.count}</td>
				<td>${item.code}</td>
				<td>${item.name}</td>
				<td>${item.description}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 分页 -->
	<div class="panelBar">
		<div class="pages">
			<span>显示10条，共${page.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.totalCount}" numPerPage="${page.numPerPage}" pageNumShown="10" currentPage="${page.pageNum}"></div>
	</div>
</div>


