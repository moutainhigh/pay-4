<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>


<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
$(document).ready(
		function(){
			queryList();
		}
);
function queryList(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#enterpriseSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/detailOfAccountList.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
			$("#pageNo").attr("value",pageNo);
		}
	});
} 

function validFrom() {
   	queryList();
}

function trim(obj){
	if (isNaN(obj.value)) {
		obj.value = "";
	}
	obj.value=obj.value.trim();
}
function download(){
	var pars = $("#enterpriseSearchFormBean").serialize();
	window.location.href = 	"./downloadDetailAccount.do?"+pars;
}
</script>
</head>

<body>

<!-- <table width="30%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">账 户 明 细</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">账户明细</h2>

<form id="enterpriseSearchFormBean" name="enterpriseSearchFormBean" >
<input type="hidden" id="pageNo" >
<input type="hidden" id="accountCode" name="accountCode" value="${accountCode}"/>
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >交易类型：</td>
		<td class="border_top_right4" align="left" >
			<select id="dealType" name="dealType" size="1">
				<option value="">--请选择--</option>
				<c:forEach items="${dealTypeList}" var="dealType">
					<option value="${dealType.code}">${dealType.message}</option>
				</c:forEach>
			</select>
		</td>	
		<td  class="border_top_right4"  align="right">起至时间：</td>
		<td  class="border_top_right4" align="left" colspan="3">
			<input class="Wdate" type="text" id="startDate" name="startDate"  onClick="WdatePicker()">
				到
			<input class="Wdate" type="text" id="endDate" name="endDate"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})">
		</td>
		<td class="border_top_right4" align="right" >交易流水号：</td>
		<td class="border_top_right4" align="left" >
					<input type="text"  name="dealId" onkeyup="trim(this);">
		</td>	
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="8" align="center">
		<a class="s03" href="javascript:validFrom()">
			<input clattributeOfAccountEdit.doass="button2" type="button" value="查询"></a>
		<a class="s03" href="javascript:download()">
			<input class="button2" type="button" value="下载"></a></td>
</tr>
</table>

</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>

</body>

