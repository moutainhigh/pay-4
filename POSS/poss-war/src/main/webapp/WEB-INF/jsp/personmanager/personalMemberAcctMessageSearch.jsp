<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
function personAcctMessageQuery(pageNo,totalCount,pageSize) {
	var loginName = document.getElementById('loginName').value;
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#personSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/personalMemberAcctMessage.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function load(pageNo,totalCount ){
	var str = $('#loginName').val();
	if(null!=str && ""!=str){
		personAcctMessageQuery(pageNo,totalCount);
	}
}
function trim(str){ 
	//删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
</script>
</head>

<body onload=load();>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">个  人  会  员  账  户    信  息</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">个人会员账户信息</h2>

<form id="personSearchFormBean" name="personSearchFormBean" >

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >登录名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="loginName" name="loginName" value="${loginName}" maxlength= "64">
		</td>
		<td class="border_top_right4" align="right" >姓名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="userName" name="userName" maxlength= "8">
		</td>
	</tr>
	<tr class="trForContent1">
		
		<td class="border_top_right4" align="right" >账户类型：</td>
		<td class="border_top_right4" align="left" >
			<select id="type" name="type">
				<option value="" >请选择</option>
				<option value="10" >人民币账户</option>				
		   </select>
		</td>
		<td class="border_top_right4" align="right" >会员号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="memberCode" name="memberCode"  maxlength= "11">
		</td>
		
	</tr>	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
			<a class="s03" href="javascript:personAcctMessageQuery()"><input class="button2" type="button" value="查询"></a>
			
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

