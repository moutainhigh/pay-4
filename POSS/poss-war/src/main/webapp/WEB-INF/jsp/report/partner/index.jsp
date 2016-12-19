<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">

<!--
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
$(function(){
	query();
});

function query() {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#reportForm").serialize() ;
	$.ajax({
		type: "POST",
		url: "${ctx}/partnerReport.htm?method=query",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function download() {
	var pars = $("#reportForm").serialize() ;
	window.location.href='${ctx}/partnerReport.htm?method=download&' + pars;
}
-->
</script>
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
		<div align="center"><font class="titletext">第三方支付平台对账报表</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form id="reportForm" name="reportForm" >
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			
			<tr class="trForContent1">
				<td align="right">订单号：</td>
				<td class="border_top_right4" align="left">
					<input type="text" name="tradeOrderNo" value=""/>	
				</td>
				<td align="right">加油站：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="partnerId" name="partnerId" value="">
				</td>
				<td align="right">客户号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="accountNo" name="accountNo" value="">
				</td>
			</tr>
			<tr class="trForContent1">
				<td align="right">支付方式：</td>
				<td class="border_top_right4" align="left">
					<select id="payType" name="payType" size="1">
						<option value="" selected>---请选择---</option>
						<option value="alipay" >支付宝</option>
						<option value="weixin" >微信</option>
					</select>	
				</td>
				<td align="right">状态：</td>
				<td class="border_top_right4" align="left">
					<select id="orderStatus" name="orderStatus" size="1">
						<option value="" selected>---请选择---</option>
						<option value="4" >成功</option>
						<option value="2" >失败</option>
					</select>	
				</td>
				<td align="right">交易时间：</td>
				<td class="border_top_right4">
	      			<input class="Wdate" type="text" id="startTime" value="${startTime}" name="startTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})">
				        	～
						<input class="Wdate" type="text" id="endTime" name="endTime" value="${endTime}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})">
	      		</td>
			</tr>
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="6" align="center">
					<a class="s03" href="javascript:query()"><img src="./images/query.jpg" border="0"> </a>
				
					<a class="s03" href="javascript:download()"><img	src="./images/download.jpg" border="0"> </a>
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

