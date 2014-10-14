<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp" %>
<script type="text/javascript">
function ajaxAPname(obj){
	$.ajax({
		type : "post",
		url : "${ctx}/sysmanagement/ap/ajaxAPname",
		data : {'name' : $(obj).val()},
		success : function(data) {
			alert(data);
			if(data==1){
				alertMsg.info("AP名称已存在");
				return false;
			}
		},
		error : function(data){
			alertMsg.info("AP名称已存在");
			return false;
		}
	});
}

function ajaxMac(obj){
	$.ajax({
		type : "post",
		url : "${ctx}/sysmanagement/ap/ajaxMac",
		data : {'name' : $(obj).val()},
		success : function(data) {
			if(data==1){
				alertMsg.info("MAC已存在");
				return false;
			}
		},
		error : function(data){
			alertMsg.info("MAC已存在");
			return false;
		}
	});
}
</script>
<form method="post" action="${ctx }/sysmanagement/ap/add" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<div>
			<dl class="nowrap">
				<dt>AP名称：</dt>
				<dd><input type="text" name="ap_name"  class="required" size="40" maxlength="50" onblur="ajaxAPname(this)"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>AC名称：</dt>
				<dd><input id="ac_name" name="ac.ac_name" type="text" postField="keyword" 
					suggestFields="ac_id,ac_name" suggestUrl="" lookupGroup="ac" value="" 
					readonly="readonly"/><a class="btnLook" href="${ctx }/sysmanagement/ac/lookUp" lookupGroup="ac">查找带回</a>
				<input  id="ac_id" name="ac.ac_id" value="" type="hidden" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>MAC地址：</dt>
				<dd><input type="text" name="mac" class="required" size="40" maxlength="100" onblur="ajaxMac(this)"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>nas_port_id:</dt>
				<dd><input type="text" name="nas_port_id" class="required" size="40" maxlength="200"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>位置：</dt>
				<dd><input type="text" name="location" class="required" size="40" maxlength="100"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>备注：</dt>
				<dd><textarea name="remark" cols="80" maxlength="2000" rows="12" ></textarea>
				</dd>
			</dl>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
