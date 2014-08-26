<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>登录成功</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/static/wifi/mobile/CSS/StyleSheet.css" />
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
	document.getElementById("div1").innerHTML = "<span class='en lag2'>"+h+":"+m+":"+s+"</span>";
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
					var exiturl="<%=request.getContextPath()%>/access/off.jsp"
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
	var url="<%=request.getParameter("url")%>";
	window.open(url);
}

</script>
</head>

<body  onload="showTime()" onbeforeunload ="checkLeave()">
    <div>
        <div class="head"></div>
        <div class="frm">
            <div class="list">
                <div class="h blue">成功登录</div>
                <div class="item"><span class="blue">欢迎使用</span><span class="en lag">I-Pudong!</span></div>
                <div class="item"><span class="blue small">您本次上网时间</span></div>
                <div class="item center" id="div1"></div>
                <div class="item alert"><p>如需上网请重新打开一个浏览器窗口</p><p>在您上网期间请保留本页面</p></div>
                <div class="item center"><input type="button" id="off"  onclick='exit(1)'/></div>
                <div class="msg"></div>
            </div>
            
        </div>
    </div>
    <input type="hidden" name="clientMac" id="clientMac" value="" />
</body>
</html>
