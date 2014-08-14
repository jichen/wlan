<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp" %>
<script type="text/javascript">
	function dateScopeuserlogin() {
		var before = document.getElementById("userlogintime1").value;
		var after = document.getElementById("userlogintime2").value;
		var beforeDate = new Date(before.replace(/\-/g, "/"));
		var afterDate = new Date(after.replace(/\-/g, "/"));
		if ((afterDate - beforeDate) < 0) {
			alertMsg.error("开始时间 要小于结束时间，请检查之后再提交。");
			return false;
		}
		return true;
	}
	function checkuserlogin() {		
		if (dateScopeuserlogin()) {
			$("form[name=userloginList]").submit();
		}
	}
</script>
<div class="pageHeader">
<form onsubmit="return navTabSearch(this);" rel="pagerForm"  id="pagerForm" name="userloginList" action="${ctx}/statistics/wlanUserLogin/list" method="post">
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
					&nbsp;用户名：
				</td>
				<td align="left">
					<input name="name" type="text" size="30" value="${pageForm.name}">
				</td>
				<td>
					&nbsp;登录时间：
				</td>
				<td>
					<input id="userlogintime1" name="auth_time1"  class="date textInput" type="text" size="20" value="${authtime1}" readonly="readonly"><a href="javascript:;"></a>
					至
					<input id="userlogintime2" name="auth_time2"  class="date textInput" type="text" size="20" value="${authtime2}"  readonly="readonly"/><a href="javascript:;"></a>
				</td>				
				<td>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" onClick="javascript:checkuserlogin();">查询</button></div></div></li>
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
				<th width="120" >用户名</th>
				<th width="150" >IP地址</th>
				<th width="150" >登录时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="logs" items="${list}" varStatus="status">
			<tr target="slt_uid" rel="${logs.id}">
				<td>${status.count}</td>
				<td>${logs.username}</td>
				<td>${logs.userip}</td>
				<td><fmt:formatDate value="${logs.authtime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
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
