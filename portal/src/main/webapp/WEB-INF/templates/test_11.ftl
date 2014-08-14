
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- LINK rel=stylesheet type=text/css href="${css_1}">-->
<HEAD>
<script src="${ctx}/static/js/jquery/jquery-1.7.2.js" type="text/javascript"></script>
<script type="text/javascript">
function submitFun(){
	var flag=true;
	var phone=$("#username").val();
	var password=$("#password").val();
	if($.trim(phone)=="" || !/^1\d{10}$/.test(phone)){
		 alert("请输入11位手机号码作为用户名");
		return false;
	}
	else if($.trim(password)==""){
		alert("请输入密码");
		return false;
	}
	var wlanurl=$("#wlanurl").val();
	var userip=$("#userip").val();
	if(flag){
		$.ajax({
			type:"POST",
			url:"${ctx}/authenticate/overdue",
			data:{'username':phone,'password':password,'userip':userip,'wlanurl':wlanurl},
			dataType:"json", 
			success:function(json){
				if(json["status"]==0){
					alert(json["msg"]);
					var url=json["url"];
					window.location.href= "${ctx}/access/wlan_index.jsp?url="+url;
				}else if(json["status"]==1){
					alert(json["msg"]);
					return false;
				}else if(json["status"]==2){
					alert(json["msg"]);
					return false;
				}
			},
			error:function(json){
				alert("系统问题请尽快联系服务方");
				return false;
			}
		});
	}else{
		return false;
	}
}


//获取动态密码
function dynamic_pwd(){
	var flag=true;
	var phone=$("#username").val();
	if($.trim(phone)=="" || !/^1\d{10}$/.test(phone)){
		 alert("请输入11位手机号码作为用户名");
		 flag=false;
	}	
	if(flag){
		$("#dynamic").attr("disabled","");
		$.ajax({
			type:"POST",
			url:"${ctx}/authenticate/countdown",
			data:{'username':phone},
			dataType:"json", 
			success:function(json){
				if(json["status"]==0){
					alert(json["msg"]);
				}else if(json["status"]==1){
					alert(json["msg"]);
					var interval_time=json["repeat"]*60;
					dynamic_disabled(interval_time);
				}else if(json["status"]==2){
					var interval_time=json["repeat"]*60;
					dynamic_disabled(interval_time);
					if(json["msg"]=='OK'){
						$.ajax({
							type:"POST",
							url:"${ctx}/authenticate/dynamic",
							data:{'username':phone},
							dataType:"json", 
							success:function(json){
								if(json["status"]==0){
									alert("已成功发送短信密码。");
								}else{
									alert("短信发送问题请尽快联系服务方。");
								}				
							},
							error:function(json){
								alert("系统问题请尽快联系服务方。");
								return false;
							}
						});
					}
				}	
			}
		});
	}
}

//动态密码按钮倒计时
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
</HEAD>
<BODY bgColor="#ffffff"><BR /><BR />
dfdfdfd
</BODY>
</html>