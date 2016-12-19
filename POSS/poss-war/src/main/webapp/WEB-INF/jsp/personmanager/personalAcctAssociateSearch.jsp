<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function personalAcctAssociateQuery(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#personSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/personalAcctAssociate.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function load(pageNo,totalCount ){
	var str = $('#loginIp').val();
	if(null!=str && ""!=str){
		personalAcctAssociateQuery(pageNo,totalCount);
		}
}
</script>
</head>

<body onload=load()>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">IP 关  联  账  户  </font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">IP关联账户</h2>


<form id="personSearchFormBean" name="personSearchFormBean" >

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		
		
		<td  class="border_top_right4" align="right" >登录名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="loginName" name="loginName"  maxlength= "64">
		</td>
		<td class="border_top_right4"  align="right" >姓名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="userName" name="userName"  maxlength= "8">
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >会员号：</td>
		<td  class="border_top_right4" align="left" >
			<input	type="text" id="memberCode" name="memberCode"  maxlength= "11">
		</td>
		<td  class="border_top_right4" align="right" >疑似关联类型(IP)：</td>
		<td  class="border_top_right4"align="left" >
			<input type="text" id="loginIp" name="loginIp"  maxlength= "32" value="${loginIp}">
		</td>
		
	</tr>
	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center"><a
			class="s03" href="javascript:personalAcctAssociateQuery()">
			<input class="button2" type="button" value="查询"></a></td></tr>

</table>

</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>

</body>

