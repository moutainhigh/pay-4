<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript"><!--
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function  validateFiRenotifyForm(){	
	fiRenotifyQuery();	
}
function fiRenotifyQuery(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#renotifyFormBean").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/gotoFiNotify.do?method=processRenotifyQuery",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function showUrl(menu,Url){	
	parent.addMenu(menu,Url);
}

--></script>
</head>

<body>
<br/><br/>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">网关重发通知 </font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form id="renotifyFormBean" name="renotifyFormBean" >
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >商户号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="sellerMemberCode" name="sellerMemberCode">
				</td>
				<td class="border_top_right4" align="right" >商家订单号：</td>
				<td class="border_top_right4" align="left" > 
					<input type="text" id="orderID" name="orderID">
				</td>
				<td class="border_top_right4" align="right" >交易流水号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="tradeSerialNO" name="tradeSerialNO">
				</td>					
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >买家帐号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="buyerMemberCode" name="buyerMemberCode">
				</td>
				<td class="border_top_right4" align="right" >买家姓名：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="buyerName" name="buyerName">
				</td>
				<td class="border_top_right4" align="right" >&nbsp; </td>
				<td class="border_top_right4" align="left" >&nbsp; </td>
<!--				<td class="border_top_right4" align="right" >买家联系方式：</td>-->
<!--				<td class="border_top_right4" align="left" >-->
<!--					<input type="text" id="buyerPhone" name="buyerPhone">-->
<!--				</td>					-->
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >交易开始时间：</td>
				<td class="border_top_right4" align="left" >
					<input class="Wdate" type="text" id="startTime"  name="startTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})">
				</td>
				<td class="border_top_right4" align="right" >交易结束时间：</td>
				<td class="border_top_right4" align="left" >
					<input class="Wdate" type="text" id="endTime" name="endTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})">
				</td>
				<td class="border_top_right4" align="right" >&nbsp; </td>
				<td class="border_top_right4" align="left" >&nbsp; </td>
			</tr>
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="6" align="center">
					<a class="s03" href="javascript:validateFiRenotifyForm()"><img
				src="./images/query.jpg" border="0"> </a>
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

