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
	var pars = $("#partnerChannelForm").serialize() + "&pageNo=" + pageNo;
	$.ajax({
		type: "POST",
		url: "${ctx}/memeberconnecthis.htm?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function deleteConfig(id){
	if (!confirm("确认删除？")) {
		return;
	}
	var pars = "id="+id;
	$.ajax({
		type: "POST",
		url: "${ctx}/memeberconnecthis.htm?method=delete",
		data: pars,
		success: function(result) {
			var msg = eval('('+result+')');
			if(msg.isSuccess==true){
				$('#addLogDiv').dialog('close');
				alert("操作成功");
				query();
			}else{
				alert(msg.reason);
			}

		}
	});
}
function add(){
	$("#partnerId").val('');
	$("#cardOrg").val('');
	$("#limitAmount").val('');
	$("#limitTimes").val('');
	$("#limitDay").val('');
	$("#switchFlag").val('');
	$('#partnerId').attr("readonly",false);
	$('#cardOrg').attr("disabled",false);
	$('#partnerId').attr("disabled",false);
	$('#cardOrg').attr("readonly",false);
	$('#addLogDiv').dialog({
		position:["center","center"],
		width:600,
		height:400,
		modal:true,
		title:'商户通道限额设置',
		overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
	});
	$('#addLogDiv').dialog('open');
}
function editpcc(){
	var partnerId = $("#partnerId").val();
	var cardOrg = $("#cardOrg").val();
	var limitAmount = $("#limitAmount").val();
	var limitTimes = $("#limitTimes").val();
	var limitDay = $("#limitDay").val();
	var switchFlag = $("#switchFlag").val();
	var id = $("#id").val();
	if('' == partnerId || isNaN(partnerId)){
		alert("请输入正确会员号！");
		return;
	}

	if('' == cardOrg){
		alert("请选择卡组织！");
		return;
	}

	if('' == limitAmount|| isNaN(partnerId)){
		alert("请输入正确限制金额！");
		return;
	}
	if('' == limitTimes || isNaN(partnerId)){
		alert("请输入正确限制笔数！");
		return;
	}

	if('' == limitDay || isNaN(partnerId)){
		alert("请输入正确限制天数！");
		return;
	}
	var pars ="partnerId=" + partnerId + "&cardOrg=" + cardOrg+ "&limitAmount=" + limitAmount + "&id=" + id +
			"&limitTimes=" + limitTimes + "&limitDay=" + limitDay + "&switchFlag=" + switchFlag;
	$.ajax({
		type: "POST",
		url: "${ctx}/membersecondlimit.htm?method=edit",
		data: pars,
		success: function(result) {
			var msg = eval('('+result+')');
			if(msg.isSuccess==true){
				$('#addLogDiv').dialog('close');
				alert("操作成功");
				query();
			}else{
				alert(msg.reason);
			}
		}
	});
}
function closepcc(){
	$('#addLogDiv').dialog('close');
}
function editConfig(id,partnerId,cardOrg,limitDay,limitTimes,limitAmount,switchFlag){
	$("#partnerId").val(partnerId);
	$("#cardOrg").val(cardOrg);
	$("#limitAmount").val(limitAmount);
	$("#limitTimes").val(limitTimes);
	$("#limitDay").val(limitDay);
	$("#switchFlag").val(switchFlag);
	$("#id").val(id);
	$('#partnerId').attr("readonly",true);
	$('#cardOrg').attr("readonly",true);
	$('#partnerId').attr("disabled",true);
	$('#cardOrg').attr("disabled",true);
	$('#addLogDiv').dialog({
		position:["center","center"],
		width:600,
		height:400,
		modal:true,
		title:'修改通道限额配置',
		overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
	});
}
-->
</script>
</head>

<body>

<h2 class="panel_title">二级商户号使用历史查询</h2>
<form id="partnerChannelForm" name="partnerChannelForm"  action="memchannelcountry.do?method=list"  method="post" enctype="multipart/form-data">
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="right">会员号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="partnerIdsearch" name="partnerId" value="">
				</td>
				<td class="border_top_right4" align="right" >银行机构：</td>
				<td class="border_top_right4" align="left" >
					<select id="paymentChannelItemId" name="paymentChannelItemId">
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
			</tr>
			<tr class="trForContent1">			
				<td class="border_top_right4"  colspan="6" align="center">
					<input type="button" onclick="query()" value="查询"/>
				</td>
			</tr>
			
	</table>
</form>

<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<div id="addLogDiv" name="addLogDiv" style="display: none" align="center">
	<form id="setPriorityForm" name="setPriorityForm" >
		<table class="border_all2" width="95%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="center" nowrap>会员号</td>
				<td class="border_top_right4" align="center" nowrap><input id="partnerId" name="partnerId"/></td>
				<td class="border_top_right4" align="center" nowrap>币种:CNY</td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="center" nowrap>卡组织</td>
				<td class="border_top_right4" align="center" nowrap>
					<select id="cardOrg" name="cardOrg">
					<option value="" selected>---请选择---</option>
						<option value="VISA">VISA</option>
						<option value="MASTER">MASTER</option>
						<option value="JCB">JCB</option>
						<option value="AE">AE</option>
						<option value="DC">DC</option>
				</select></td>
				<td class="border_top_right4" align="center" nowrap><span style="color: red">*</span></td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="center" nowrap>通道金额限制</td>
				<td class="border_top_right4" align="center" nowrap>
					<input id="limitAmount" name="limitAmount"/>
				</td>
				<td class="border_top_right4" align="center" nowrap>币种:CNY(无限制使用0代替)</td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="center" nowrap>通道笔数限制</td>
				<td class="border_top_right4" align="center" nowrap>
					<input id="limitTimes" name="limitTimes"/>
				</td>
				<td class="border_top_right4" align="center" nowrap>笔(无限制使用0代替)</td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="center" nowrap>通道笔数限制</td>
				<td class="border_top_right4" align="center" nowrap>
					<input id="limitDay" name="limitDay"/>
				</td>
				<td class="border_top_right4" align="center" nowrap>天(无限制使用0代替)</td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="center" nowrap>隔月切换通道</td>
				<td class="border_top_right4" align="center" nowrap>
					<select id="switchFlag" name="switchFlag">
						<option value="1">是</option>
						<option value="0" selected>否</option>
					</select>
				</td>
				<td class="border_top_right4" align="center" nowrap></td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" colspan="3" align="center" nowrap>
					<input type="hidden" id="id"/>
					<input type="button" value="提交" onclick="editpcc()"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="取消" onclick="closepcc()"/>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>

