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
	var pars = $("#paymentCategoryForm").serialize() ;
	$.ajax({
		type: "POST",
		url: "${ctx}/channelCategory.htm?method=query",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function add() {
	window.location.href="${ctx}/channelCategory.htm?method=initAdd";
} 

-->
</script>
</head>

<body>
<h2 class="panel_title">渠道类别管理</h2>
<form id="paymentCategoryForm" name="paymentCategoryForm" >
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >渠道类别名称：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="itemName" name="itemName" value="">
				</td>	
				<td class="border_top_right4" align="right" >支付通道别名：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="alias" name="alias" value="">
				</td>
			</tr>
			
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="6" align="center">
					<a class="s03" href="javascript:query()"><input class="button2" type="button" value="查询"> </a>
					<a class="s03" href="javascript:add()"><input class="button2" type="button" value="新增"> </a>
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

