<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/include.inc.jsp"%>
<h2 class="contentTitle">修改模块</h2>
<form method="post" action="${ctx}/security/module/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="id" value="${module.id }"/>
	<input type="hidden" name="parent.id" value="${module.parent.id }"/>
	<div class="pageFormContent" layoutH="97">
		<dl>
			<dt>名称：</dt>
			<dd>
				<input type="text" name="name" class="required" size="32" maxlength="32" value="${module.name }" alt="请输入模块名称"/>
			</dd>
		</dl>
		<dl>
			<dt>优先级：</dt>
			<dd>
				<input type="text" name="priority" class="required digits" size="2" maxlength="2" value="${module.priority }"/>
				<span class="info">&nbsp;&nbsp;默认:99</span>
			</dd>
		</dl>		
		<dl>
			<dt>授权名称：</dt>
			<dd>
				<input type="text" name="sn" class="required" size="32" maxlength="32" alt="授权名称" value="${module.sn }"/>
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>URL：</dt>
			<dd>
				<textarea name="url"  cols="80" rows="5" maxlength="600">${module.url }</textarea>
			</dd>
		</dl>		
		<dl class="nowrap">
			<dt>描述：</dt>
			<dd>
				<textarea name="description" cols="80" rows="5" maxlength="255">${module.description }</textarea>
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