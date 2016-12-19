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
		url: "${ctx}/couponList.do?method=query",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function add() {
	window.location.href="${ctx}/couponList.do?method=toAdd";
} 

function del(id,orgCode,orgMerchantCode) {
	
	if(confirm('删除该记录会将配置的商户关联将一起删除，确认删除吗？')){
		var pars = "id=" + id + "&orgCode=" + orgCode + "&orgMerchantCode=" + orgMerchantCode;
		var url="${ctx}/couponList.do?method=del";
		$.post(url,pars,function(res){
			if(1==res){
				alert('操作成功');
				query();
			}else{
				alert(res);
			}
		});
	}
	
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
		<div align="center"><font class="titletext">优惠券配置管理 </font></div>
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
				<td class="border_top_right4" align="left">
					优惠券号码：<input type="text" name="couponNumber" id="couponNumber"/>	
				</td>
				<td class="border_top_right4" align="left" >
					优惠券面值：<input type="text" id="value" name="value" value="">
				</td>
				<td align="left" class="border_top_right4">
				订单最低金额：
				<input type="text" name="minOrderAmount" id="minOrderAmount"/>
			</td>
				<td class="border_top_right4" align="left" >
					发生机构：<input type="text" name="orgCode" id="orgCode"/>
				</td>
				<td class="border_top_right4" align="left">
					状态：<select id="status" name="status" size="1">
						<option value="" selected>---请选择---</option>
						<option value="1" >已使用</option>
						<option value="0" >未使用</option>
					</select>	
				</td>
			</tr>	
			
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="5" align="center">
					<a class="s03" href="javascript:query()"><img src="./images/query.jpg" border="0"> </a>
				
					<a class="s03" href="javascript:add()"><img	src="./images/add.jpg" border="0"> </a>
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

