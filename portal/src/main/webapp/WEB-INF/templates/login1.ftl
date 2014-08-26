<html >
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>智慧浦东无线城市--公众免费无线宽带（WLAN）上网</title>
    <meta name="description" content="" />
    
    <link type="text/css" rel="stylesheet" href="${ctx}/static/wifi/web/main.css" />
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
						window.location.href= "${ctx}/access/success1.jsp?mac="+mac;
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
 
    
    
    
    
</head>
<body>
    <!--导航头部区域-->
    <div class="header"><img src="${ctx}/static/wifi/web/logo.gif" width="558" height="80" /></div>
    <div class="nav_wrap">
        <ul>
            <li class="li_bj"><a href="#">首页</a></li>
            <li class="li_bj"><a href="#">场点查询</a></li>
            <li class="li_bj"><a href="#">常见问题</a></li>
            <li class="li_bj"><a href="#">关于我们</a></li>
        </ul>
    </div>
    <!--内容区-->
    <div class="main_wrap">
        <form id="form1" name="form1" method="post" action="" style="margin:0; padding:0;">
            <dl class="DengLu_dl">
                <dt class="dth">用户登录<span>User Login</span></dt>
                <dt class="dt1">手机号码<span class="font_en">Mobile phone Number</span></dt>
                <dd class="dt2"><input name="username" id="username" type="text" class="input" /></dd>
                <dt class="dt3">登录密码<span class="font_en">Password</span></dt>
                <dd class="dt4"><input name="password" id="password" type="password" class="input" /></dd>
                <dd class="dt5">
                    <input name="" type="button" class="btn1" id="getPWInner" onclick="dynamic_pwd()" />
                </dd>
                <dd class="dt6"><input name="" id="loginbutton" type="button" class="btn2"onclick="submitFun()" /></dd>
            </dl>
			<input id="clientMac" name="clientMac" type="hidden" maxlength="20" value="${clientMac}" />
			<input id="userip" name="userip" type="hidden" maxlength="20" value="${userip}" />
        </form>
        <div class="Tips">
            <div class="dth">说明<span>TIPS</span></div>
            <p>
                1、每账号（手机号码）每天可累计免费使用24小时，密码当天有效，过期请重新通过手机短信申请。<br />
                2、为保证普遍服务质量，对视频、游戏等大流量应用有流量限制。
            </p>
        </div>
        <div class="ipudong">
            <img src="${ctx}/static/wifi/web/ipudong_14.png" width="415" height="353" />
        </div>
    </div>
    <!--底部-->
    <div class="bottom_wrap">
        <ul class="bottom"><li class="li1">客服电话：10086</li><li class="li2">中国移动通信集团上海有限公司 版权所有 Copyright &#169; 2014 </li> <li class="li3"><img style="margin-top:5px;" src="${ctx}/static/wifi/web/logo.png" /></li></ul>
    </div>

</body>
</html>