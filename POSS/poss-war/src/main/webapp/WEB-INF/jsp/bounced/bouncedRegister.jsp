<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
$(document)
.ready(function() {
			var orgCode=${dto.orgCode};
			
			//Credarox是多币种，其他为cny,页面加载默认币种
			if(orgCode!="10075001")
			{
			
				$("#bankCurrencyCode").attr("value","CNY");
			}
			$("#transferCurrencyCode").attr("value","${dto.transferCurrencyCode}");
			}
		);
		
$(function(){ 
	var orgCode=${dto.orgCode};
	$("#reasonCode").empty();
/* 	$("#reasonCode").append("<option value=''>全部</option>"); */
	$.ajax({
		type: "POST",
		url: "${ctx}/bouncedReasonMapping.do?method=queryCode",
		data: "&orgCode="+orgCode,
		success: function(result) {
			var parsedJson = jQuery.parseJSON(result)				
			for (var i = 0; i < parsedJson .length; i++) {
				var reasonCode = parsedJson [i].reasonCode;
				var bouncedReason = parsedJson [i].bouncedReason;
				$("#reasonCode").append("<option value='"+reasonCode+"'>"+reasonCode+"</option>");
				/* if(orgCode=='10075001'){
					$("#reasonCode").append("<option value='"+reasonCode+"'>"+reasonCode+"——"+bouncedReason+"</option>");
				}else{
					
				} */
				getVisableCode();
			}
		}
	});	
});

function getVisableCode(){
	var orgCode=${dto.orgCode};
	var reasonCode=$("#reasonCode").val();
	$("#visableCode").empty();
	/* $("#reasonCode").append("<option value=''>全部</option>"); */
	$.ajax({
		type: "POST",
		url: "${ctx}/bouncedReasonMapping.do?method=queryCode",
		data: "&orgCode="+orgCode+"&reasonCode="+reasonCode,
		success: function(result) {
			var parsedJson = jQuery.parseJSON(result)				
			for (var i = 0; i < parsedJson .length; i++) {
				//alert(visableName);
				var visableCode = parsedJson [i].visableCode;
				var visableName = parsedJson [i].visableName;
				$("#visableCode").append("<option value="+visableCode+">"+visableCode+"——"+visableName+"</option>");
			}
		}
	});	
	
	
	
}
function closePage() {
	var url="bounced-register.do?method=query";
	parent.closePage(url);
}
function setMaxBankAmount(orderAmount,payAmount,transferRate,transferCurrencyCode,canBouncedAmount,cardOrg,stranDate,
		orgCode,cardholderCardno,floatValue)
{
	var bankCurrencyCode=$("#bankCurrencyCode").val();
	var partnerId=$("#partnerId").val();
	var pars = "&bankCurrencyCode="+bankCurrencyCode
	+"&orderAmount=" + orderAmount
	+ "&payAmount=" + payAmount
	+ "&transferRate=" + transferRate
	+ "&canBouncedAmount=" + canBouncedAmount
	+ "&cardOrg="+cardOrg
	+ "&stranDate="+stranDate
	+ "&partnerId="+partnerId
	+ "&orgCode="+orgCode
	+ "&cardholderCardno="+cardholderCardno
	+ "&floatValue="+floatValue
	+ "&transferCurrencyCode=" + transferCurrencyCode;
	$.ajax({
	type : "POST",
	url : "bounced-register.do?method=setMaxBankAmount",
	data : pars,
	success : function(result) {
		var parsedJson = jQuery.parseJSON(result)
		if(""==result)
		{
			alert("该币种没配置对应的交易汇率！");
		}else{
				$("#maxAmount").attr("value",parsedJson.sbankAmount);
				$("#stransferRate").attr("value",parsedJson.rate);
			}
			
			
			
	}	
	});
}
//验证数字的合法性
function validNumber(objVal){
	if(null == objVal || 0 == objVal.length){
		return true;
	}
	var reg = /^[0-9]+(.[0-9]{1,2})?$/g;
	if(!reg.test(objVal)){
		alert("您输入的金额格式是错误的,请更改后再查询!($###.##)");
		return false;
	}
	return true;
}
function submitSave(orderAmount,payAmount,orgCode,sdoingBouncedAmount,floatValue,soverBouncedAmount,
		assureSettlementFlg,settlementFlg,merchantCode,checkFlag,bouncedRemark) {
	var bouncedType = $("#bouncedType").val();
	var transferCurrencyCode = $("#transferCurrencyCode").val();
	var settlementCurrencyCode = $("#settlementCurrencyCode").val();
	var settlementRate = $("#settlementRate").val();
	var lastDate = $("#lastDate").val();
	var cpdDate = $("#cpdDate").val();
	var orderId = $("#orderId").val();
	var stransferRate = $("#stransferRate").val();
	var currencyCode = $("#currencyCode").val();
	var tranDate=$("#tranDate").val();

	var traDate=tranDate.split(" ");
		if(traDate[0]>cpdDate){
			alert("交易时间必须大于拒付时间!");
			return false;		
		}
	
		if(bouncedType=="")
		{
		alert("请选择拒付类型！");
		return false;
		}
	var remark = document.getElementById('remark').value;
	var reg = /^\d+$/;
	var amount1 = $("#maxAmount").val();
	var amount2 = $("#bankAmount").val();
	if(amount2=="")
	{
	alert("请输入银行拒付金额！");
	return false;
	}
	if(lastDate=="")
	{
	alert("请输入最后回复日期！");
	return false;
	}
	if(cpdDate=="")
	{
	alert("请输入拒付时间！");
	return false;
	}
	if(cpdDate>lastDate)
	{
	alert("拒付时间不能大于最后回复日期！");
	return false;
	}
	if(!validNumber(amount1)){
		return false;
	}
	
	if(!validNumber(amount2)){
		return false;
	}
	var reg = /^[0-9]*$/;
	//拒付类型：1-全额，2-部分
	if(parseFloat(amount2)>parseFloat(amount1)){
		alert("银行拒付金额不得大于可录入最大金额!");
		return false;
	}else if(parseFloat(amount2)<parseFloat(amount1)){
		cpFalg="2";
	}else{
		cpFalg="1";
	}
	
	if(remark.length > 200){
		alert("备注内容长度过长");
		return;
	}
	//document.getElementById('businessTypeForm').submit();
	document.businessTypeForm.action = "bounced-register.do?method=save&orderAmount=" + orderAmount
		+ "&payAmount=" + payAmount+"&orgCode="+orgCode+"&cpFalg="+cpFalg+
		"&transferCurrencyCode="+transferCurrencyCode
		+"&settlementCurrencyCode="+settlementCurrencyCode
		+"&settlementRate="+settlementRate
		+"&stransferRate="+stransferRate
		+"&orderId="+orderId
		+"&doingBouncedAmount="+sdoingBouncedAmount
		+"&floatValue="+floatValue
		+"&overBouncedAmount="+soverBouncedAmount
		+"&assureSettlementFlg="+assureSettlementFlg
		+"&settlementFlg="+settlementFlg
		+"&merchantCode="+merchantCode
		+"&checkFlag="+checkFlag
		+"&bouncedRemark="+bouncedRemark
		+"&currencyCode="+currencyCode;
	
	document.businessTypeForm.submit();
}
function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
</script>
</head>
<div id="editChannelDiv">
<body >

