/**
 * 下拉框对象
 * 参数说明
 * pid：父下拉框id 
 * id：(子)下拉框id 
 * name：(子)下拉框显示名称
 * 
 */
/** @namespace 总命名空间 */
var mss = {};

function dropDownListMode(pid, id, name) {
	this.pid = pid;
	this.id = id;
	this.name = name;
}

/**
 * 默认的ajax执行前处理。<br>
 *
 * @public
 * @method
 */
var ajaxStart_default = mss.ajaxStart_default = function () {
	if (mss.showLoading) {
		jQuery
			.blockUI({
				message: '<div style="margin:60px 50px 50px 50px;"><img src="images/common/loading.gif"/><br/><span style="font-size:10pt;font-weight:bold;color:#4C6AAC;">loading...</span></div>',
				fadeIn: 50,
				css: {
					backgroundColor: '#ffffff',
					color: '#000000',
					width: '30%',
					height: '22%',
					/*
					 left: '37%',
					 top: '45%',*/
					border: '2px solid #4C6AAC'
				}
			}); // 锁定页面
	}
};

/**
 * 校验字符串是否为空
 * @param str
 * @returns {boolean}
 */
function isNullStr(str){
	if(str==null||typeof(str)==undefined){
		return true;
	}
	if($.trim(str+"").length<=0){
		return true;
	}

	return false;
}

/**
 * 页面无刷新方式下载文件
 */
var downloadClearing = mss.downloadClearing = function (action, params) {
	ajaxStart_default();
	var ifream = $("<iframe>"); // 定义一个iframe表单
	ifream.attr('style', 'display:none'); // 在iframe表单中添加查询参数
	ifream.attr('name', 'hiddenFream');
	$('body').append(ifream); // 将iframe放置在web中

	var form = $("<form>"); // 定义一个form表单
	form.attr('style', 'display:none'); // 在form表单中添加查询参数
	form.attr('target', 'hiddenFream');
	form.attr('method', 'post');
	form.attr('action', action);

	if (params != undefined) {
		for (name in params) {
			var input = $("<input>");
			input.attr('name', name); // 在form表单中添加查询参数
			input.attr('value', params[name]);
			input.attr('type', 'hidden');
			form.append(input); // 将查询参数控件提交到表单上
		}
	}

	$('body').append(form); // 将表单放置在web中

	form.submit(); // 表单提交
	
	
};

/**
 * 默认的ajax执行完毕处理。<br>
 *
 * @public
 * @method
 */
var ajaxStop_default = mss.ajaxStop_default = function () {
	if (mss.showLoading) {
		setTimeout(function () {
			jQuery.unblockUI(); // 解锁页面
		}, 0);
	}
};

/**
 * 下拉框联动
 * demo详见merchantList.jsp的function changeProvince()
 * 
 * 参数说明 
 * String fatherId：父下拉框id
 * String sonId：根据父下拉框值的变化而变化的子下拉框的id
 * Array[] relationDataArray：父子关系数据,Array<dropDownListMode> 
 * Array[] sonDataArray：子下拉框原始数据,Array<dropDownListMode> 可以为空
 */
function changeFatherSelect(fatherId, sonId, relationDataArray,	sonDataArray) {
	
	var obj = document.getElementById(fatherId);
	var toobj = document.getElementById(sonId);
	var relationArray = relationDataArray;
	var sonArray = sonDataArray;
	/*for (i = toobj.options.length - 1; i > 0; i--) {
		toobj.options[i] = null;
	}
	*/
	var ln = toobj.options.length;
	while(ln--){
		toobj.options[ln] = null;
	}
	if (obj.value != "") {
		for ( var i = 0; i < relationArray.length; i++) {
			if (relationArray[i].pid == obj.value) {
				toobj.options[toobj.options.length] = new Option(
						relationArray[i].name, relationArray[i].id);
			}
		}
	} else {
		for ( var i = 0; i < sonArray.length; i++) {
			toobj.options[toobj.options.length] = new Option(sonArray[i].name,
					sonArray[i].id);
		}
	}

}
function appealChangeFatherSelect(fatherId, sonId, relationDataArray,sonDataArray) {
	var obj = document.getElementById(fatherId);
	var toobj = document.getElementById(sonId);
	var relationArray = relationDataArray;
	var sonArray = sonDataArray;
	/*for (i = toobj.options.length - 1; i > 0; i--) {
		toobj.options[i] = null;
	}
	*/
	var ln = toobj.options.length;
	while(ln--){
		toobj.options[ln] = null;
	}
	toobj.options[0] = new Option('---请选择---', '');
	if (obj.value != "") {
		
		for ( var i = 0; i < relationArray.length; i++) {
			if (relationArray[i].pid == obj.value) {
				toobj.options[toobj.options.length] = new Option(
						relationArray[i].name, relationArray[i].id);
			}
		}
	} else {
		
		for ( var i = 0; i < sonArray.length; i++) {
			toobj.options[toobj.options.length] = new Option(sonArray[i].name,
					sonArray[i].id);
		}
	}
}


