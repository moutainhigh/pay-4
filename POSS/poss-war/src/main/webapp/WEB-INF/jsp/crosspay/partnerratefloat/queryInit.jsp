<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="${ctx}/css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

</head>

<body>
<h2 class="panel_title">商户汇率浮动管理</h2>
<form id="floatRate" name="paymentChannelItemFormBean" >
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >会员号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="partnerId" name="partnerId" value="${partnerId}">
				</td>	
				<td class="border_top_right4" align="right" >浮动起始点：</td>
				<td class="border_top_right4" align="left">
                    <input type="text" id="startPoint" name="startPoint">
				</td>
				<td class="border_top_right4">‰ 到</td>
				<td class="border_top_right4" align="right" >浮动截止点：</td>
				<td class="border_top_right4" align="left">
                    <input type="text" id="endPoint" name="endPoint">‰
				</td>
			</tr>		
			
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="7" align="center">
				   <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="query();">
				   <input type="button"  name="butSubmit" value="添加" class="button2" onclick="toAdd();">
				</td>
			</tr>
	</table>
</form>
<c:if test="${not empty responseCode && responseCode == '0000'}">
	<font color="red"><b>操作成功！</b></font>	
</c:if>
<c:if test="${not empty responseCode}">
	<font color="red"><b>${responseDesc }</b></font>
</c:if>
<div id="resultListDiv" class="listFence" style="text-align: center;"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

$(function(){
	search();
});

function query() {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#floatRate").serialize();
	$.ajax({
		type: "POST",
		url: "${ctx}/partnerRateFloat.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function search(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#floatRate").serialize();
	$.ajax({
		type: "POST",
		url: "${ctx}/partnerRateFloat.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
}

function toAdd(){
	window.location.href="${ctx}/partnerRateFloat.do?method=toAdd";
}


function deleteCure(id) {
	if(!confirm("确认删除？")) {
		return ;
	}
	window.location.href="${ctx}/partnerRateFloat.do?method=delete&id="+id;
} 

</script>
</body>

