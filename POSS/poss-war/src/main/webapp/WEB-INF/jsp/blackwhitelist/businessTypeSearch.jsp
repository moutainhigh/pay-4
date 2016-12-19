<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
function businessTypeSearch(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#businessTypeSearchForm").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/businessTypeSearchList.do?method=businessTypeSearchList",
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
	businessTypeSearch(pageNo,totalCount,pageSize);
	
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
		<div align="center"><font class="titletext">黑   白  名  单  业  务  类   型   查   询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table> -->
 <h2 class="panel_title">黑白名单业务类型查询</h2>

<form id="businessTypeSearchForm" name="businessTypeSearchForm" >

<input type="hidden" name="queryHisStatus" value="0"/>
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >业务描述：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="businessName" name="businessName" maxlength= "50">
		</td>
		<td class="border_top_right4" align="right" >业务类型：</td>
		<td class="border_top_right4" align="left" >
			<select name="businessType" id="businessType">
			    <option value="" >请选择</option>
				<option value="1" >FO</option>
				<option value="2" >FI</option>
				<option value="3" >MA</option>
				<option value="4" >APP</option>
			</select>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >业务类型ID：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="businessTypeId" name="businessTypeId" value="" maxlength= "4">
		</td>
		<td class="border_top_right4" align="right" >有效性标志：</td>
		<td class="border_top_right4" align="left" >
			<select name="status">
				<option value="" >请选择</option>
				<option value="1" >有效</option>
				<option value="0" >无效</option>
			</select>
		</td>
	</tr>	
	 
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
			<a class="s03" href="javascript:businessTypeSearch()">
				<input class="button2" type="button" value="查询">
			</a>
			
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

