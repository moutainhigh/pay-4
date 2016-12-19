<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";


function closePage() {
	var signMessageId = document.getElementById('signMessageId').value;
	parent.closePage('signMessageUpdate.do?method=updateView&signMessageId='+signMessageId);
}
function submitSave() {
	var emailReg = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)/;
	//var reg  = new RegExp("^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$");
	var mobileReg  = new RegExp("^(13|14|15|18)\\d{9}$");
	
	if($('#departmentName').val()==""){
		alert("频道名称不能为空");
		return;
	}
	if($('#departmentPrincipal').val()==""){
		alert("频道负责人不能为空");
		return;
	}
	if($('#email').val()==""){
		alert("频道邮箱不能为空");
		return;
	}
	if(!emailReg.test($('#email').val())){
		alert("频道邮箱输入格式错误!请检查");
		return;
	}
	if($('#principalMobile').val()==""){
		alert("频道负责人电话不能为空");
		return;
	}
	if(!mobileReg.test($('#principalMobile').val())){
		alert("频道负责人电话格式错误!请检查");
		return;
	}
	document.getElementById('signMessageUpdateForm').submit();
}


</script>
</head>

<body>

<table width="80%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" >
	<tr>
		<td height="2" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">频 道  修  改  </font></div>
		</td>
	</tr>
	<c:if test="${success}">
		<tr>
			<td height="2" ><font color="red" >操作已成功</font></td>
		</tr>
	</c:if>
	<c:if test="${message=='1'}">
		<tr>
			<td height="2" ><font color="red" >该频道名称已存在</font></td>
		</tr> 
	</c:if>
</table>

<form id="signMessageUpdateForm" name="signMessageUpdateForm" method="post" action="signMessageCreateView.do">
	<input type="hidden" name="method" value="updateSave"/>
	<input type="hidden" id="signMessageId"name="signMessageId" value="${dto.signMessageId}"/>
	<input type="hidden" id="updateFlag"name="updateFlag" value="thisUpdate"/>
	
	<table class="border_all2" width="80%" border="0" cellspacing="0"cellpadding="1" align="center">	
	
	<tr class="trForContent2">
		<td class="" align="right" >频道名称：<font color="red" >*</font></td>
		<td class="" align="left" >
			<input	type="text" id="departmentName" name="departmentName" value="${dto.departmentName}" maxlength= "16" style="width: 220px;">
		</td>
		<td class="" align="right" >频道负责人：<font color="red" >*</font></td>
		<td class="" align="left" >
			<input	type="text" id="departmentPrincipal" name="departmentPrincipal" value="${dto.departmentPrincipal}" maxlength= "4" style="width: 220px;">
		</td>
	</tr>	
	
	<tr class="trForContent2">
		<td class="" align="right" >频道邮箱：<font color="red" >*</font></td>
		<td class="" align="left" >
			<input	type="text" id="email" name="email" value="${dto.email}" maxlength= "64" style="width: 220px;">
		</td>
		
		<td class="" align="right" >频道负责人电话：<font color="red" >*</font></td>
		<td class="" align="left" >
			<input	type="text" id="principalMobile" name="principalMobile" value="${dto.principalMobile}" maxlength= "11" style="width: 220px;">
		</td>
	</tr>	
		

	<tr class="trForContent1">
		<td colspan="4" align="center">
			<input type = "button"  onclick="javascript:submitSave();" value="保存">
			<input type = "button"  onclick="javascript:closePage();" value="取消">
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

