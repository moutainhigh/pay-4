<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
function signMessageSearch(pageNo,totalCount,pageSize) {
	var endDate = $("#endDate").val();
	var startDate = $("#startDate").val();
	if(null != endDate && 0 != endDate.length){
		if(0 == startDate.length || null == startDate){
			alert("请输入创建日期起始端");
			return;
			}
		}
	
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#signMessageSearchForm").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/signMessageSearchList.do?method=searchList",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function load(pageNo,totalCount,pageSize){
	signMessageSearch(pageNo,totalCount,pageSize);
}

</script>
</head>

<body onload=load();>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">频  道  配  置  查 询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


<form id="signMessageSearchForm" name="signMessageSearchForm" >
<input	type="hidden" id="queryStatus" name="queryStatus"  value="0">  
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >频道名称：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="departmentName" name="departmentName" maxlength= "32">
		</td>
		<td class="border_top_right4" align="right" >频道负责人：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="departmentPrincipal" name="departmentPrincipal" maxlength= "8">
		</td>
	</tr>
	<tr class="trForContent1">
		 <td  class="border_top_right4" align="right" >创建日期：</td>
		 <td  class="border_top_right4" align="left" >
			<input class="Wdate" type="text" id= "startDate" name="startDate"  onClick="WdatePicker()">
			～
			<input class="Wdate" type="text" id= "endDate" name="endDate"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})">
		 </td>
		<td  class="border_top_right4"> &nbsp;</td>
		<td  class="border_top_right4">&nbsp;</td>
	</tr>	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
			<a class="s03" href="javascript:signMessageSearch()"><img src="./images/query.jpg" border="0"></a>
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

