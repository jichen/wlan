<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp" %>
<script type="text/javascript">

function ajaxCustomer(obj){
	alert($(obj).val());
	$.ajax({
		type : "post",
		url : "${ctx}/sysmanagement/customer/ajaxCustomer",
		data : {'name' : $(obj).val()},
		success : function(data) {
			if(data==1){
				alertMsg.info("企业名称已存在");
				return false;
			}
		},
		error : function(data){
			alertMsg.info("企业名称已存在");
			return false;
		}
	});
}
</script>
<form method="post" action="${ctx }/sysmanagement/customer/add" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<div>
			<dl class="nowrap">
				<dt>企业名称：</dt>
				<dd><input type="text" name="cust_name" class="required" size="40" maxlength="200" onblur="ajaxCustomer(this)"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>地址：</dt>
				<dd><input type="text" name="address" class="required" size="40" maxlength="200"  /></dd>
			</dl>
			<dl class="nowrap">
				<dt>联系人：</dt>
				<dd><input type="text" name="contact"   class="required" size="40" maxlength="100"  /></dd>
			</dl>
			<dl class="nowrap">
				<dt>联系方式：</dt>
				<dd><input type="text" name="phone"   class="required" size="40" maxlength="50"  /></dd>
			</dl>
			<dl class="nowrap">
				<dt>备注：</dt>
				<dd><textarea name="remark" cols="80" maxlength="2000" rows="12" ></textarea></dd>
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
