<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/include.inc.jsp"%>
<form method="post" action="${ctx}/templatepage/authpage/update" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<input type="hidden" name="id" value="${bean.id}"/>
	<div class="pageFormContent" layoutH="56">
		<div>
			<dl class="nowrap">
				<dt>模板名称：</dt>
				<dd><input type="text" name="templatename" class="required" size="40" maxlength="50"  value="${bean.templatename}" /></dd>
			</dl>
			<dl class="nowrap">
				<dt>css文件数量：</dt>
				<dd><input type="text" name=csscount class="required" size="10" maxlength="2"  value="${bean.csscount}" /></dd>
			</dl>
			<dl class="nowrap">
				<dt>图片数量：</dt>
				<dd><input type="text" name=imagecount class="required" size="10" maxlength="2"  value="${bean.imagecount}" /></dd>
			</dl>
			<dl class="nowrap">
				<dt>文本数量：</dt>
				<dd><input type="text" name=textcount class="required" size="10" maxlength="2"  value="${bean.textcount}" /></dd>
			</dl>
			<dl class="nowrap">
				<dt>状态：</dt>
				<dd><input type="checkbox" style="margin-top: 5px" <c:if test="${'Y'== bean.isdelete}">checked</c:if>
						name="isdelete" value="Y" class="textInput"><label style="width: 45px">已删除</label>
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