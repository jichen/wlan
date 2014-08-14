<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/include.inc.jsp"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<form id="pagerForm" onsubmit="return divSearch(this, 'jbsxBox2module');" action="${ctx}/security/module/list/${parentModule.id}" method="post">
	<input type="hidden" name="pageNum" value="${page.pageNum}" />
	<input type="hidden" name="numPerPage" value="${page.numPerPage}" /> 
	<input type="hidden" name="orderField" value="${page.orderField}" />
	<input type="hidden" name="orderDirection" value="${page.orderDirection}" />
	 
	<input type="hidden" name="name" value="${pageForm.name}"/>
</form>

<form method="post" action="${ctx }/security/module/list/${parentModule.id}" onsubmit="return divSearch(this, 'jbsxBox2module');">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>模块名称：</label>
					<input type="text" name="name" value="${pageForm.name}"/>
				</li>
			</ul>
			<div class="subBar">
				<ul>						
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
		<li><a class="add" target="dialog" width="600" height="500" mask="true" href="${ctx }/security/module/preAdd" ><span>添加</span></a></li>
		<li><a class="edit" target="dialog" width="600" height="500" mask="true" href="${ctx }/security/module/preUpdate/{slt_uid}" ><span>编辑</span></a></li>
		<li><a class="delete" target="ajaxTodo" href="${ctx }/security/module/delete/{slt_uid}" title="确认要删除该模块?"><span>删除</span></a></li>
		</ul>
	</div>
	<table class="table" layoutH="138" width="100%" rel="jbsxBox2module" >
		<thead>
			<tr>
				<th width="60" >名称</th>
				<th width="40" >优先级</th>
				<th width="60" >父模块</th>
				<th>模块地址</th>
				<th>SN</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${list}">
			<tr target="slt_uid" rel="${item.id}">
				<td>${item.name}</td>
				<td>${item.priority}</td>
				<td>${item.parentModule_name}</td>
				<td>${item.url}</td>
				<td>${item.sn}</td>
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


