<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

$(function(){
	var loginName = false;
	//var mobile = false;
	$("#loginName").focusout(function(){
		var loginName = $.trim($("#loginName").val());
		if('' != loginName){
			var pars = "loginName=" + loginName;
			$.ajax({
				type: "POST",
				url: "${ctx}/checkLoginName.do?method=checkLoginName",
				data: pars,
				success: function(result) {
					//进异步交易前先清空
					document.getElementById("activFlag").value=""; //清空激活提醒
					document.getElementById("loginNameFlag").value=""; //清空找不到会员提醒
					if (result == '2') {
						loginName = true;
					}else if(result == '1'){
						loginName = false;
						alert("你输入的会员登录号找不到该会员");
						document.getElementById("loginNameFlag").value="NO"; 
						document.getElementById("activFlag").value=""; //清空激活提醒
					} else if(result == '3'){
						loginName = false;
						alert("该会员未激活");
						document.getElementById("activFlag").value="NO"; 
						document.getElementById("loginNameFlag").value=""; //清空找不到会员提醒
					}
				}
			});
		}
	});
	
/* 	$("#mobile").focusout(function(){
		var mobile = $.trim($("#mobile").val());
		if('' != mobile){
			var pars = "mobile=" + mobile;
			$.ajax({
				type: "POST",
				url: "${ctx}/checkLoginName.do?method=checkLoginName",
				data: pars,
				success: function(result) {
					document.getElementById("mobileFlag").value=""; 
					if (result == 'true') {
						mobile = true;
					}else{
						mobile = false;
						alert("手机号码错误,请检查");
						document.getElementById("mobileFlag").value="NO"; 
					} 
				}
			});
		}
	}); */
});

function closePage() {
	parent.closePage('enterprisePasswordResetAdd.do?method=enterpriseCreate');
}
function submitSave() {
	var loginNameFlag = document.getElementById("loginNameFlag").value;
	var mobileFlag = document.getElementById("mobileFlag").value; 
	var activFlag = document.getElementById("activFlag").value; 
	
	var passwordType = document.getElementsByName("passwordType");
	var typeFlag = false;
	for(var i = 0;i<passwordType.length;i++){
		if(passwordType[i].checked==true){
		    typeFlag = true;
	    }
	}
	if(typeFlag == false){
		alert("请选择重置密码类型");
		return;	
	}
	if($('#formNumber').val()==""){
		alert("申请单编号不能为空");
		return;
	}
	if($('#loginName').val()==""){
		alert("会员登录号不能为空");
		return;
	}
	if("NO" ==loginNameFlag){
		alert("你输入的会员登录号找不到该会员");
		return;
	}
	if("NO" ==activFlag){
		alert("该会员未激活");
		return;
	}
	
	if($('#memberName').val()==""){
		alert("会员中文名不能为空");
		return;
	}
	if($('#proposerName').val()==""){
		alert("经办人姓名不能为空");
		return;
	}
	if($('#idcard').val()==""){
		alert("经办人身份证不能为空");
		return;
	}
	/* if($('#mobile').val()==""){
		alert("经办人联系电话不能为空");
		return;
	} */
	if("NO" == mobileFlag){
		alert("手机号码错误,请检查");
		return;
	}
	if($('#formUrlP').val()==""){
		alert("申请表影像不能为空");
		return;
	}
	if($('#remark').val()==""){
		alert("备注不能为空");
		return;
	}
	if($('#remark').val().length > 64){
		alert("备注内容长度过长");
		return;
	}
	document.getElementById('enterprisePasswordReset').submit();
}
function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
function viewmypic(mypic,imgfile) {   
	if (imgfile.value){  
		mypic.src=imgfile.value; 
		mypic.style.display="";        
		mypic.border=1;        
	}        
}  

</script>
</head>

<body>
<h2 class="panel_title">企业会员重置密码新增</h2>
<table width="80%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" >
	
	<c:if test="${message!=null}">
		<tr>
			<td width="50%" class="border_top_right4"  height="2" ><font color="red" >${message}</font></td>
		</tr>
	</c:if>
	<c:if test="${sucess!=null}">
		<tr>
			<td width="50%" class="border_top_right4"  height="2" ><font color="red" >${sucess}</font></td>
		</tr>
	</c:if>
</table>


