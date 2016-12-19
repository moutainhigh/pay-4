<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="${ctx}/css/main.css">
<script src="${ctx}/js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
$(function(){
	var s = '${msg}';
	if(s != '')
	alert(s);
});
function userRelationQuery(pageNo,totalCount,pageSize) {
	
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#userRelationForm").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/userrelation/userRelationQuery.do?method=queryList",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 


function load(pageNo,totalCount,pageSize){
	userRelationQuery(pageNo,totalCount,pageSize);
}

function add(){
	window.location.href="${ctx}/userrelation/userRelationQuery.do?method=toAddPage";
	return false;
}


</script>
</head>

<body onload=load()>

<table width="95%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">维护用户关联关系</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table>

<form id="userRelationForm" name="userRelationForm" >
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >销售人员姓名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="loginName" name="name" value="${name}" maxlength= "17">
		</td>

		<td class="border_top_right4" align="right" >销售主管姓名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="loginName" name="pname" value="${pname}" maxlength= "17">
		</td>

	</tr>	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
			<a class="s03" href="javascript:userRelationQuery()"><img src="${ctx}/images/query.jpg" border="0"></a>	
		
			<input id="addbtn" onclick="return add();" type="button" value="新 增 "/>	
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

