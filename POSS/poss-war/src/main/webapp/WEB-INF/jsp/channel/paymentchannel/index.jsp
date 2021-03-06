<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js">
</script>

<script language="javascript"><!--
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
$(function(){
	query();
});
function query() {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#paymentChannelForm").serialize() ;
	$.ajax({
		type: "POST",
		url: "${ctx}/paymentChannel.htm?method=query",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function add() {
	window.location.href="${ctx}/paymentChannel.htm?method=initAdd";
} 

-->
/* $(document).ready(function(){
	<c:if test="${info != null}">
		alert("${info}");
	</c:if>
		
}); */

function del(id){
	if(confirm("确认删除？")) {
	window.location.href="${ctx}/paymentChannel.htm?method=remove&id="+id;
	}
}
</script>
</head>

<body>
<h2 class="panel_title">渠道管理</h2>	
	<font color="red">${info } </font>
<form id="paymentChannelForm" name="paymentChannelForm" >
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >渠道名称：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="name" name="name" value="">
				</td>	
				<td class="border_top_right4" align="right" >渠道编号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="code" name="code" value="">
				</td>
			</tr>
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >渠道状态：</td>
				<td class="border_top_right4" align="left" colspan="3">
					<select id="status" name="status" size="1">
						<option value="" selected>---请选择---</option>
						<option value="1" >启用</option>
						<option value="0" >禁用</option>
					</select>	
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

