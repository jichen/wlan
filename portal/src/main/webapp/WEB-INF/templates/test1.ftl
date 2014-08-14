
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<LINK rel=stylesheet type=text/css href="http://221.130.184.30:6069/css.css">

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
					window.location.href= "${ctx}/access/wlan_index.jsp?mac="+mac;
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
<form id="login_wlan" name="login_wlan">
<TABLE border=0 cellSpacing=0 cellPadding=0 width="350px" align="center">
  <TR>
    <TD bgColor="#ffcc00"><DIV class="text" align="center">${text_1}</DIV></TD>
    <TD width="1"></TD>
  </TR>
  <TR>
    <TD bgColor=#f4f9fd vAlign=center colSpan=2>
      <TABLE class=d border=0 cellSpacing=0 cellPadding=0 width="350px" align="center" height="59">
        <TR bgColor=#f1f9fe>
          <TD height=30 width="200px">${text_2}
			<input id="username" name="username" type="text" maxlength="20" value="" size="15" />
          </TD>
          <TD height=30 width="150px">${text_3}
          	<input id="password" name="password" type="password" maxlength="20" value="" size="8"/>
			<input id="userip" name="userip" type="hidden" maxlength="20" value="${userip}" />
			<input id="clientMac" name="clientMac" type="hidden" maxlength="20" value="${clientMac}" />
          </TD>
        </TR>
       </TABLE>
    </TD>
  </TR>
  <TR>
    <TD>
		<input name="" type="button" id="J_SubmitStatic"  value="登录" onclick="submitFun()"/>
		<input name="" type="reset" id="J_ResetStatic"  value="重置"/>
		<input id="dynamic" name="dynamic" type="button" onclick="dynamic_pwd()" value="获取动态密码"  />
    </TD>
  </TR>
  <TR>
    <TD>
    	<!-- http://221.130.184.30:6069/buttom.jpg -->
		<img border=0 src="${ctx}/template/imageshow?path=${image_1}" />
    </TD>
  </TR>
</TABLE>

</form>
</BODY>
</html>