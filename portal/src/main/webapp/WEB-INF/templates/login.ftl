<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<head>
    <title>登录验证</title>   
    <link type="text/css" rel="stylesheet" href="${ctx}/static/wifi/mobile/CSS/StyleSheet.css" />
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
		var clientMac=$("#clientMac").val();
		if(flag){
			$.ajax({
				type:"POST",
				url:"${ctx}/authenticate/overdue",
				data:{'username':phone,'password':password,'userip':userip,'wlanurl':wlanurl,'clientMac':clientMac},
				dataType:"json", 
				success:function(json){
					if(json["status"]==0){
						alert(json["msg"]);
						var url=json["url"];
						var mac=json["mac"];
						window.location.href= "${ctx}/access/success.jsp?mac="+mac;
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
</head>


<body>
    <div>
        <div class="head"></div>
        <div class="frm">
            <div class="list">
                <div class="h blue">${text_1}</div>
                <div class="item"><span class ="blue">${text_2}</span><span class="en">Moblie Phone Number</span></div>
                <div class="item center"><input type="text" id="username" name="username"/></div>
                <div class="item"><span class="blue">${text_3}</span><span class="en">Password</span></div>
                <div class="item center"><input type="password"  id="password" name="password"/></div>
                <div class="item center"><input type="button" id="getpwd" onclick="dynamic_pwd()" /></div>
                <div class="item center"><input type="button" id="login"  onclick="submitFun()"/></div>
                
            </div>
            
        </div>
    </div>
	<input id="clientMac" name="clientMac" type="hidden" maxlength="20" value="${clientMac}" />
	<input id="userip" name="userip" type="hidden" maxlength="20" value="${userip}" />
</body>
</html>
