<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/include.inc.jsp"%>
<script type="text/javascript">

//给文本框赋值
function filetohidden(file,hfile){
	alert(file);
	file.select();	
	alert(file.select());
	alert(document.selection);
	alert(document.selection.createRange());
	alert(document.selection.createRange().text);
	hfile.value=document.selection.createRange().text;	
}
</script>
<form method="post" action="${ctx}/templatepage/authpage/add" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<input type="hidden" name="id" value="${bean.id}"/>
	<div class="pageFormContent" layoutH="56">
		<div>
			<dl class="nowrap">
				<dt>模板名称：</dt>
				<dd><input type="text" name="templatename" class="required" size="40" maxlength="50"  value="" /></dd>
			</dl>
			<dl class="nowrap">
				<dt>css文件数量：</dt>
				<dd><input type="text" name=csscount class="required" size="10" maxlength="2"  value="" /></dd>
			</dl>
			<dl class="nowrap">
				<dt>显示图片数量：</dt>
				<dd><input type="text" name=imagecount class="required" size="10" maxlength="2"  value="" /></dd>
			</dl>
			<dl class="nowrap">
				<dt>显示文本数量：</dt>
				<dd>
					<input type="text" name=textcount class="required" size="10" maxlength="2"  value="" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>上传模板：</dt>
				<dd><input name="templatelocal_file" type="file" size="30" style="margin-left: -300px" class="required" onchange="filetohidden(this,templatelocal)" />
					<input name="templatelocal" type="hidden" value="" />
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