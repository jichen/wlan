<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上网登录页面</title>
<script src="<%=request.getContextPath() %>/static/js/jquery/jquery-1.7.2.js" type="text/javascript"></script>
<script type="text/javascript">

function submitFun(){
	var flag=true;
	var phone=$("#username").val();
	if(phone=="" || !/^\d{11}$/.test(phone)){
		alert("请输入用户名(手机号码)！");
		flag=false;
	}
	
	if($("#password").val()=="" || !/^\d{6}$/.test($("#password").val())){
		alert("请输入6位动态密码！");
		flag=false;
	}
	
	if(flag){
		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath() %>/authenticate/overdue",
			data:{'username':phone},
			success:function(date){
				if(date=="N"){
					alert("登录出现异常请与服务方联系");
					return false;
				}else{
					alert("登陆成功");
					//跳出转计时页面
					
					//loginform.submit();
				}				
			},
			error:function(date){
				alert("系统问题请尽快联系服务方");
				return false;
			}
		});
	}else{
		return false;
	}
	

}


function dynamic_pwd(){
	var flag=true;
	var phone=$("#username").val();
	if(phone=="" || !/^\d{11}$/.test(phone)){
		 alert("请输入11为手机号码作为用户名");
		 flag=false;
	}
	if(flag){
		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath() %>/authenticate/dynamic",
			data:{'username':phone},
			success:function(date){
				if(date=="N"){
					alert("系统错误请再次获取密码");
					dynamic_look();
				}else{
					//间隔时间(换算成秒)
					interval_time=date*60;
					$("#dynamic").attr("disabled","");
					alert("已发送短信密码");
					dynamic_disabled(interval_time);

				}				
			}
		});
	}else{
		return false;
	}
	
}
//
function dynamic_disabled(secs){
	wait=secs*1000;//等的毫秒数;
	for(i=1;i<=secs;i++){
		setTimeout("doUpdate("+i+","+secs+")",i*1000);
	}
}
function doUpdate(num,all){
	var wut=all-num;
	if(wut!=0){
		$("#dynamic").attr("value","获取动态密码("+wut+")");
	}else{
		dynamic_look();
	}
}
function dynamic_look(){
	$("#dynamic").attr("disabled",false);
	$("#dynamic").attr("value","获取动态密码");
}



</script>
</head>
<body>
<div id="login"  align="center">
    <div>用户登录</div>
    <div>
       <form id="login_wlan" name="login_wlan" action="<%=request.getContextPath() %>/login_wlan/submit" method="post" >
          <div>
            <span>用户名：</span>
            <input id="username" name="username" type="text" maxlength="20" value="" />
          </div>
          <div>
            <span>密&nbsp;&nbsp;码：</span>
            <input id="password" name="password" type="password" maxlength="20" value="" />
          </div>
          <div>
		  </div>
          <div align="center">
		    <input name="" type="button" id="J_SubmitStatic" value="登录" onclick="submitFun()"/>
		    <input name="" type="reset" id="J_ResetStatic" value="重置"/>
            <input id="dynamic" name="dynamic" type="button" onclick="dynamic_pwd()" value="获取动态密码"  />
  		  </div>
       </form>
    </div>
</div>
</body>
</html>