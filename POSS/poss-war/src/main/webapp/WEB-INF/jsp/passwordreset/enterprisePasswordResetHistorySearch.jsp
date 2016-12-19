<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
function enterprisePasswordResetHistorySearch(pageNo,totalCount,pageSize) {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	if(null != endDate && 0 != endDate.length){
		if(0 == startDate.length || null == startDate){
			alert("请输入时间起始端");
			return;
			}
	}

	var loginName = document.getElementById('loginName').value;
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#enterprisePasswordResetHistoryForm").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/enterprisePasswordResetHistorySearchList.do?method=enterpriseHistorySearchList",
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
function load(pageNo,totalCount,pageSize){
	//var str = document.getElementById('loginName').value;
	var str = $("#loginName").val();
	enterprisePasswordResetHistorySearch(pageNo,totalCount,pageSize);
	
}

</script>
</head>

<body onload="load()">

<!-- <table width="95%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">企 业 密 码 重 置 历 史 查 询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table> -->
<h2 class="panel_title">企业密码重置历史查询</h2>

<form id="enterprisePasswordResetHistoryForm" name="enterprisePasswordResetHistoryForm" >

<input type="hidden" name="queryHisStatus" value="0"/>
<table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >申请单编号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="loginName" name="formNumber" maxlength= "17">
		</td>
		<td class="border_top_right4" align="right" >会员中文名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="memberName" name="memberName" maxlength= "32"></td>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >会员登录名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="loginName" name="loginName" value="${dto.loginName}" maxlength= "20">
		</td>
		<td class="border_top_right4" align="right" >重置状态：</td>
		<td class="border_top_right4" align="left" >
			<select id="status" name="status">
				<option value="" selected >---请选择---</option>
				<option value="3" <c:if test="${tatus=='3'}">select</c:if> >审核登录密码成功</option>	
				<option value="4" <c:if test="${tatus=='4'}">select</c:if> >审核登录密码失败</option>	
				<option value="5" <c:if test="${tatus=='5'}">select</c:if> >审核支付密码成功</option>	
				<option value="6" <c:if test="${tatus=='6'}">select</c:if> >审核支付密码失败</option>
					
		   </select>
		</td>
	</tr>	
	<tr class="trForContent1">
	<td  class="border_top_right4" align="right" >选择时间：</td>
		 <td  class="border_top_right4" align="left" >
			<input class="Wdate" type="text" id= "startDate" name="startDate"  onClick="WdatePicker()">
			～
			<input class="Wdate" type="text" id= "endDate" name="endDate"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})">
		 </td>
		 <td class="border_top_right4" align="right" >&nbsp;</td>
		 <td class="border_top_right4" align="left" >&nbsp;</td>
	</tr>	 
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
			<a class="s03" href="javascript:enterprisePasswordResetHistorySearch()"><input class="button2" type="button" value="查询"></a>
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

