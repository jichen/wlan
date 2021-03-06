<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp" %>
<script type="text/javascript">
	function dateScopewlanUser() {
		var datetype = document.getElementById("datetype").value;
		var before = document.getElementById("wlanUserLogTime1").value;
		var after = document.getElementById("wlanUserLogTime2").value;
		var beforeDate = new Date(before.replace(/\-/g, "/"));
		var afterDate = new Date(after.replace(/\-/g, "/"));
		if ((afterDate - beforeDate) < 0) {
			alertMsg.error("开始时间要小于结束时间，请检查之后再提交。");
			return false;
		}
		return true;
	}
	function checkwlanUserLog() {		
		if (dateScopewlanUser()) {
			$("form[name=wlanUserLog]").submit();
		}
	}
</script>
<div class="pageHeader">
<form onsubmit="return navTabSearch(this);" rel="pagerForm"  id="pagerForm" name="wlanUserLog"  action="${ctx}/statistics/wlanUserLog/list" method="post">
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
					&nbsp;统计维度选择：
				</td>
				<td>
					<select id="datetype" name="datetype"  style="width: 132px">
						<option ${pageForm.datetype == "m"?"selected":"" }  value="m">月</option>
						<option ${pageForm.datetype == "d"?"selected":"" }  value="d">日</option>
						<option ${pageForm.datetype == "h"?"selected":"" }  value="h">时</option>
					</select>
				</td>
				<td>
					&nbsp;统计时间：
				</td>
				<td>
					<input id="wlanUserLogTime1" name="startTime"  class="date textInput" type="text" datefmt="yyyy-MM-dd HH:mm" size="20" value="${startTime}" readonly="readonly"><a href="javascript:;"></a>
					&nbsp;至&nbsp;
					<input id="wlanUserLogTime2" name="endTime"  class="date textInput" type="text" datefmt="yyyy-MM-dd HH:mm" size="20" value="${endTime}"  readonly="readonly"/><a href="javascript:;"></a>
				</td>
				<td></td>
				<td>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" onClick="javascript:checkwlanUserLog();">查询</button></div></div></li>
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
				<th width="30" >序号</th>
				<th width="100" >时间</th>
				<th width="100" >数量</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="lst" items="${list}"  varStatus="status">
			<tr>
				<td>${status.index+1}</td>
				<td>${lst.time}<c:if test="${'h'== pageForm.datetype}">时</c:if></td>
				<td>${lst.num}</td>
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
