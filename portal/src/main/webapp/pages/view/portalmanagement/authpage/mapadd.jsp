<%@page contentType="text/html;charset=UTF-8" 	trimDirectiveWhitespaces="true"%>
<%@include file="/include.inc.jsp" %>
<script type="text/javascript">

//给文本框赋值
function filetohidden(file,hfile){
	file.select();	
	hfile.value=document.selection.createRange().text;
}

function pc_module(){
	$("[id=file_pc]").each(function(){
		if($(this).attr("disabled")==false || $(this).attr("disabled")==null){
			$(this).attr("disabled","disabled");
		}else{
			$(this).attr("disabled",false);
		}
	});
	$("[id=url_pc]").each(function(){
		if($(this).attr("disabled")==false || $(this).attr("disabled")==null){
			$(this).attr("disabled","disabled");
		}else{
			$(this).attr("disabled",false);
		}
	});
}

function mobile_module(){
	$("[id=file_mobile]").each(function(){
		if($(this).attr("disabled")==false || $(this).attr("disabled")==null){
			$(this).attr("disabled","disabled");
		}else{
			$(this).attr("disabled",false);
		}
	});
	$("[id=url_mobile]").each(function(){
		if($(this).attr("disabled")==false || $(this).attr("disabled")==null){
			$(this).attr("disabled","disabled");
		}else{
			$(this).attr("disabled",false);
		}
	});
}

function formSubmit(){
	var url="${ctx }/templatemap/authmap/tMadd";
	$("#templateMapForm").attr("action", url);
	$("#templateMapForm").submit();
	
}
function show(){
	var url="${ctx }/template/previewPage";
	$("#templateMapForm").attr("action", url);
	$("#templateMapForm").submit();
	
}
</script>
<form  id="templateMapForm" action="" method="post" class="pageForm required-validate" 
	enctype="multipart/form-data" onsubmit="return validateCallback(this, navTabAjaxDone);" >
	
	<input type=hidden name="id" value="${bean.id}"/>
	<div class="pageFormContent" layoutH="56" align="left">
		<div>
			<dl class="nowrap">
				<dt>模板名称：</dt>
				<dd><label style="width: 80px">${bean.templatename}</label><input type="hidden" name="template_id"  value="${bean.id}" class="digits" readonly="readonly"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>终端类型：</dt>
				<dd>
					<input type="checkbox" style="margin-top:2px" name="pc"  value="Y" class="textInput"  onchange="pc_module()"><label style="width: 20px">PC</label>
					<input type="checkbox" style="margin-top:2px" name="mobile"  value="Y" class="textInput" onchange="mobile_module()"><label style="width: 60px">移动终端</label>
				</dd>
			</dl>
			<!-- 分别显示需要上传的pc和mobile的css和image -->
			<c:if test="${bean.csscount >0}">
				<c:forEach var="x" begin="1" end="${bean.csscount}" step="1">
					<dl class="nowrap">
						<dt>PC端CSS式样${x}：</dt>
						<dd>
							<input id="file_pc" name="css_${x}_file_pc" type="file" disabled="disabled" size="40" class="required" onchange="filetohidden(this,css_${x}_pc)"/>
							<input name="css_${x}_pc" type="hidden" value="" />
						</dd>
					</dl>
				</c:forEach>
			</c:if>			
			<c:if test="${bean.csscount >0}">
				<c:forEach var="x" begin="1" end="${bean.csscount}" step="1">
					<dl class="nowrap">
						<dt>移动终端CSS式样${x}：</dt>
						<dd>
						<input id="file_mobile" name="css_${x}_file_mobile" type="file" disabled="disabled" size="40" class="required" onchange="filetohidden(this,css_${x}_mobile)"/>
						<input name="css_${x}_mobile" type="hidden" value="" />
						</dd>
					</dl>
				</c:forEach>
			</c:if>				
			<c:if test="${bean.imagecount >0}">
				<c:forEach var="x" begin="1" end="${bean.imagecount}" step="1">
					<dl class="nowrap">
						<dt>PC端页面图片${x}：</dt>
						<dd>
						<input id="file_pc" name="image_${x}_file_pc" type="file" disabled="disabled" size="40" class="required" onchange="filetohidden(this,image_${x}_pc)" />
						<input name="image_${x}_pc" type="hidden" value="" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>PC端图片链接${x}：</dt>
						<dd><input id="url_pc" name="image_${x}_url_pc"  size="60" maxlength="255" type=text value="" disabled="disabled" ></dd>
					</dl>
				</c:forEach>
			</c:if>
			<c:if test="${bean.imagecount >0}">
				<c:forEach var="x" begin="1" end="${bean.imagecount}" step="1">
					<dl class="nowrap">
						<dt>移动终端页面图片${x}：</dt>
						<dd>
						<input id="file_mobile" name="image_${x}_file_mobile" type="file" disabled="disabled" size="40" class="required" onchange="filetohidden(this,image_${x}_mobile)" />
						<input name="image_${x}_mobile" type="hidden" value="" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>移动终端图片链接${x}：</dt>
						<dd><input id="url_mobile" name="image_${x}_url_mobile"  size="60" maxlength="255" type=text value="" disabled="disabled" ></dd>
					</dl>
				</c:forEach>
			</c:if>
			<!-- end -->
			<c:if test="${bean.textcount >0}">
				<c:forEach var="x" begin="1" end="${bean.textcount}" step="1">
					<dl class="nowrap">
						<dt>文本内容${x}：</dt>
						<dd><input id="text_${x}" class="required"  size="75" maxlength="255" name="text_${x}" type=text value="" /> </dd>
					</dl>
				</c:forEach>
			</c:if>			
			<input type="hidden" value="${bean.textcount}" name="textcount" >
			<input type="hidden" value="${bean.imagecount}" name="imagecount" >
			<input type="hidden" value="${bean.csscount}" name="csscount" >
		</div>
			
	
	</div>
	<div class="formBar">
		<ul>
			<!-- 
			<li><div class="button"><div class="buttonContent"><button type="button" onclick="show()">效果</button></div></div></li>
			 -->
			<li><div class="button"><div class="buttonContent"><button type="button" onclick="formSubmit()">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</table>
</form>
