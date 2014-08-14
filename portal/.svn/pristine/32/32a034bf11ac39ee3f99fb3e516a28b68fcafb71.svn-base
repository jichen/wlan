<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp" %>
<script type="text/javascript">
	function dateScopelogin() {
		var before = document.getElementById("loginstartTime").value;
		var after = document.getElementById("loginendTime").value;
		var beforeDate = new Date(before.replace(/\-/g, "/"));
		var afterDate = new Date(after.replace(/\-/g, "/"));
		if ((afterDate - beforeDate) < 0) {
			alertMsg.error("开始时间 要小于结束时间，请检查之后再提交。");
			return false;
		}
		return true;
	}
	function checkloginlist() {		
		if (dateScopelogin()) {
			$("form[name=loginList]").submit();
		}
	}
</script>
<div class="pageHeader">
<form onsubmit="return navTabSearch(this);" rel="pagerForm"  id="pagerForm" name="loginList" action="${ctx}/security/log/loginlist" method="post">
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
		<table class="searchContent"  align="left" >
			<tr>
				<td>
					&nbsp;登录名称：
				</td>
				<td>
					<input name="name" type="text" size="20" value="${pageForm.name}">
				</td>
				<td>
					&nbsp;登录时间：
				</td>
				<td>
					<input id="loginstartTime" name="startTime"  class="date textInput" type="text" size="20" value="${start_time}" readonly="readonly"><a href="javascript:;"></a>
					&nbsp;至&nbsp;
					<input id="loginendTime" name="endTime"  class="date textInput" type="text" size="20" value="${end_time}"  readonly="readonly"/><a href="javascript:;"></a>
				</td>
				<td></td>
				<td>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" onClick="javascript:checkloginlist();">查询</button></div></div></li>
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
	</div>
	<table class="table"  layoutH="145"  width="100%" >
		<thead>
			<tr>
				<th width="50" >序号</th>
				<th width="120" >登录名称</th>
				<th width="150" >IP地址</th>
				<th width="150" >登录时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="logs" items="${list}"  varStatus="status">
			<tr target="slt_uid" rel="${logs.id}">
				<td>${status.index+1}</td>
				<td>${logs.username}</td>
				<td>${logs.ip}</td>
				<td><fmt:formatDate value="${logs.login_time}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示10条，共${page.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.totalCount}" numPerPage="10" pageNumShown="10" currentPage="${page.pageNum}"></div>
	</div>
</div>
