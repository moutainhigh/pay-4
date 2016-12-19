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
	getPayCurrencyCode1();
});
function query(pageNo) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#sccForm").serialize() + "&pageNo=" + pageNo;
	$.ajax({
		type: "POST",
		url: "${ctx}/settlementCurrencyConfig.htm?method=list",
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
	var pars = "configId="+id;
	$.ajax({
		type: "POST",
		url: "${ctx}/settlementCurrencyConfig.htm?method=delete",
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
function isInt(testNumber){
	var regType = "^\\d+$";
	var re = new RegExp(regType);
	return re.test(testNumber);
}
function add(){
	$("#memberCode").val('');
	$('#memberCode').attr("readonly",false);
	$('#memberCode').attr("disabled",false);
	$("#payType option").eq(0).attr('selected', 'true');
	$("#tradeCurrencyCode option").eq(0).attr('selected', 'true');
	$("#payCurrencyCode option").eq(0).attr('selected', 'true');
	$("#settlementCurrencyCode option").eq(0).attr('selected', 'true');
	$("#mark").val('');
	$("#grade").val('0');
	$("#configId").val('');
	$('#addLogDiv').dialog({
		position:["center","center"],
		width:600,
		height:400,
		modal:true,
		title:'结算币种配置',
		overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
	});
	$('#addLogDiv').dialog('open');
}
function editpcc(){
	var memberCode = $.trim($("#memberCode").val());
	var payType = $("#payType").val();
	var tradeCurrencyCode = $("#tradeCurrencyCode").val();
	var payCurrencyCode = $("#payCurrencyCode").val();
	var settlementCurrencyCode = $("#settlementCurrencyCode").val();
	var mark = $("#mark").val();
	var grade = $("#grade").val();
	var configId = $("#configId").val();
	if('' == memberCode || isNaN(memberCode)){
		alert("请输入正确会员号！");
		return;
	}

	if('' == payType){
		alert("请选择交易类型！");
		return;
	}

	if('' == tradeCurrencyCode){
		alert("请选择交易币种！");
		return;
	}
	if('' == payCurrencyCode){
		alert("请选择支付币种！");
		return;
	}
	if('' == settlementCurrencyCode){
		alert("请选择结算币种！");
		return;
	}
	
	var pars ="memberCode=" + memberCode + "&payType=" + payType+ "&tradeCurrencyCode=" + tradeCurrencyCode + "&payCurrencyCode=" + payCurrencyCode +
			"&settlementCurrencyCode=" + settlementCurrencyCode + "&mark=" + mark+ "&configId=" + configId+ "&grade=" + grade;
	$.ajax({
		type: "POST",
		url: "${ctx}/settlementCurrencyConfig.htm?method=edit",
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
function editConfig(configId,memberCode,payType,tradeCurrencyCode,payCurrencyCode,settlementCurrencyCode,mark,grade){
	$("#memberCode").val(memberCode);
	$("#payType").val(payType);
	var pars ="memberCode=" + memberCode;
	getSettlementCurrencyCode(pars,settlementCurrencyCode);
	$("#tradeCurrencyCode").attr("value",tradeCurrencyCode);
	$("#payCurrencyCode").attr("value",payCurrencyCode);
	$("#mark").val(mark);
	$("#grade").val(grade);
	$("#configId").val(configId);
	$('#memberCode').attr("readonly",true);
	$('#memberCode').attr("disabled",true);
	
	$("#payCurrencyCode").attr("value",payCurrencyCode);
	$('#addLogDiv').dialog({
		position:["center","center"],
		width:600,
		height:400,
		modal:true,
		title:'修改结算币种配置',
		overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
	});
}
function isExsit(memberCode){
	var memberCode = $.trim(memberCode);
	if('' == memberCode || isNaN(memberCode)){
		alert("请输入正确会员号！");
		return;
	}
	var pars ="memberCode=" + memberCode;
	$.ajax({
		type: "POST",
		url: "${ctx}/settlementCurrencyConfig.htm?method=isExsit",
		data: pars,
		success: function(result) {
			var msg = eval('('+result+')');
			if(msg.isSuccess!=true){
				alert(msg.reason);
			}else{
				getSettlementCurrencyCode(pars);
			}
		}
	});
}
function getSettlementCurrencyCode(pars,settlementCurrencyCode){
	$.ajax({
		type: "POST",
		url: "${ctx}/settlementCurrencyConfig.htm?method=getSettlementCurrencyByMemberCode",
		data: pars,
		success: function(res) {
			var msg1 = eval("("+res+")");
			if(msg1.isSuccess==true){
				$("#settlementCurrencyCode").empty();
				$("#settlementCurrencyCode").append(msg1.reason);
				if(''!=settlementCurrencyCode && settlementCurrencyCode!=undefined){
					$("#settlementCurrencyCode").attr("value",settlementCurrencyCode);
				}
			}
		}
	});
}

function getPayCurrencyCode1(){
	$.ajax({
		type: "POST",
		url: "${ctx}/settlementCurrencyConfig.htm?method=getPayCurrencyByMemberCode",
		success: function(res) {
			var msg1 = eval("("+res+")");
			if(msg1.isSuccess==true){
				$("#payCurrencyCode").empty();
				$("#payCurrencyCode").append(msg1.reason);
			}
		}
	});
}


function getPayCurrencyCode(payType,payCurrencyCode){
	var memberCode = $.trim($("#memberCode").val());
	if('' == memberCode || isNaN(memberCode)){
		alert("请输入正确会员号！");
		return;
	}
	var pars ="memberCode=" + memberCode+"&payType="+payType;
	$.ajax({
		type: "POST",
		url: "${ctx}/settlementCurrencyConfig.htm?method=getPayCurrencyByMemberCode",
		data: pars,
		success: function(res) {
			var msg1 = eval("("+res+")");
			if(msg1.isSuccess==true){
				$("#payCurrencyCode").empty();
				$("#payCurrencyCode").append(msg1.reason);
				if(''!=payCurrencyCode && payCurrencyCode!=undefined){
					$("#payCurrencyCode").attr("value",payCurrencyCode);
				}
			}
		}
	});
}
-->
</script>
</head>

<body>
<h2 class="panel_title">结算币种配置</h2>
<form id="sccForm" name="sccForm"  action="settlementCurrencyConfig.htm?method=list"  method="post" enctype="multipart/form-data">
	<table class="border_all2" width="95%" border="0" cellspacing="0" cellpadding="1" align="center">		
			<tr class="trForContent1">
				<td class="border_top_right4" align="center">
					会员号：<input type="text" id="memberCodeQ" name="memberCodeQ" value="">
				</td>
				<td class="border_top_right4" align="center">
					交易类型：<select id="payTypeQ" name="payTypeQ">
						<option value="" selected>---请选择---</option>
						<option value="DCC">DCC</option>
						<option value="EDC">EDC</option>
					</select>
				</td>
				<td class="border_top_right4" align="center">
				     交易币种：
				     <select id="tradeCurrencyCodeQ" name="tradeCurrencyCodeQ">
						<option value="">--请选择--</option>
						<c:forEach var="tcCode" items="${currencyCodeEnum}" varStatus="v">
							<option value="${tcCode.code}">${tcCode.desc}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="center">
				     支付币种：
				     <select id="payCurrencyCodeQ" name="payCurrencyCodeQ">
						<option value="">--请选择--</option>
						<c:forEach var="pcCode" items="${currencyCodeEnum}" varStatus="v">
							<option value="${pcCode.code}">${pcCode.desc}</option>
						</c:forEach>
					</select>
				</td>
				<td class="border_top_right4" align="center">
				     结算币种：
				     <select id="settlementCurrencyCodeQ" name="settlementCurrencyCodeQ">
						<option value="">--请选择--</option>
						<c:forEach var="scCode" items="${currencyCodeEnum}" varStatus="v">
							<option value="${scCode.code}">${scCode.desc}</option>
						</c:forEach>
					</select>
				</td>
				<td class="border_top_right4" align="center"></td>
			</tr>
			<tr class="trForContent1">			
				<td class="border_top_right4"  align="center" colspan="3">
					<input type="button" onclick="query()" value="检索"/>
					<input type="button" onclick="add()" value="新增"/>
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
				<td class="border_top_right4" align="right" nowrap>会员号</td>
				<td class="border_top_right4" align="left" nowrap><input id="memberCode" name="memberCode" onblur="isExsit(this.value)"/><span style="color: red">*</span></td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="right" nowrap>交易类型</td>
				<td class="border_top_right4" align="left" nowrap>
					<select id="payType" name="payType">
						<option value="" selected>---请选择---</option>
						<option value="DCC">DCC</option>
						<option value="EDC">EDC</option>
					</select>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="right" nowrap>交易币种</td>
				<td class="border_top_right4" align="left" nowrap>
					<select id="tradeCurrencyCode" name="tradeCurrencyCode">
						<option value="">--请选择--</option>
						<option value="*">*</option>
						<c:forEach var="tcCode" items="${currencyCodeEnum}" varStatus="v">
							<option value="${tcCode.code}">${tcCode.desc}</option>
						</c:forEach>
					</select>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="right" nowrap>支付币种</td>
				<td class="border_top_right4" align="left" nowrap>
					<select id="payCurrencyCode" name="payCurrencyCode">
						<option value="">--请选择--</option>
					</select>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="right" nowrap>结算币种</td>
				<td class="border_top_right4" align="left" nowrap>
					<select id="settlementCurrencyCode" name="settlementCurrencyCode">
						<option value="">--请选择--</option>
					</select>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="right" nowrap>优先级</td>
				<td class="border_top_right4" align="left" nowrap>
					<input id="grade" name="grade" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" />
					<span style="color: red">必须为整数0-999</span>
				</td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="right" nowrap>备注</td>
				<td class="border_top_right4" align="left" nowrap>
					<input id="mark" name="mark" value="0" maxlength="30"/>
				</td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" colspan="3" align="center" nowrap>
					<input type="hidden" id="configId"/>
					<input type="button" value="提交" onclick="editpcc()"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="取消" onclick="closepcc()"/>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>

