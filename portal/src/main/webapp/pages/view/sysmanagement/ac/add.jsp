<%@page contentType="text/html;charset=UTF-8" 	trimDirectiveWhitespaces="true"%>
<%@include file="/include.inc.jsp" %>
<script type="text/javascript">
function ajaxACname(obj){
	$.ajax({
		type : "post",
		url : "${ctx}/sysmanagement/ac/ajaxACname",
		data : {'name' : $(obj).val()},
		success : function(data) {
			if(data==1){
				alertMsg.info("AC名称已存在");
				return false;
			}
		},
		error : function(data){
			alertMsg.info("AC名称已存在");
			return false;
		}
	});
}

function ajaxIP(obj){
	$.ajax({
		type : "post",
		url : "${ctx}/sysmanagement/ac/ajaxIP",
		data : {'name' : $(obj).val()},
		success : function(data) {
			if(data==1){
				alertMsg.info("IP已存在");
				return false;
			}
		},
		error : function(data){
			alertMsg.info("IP已存在");
			return false;
		}
	});
}
</script>
<form method="post" action="${ctx }/sysmanagement/ac/add" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<div>
			<dl class="nowrap">
				<dt>AC名称:</dt>
				<dd><input type="text" name="ac_name"  class="required" size="40" maxlength="200" onblur="ajaxACname(this)"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>公司名称：</dt>
				<dd>
				<input id="cust_name" name="cust.cust_name" type="text" 
						postField="keyword" suggestFields="cust_id,cust_name" suggestUrl="" 
						lookupGroup="cust" value="" readonly="readonly" />
				<a class="btnLook"   href="${ctx }/sysmanagement/customer/lookUp" lookupGroup="cust">查找带回</a>
				<input  id="cust_id" name="cust.cust_id" value="" type="hidden" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>IP：</dt>
				<dd><input type="text" name="ip"   class="required" size="40" maxlength="20" onblur="ajaxIP(this)"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>位置：</dt>
				<dd><input type="text" name="location"   class="required" size="40" maxlength="200"/></dd>
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
