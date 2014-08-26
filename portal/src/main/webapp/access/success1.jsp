<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>智慧浦东无线城市--公众免费无线宽带（WLAN）上网</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/static/wifi/web/main.css" />
	<script src="<%=request.getContextPath() %>/static/js/jquery/jquery-1.7.2.js" type="text/javascript"></script>
<script type="text/javascript">

var indate = new Date();
var num=1;
function showTime(){
	var todate = new Date();
	var timelong = todate - indate;
	var h = parseInt(timelong/3600000,10);
	var m = parseInt(timelong%3600000/60000,10);
	var s = Math.floor(timelong%60000/1000);
	document.getElementById("div1").innerHTML = ""+h+":"+m+":"+s+"";
	setTimeout("showTime()",1000);
}
function exit(type){
	var str;
	if(type=='1'){
		str="你确定要下线吗？如果下线，联网服务也将关闭。";
	}else{
		str="刷新页面或者关闭页面，联网服务将关闭。";
	}
	if(window.confirm(str)){
		var userip=$("#userip").val();
		var clientMac=$("#clientMac").val();
		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath() %>/authenticate/wLanExit",
			data:{'userip':userip,'clientMac':clientMac},
			dataType:"json", 
			success:function(json){
				if(json["status"]==0){
					alert(json["msg"]);
					var userAgent = navigator.userAgent;
					var exiturl="<%=request.getContextPath()%>/access/off1.jsp"
					//alert(userAgent);
					if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Mozilla") != -1 || userAgent.indexOf("Presto") != -1 || userAgent.indexOf("Safari") != -1) {
					    num=2;
						window.location.replace(exiturl);
					} else {
						num=2;
						window.opener=null;      
						window.open(exiturl,'_self');      
						window.close();     
					}
				}else if(json["status"]==1){
					alert(json["msg"]);
					return false;
				}else if(json["status"]==2){
					alert(json["msg"]);
					return false;
				}else if(json["status"]==3){
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
function checkLeave(){
	if(num=="1"){
		exit(2);
		return false;
	}else{
		
	}
}

setTimeout("openurl()",2000);
function openurl(){
	var mac="<%=request.getParameter("mac")%>";
	if(mac!=""){
		$("#clientMac").attr("value",mac);
	}else{
		alert("MAC为空");
	}
}

</script>
</head>

<body  onload="showTime()" onbeforeunload ="checkLeave()">
    <!--导航头部区域-->
    <div class="header"><img src="<%=request.getContextPath() %>/static/wifi/web/logo.gif" alt="智慧浦东，无线未来" /></div>
    <div class="nav_wrap">
        <ul>
            <li class="li_bj"><a href="javascript:void(0);">首页</a></li>
            <li class="li_bj"><a href="javascript:void(0);" target="blank">场点查询</a></li>
            <li class="li_bj"><a href="javascript:void(0);" target="blank">常见问题</a></li>
            <li class="li_bj"><a href="javascript:void(0);" target="blank">关于我们</a></li>
        </ul>
    </div>
    <!--内容区-->
    <div class="main_wrap">
        <dl class="DengLu_dl">
            <dt class="dth center lag">成功登录</dt>
            <dt class="dt1 center lag">欢迎使用<span class="en">I-Pudong!</span></dt>
            <dt class="normal l2">您本次上网时间</dt>
            <dt class="lag2 l3"><div id="div1"></div></dt>
            <dt class="l4"><img src="<%=request.getContextPath() %>/static/wifi/web/ipudong_42.png" /></dt>
            <dt class="l5 small yellow">如需上网请重新打开一个浏览器窗口</dt>
            <dt class="l6 small yellow">在您上网期间请保留本页面</dt>            
            <dt class="l7"><input type="button" id="off" onclick="exit(1)"/></dt>
        </dl>
    	<input type="hidden" name="clientMac" id="clientMac" value="" />
        <div class="mark"></div>
        <div class="DL_logo"><img src="<%=request.getContextPath() %>/static/wifi/web/ipudong_14.png" width="415" height="353" /></div>
    </div>
    <!--底部-->
    <div class="bottom_wrap">
        <ul class="bottom">
        	<li class="li1">客服电话：10086</li>
        	<li class="li2">中国移动通信集团上海有限公司 版权所有 Copyright &#169; 2014 </li>
        	<li class="li3"><img style="margin-top:5px;" src="<%=request.getContextPath() %>/static/wifi/web/logo.png" /></li>
        </ul>
    </div>
</body>
</body>
</html>
