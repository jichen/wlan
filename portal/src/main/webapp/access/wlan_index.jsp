<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录计时页面</title>
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
	document.getElementById("div1").innerHTML = "您的上线时间为："+h + "小时" + m + "分" + s + "秒。如果关闭或刷新此页，联网服务也将关闭。"
												+"<br/><input type='button' value='下线' onclick='exit(1)'/>"
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
					//alert(userAgent);
					if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Mozilla") != -1 || userAgent.indexOf("Presto") != -1 || userAgent.indexOf("Safari") != -1) {
					    num=2;
						window.location.replace("http://www.baidu.com/");
					} else {
						num=2;
						window.opener=null;      
						window.open("http://www.baidu.com/",'_self');      
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
	var url="<%=request.getParameter("url")%>";
	window.open(url);
}

</script>
</head>
<body onload="showTime()" onbeforeunload ="checkLeave()">
<input type="hidden" name="clientMac" id="clientMac" value="" />
<input type="hidden" name="userip" id="userip" value=<%=session.getAttribute("userip")%> />
用户：<%=session.getAttribute("username")%>，您好：<div id="div1"><div>
</body>
</html>