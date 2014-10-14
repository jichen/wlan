<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/include.inc.jsp"%>
<script type="text/javascript">
function ajaxAPname(obj){
	if($("#bf1").val()!=$(obj).val()){
		$.ajax({
			type : "post",
			url : "${ctx}/sysmanagement/ap/ajaxAPname",
			data : {'name' : $(obj).val()},
			success : function(data) {
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
}

function ajaxMac(obj){
	if($("#bf2").val()!=$(obj).val()){
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
}
</script>
<form method="post" action="${ctx}/sysmanagement/ap/update" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<input type="hidden" name="id" value="${bean.id}"/>
	<div class="pageFormContent" layoutH="56">
		<div>
			<dl class="nowrap">
				<label>AP名称：</label>
				<input type="text" name="ap_name" class="required" size="40" maxlength="50"  value="${bean.ap_name}" onblur="ajaxAPname(this)"/>
			</dl>
			<dl class="nowrap">
				<label>AC名称：</label>
				<input id="ac_name" name="ac.ac_name" type="text" postField="keyword" 
					suggestFields="ac_id,ac_name" suggestUrl="" lookupGroup="ac" value="${bean.ac_name}" 
					readonly="readonly"/><a class="btnLook" href="${ctx }/sysmanagement/ac/lookUp" lookupGroup="ac">查找带回</a>
				<input  id="ac_id" name="ac.ac_id" value="${bean.ac_id}" type="hidden" size="3" />
			</dl>
			<dl class="nowrap">
				<label>MAC地址：</label>
				<input type="text" name="mac" value="${bean.mac}"  class="required" size="40" maxlength="100"  onblur="ajaxMac(this)"/>
			</dl>
			<dl class="nowrap">
				<label>nas_port_id：</label><input type="text" name="nas_port_id" value="${bean.nas_port_id}" class="required" size="40" maxlength="200"/>
			</dl>
			<dl class="nowrap">
				<label>位置：</label><input type="text" name="location" value="${bean.location}" class="required" size="40" maxlength="100"/>
			</dl>
			<dl class="nowrap"  style="display: none;">
				<dt>状态：</dt>
				<dd><input type="checkbox" style="margin-top: 5px" <c:if test="${'Y'== bean.isdelete}">checked</c:if> name="isdelete" value="Y" class="textInput"><label style="width: 45px">已删除</label></dd>
			</dl>
			<dl class="nowrap">
				<dt>备注：</dt>
				<dd><textarea name="remark" cols="80" maxlength="2000" rows="10" >${bean.location}</textarea>
				</dd>
			</dl>
		</div>
	</div>
			<input type="hidden" name="createusername" value="${bean.createusername}" readonly="readonly"/>
			<input type="hidden" name="createtime" value="${bean.createtime}" readonly="readonly"/>
	<input type="hidden" id="bf1" name="bf1"  value="${bean.ap_name}" >
	<input type="hidden" id="bf2" name="bf2"  value="${bean.mac}" >
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>