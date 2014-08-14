function submitFun(){
	var flag=true;
	var phone=$("#username").val();
	var password=$("#password").val();
	if(flag){
		$.ajax({
			type:"POST",
			url:"/portal/authenticate/overdue",
			data:{'username':phone,'password':password},
			dataType:"json", 
			success:function(json){
				if(json["status"]==0){
					alert(json["msg"]);
					window.location.href= "/portal/access/wlan_index.jsp";
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
	if(phone=="" || !/^\d{11}$/.test(phone)){
		 alert("请输入11为手机号码作为用户名");
		 flag=false;
	}
	if(flag){
		$.ajax({
			type:"POST",
			url:"/portal/authenticate/dynamic",
			data:{'username':phone},
			dataType:"json", 
			success:function(json){
				if(json["status"]==0){//没有权限
					alert(json["msg"]);
					dynamic_look();
				}else if(json["status"]==1){//系统错误
					alert(json["msg"]);
					dynamic_look();
				}else if(json["status"]==2){//正常倒计时
					
					interval_time=json["msg"]*60;
					$("#dynamic").attr("disabled","");
					alert("已发送短信密码");
					dynamic_disabled(interval_time);
					
				}else if(json["status"]==3){//倒计时剩余时间
					interval_time=json["msg"];
					$("#dynamic").attr("disabled","");
					alert("请不要连续申请短信密码");
					dynamic_disabled(interval_time);
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

