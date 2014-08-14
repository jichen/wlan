<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp" %>
<script type="text/javascript">
	function dateScopeoperationList() {
		var before = document.getElementById("operationstartTime").value;
		var after = document.getElementById("operationendTime").value;
		var beforeDate = new Date(before.replace(/\-/g, "/"));
		var afterDate = new Date(after.replace(/\-/g, "/"));
		if ((afterDate - beforeDate) < 0) {
			alertMsg.error("开始时间 要小于结束时间，请检查之后再提交。");
			return false;
		}
		return true;
	}
	function checkoperationList() {		
		if (dateScopeoperationList()) {
			$("form[name=operationList]").submit();
		}
	}
</script>
<div class="pageHeader">
<form onsubmit="return navTabSearch(this);" rel="pagerForm"  id="pagerForm" name="operationList" action="${ctx}/security/log/operationlist" method="post">
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
					&nbsp;操作人员：
				</td>
				<td align="left">
					<input name="name" type="text" value="${pageForm.name}">
				</td>
				<td>
					&nbsp;操作模块：
				</td>
				<td>
					<input name="module" type="text" value="${pageForm.module}">
				</td>
				<td>
					&nbsp;操作类型：
				</td>
				<td>
					<select name="function_name"  style="width: 132px">
						<option ${pageForm.function_name == "null"?"selected":"" }  value="">请选择</option>
						<option ${pageForm.function_name == "添加"?"selected":"" }  value="添加">添加</option>
						<option ${pageForm.function_name == "修改"?"selected":"" }  value="修改">修改</option>
						<option ${pageForm.function_name == "删除"?"selected":"" }  value="删除">删除</option>
						<option ${pageForm.function_name == "模板页面内容添加"?"selected":"" }  value="模板页面内容添加">模板页面内容添加</option>
						<option ${pageForm.function_name == "模板内容修改"?"selected":"" }  value="模板内容修改">模板内容修改</option>
						<option ${pageForm.function_name == "分配权限"?"selected":"" }  value="分配权限">分配权限</option>
						<option ${pageForm.function_name == "撤销权限"?"selected":"" }  value="撤销权限">撤销权限</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;操作时间：
				</td>
				<td  align="left">
					<input id="operationstartTime" name="startTime"  class="date textInput" type="text" size="20" value="${start_time}" readonly="readonly"><a href="javascript:;"></a>
					&nbsp;至&nbsp;
					<input id="operationendTime" name="endTime"  class="date textInput" type="text" size="20" value="${end_time}"  readonly="readonly"/><a href="javascript:;"></a>
				</td>
				<td></td><td></td>
				<td>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" onClick="javascript:checkoperationList();">查询</button></div></div></li>
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
				<th width="100" >操作人员</th>
				<th width="150" >操作模块</th>
				<th width="150" >操作类型</th>
				<th width="120" >操作时间</th>
				<th width="120" >操作IP</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="operates" items="${list}" varStatus="status">
			<tr target="slt_uid" rel="${operates.id}">
				<td>${status.index+1}</td>
				<td>${operates.username}</td>
				<td>${operates.operate}</td>
				<td>${operates.function_name}</td>
				<td><fmt:formatDate value="${operates.operatetime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
				<td>${operates.ip}</td>
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
