<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/include.inc.jsp"%>
<script type="text/javascript">
function ajaxCustomer(obj){
	if($("#bf1").val()!=$(obj).val()){
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
}
</script>

<form method="post" action="${ctx}/sysmanagement/customer/update" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<input type="hidden" name="id" value="${bean.id}"/>
	<div class="pageFormContent" layoutH="56">
		<div>
			<dl class="nowrap">
				<dt>企业名称：</dt>
				<dd><input type="text" name="cust_name"  value="${bean.cust_name}"  class="required" size="40" maxlength="200"  onblur="ajaxCustomer(this)"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>地址：</dt>
				<dd><input type="text" name="address"   value="${bean.address}"  class="required" size="40" maxlength="200"  /></dd>
			</dl>
			<dl class="nowrap">
				<dt>联系人：</dt>
				<dd><input type="text" name="contact" value="${bean.contact}" class="required" size="40" maxlength="100"  /></dd>
			</dl>
			<dl class="nowrap">
				<dt>联系方式：</dt>
				<dd><input type="text" name="phone" value="${bean.phone}"   class="required" size="40" maxlength="50"  /></dd>
			</dl>
			<dl class="nowrap" style="display: none;">
				<dt>状态：</dt>
				<dd><input type="checkbox" style="margin-top: 5px" <c:if test="${'Y'== bean.isdelete}">checked</c:if>
						name="isdelete" value="Y" class="textInput"><label style="width: 50px">已删除</label>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>备注：</dt>
				<dd><textarea name="remark" cols="60" maxlength="2000"  rows="10" >${bean.remark}</textarea>
				</dd>
			</dl>
		</div>
	</div>
	<input type="hidden" name="createusername" value="${bean.createusername}" readonly="readonly"/>
	<input type="hidden" name="createtime" value="${bean.createtime}" readonly="readonly"/>
	<input type="hidden" id="bf1" name="bf1"  value="${bean.cust_name}" >
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>