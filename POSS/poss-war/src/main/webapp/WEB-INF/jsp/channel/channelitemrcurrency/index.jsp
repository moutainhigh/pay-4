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
function query(pageNo) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#channelItemCurrencyForm").serialize() + "&pageNo=" + pageNo;
	$.ajax({
		type: "POST",
		url: "${ctx}/channelItemRCurrency.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function deleteChannelItemCurrency(id){
	if (!confirm("确认删除？")) {
		return;
	 }
	window.location.href="${ctx}/channelItemRCurrency.do?method=deleteChannelCurrency&relateId="+id;
}
function add(){
	window.location.href="${ctx}/channelItemRCurrency.do?method=add";
}
-->
</script>
</head>

<body>
<h2 class="panel_title">渠道支持币种配置</h2>
	<c:if test="${not empty msg}">
		<p>	<font color="red">${msg}</font></p>
	</c:if>
<form id="channelItemCurrencyForm" name="channelItemCurrencyForm"  action="channelItemRCurrency.do?method=batchAdd"  method="post" enctype="multipart/form-data">
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="left">
					渠道名称：<select id="channelItemCode" name="channelItemCode">
						<option value="" selected>---请选择---</option>
						<c:forEach var="channel" items="${channelItems}" varStatus="v">
							<option value="${channel.code}">${channel.desc}</option>
						</c:forEach>
					</select>	
				</td>
				<td class="border_top_right4" align="left">
					支持币种：<input type="text" id="currencyCode" name="currencyCode" value="">
				</td>
			</tr>
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="2" align="center">
					<a class="s03" href="javascript:query()"><input class="button2" type="button" value="查询"> </a>
					<a class="s03" href="javascript:add()"><input class="button2" type="button" value="新增"></a>
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

