<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/include.inc.jsp"%>
<script type="text/javascript">
function passwrodNavTabAjaxDone(json){
	if(json["callbackType"]=="closeCurrent"){
		alertMsg.correct(json["message"]);
		$.pdialog.closeCurrent();
	}
	else{
		alertMsg.error(json["message"]);
	}
}
</script>
<div class="pageContent">
<form method="post" action="${ctx}/security/user/password" class="pageForm required-validate" onsubmit="return validateCallback(this, passwrodNavTabAjaxDone);">
	<div class="pageFormContent" style="overflow-x:hidden;overflow:hidden;">
		<dl>
			<dt>原始密码：</dt>
			<dd>
				<input type="password" name="oldPassword" size="25" class="required" maxlength="30" value=""/>
			</dd>
		</dl>
		<dl>
			<dt>新密码：</dt>
			<dd>
				<input id="newPasswordID" type="password" name="newPassword" size="25" class="required" minlength="6" maxlength="30" value=""/>
			</dd>
		</dl>
		<dl>
			<dt>重复新密码：</dt>
			<dd>
				<input type="password" size="25" class="required" minlength="6" maxlength="30" value="" equalto="#newPasswordID" />
			</dd>
		</dl>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>