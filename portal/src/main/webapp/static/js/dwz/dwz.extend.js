/**
 * 扩展实现表格头点击实现排序查询
 */
function initTableHeader() {

	// "navTab-panel tabsPageContent layoutBox
	var navTab = $(".layoutBox").find("div[style*=block]");

	if ($(navTab) != null && $("#pagerForm") != null) {
		var sortName = "";
		var sortType = "";
		$(navTab).find("#pagerForm>input:hidden").each(function() {
			if ($(this).attr("name") == "orderField") {
				sortName = $(this).val();

			} else if ($(this).attr("name") == "orderDirection") {
				sortType = $(this).val();
			}
		});

		$(navTab).find("thead>tr>th").each(function(i) {
			if ($(this).attr("orderField") == sortName) {
				$(this).attr("class", sortType);
			} else {
				if (!$(this).attr("orderField")) {
					$(this).removeAttr("class");
				}

			}
		});
	}

}

function getDialogIds(){
	var navTab = $(".layoutBox").find("div[style*=block]");
	var ids = [];
	var index = 0
	if ($(navTab) != null && $("#pagerForm") != null) {
		 $(navTab).find(".gridTbody").find(":checkbox").each(function(){
			 if ($(this).attr("checked")){
				ids[index] = $(this).val();
				index++ ;
			 }
			 
		 });
	}
	
	return ids.toString();
}


/**
 * 设置不可读和按钮绑定
 */

function initReadOnly(_box) {
	if (!_box) {
		return;
	}
	if ($(_box).find("input[name=readonly]").size() != 1) {
		return;
	}
	if ($(_box).find("input[name=readonly]").val() == "1") {
		$(_box).find("input[type=text]").each(function(i) {
			$(this).attr("disabled", "disabled");
		});
		$(_box).find("select").each(function(i) {
			$(this).attr("disabled", "disabled");
		});
		$(_box).find("button").each(
				function(i) {
					if ($.trim($(this).html()) == "保存") {
						$(this).hide();
					}
					if ($.trim($(this).html()) == "编辑") {
						$(this).bind(
								"click",
								function() {
									$($(navTab.getCurrentPanel()).find("input[name=readonly]")[0]).val("0");
									$(navTab.getCurrentPanel()).find(
											"input[type=text]").each(
											function(i) {
												$(this).removeAttr("disabled");
											});
									$(navTab.getCurrentPanel()).find("select")
											.each(function(i) {
												$(this).removeAttr("disabled");
											});
									$(this).next().show();
									$(this).prev().show();
									$(this).hide();
								});
						$(this).show();
					}
				});
	} else {
		$(_box).find("button").each(function(i) {
			if ($.trim($(this).html()) == "保存") {
				$(this).show();
			}
			if ($.trim($(this).html()) == "编辑") {
				$(this).hide();
			}
		});
	}
}

/**
 * 下载文件扩展
 * @param obj
 */
function exportFile(obj) {
	var navTab = $(".layoutBox").find("div[style*=block]");
	var id = $(navTab).find(".selected").attr("rel");
	var url = $(obj).attr("href");
	var temp = url.split("/");
	if (!isNaN(temp[temp.length-1])){
		var lastLen =(temp[temp.length-1]).length ;
		url = url.substring(0,url.length-lastLen-1);
	}
	if (isNaN(id)) {
		$(obj).attr("href", "#");
	} else {
		$(obj).attr("href", url + "/" + id);
	}
	
}

function _dowloadFile(obj){
	var $form =$("#hiddenForm"), $iframe = $("#hiddenFrame");
	// frame
	if ($iframe.size() == 0) {
		$frame = $("<iframe id='hiddenFrame' name='hiddenFrame' src='about:blank' style='display:none'></iframe>").appendTo("body");
	}
	// form
	if ($form.size() == 0) {
		$form = $("<form id='hiddenForm' method='post' target='hiddenFrame' style='display:none'></form>").appendTo("body");
	}
	// 属性
	$($form).attr('action',$(obj).attr("rel"));
	$($form).empty();
	var $arr = $($(obj).parents("form")[0]).serializeArray();
	$.each($($arr),function(i,n){
		$("<input name='"+$(this)[0].name+"' value='"+$(this)[0].value+"' />").appendTo($form);
	});
	$($form).submit();
	return false;
}