<h2 class="panel_title">拒付单项登记</h2>

<form id="businessTypeForm" name="businessTypeForm" method="post" action="bounced-register.do?method=save">
	<table class="border_all2" width="700" border="1" cellspacing="0" cellpadding="0" align="center">
			<tr class='trForContent1'>
			<input type="hidden" id='settlementCurrencyCode' name='settlementCurrencyCode' value="${dto.settlementCurrencyCode}" >
			<input type="hidden" id='settlementRate' name='settlementRate' value="${dto.settlementRate}" >
				<td class='border_top_right4' width='24%' align='left'>商户会员号:</td>
				<td class='border_top_right4' width='24%' align='left'>
				<input type='text' id='partnerId' name='partnerId' value="${dto.partnerId}" readonly="readonly" >
				</td>
				<td class='border_top_right4' width='24%' align='left'>网关订单号:</td>
				<td class='border_top_right4' width='24%' align='left'>
				<input type='text' id='tradeOrderNo' name='tradeOrderNo' value="${dto.tradeOrderNo}" readonly="readonly" >
				</td>
			</tr>
			<tr class='trForContent1'>
				<td class='border_top_right4' width='24%' align='left'>档案号:</td>
				<td class='border_top_right4' width='24%' align='left'><input
					type='text' id='refNo' name='refNo' value="${dto.refNo}"></td>
				<td class='border_top_right4' width='24%' align='left'>交易日期</td>
				<td class='border_top_right4' width='24%' align='left'>
				<input type='text' id='tranDate' name='tranDate' value="${dto.stranDate}" readonly="readonly">
				</td>
			</tr>
			<tr class='trForContent1'>
				<td class='border_top_right4' width='24%' align='left'>批次号:</td>
				<td class='border_top_right4' width='24%' align='left'>
				<input type='text' id='batchNo' name='batchNo' value="${dto.batchNo}"></td>
				<td class='border_top_right4' width='24%' align='left'>银行卡</td>
				<td class='border_top_right4' width='24%' align='left'>
				<input type='text' id='cardholderCardno' name='cardholderCardno' value="${dto.cardholderCardno}" readonly="readonly">
				</td>
			</tr>
			<tr class='trForContent1'>
				<td class='border_top_right4' width='24%' align='left'>授权码:</td>
				<td class='border_top_right4' width='24%' align='left'>
				<input type='text' id='authorisation' name='authorisation' value="${dto.authorisation}" readonly="readonly">
				</td>
				<td class='border_top_right4' width='24%' align='left'>渠道:</td>
				<td class='border_top_right4' width='24%' align='left' id="orgCode">
					<c:choose>
					<c:when test="${dto.orgCode=='10076001'}">中银卡司</c:when>
					<c:when test="${dto.orgCode=='10079001'}">中银MOTO</c:when>
					<c:when test="${dto.orgCode=='10080001'}">中银MIGS</c:when>
					<c:when test="${dto.orgCode=='10003001'}">中国银行</c:when>
					<c:when test="${dto.orgCode=='10002001'}">农业银行</c:when>
					<c:when test="${dto.orgCode=='10075001'}">CREDORAX</c:when>
					<c:when test="${dto.orgCode=='10077001'}">Adyen</c:when>
					<c:when test="${dto.orgCode=='10077002'}">Belto</c:when>
					<c:when test="${dto.orgCode=='10077003'}">Cashu</c:when>
					<c:when test="${dto.orgCode=='10078001'}">农行CTV</c:when>       
					<c:when test="${dto.orgCode=='10077004'}">新生支付</c:when>      
					<c:when test="${dto.orgCode=='10081001'}">CT_BOLETO</c:when>     
					<c:when test="${dto.orgCode=='10081002'}">CT_SAFETYPAY</c:when>  
					<c:when test="${dto.orgCode=='10081003'}">CT_DirectDebitsSSL</c:when> 
					<c:when test="${dto.orgCode=='10081004'}">CT_SofortBanking</c:when> 
					<c:when test="${dto.orgCode=='10081005'}">CT_EPS</c:when>        
					<c:when test="${dto.orgCode=='10081006'}">CT_Giropay</c:when>    
					<c:when test="${dto.orgCode=='10081007'}">CT_PagBrasilDebitCard</c:when> 
					<c:when test="${dto.orgCode=='10081008'}">CT_PagBrasilOTF</c:when> 
					<c:when test="${dto.orgCode=='10081009'}">CT_Poli</c:when>        
					<c:when test="${dto.orgCode=='10081010'}">CT_Przelewy24</c:when> 
					<c:when test="${dto.orgCode=='10081016'}">前海万融</c:when> 
					<c:otherwise>未知机构</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr class='trForContent1'>
				<td class='border_top_right4' width='24%' align='left'>拒付时间:</td>
				<td class='border_top_right4' width='24%' align='left'><input
					class='Wdate' type='text' id='cpdDate' name='cpdDate'
					value='<fmt:formatDate value='${todayDate}' type='date' pattern='yyyy-MM-dd'/>'
					onClick='WdatePicker()' required="required"><font color="red" >*</font></td>
				<td class='border_top_right4' width='24%' align='left'>可拒付金额</td>
				<td class='border_top_right4' width='24%' align='left'>
				<input type='text' id='canBouncedAmount' name='canBouncedAmount' value="${dto.scanBouncedAmount}" readonly="readonly">
				${dto.currencyCode}
				</td>
			</tr>
			<tr class='trForContent1'>
				<td class='border_top_right4' width='24%' align='left'>银行拒付币种:</td>
				<td class='border_top_right4' width='24%' align='left'>
				<select id="bankCurrencyCode" name="bankCurrencyCode" required="required"
				onchange="javascript:setMaxBankAmount('${dto.sorderAmount}','${dto.spayAmount}','${dto.stransferRate}',
				'${dto.transferCurrencyCode}','${dto.scanBouncedAmount}','${dto.cardOrg}','${dto.stranDate}','${dto.orgCode}',
				'${dto.cardholderCardno}','${dto.floatValue}')">
					<option value="">--请选择--</option>
					<c:forEach var="curCode" items="${currencyCodeEnum}" varStatus="v">
						<option value="${curCode.code}">${curCode.desc}</option>
					</c:forEach>
				</select>
				<font color="red" >*</font></td>
				<td class='border_top_right4' width='24%' align='left'>支付币种:</td>
				<td class='border_top_right4' width='24%' align='left'>
				<select id="transferCurrencyCode" name="transferCurrencyCode" disabled="disabled">
					<option value="" selected="selected">--请选择--</option>
					<c:forEach var="curCode" items="${currencyCodeEnum}" varStatus="v">
						<option value="${curCode.code}">${curCode.desc}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
			<tr class='trForContent1'>
				<td class='border_top_right4' width='24%' align='left'>可录入最大值</td>
				<td class='border_top_right4' width='24%' align='left'><input
					type='text' id='maxAmount' name='maxAmount' readonly="readonly"></td>
				<td class='border_top_right4' width='24%' align='left'>银行拒付金额</td>
				<td class='border_top_right4' width='24%' align='left'><input
					type='text' id='bankAmount' name='bankAmount' required="required" 
					onclick="javascript:setMaxBankAmount('${dto.sorderAmount}','${dto.spayAmount}','${dto.stransferRate}',
				'${dto.transferCurrencyCode}','${dto.scanBouncedAmount}','${dto.cardOrg}','${dto.stranDate}','${dto.orgCode}',
				'${dto.cardholderCardno}','${dto.floatValue}')"><font color="red" >*</font></td>
			</tr>
			<tr class='trForContent1'>
				<td class='border_top_right4' width='24%' align='left'>拒付原因:</td>
				<td class='border_top_right4' width='24%' align='left'>
				<select id="reasonCode" name="reasonCode"  onchange="getVisableCode();">
				</select>
				<font color="red" >*</font></td>
				<td class='border_top_right4' width='24%' align='left'>显示原因</td>
				<td class='border_top_right4' width='24%' align='left'>
					<select id="visableCode" name="visableCode" >
					
					</select>
				<font color="red" >*</font></td>
			</tr>
			<tr class='trForContent1'>
				<td class='border_top_right4' width='24%' align='left'>最晚回复时间:</td>
				<td class='border_top_right4' width='24%' align='left'><input
					class='Wdate' type='text' id='lastDate' name='lastDate'
					value='<fmt:formatDate value='${todayDate}' type='date' pattern='yyyy-MM-dd'/>'
					onClick='WdatePicker()'>18:00<font color="red" >*</font></td>
				<td class='border_top_right4' width='24%' align='left'>拒付类型</td>
				<td class='border_top_right4' align='left'><select
					id='bouncedType' name='bouncedType' >
						<option value='' selected>---请选择---</option>
						<option value='0'>拒付</option>
						<option value='1'>银行调单</option>
						<option value='2'>内部调单</option>
				</select><font color="red" >*</font></td>
			</tr>
			<tr class='trForContent1' colspan='4'>
				<td  class='border_top_right4'colspan='4'>操作备注：<textarea rows='4' cols='85' name='remark'
						id='remark'>${dto.remark}</textarea></td>
			<input type="hidden" id='channelOrderNo' name='channelOrderNo' value="${dto.channelOrderNo}" >
		<input type="hidden" id='orderId' name='orderId' value="${dto.orderId}"  >
		<input type="hidden" id='currencyCode' name='currencyCode' value="${dto.currencyCode}" >
		<input type="hidden" id='stransferRate' name='stransferRate'>			
		<input type="hidden" id='merchantCode' name='merchantCode' value="${dto.merchantCode}">			
			</tr>
			
		<tr class="trForContent1">
			<td class='border_top_right4' colspan="4" align="center">
				<input type = "button"  onclick="javascript:submitSave('${dto.sorderAmount}','${dto.spayAmount}','${dto.orgCode}',
				'${dto.sdoingBouncedAmount}','${dto.floatValue}','${dto.soverBouncedAmount}','${dto.assureSettlementFlg}'
				,'${dto.settlementFlg}','${dto.merchantCode}','${dto.checkFlag}','${dto.bouncedRemark}')" value="保存">
				<input type = "button"  onclick="javascript:closePage();" value="关闭">
			</td>
		</tr>
	</table>
</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>

</body>
</div>
