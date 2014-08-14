function countDown(obj,second) {
	if (second>=0) {
		if (typeof buttonDefaultValue === 'undefined') {
			buttonDefaultValue = obj.defaultValue;
		}
		obj.disabled = true;
		
		obj.value = '(' + second + ')秒后重新获取验证码!';
		
		second--;
		
		setTimeout(function(){
			countDown(obj,second);
		},1000);
	} else {
		obj.disabled = false;
		obj.value = buttonDefaultValue;
	}
}