/*
 *输入框最大长度校验，避免js的maxlength对字母和汉字不区分的问题
 */  
function getCharLength4Str(str){
		
	var i,sum,str; 
	sum=0; 
	for(i=0;i<str.length;i++) 
	{ 
	if ((str.charCodeAt(i)>=0) && (str.charCodeAt(i)<=255)) 
		sum=sum+1; 
	else 
		sum=sum+2; 
	} 
	return sum;
}
function checkMaxLength(elementId,maxLen){
	if(null == elementId || null == maxLen){
		return false;
	}
	var obj = document.getElementById(elementId);
	var str = obj.value;
	if (getCharLength4Str(str)>maxLen ){
	    var len = parseInt(maxLen/2);
	    alert("输入的长度超长，最多只能录入"+len+"个汉字，或"+maxLen+"个字符，请重新录入！");
	    obj.focus();
	    return false;
	}else{
	    return true;
  	}

	}

//验证日期
function validDate(strDate1,strDate2){
	if("" != strDate1 && "" != strDate2 &&
			0 != strDate1.length && 0 != strDate2.length){
		var date1 = new Date(strDate1.replace("-","/"));
		var date2 = new Date(strDate2.replace("-","/"));
		if(date1 > date2){
			return false;
		}
	}
	return true;
}


/**
 * 页面无刷新方式上传文件
 */
/*
var uploadToServer = mss.uploadToServer = function uploadToServer() {
	//var row = $('#dunningTable').datagrid('getSelected');
	var depositStatusId = $("#rewardTypeList").combobox('getValue');
	if(null==depositStatusId||depositStatusId==''||depositStatusId=="0"){
		alertError("请选择打款类型!");
		return;
	}
	$("#confirmRemittance").dialog();
	var inputFile = document.createElement('input');
	inputFile.setAttribute('id', 'fileToUpload');
	inputFile.setAttribute('name', 'fileToUpload');
	inputFile.setAttribute('type', 'file');
	inputFile.setAttribute("style", 'visibility:hidden');
	//inputFile.setAttribute("accept", 'image/!*,text/!*');
	var old = document.getElementById("fileToUpload");
	if (old) {
		document.body.replaceChild(inputFile, old);
	} else {
		document.body.appendChild(inputFile);
	}
	inputFile.click();
	inputFile.onchange = function () {
		$.ajaxFileUpload({
			url: "UploadExcel.do",
			secureuri: false,
			async: false,
			data: {depositStatusId: depositStatusId},
			fileElementId: 'fileToUpload',
			dataType: 'json',
			success: function (data, status) {
				// BatchRemittancet.showFile(data, inputFile.value);

				var str = data.responseText;
				var start = str.indexOf("<pre>");
				var end = str.indexOf("</pre>");
				str = str.substring(start + 5);
				str = str.substring(0, str.length - 6);
				data = JSON.parse(str);
				BatchRemittancet.showFile(data, inputFile.value);
			},
			error: function (data, status, e) {
				//alert(data.responseText);
				var str = data.responseText;
				var start = str.indexOf("<pre>");
				var end = str.indexOf("</pre>");
				str = str.substring(start + 5);
				str = str.substring(0, str.length - 6);
				//alert(str);
				data = JSON.parse(str);
				// alert(data.st);
				BatchRemittancet.showFile(data, inputFile.value);
			}
		});
	};
};*/
