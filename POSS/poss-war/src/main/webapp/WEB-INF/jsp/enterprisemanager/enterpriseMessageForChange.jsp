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
	$("#loginName").focusout(function(){
		var loginName = $.trim($("#loginName").val());
		if('' != loginName){
			var pars = "email=" + loginName;
			$.ajax({
				type: "POST",
				url: "${ctx}/merchantAddValidate.do?method=email",
				data: pars,
				success: function(result) {
					document.getElementById("loginNameFlag").value=""; 
					if (result == 'true') {
						loginName = true;
						alert("新会员登录号已经存在!请重新填写");
						document.getElementById("loginNameFlag").value="NO";
					}else{
						loginName = false;
					} 
				}
			});
		}
	});
});

function closePage() {
	var memberCode = document.getElementById('memberCode').value;
	var url = "enterpriseMessageForChange.do?memberCode="+memberCode
	parent.closePage(url);
}
function submitSave(){
	var loginName = document.getElementById("loginName").value;
	var loginNameFlag = document.getElementById("loginNameFlag").value;
	var reg1 = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)/;
	var flag = reg1.test(loginName);
	if("NO" ==loginNameFlag){
		alert("新会员登录号已经存在!请重新填写");
		return;
	}
	if(!flag){
		alert("新会员登录号格式错误");
		return;
	}
	document.getElementById('enterpriseMessageForChange').submit();
}
function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
</script>
</head>

<body>
<h2 class="panel_title">企业会员登录名修改</h2>
<table width="80%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" >
	<!-- <tr>
		<td width="50%" height="2" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td width="50%" height="18">
		<div align="center"><font class="titletext">企 业 会 员 登 录 名 修 改  </font></div>
		</td>
	</tr> -->
	<c:choose>
		<c:when test="${success}">
		<tr>
			<td width="50%" height="2" ><font color="red" >操作成功!</font></td>
		</tr>
		</c:when>
		<c:when test="${(not empty success) && not success}">
		<tr>
			<td width="50%" height="2" ><font color="red" >新会员登录号已经存在!请重新填写!</font></td>
		</tr>
		</c:when>
	</c:choose>	
</table>


<form id="enterpriseMessageForChange" name="enterpriseMessageForChange" method="post" action="enterpriseMessageForChange.do">
	<input type="hidden" id="memberCode" name="memberCode" value="${memberCode}"/>
	<input type="hidden" id="loginNameFlag"name="loginNameFlag" value="YES"/>
	<table class="border_all2" width="80%" border="0" cellspacing="0"cellpadding="1" align="center">	

	<tr class="trForContent1">
		<td width="50%" class="border_top_right4" align="right" >原会员登录号：</td>
		<td width="50%" class="border_top_right4" align="left" >
			<input	type="hidden" id="oldLoginName" name="oldLoginName" value="${loginName}" >
			<input	type="text" id="oldLoginNameShow" name="oldLoginNameShow" value="${loginName}" maxlength= "32" disabled="true">
		</td>
	</tr>	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4" align="right" >新会员登录号：</td>
		<td width="50%" class="border_top_right4" align="left" >
			<input	type="text" id="loginName" name="loginName" value="" maxlength= "32">
		</td>
	</tr>
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4" colspan="2" align="center">
			<input type = "button"  onclick="javascript:submitSave();" value="修改">
			<input type = "button"  onclick="javascript:closePage();" value="关闭">
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