<form id="enterprisePasswordReset" name="enterprisePasswordReset" method="post" action="enterprisePasswordResetAddSave.do" enctype="multipart/form-data">
	<input type="hidden" name="method" value="enterpriseCreateSave"/>
	<input type="hidden" id="loginNameFlag"name="loginNameFlag" value="YES"/>
	<input type="hidden" id="mobileFlag"name="mobileFlag" value="YES"/>
	<input type="hidden" id="activFlag" name="activFlag" value="YES"/>
	
	<table class="border_all2" width="80%" border="0" cellspacing="0"cellpadding="1" align="center">	
	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"    align="right" >重置密码类型：<font color="red" >*</font></td>
		<td width="50%" class="border_top_right4"   align="left">
			<input type="radio" name="passwordType" value="1">登录密码
			<input type="radio" name="passwordType" value="2">支付密码
		</td>
	</tr>
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"   align="right" >会员信息：</td>
		<td width="50%" class="border_top_right4"  align="left" >&nbsp;</td>
	</tr>
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"  class="" align="right" >申请单编号：<font color="red" >*</font></td>
		<td width="50%" class="border_top_right4"  class="" align="left" >
			<input	type="text" id="formNumber" name="formNumber" value="" maxlength= "17">
		</td>
	</tr>	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"  class="" align="right" >会员登录号：<font color="red" >*</font></td>
		<td width="50%" class="border_top_right4"  class="" align="left" >
			<input	type="text" id="loginName" name="loginName" value="" maxlength= "32">
		</td>
	</tr>	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"  class="" align="right" >会员中文名：<font color="red" >*</font></td>
		<td width="50%" class="border_top_right4"  class="" align="left" >
			<input	type="text" id="memberName" name="memberName" value="" maxlength= "64">
		</td>
	</tr>	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"  class="" align="right" >经办人姓名：<font color="red" >*</font></td>
		<td width="50%" class="border_top_right4"  class="" align="left" >
			<input	type="text" id="proposerName" name="proposerName" value="" maxlength= "8">
		</td>
	</tr>	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"  class="" align="right" >经办人身份证：<font color="red" >*</font></td>
		<td width="50%" class="border_top_right4"  class="" align="left" >
			<input	type="text" id="idcard" name="idcard" value="" maxlength= "18">
		</td>
	</tr>	
	<!-- <tr class="trForContent1">
		<td width="50%" class="border_top_right4"  class="" align="right" >经办人联系电话：<font color="red" >*</font></td>
		<td width="50%" class="border_top_right4"  class="" align="left" >
			<input	type="text" id="mobile" name="mobile" value="" maxlength= "11">
		</td>
	</tr>	 -->
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"  class="" align="right" ><font color="red" >上传图片请注意:</font></td>
		<td width="50%" class="border_top_right4"  class="" align="left" >
			<font color="red" >以下图片上传格式为jpg,jpeg,bmp格式</font>
		</td>
	</tr>	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"  class="" align="right" >经办人身份证正面：</td>
		<td width="50%" class="border_top_right4"  class="" align="left" >
			<input name="proposerObverseUrlP" id="proposerObverseUrlP" type="file" />  
		</td>
	</tr>
	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"  class="" align="right" >经办人身份证背面：</td>
		<td width="50%" class="border_top_right4"  class="" align="left" >
			<input name="proposerReverseurlP" id="proposerReverseurlP" type="file" />  
		</td>
	</tr>
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"  class="" align="right" >法人身份证正面：</td>
		<td width="50%" class="border_top_right4"  class="" align="left" >
			<input name="legalObverseUrlP" id="legalObverseUrlP" type="file" />  
		</td>
	</tr>
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"  class="" align="right" >法人身份证背面：</td>
		<td width="50%" class="border_top_right4"  class="" align="left" >
			<input name="legalReverseUrlP" id="legalReverseUrlP" type="file" />  
		</td>
	</tr>
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"  class="" align="right" >公司营业执照正本复印件：</td>
		<td width="50%" class="border_top_right4"  class="" align="left" >
			<input name="licenceUrlP" id="licenceUrlP" type="file" />  
		</td>
	</tr>
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"  class="" align="right" >申请表影像：<font color="red" >*</font></td>
		<td width="50%" class="border_top_right4"  class="" align="left" >
			<input name="formUrlP" id="formUrlP" type="file" />  
		</td>
	</tr>

	<tr class="trForContent1">
			<td width="50%" class="border_top_right4"  class="" align="right" >备注：<font color="red" >*</font></td>
			<td width="50%" class="border_top_right4"  class="" align="left" >
				<textarea rows="7" cols="30" id="remark" name="remark" value="" ></textarea>
			</td>
	</tr>
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"  colspan="2" align="center">
			<input type = "button" class="button2"  onclick="javascript:submitSave();" value="新增">
			<input type = "button" class="button2"  onclick="javascript:closePage();" value="取消">
		</td>
	</tr>
	</table>
</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>

</body>

