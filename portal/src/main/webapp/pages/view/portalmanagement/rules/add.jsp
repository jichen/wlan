<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp" %>
<script type="text/javascript">
function rules_add_Callback(form, callback) {
	//获取表单
	var $form = $(form);
	//定义submit
	var _submitFn = function() {
		$.ajax({
			type : form.method || 'POST',
			url : $form.attr("action"),
			data : $form.serializeArray(),
			dataType : "json",
			cache : false,
			success : callback || DWZ.ajaxDone,
			error : DWZ.ajaxError
		});
	}
	//校验blacklist
	var flag = false;
	var message="请输入11位数字组成的电话号码";
	var list= new Array();
	var blacklist=$form.find("textarea").val();
	//js换行符    \n    java中\r\n  
	list=blacklist.split("\n");

	if(list !=null && list !=""){
		for(var a=0;a<list.length;a++){
			if(!/^1\d{10}$/.test(list[a])){
				flag = true;
			}
			
		}
	}

	if (flag) {
		alertMsg.info(message);
	} else {
		_submitFn();
	}
	return false;
}

</script>
<form method="post" action="${ctx }${url}" class="pageForm required-validate" onsubmit="return rules_add_Callback(this, navTabAjaxDone);">
	<input type=hidden name="id" value="${bean.id}"/>
	<div class="pageFormContent" layoutH="56">
		<div>
			<dl class="nowrap">
				<c:choose>
					<c:when test="${bean.is_only_client=='1'}">
						<dt>禁止同一用户同时<br>使用多个客户端</dt>
						<dd><input type="checkbox" style="margin-top: 5px;margin-left: -280px" name="is_only_client" checked="checked"></dd>
					</c:when>
					<c:otherwise>
						<dt>禁止同一用户同时使用多个客户端</dt>
						<dd><input type="checkbox" style="margin-top: 5px;margin-left: -280px" name="is_only_client" checked="checked"></dd>
					</c:otherwise>
					</c:choose>
			</dl>	
			<dl class="nowrap">
				<dt>重复验证间隔</dt>
				<dd><input type="text" name="auth_interval" size="2" maxlength="2" value="${bean.auth_interval}" class="digits" /><label style="width:40px">分钟</label></dd>
			</dl>
			<dl class="nowrap">
				<dt>验证码有效期</dt>
				<dd><input type="text" name="time" size="2" maxlength="2" value="${bean.time}" class="digits" /><label style="width:40px">分钟</label></dd>
			</dl>
			<dl class="nowrap">
				<dt>手机号码黑名单<br>(每行一个号码)</dt>
				<dd>
					<textarea name="blacklist" cols="80" maxlength="2000" rows="12" >${blacklist}</textarea>
				</dd>
			</dl>
				<input type="hidden" name="createusername" value="${bean.createusername}" readonly="readonly"/>
				<input type="hidden" name="createtime" value="${bean.createtime}" readonly="readonly"/>
		</div>
	</div>	
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</table>
</form>
