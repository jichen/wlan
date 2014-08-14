<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp" %>
<div class="pageHeader">
人员管理


	<table class="table"layoutH="138" width="100%">
		<thead>
			<tr>
				<th width="100">序号</th>
				<th width="100" orderField="employeeName">姓名</th>
				<th width="100" orderField="depart.departName">部门</th>
				<th width="100" orderField="phone">电话</th>
				<th width="100" orderField="email">email</th>
				<th width="100" orderField="address">办公地址</th>
				<th width="100" orderField="sex">性别</th>
				<th width="100" orderField="code">工号</th>
				<th width="100" orderField="user.userName">用户账号</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tmp" items="${page.content}" varStatus="temStatus">
			<tr target="slt_uid" rel="${tmp.employeeId}">
				<td>${temStatus.count}
				<td>${tmp.employeeName}</td>
				<td>${tmp.depart.departName}</td>
				<td>${tmp.phone}</td>
				<td>${tmp.email}</td>
				<td>${tmp.address}</td>
				<td>${tmp.sex}</td>
				<td>${tmp.code}</td>
				<td>${tmp.user.userName}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<my:paging total="${page.totalElements}" start="${page.number+1}" />

</div>
