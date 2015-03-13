//去前后空格
function trim(str) {
	if (str != null && str != undefined)
		return str.replace(/(^\s*)|(\s*$)/g, "");
	return false;
}

//验证mac地址
function isMacAddress(mac){
	var temp = /[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}/;
	if (!temp.test(mac)){
	     return false;
	}
	return true;
}

//验证车牌号
function isCarNumber(carNumber){
	var re = /^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/;
	if (carNumber.search(re) == -1) {
		// alert("输入的车牌号格式不正确");
		return false;
	} else {
		return true;
	}
}

//验证手机号 true:手机号正确  false:手机错误
function checkPhone(phone){
	var p1 = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/ ; 
	if (!p1.test(phone)){
		return false;
	}
	return true;
}

//数组去重
function uniqueArray(arr) {
	var ret = [];
	var hash = {};
	for (var i = 0; i < arr.length; i++) {
		var item = arr[i];
		var key = typeof (item) + item;
		if (hash[key] !== 1) {
			ret.push(item);
			hash[key] = 1;
		}
	}
	return ret;
}

//字符串去重
function filterRepeatStr(str){ 
	var ar2 = str.split(",");
	var array = new Array();
	var j = 0
	for (var i = 0; i < ar2.length; i++) {
		if ((array == "" || array.toString().match(new RegExp(ar2[i], "g")) == null)
				&& ar2[i] != "") {
			array[j] = ar2[i];
			array.sort();
			j++;
		}
	}
	return array.toString(); 
}


