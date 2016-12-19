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
	var pars = $("#paymentChannelItemFormBean").serialize() ;
	$.ajax({
		type: "POST",
		url: "${ctx}/second_merchant_relation.htm?method=query",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function add() {
	
	window.location.href="${ctx}/second_merchant_relation.htm?method=toAdd";
}
function delCurCon(id){
	if (!confirm("确认删除？")) {
		return;
	}
	var pars = "id=" + id;
	$.ajax({
		type: "POST",
		url: "${ctx}/second_merchant_relation.htm?method=delCurCon",
		data: pars,
		success: function(r) {
			if(r == 1){
				alert('删除成功');
				query();
			}else{
				alert(r);
			}

		}
	});
}
function del(id,orgMerchantCode,orgCode) {
	if (!confirm("确认删除？")) {
		return;
	 }
	var paymentCategoryCode = $("#paymentCategoryCode").val();
	var pars = "id=" + id+"&cardOrg="+paymentCategoryCode+"&orgMerchantCode="+orgMerchantCode +"&orgCode="+orgCode;
	$.ajax({
		type: "POST",
		url: "${ctx}/second_merchant_relation.htm?method=del",
		data: pars,
		success: function(r) {
			if(r == 1){
				alert('删除成功');
				query();
			}else{
				alert(r);
			}
			
		}
	});
}
function onChangeQueryType(){
	if($("#queryType").val() == "0"){
		$("#add").show();
		$("#batchAdd").show();
	}else{
		$("#add").hide();
		$("#batchAdd").hide();
	}
	query();
}
function batchAddfu(){
	window.location.href = "${ctx}/second_merchant_relation.htm?method=initBatchAdd";
}
-->

</script>
</head>

<body>

<h2 class="panel_title">商户二级商户号管理</h2>
<c:if test="${! empty errorMsg}">
			<p>	<font color="red">${errorMsg}</font></p>
			</c:if>
<form id="paymentChannelItemFormBean" name="paymentChannelItemFormBean" >
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			<tr class="trForContent1">
				<td class="border_top_right4" align="right">会员号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="memberCode" name="memberCode" value="${memberCode}">
				</td>
				<td class="border_top_right4" align="right" >银行机构：</td>
				<td class="border_top_right4" align="left" >
					<select id="orgCode" name="orgCode">
						<option value="" selected>---请选择---</option>
						<c:forEach var="item" items="${channelItems}" varStatus="v">
							<option value="${item.id}">${item.name}</option>
						</c:forEach>
					</select>
				</td>	
				<td class="border_top_right4" align="right" >二级商户号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="orgMerchantCode" name="orgMerchantCode" value="${orgMerchantCode}">
				</td>
				<td class="border_top_right4" align="right" >配置类型:</td>
				<td class="border_top_right4" align="left" >
					<select id="queryType" name="queryType" onchange="onChangeQueryType();">
						<option value="0" selected>手动</option>
						<option value="1" >自动</option>
					</select>
				</td>
			</tr>
			
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="8" align="center">
					<input type="button" class="button2" value="检索" onclick="query();">
					<input type="button" class="button2" value="新增" onclick="add();">
					<input type="button" class="button2" value="批量新增" onclick="batchAddfu();" id="batchAdd"/>
				</td>
			</tr>
	</table>
	<input type="hidden" id="checkFlg" value=""/>
</form>

<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
</body>
