<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台登录页面</title>
<link rel="stylesheet" href="${ctx}/static/css/login.css"/>
<script src="${ctx}/static/js/jquery/jquery-1.7.2.js" type="text/javascript"></script>
<script type="text/javascript">
function submitFun(){
	if($.trim($("#usernameId").val())==""){
		alert("请输入用户名称");
	}
	else if($.trim($("#passwordId").val())==""){
		alert("请输入密码");
	}
	else if($.trim($("#checkId").val())==""){
		alert("请输入验证码");
	}
	else{
		$.ajax({
			type: "post", 
			url: "${ctx}/submit",
			data:{username:$("#usernameId").val(),password:$("#passwordId").val(),check:$("#checkId").val()},
			dataType:"json", 
			success:function(json){
				if(json["status"]==0){
					$("#randImageId").attr("src","${ctx}/verify?"+Math.random());
					alert(json["msg"] );
				}
				else if(json["status"]==1){
					window.location.href="${ctx}/index";
				}
			}
		});
	}
	return false;
}
function resetform(){
	$("#usernameId").val("");
	$("#passwordId").val("");
	$("#checkId").val("");
	reloadImage();
}

function reloadImage(){
	$("#randImageId").attr("src","${ctx}/verify?"+Math.random()); 
}

function keyEvent(){
	if(window.event.keyCode==13){
		submitFun();
	}
}

$(document).ready(function(){
	var divID = $("body>div").attr("id");
	if (divID != "login"){
		window.location.href = "${ctx}/login";
	}
});

</script>
</head>
<body onkeypress="keyEvent()">
<div class="main" id="login">
  <div class="login_main">
    <div class="login_home"></div>
    <div class="login_title">
      <div class="big_title"> WLAN业务管理系统登录页面 </div>
      <div class="login_system"></div>
    </div>
    <div class="login_body">
      <form id="Loginform" action="" method="post" class="login2" >
        <div class="login_frist">
          <div class="login_username">用户名
            <input id="usernameId" name="username" type="text" style="color:white;" maxlength="10" value="" />
          </div>
		  <div class="login_password">密&nbsp;&nbsp;&nbsp;&nbsp;码
	          <input id="passwordId" name="password" type="password" style="color:white;" maxlength="10" value="" />
	      </div>

        </div>
        <div class="login_secred">
          <div class="login_code">验证码
          	<input id="checkId" name="check" type="text" style="color:white;" maxlength="20" />
          </div>
		  <div class="login_sub">
			<img class="log" id="randImageId" onclick="reloadImage()" style="width:100px;height:22px;" src="${ctx}/verify" />
		  </div>
        </div>
		<div class="login_sub_1">
		  <image src="${ctx}/static/images/dl.gif" style="height:19px;"  onclick="return submitFun(this);" >
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  <image src="${ctx}/static/images/cz.png"  style="height:19px;" onclick="resetform();" >
		</div>
      </form>
    </div>
  </div>
</div>
</body>
</html>