<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp" %>
<script type="text/javascript">
function isview(flag,id,deleteflag){
	var url="${ctx}/template/preview/"+id;
	$("#preview").attr("href",url);
	if(deleteflag=='N'){
		if(flag=='N'){
			//alert($(".pageShow").find("#tMAdd"));
			$(".pageShow").find("#tMAdd").attr("disabled",false);
			$(".pageShow").find("#tMUpdate").attr("disabled","");
			//$(".pageShow").find("#preview").attr("disabled","");
		}else if(flag=='Y'){
			//alert($(".pageShow").find("#tMAdd"));
			$(".pageShow").find("#tMAdd").attr("disabled","");
			$(".pageShow").find("#tMUpdate").attr("disabled",false);	
			//$(".pageShow").find("#preview").attr("disabled",false);	
		}
	}else if(deleteflag=='Y'){
		//alert($(".pageShow").find("#tMAdd"));
		$(".pageShow").find("#tMAdd").attr("disabled","");
		$(".pageShow").find("#tMUpdate").attr("disabled","");
		//$(".pageShow").find("#preview").attr("disabled","");
	}
	
}
</script>

<div class="pageHeader">
<form  id="pagerForm" action="${ctx}/templatepage/authpage/list"  method="post"  rel="pagerForm"  onsubmit="return navTabSearch(this);">
		<c:choose>
			<c:when test="${pageForm.pageNum==null}">
				<input type="hidden" name="pageNum" value="1" />
			</c:when>
			<c:otherwise>
				<input type="hidden" name="pageNum" value="${pageForm.pageNum}" />
			</c:otherwise>
		</c:choose>
		<input type="hidden" name="numPerPage" value="10" /> 
		<input type="hidden" name="orderField" value="" /> 
		<input type="hidden" name="orderDirection" value="" />
	<div class="searchBar">
		<table class="searchContent"  align="left" >
			<tr>
				<td>
					&nbsp;模板名称：
				</td>
				<td>
					<input name="name" type="text" value="${pageForm.name}">
				</td>
				<td>
					 
				</td>
				<td>
				<!-- 
					<select name="isdelete"  style="width: 132px">
						<option ${pageForm.isdelete == "null"?"selected":"" }  value="">请选择</option>
						<option ${pageForm.isdelete == "N"?"selected":"" }  value="N">使用中</option>
						<option ${pageForm.isdelete == "Y"?"selected":"" }  value="Y">已删除</option>
					</select>
					 -->
				</td>
				<td>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
					</ul>
				</div>
				</td>
			</tr>
		</table>
	</div>
</form>
</div>
<div class="pageContent">
	<div class="pageShow">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add"   id="tPAdd"  href="${ctx}/templatepage/authpage/preadd"     target="navTab" rel="tPAdd"><span>新增模板</span></a></li>
			<li><a class="edit"   id="tPUpdate" name="tPUpdate" href="${ctx}/templatepage/authpage/preupdate/{slt_uid}"     target="navTab" rel="tPUpdate"><span>编辑模板</span></a></li>
			<li><a class="add"   id="tMAdd"   name="tMAdd" disabled  href="${ctx}/templatemap/authmap/preadd/{slt_uid}"     target="navTab" rel="tMAdd"><span>新增模板内容</span></a></li>
			<li><a class="edit"  id="tMUpdate" name="tMUpdate" disabled  href="${ctx}/templatemap/authmap/preUpdate/{slt_uid}"  target="navTab" rel="tMUpdate"><span>编辑模板内容</span></a></li>
			<!-- 
			<li><a class="icon"  id="preview"  disabled  href="" target="_blank" ><span>预览</span></a></li>
			
			<li><a class="delete" href="${ctx}/sysmanagement/ac/delete/{slt_uid}" target="ajaxTodo" title="确认要删除该设置?"><span>删除模板内容</span></a></li>
			-->
		</ul>
	</div>
	</div>
	<table class="table"  layoutH="145"  width="100%" >
		<thead>
			<tr>
				<th width="50" >序号</th>
				<th width="200" >模板名称</th>
				<th width="100" >式样数量</th>
				<th width="100" >图片数量</th>
				<th width="100" >文本数量</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="templatePages" items="${list}"  varStatus="status">
			<tr target="slt_uid" rel="${templatePages.id}" onclick="isview('${templatePages.ismap}','${templatePages.id}','${templatePages.isdelete}')">
				<td>${status.index+1}</td>
				<td>${templatePages.templatename}</td>
				<td>${templatePages.csscount}</td>
				<td>${templatePages.imagecount}</td>
				<td>${templatePages.textcount}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示10条，共${page.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.totalCount}" numPerPage="10" pageNumShown="10" currentPage="${page.pageNum}"></div>
	</div>
</div>
