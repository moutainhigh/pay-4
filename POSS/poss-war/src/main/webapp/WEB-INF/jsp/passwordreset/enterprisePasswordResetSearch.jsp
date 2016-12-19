<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
function enterprisePasswordResetQuery(pageNo,totalCount,pageSize) {
	//var loginName = document.getElementById('loginName').value;
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#enterprisePasswordResetSearchForm").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/enterprisePasswordResetSearchList.do?method=enterpriseSearchList",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function trim(str){ 
	//删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
</script>
</head>

<body>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">企 业 密 码 审 核 查 询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->

<h2 class="panel_title">企业密码审核查询</h2>

<form id="enterprisePasswordResetSearchForm" name="enterprisePasswordResetSearchForm" >
<input	type="hidden" id="queryStatus" name="queryStatus"  value="0">  <!-- 保证传入SQL的参数有值,在SQL中已经写为status in(1,2)(未审核,已审核,) -->
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >申请单编号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="formNumber" name="formNumber" maxlength= "17">
		</td>
		<td class="border_top_right4" align="right" >会员中文名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="memberName" name="memberName" maxlength= "32">
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >会员登录名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="loginName" name="loginName" value="" maxlength= "20">
			<!--  
			<font color="red">会员号为搜索必填项</font>
			-->
		</td>
		<td class="border_top_right4" align="right" >审核状态：</td>
		<td class="border_top_right4" align="left" >
			<select id="status" name="status">
				<option value="" selected >---请选择---</option>
				<option value="1" <c:if test="${status=='1'}">select</c:if> >未审核</option>	
				<option value="2" <c:if test="${status=='2'}">select</c:if> >已审核</option>						
		   </select>
		</td>
	</tr>	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
			<a class="s03" href="javascript:enterprisePasswordResetQuery()"><input class="button2" type="button" value="查询"></a>
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

