<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/include.inc.jsp"%>
<form method="post" action="${ctx}/sysmanagement/ac/update" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<input type="hidden" name="id" value="${bean.id}"/>
	<div class="pageFormContent" layoutH="56">
		<div>
			<dl class="nowrap">
				<dt>AC名称：</dt>
				<dd><input type="text" name="ac_name"  value="${bean.ac_name}"  class="required" size="40" maxlength="200" /></dd>
			</dl>
			<dl class="nowrap">
				<dt>公司名称：</dt>
				<dd>
				<input id="cust_name" name="cust.cust_name" type="text" 
						postField="keyword" suggestFields="cust_id,cust_name" suggestUrl="" 
						lookupGroup="cust" value="${bean.cust_name}" readonly="readonly" />
				<a class="btnLook"   href="${ctx }/sysmanagement/customer/lookUp" lookupGroup="cust">查找带回</a>
				<input  id="cust_id" name="cust.cust_id" value="${bean.cust_id}" type="hidden" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>IP：</dt>
				<dd><input type="text" name="ip"   value="${bean.ip}"  class="required" size="40" maxlength="20"  /></dd>
			</dl>
			<dl class="nowrap">
				<dt>位置：</dt>
				<dd><input type="text" name="location"  value="${bean.location}"  class="required" size="40" maxlength="200"  /></dd>
			</dl>
			<dl class="nowrap">
				<dt>状态：</dt>
				<dd> <input type="checkbox"  style="margin-top: 5px" <c:if test="${'Y'== bean.isdelete}">checked</c:if> name="isdelete" value="Y" class="textInput"><label style="width: 50px">已删除</label></dd>
			</dl>
			<dl class="nowrap">
				<dt>备注：</dt>
				<dd><textarea name="remark" cols="80" maxlength="2000" rows="10" >${bean.remark}</textarea></dd>
			</dl>
		</div>
	</div>
	<input type="hidden" name="createusername" value="${bean.createusername}" readonly="readonly"/>
	<input type="hidden" name="createtime" value="${bean.createtime}" readonly="readonly"/>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>