<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp" %>
<script type="text/javascript">
	function dateScopeportallog() {
		var before = document.getElementById("portaltime1").value;
		var after = document.getElementById("portaltime2").value;
		var beforeDate = new Date(before.replace(/\-/g, "/"));
		var afterDate = new Date(after.replace(/\-/g, "/"));
		if ((afterDate - beforeDate) < 0) {
			alertMsg.error("认证时间范围，开始时间 要小于结束时间，请检查之后再提交。");
			return false;
		}
		var before1 = document.getElementById("portaltime3").value;
		var after1 = document.getElementById("portaltime4").value;
		var beforeDate1 = new Date(before1.replace(/\-/g, "/"));
		var afterDate1 = new Date(after1.replace(/\-/g, "/"));
		if ((afterDate1 - beforeDate1) < 0) {
			alertMsg.error("下线时间范围，开始时间要小于结束时间，请检查之后再提交。");
			return false;
		}
		return true;
	}
	function checkportallog() {		
		if (dateScopeportallog()) {
			$("form[name=portallogList]").submit();
		}
	}
</script>
<div class="pageHeader">
<form onsubmit="return navTabSearch(this);" rel="pagerForm"  id="pagerForm" name="portallogList" action="${ctx}/portallog/list" method="post">
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
					&nbsp;动态用户:
				</td>
				<td align="left">
					<input name="name" type="text" size="30" value="${pageForm.name}">
				</td>
				<td>
					<!-- &nbsp;登录的AP: -->
				</td>
				<td align="left">
				<%-- 	<input name="ap_name" type="text" size="30" value="${pageForm.ap_name}">
					<input name="ap_id" type="hidden" value="${pageForm.ap_id}"> --%>
				</td>
				<td>
					 
				</td>
				<td align="left">
					 
				</td>					
			</tr>
			<tr>
				<td>
					认证时间:&nbsp;
				</td>
				<td>
					<input id="portaltime1" name="auth_time1"  class="date textInput" type="text" size="10" value="${authtime1}" readonly="readonly"><a href="javascript:;"></a>
					至
					<input id="portaltime2" name="auth_time2"  class="date textInput" type="text" size="10" value="${authtime2}"  readonly="readonly"/><a href="javascript:;"></a>
				</td>
				<td>
					下线时间:&nbsp;
				</td>
				<td>
					<input id="portaltime3" name="exit_time1"  class="date textInput" type="text" size="10" value="${exittime1}" readonly="readonly"><a href="javascript:;"></a>
					至
					<input id="portaltime4" name="exit_time2"  class="date textInput" type="text" size="10" value="${exittime2}"  readonly="readonly"/><a href="javascript:;"></a>
				</td>				
				<td>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" onClick="javascript:checkportallog();">查询</button></div></div></li>
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
				<th width="40" >序号</th>
				<th width="120" >动态用户</th>
				<th width="150" >IP地址</th>
				<th width="100" >认证时间</th>
				<th width="100" >下线时间</th>
				<th width="80" >在线时长</th>
				<th width="150" >AP</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach var="logs" items="${list}" varStatus="status">
			<tr target="slt_uid" rel="${logs.id}">
				<td>${status.index+1}</td>
				<td>${logs.username}</td>
				<td>${logs.userip}</td>
				<td><fmt:formatDate value="${logs.authtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${logs.exittime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${logs.onlinetime}</td>
				<td>${logs.apid}</td>
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
