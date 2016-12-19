<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="${ctx}/css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script language="javascript">
<!--
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
$(function(){
	query();
});
function query(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#currency").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/currency/currency.do?method=query",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function add() {
	var currencyName = $.trim($('#currencyName').val());
	if(''==currencyName){
		alert('请输入货币名称！');
		return;
	}
	var currencyCode = $('#currencyCode').val();
	if(''==currencyCode){
		alert('请输入货币缩写！');
		return;
	}
	var currencyNum = $('#currencyNum').val();
	if(''==currencyNum){
		alert('请输入货币号码！');
		return;
	}
	
	var pars = $("#currency").serialize() ;
	$.ajax({
		type: "POST",
		url: "${ctx}/currency/currency.do?method=add",
		data: pars,
		success: function(d) {
			if(1==d){
				window.location.href="${ctx}/currency/currency.do?method=query";
			}else{
				return;
			}
		}
	});
}


function deleteCure(id) {
	
	if(!confirm("确认删除？")) {
		return false;
	}
	
	$.ajax({
		type: "POST",
		url: "${ctx}/currency/currency.do?method=delete",
		data: 'id='+id,
		success: function(d) {
			if(1==d){
				window.location.href="${ctx}/currency/currency.do";
			}else{
				alert('货币删除失败');
				return;
			}
		}
	});
} 

-->
</script>
</head>

<body>

<h2 class="panel_title">货币管理</h2>
<form id="currency" name="paymentChannelItemFormBean" >
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >货币名称：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="currencyName" name="currencyName">
				</td>	
				<td class="border_top_right4" align="right" >货币缩写：</td>
				<td class="border_top_right4" align="left">
                    <input type="text" id="currencyCode" name="currencyCode">
				</td>
			</tr>
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >货币号码：</td>
				<td class="border_top_right4" align="left">
                    <input type="text" id="currencyNum" name="currencyNum">
				</td>
				<td class="border_top_right4" align="right" >货币用途：</td>
				<td class="border_top_right4" align="left">
					<select id="flag" name="flag">
					
						<option value=""  selected>--全部--</option>
						<option value="1" >交易支持</option>
						<option value="2">货币兑换</option>
						<option value="4">渠道币种</option>
						<option value="3">其他</option>
						<option value="5">授信币种</option>
						<option value="6">退款手续费币种</option>
						<option value="7">DCC币种</option>
					</select>	
				</td>
			</tr>				
			
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="6" align="center">
				    <!-- <button onclick="javascript:query()">查询</button> -->
				    <input type="button" value="查询" onclick="query();">
				    <button onclick="javascript:add()">添加</button>
				</td>
			</tr>
	</table>
</form>

<div id="resultListDiv" class="listFence" style="text-align: center;"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
</body>

