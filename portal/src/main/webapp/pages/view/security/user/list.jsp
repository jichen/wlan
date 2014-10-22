<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp" %>
<div class="pageHeader">
<form onsubmit="return navTabSearch(this);" rel="pagerForm"  id="pagerForm" action="${ctx}/security/user/list" method="post">
		<c:choose>
			<c:when test="${pageForm.pageNum==null}">
				<input type="hidden" name="pageNum" value="1" />
			</c:when>
			<c:otherwise>
				<input type="hidden" name="pageNum" value="${page.pageNum}" />
			</c:otherwise>
		</c:choose>
		<input type="hidden" name="numPerPage" value="10" /> 
	<div class="searchBar">
		<table class="searchContent"  align="left">
			<tr>
				<td>
					用户名称:
				</td>
				<td>
					<input name="name" type="text" value="${pageForm.name}">
				</td>
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
				<td>
				</td>
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
			<li><a class="add"    href="${ctx}/security/user/preAdd" target="navTab" rel="UserAdd"><span>新增用户</span></a></li>
			<li><a class="edit"   href="${ctx}/security/user/preUpdate/{slt_uid}" target="navTab" rel="UserUpdate"><span>编辑用户</span></a></li>
			<!-- 
			<li><a class="delete" href="${ctx}/security/user/delete/{slt_uid}" target="ajaxTodo" title="确认要删除该用户?"><span>删除用户</span></a></li>
			-->
			<li><a class="add"    href="${ctx}/security/user/preAssignRole/{slt_uid}" target="dialog" mask="true" width="400" height="500" title="分配角色"><span>分配角色</span></a></li>
			
		</ul>
	</div>
	<table class="table"  layoutH="145" width="100%">
		<thead>
			<tr>
				<th width="50" >序号</th>
				<th width="200" >用户名称</th>
				<th width="200" >创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="users" items="${list}">
			<tr target="slt_uid" rel="${users.userid}">
				<td>${users.userid}</td>
				<td>${users.username}</td>
				<td><fmt:formatDate value="${users.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
