<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript"><!--
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
$(function(){
	query();
});
function query(pageNo,totalCount,pageSize) {
	var memberCode = $("#memberCode").val();
	var reg = /^\d{11}$/;
	if (memberCode !='' && !reg.test(memberCode)) {
		alert('请输入正确的商户号');
		$("#memberCode").val('');
		return ;
	}
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#paymentChannelItemFormBean").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize; ;
	$.ajax({
		type: "POST",
		url: "${ctx}/paymentchannelconfigformerchant.htm?method=search",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function add() {
	window.location.href="${ctx}/paymentchannelconfig.htm?method=initAdd";
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
		<div align="center"><font class="titletext"> 商 户 渠 道 管 理 </font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form id="paymentChannelItemFormBean" name="paymentChannelItemFormBean" >
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >商户号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="memberCode" name="memberCode" value="">
				</td>	
			</tr>
			
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="2" align="center">
					<a class="s03" href="javascript:query()"><img
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

