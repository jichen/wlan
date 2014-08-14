<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/include.inc.jsp"%>
<script type="text/javascript">
<!--
function security_user_grant(roleId){
	$("#granRoles").find("tr[rel="+roleId+"]").each(function(i){
        $(this).remove();
	});
	var userId = "${user_id}";
	var url = "${ctx}/security/user/granRole";
	$.ajax({
		type: "post", 
		url: url,
		data:{userId:userId,roleId:roleId},
		dataType:"json", 
		success:function(json){
			if(json["status"]){
				if(json["status"]==1){
					var tr = $("<tr rel="+roleId+"></tr>");
					var td1 = $("<td>"+json["role_info"]["name"]+"</td>");
					var td2 = $("<td></td>");
					var btn  = $("<button>撤消</button>").bind("click", function(){
						security_user_revoke(roleId);
					});
					var div1 = $("<div></div>").addClass("button");
					var div2 = $("<div></div>").addClass("buttonContent");
					$(btn).appendTo($(div1)).appendTo($(div2)).appendTo($(td2));
					$(td1).appendTo($(tr));
					$(td2).appendTo($(tr));
					$(tr).appendTo($("#revokeRoles"));
				}
			}
		}
	});
}
function security_user_revoke(roleId){
	$("#revokeRoles").find("tr[rel="+roleId+"]").each(function(i){
        $(this).remove();
	});
	var userId = "${user_id}";
	var url = "${ctx}/security/user/revokeRole";
	$.ajax({
		type: "post", 
		url: url,
		data:{userId:userId,roleId:roleId},
		dataType:"json", 
		success:function(json){
			if(json["status"]){
				if(json["status"]==1){
					var tr = $("<tr rel="+roleId+"></tr>");
					var td1 = $("<td>"+json["role_info"]["name"]+"</td>");
					var td2 = $("<td></td>");
					var btn  = $("<button>分配</button>").bind("click", function(){
						security_user_grant(roleId);
					});
					var div1 = $("<div></div>").addClass("button");
					var div2 = $("<div></div>").addClass("buttonContent");
					$(btn).appendTo($(div1)).appendTo($(div2)).appendTo($(td2));
					$(td1).appendTo($(tr));
					$(td2).appendTo($(tr));
					$(tr).appendTo($("#granRoles"));
				}
			}
		}
	});
}
//-->
</script>
<form method="post" action="${ctx}/security/user/refresh" class="pageForm required-validate" onsubmit="return iframeCallback(this,dialogAjaxDone);">
<div class="pageContent" layoutH="30" >
		<h2>登录帐号:</label>${user.username}</h2>
		
	<fieldset>
		<legend>已分配角色</legend>
		<table class="list" width="100%">
			<thead>
				<tr>
					<th width="150">角色名称</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="revokeRoles">
				<c:forEach var="item" items="${userRoles}">
				<tr rel="${item.role.id}">
					<td>${item.role.name}</td>
					<td><div class="button">
							<div class="buttonContent">
								<button onclick="security_user_revoke(${item.role.id})">撤消</button>
							</div>
						</div>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
	<fieldset>
		<legend>可分配角色</legend>
		<table class="list" width="100%">
			<thead>
				<tr>
					<th width="150">角色名称</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="granRoles">
				<c:forEach var="item" items="${roles}">
				<%-- 不显示超级管理员角色，超级管理员只能拥有一个，且id=1 --%>
				<c:if test="${item.id != 1 }">
				<tr rel="${item.id}">
					<td>${item.name}</td>
					<td>
						<div class="button">
							<div class="buttonContent">
								<button onclick="security_user_grant(${item.id})">分配</button>
							</div>
						</div>
					</td>
				</tr>					
				</c:if>			
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit" class="submit">关闭</button></div></div></li>
		</ul>
	</div>
</form>
