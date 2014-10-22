<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp" %>
<div class="pageHeader">
<form onsubmit="return navTabSearch(this);" rel="pagerForm"  id="pagerForm" action="${ctx}/sysmanagement/ap/list" method="post">
	<c:choose>
		<c:when test="${pageForm.pageNum==null}">
			<input type="hidden" name="pageNum" value="1" />
		</c:when>
		<c:otherwise>
			<input type="hidden" name="pageNum" value="${pageForm.pageNum}" />
		</c:otherwise>
	</c:choose>
	<input type="hidden" name="numPerPage" value="10" /> 
	<input type="hidden" name="orderField" value="" /> 
	<input type="hidden" name="orderDirection" value="" />
	<div class="searchBar">
		<table class="searchContent" align="left">
			<tr>
				<td>
					&nbsp;名称：
				</td>
				<td>
					<input name="name" type="text" value="${pageForm.name}">
				</td>
				<td>
					&nbsp;位置：
				</td>
				<td>
					<input name="location" type="text" value="${pageForm.location}">
				</td>
				<td>
					&nbsp;AC：
				</td>
				<td>
					<input name="ac_id" type="text" value="${pageForm.ac_id}">
				</td>
			</tr>
			<tr>
				<td>
					
				</td>
				<td>
				<!-- 
					<select name="isdelete"  style="width: 132px">
						<option ${pageForm.isdelete == "null"?"selected":"" }  value="">请选择</option>
						<option ${pageForm.isdelete == "N"?"selected":"" }  value="N">使用中</option>
						<option ${pageForm.isdelete == "Y"?"selected":"" }  value="Y">已删除</option>
					</select>
				-->
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
					</ul>
				</div>
				</td>
			</tr>
		</table>
	</div>
</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add"    href="${ctx}/sysmanagement/ap/preadd" target="navTab" rel="apadd"><span>新增AP</span></a></li>
			<li><a class="edit"   href="${ctx}/sysmanagement/ap/preUpdate/{slt_uid}" target="navTab" rel="UserUpdate"><span>编辑AP</span></a></li>
			<li><a class="delete" href="${ctx}/sysmanagement/ap/delete/{slt_uid}" target="ajaxTodo" title="确认要删除该AP?"><span>删除AP</span></a></li>
		</ul>
	</div>
	<table class="table"   layoutH="145" width="100%" style="table-layout: fixed;">
		<thead>
			<tr>
				<th width="20" >序号</th>
				<th width="100" >AP名称</th>
				<th width="100" >位置</th>
				<th width="100" >Nas-Port-ID</th>
				<th width="200" >MAC地址</th>
				<th width="100" >AC名称</th>
				<th width="100" >创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="aps" items="${list}" varStatus="status">
			<tr target="slt_uid" rel="${aps.id}">
				<td>${status.index+1}</td>
				<td>${aps.ap_name}</td>
				<td>${aps.location}</td>
				<td>${aps.nas_port_id}</td>
				<td>${aps.mac}</td>
				<td>${aps.ac_name}</td>
				<td><fmt:formatDate value="${aps.createtime}" pattern="yyyy-MM-dd"/></td>
				<!-- 
				<c:choose>
					<c:when test="${aps.isdelete=='Y'}">
						<td>已删除</td>
					</c:when>
					<c:otherwise>
						<td>使用中</td>
					</c:otherwise>
				</c:choose>
				 -->
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示10条，共${page.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.totalCount}" numPerPage="${page.numPerPage}" pageNumShown="10" currentPage="${page.pageNum}"></div>
	</div>
</div>
