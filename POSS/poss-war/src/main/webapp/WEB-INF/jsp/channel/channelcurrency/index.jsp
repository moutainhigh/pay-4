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
	var pars = $("#channelCurrencyForm").serialize() + "&pageNo=" + pageNo;
	$.ajax({
		type: "POST",
		url: "${ctx}/channelCurrency.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function deleteChannelCurrency(id){
	if (!confirm("确认删除？")) {
		return;
	 }
	window.location.href="${ctx}/channelCurrency.do?method=deleteChannelCurrency&id="+id;
}

function uploadeFile(){
	var file=$("#addChannelCurrencyFile").val();
	
	if(file == ''){
		alert("请选择上传文件！");
		return false;
	}
	$("#channelCurrencyForm").submit();
}
function save() {
	var orgCode=$("#orgCode").val();
	var currencyCode=$("#currencyCode").val();
	var cardCurrencyCode=$("#cardCurrencyCode").val();
	var channelCurrencyCode=$("#channelCurrencyCode").val();
	var payType=$("#payTypeEd").val();
	if(orgCode == ""){
		alert("请选择渠道名称!");
		return;
	}
	if(prdtCode == ""){
		alert("请选择产品类型！");
		return ;
	}
	if(currencyCode == ""){
		alert("请选择交易币种！");
		return ;
	}
	if(payType =='DCC'){
		if(cardCurrencyCode == ""){
			alert("请输入卡本币！");
			return ;
		}
	}
	if(channelCurrencyCode == "" || channelCurrencyCode == null){
		alert("请选择送渠道币种！");
		return ;
	}
	if(payType == ""){
		alert("请选择支付类型！");
		return ;
	}
	for(var i=0;i<prdtCode.length;i++){
		if(prdtCode[i].value=="PARTNER_STANDARD_DCC"&&prdtCode[i].checked&&currencyCode!="CNY"){
			alert("交易币种应选为CNY");
			return;
		}
	}
	if (!confirm("确认新增该渠道币种配置？")) {
		return;
	}

	$("#frm").submit();
}

function selectPayType(){
	var payType=$("#payTypeEd").val();
	$("#currencyCodeTr").show();
	$("#cardCurrencyCodeTr").show();
	if(payType == 'EDC'){
		$("#currencyCodeTr").show();
		$("#cardCurrencyCodeTr").hide();
		$("#cardCurrencyCodeEdit").val('');//同时值
	}else if(payType == 'DCC'){
		$("#currencyCodeTr").hide();
		$("#cardCurrencyCodeTr").show();
		$("#currencyCode").val('');//同时值
	}
}
function change(){
	var payType=$("#payType").val();
	$("#currencyCodeTD").show();
	$("#cardCurrencyCodeTD").show();
	if(payType == 'EDC'){
		$("#currencyCodeTD").show();
		$("#cardCurrencyCodeTD").hide();
		$("#cardCurrencyCode").val('');//同时值
	}else if(payType == 'DCC'){
		$("#currencyCodeTD").hide();
		$("#cardCurrencyCodeTD").show();
		$("#currencyCodeFind").val('');//同时值
	}
}
function getChannelRCurrency(channelCurrencyCode){
	var orgCode=$("#orgCodeEdit").val();
	$("#channelCurrencyCode").empty();
	$.ajax({
		type: "POST",
		url: "${ctx}/channelItemRCurrency.do?method=getChannelItemCurrencyCode&channelItemCode="+orgCode,
		success: function(result) {
			var json=JSON.parse(result);
			for(var k in json){
				if(json[k].currencyCode == channelCurrencyCode){
					$("#channelCurrencyCode").append("<option selected='true'>"+json[k].currencyCode+"</option>");
				}else{
					$("#channelCurrencyCode").append("<option>"+json[k].currencyCode+"</option>");

				}
			}
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
		url: "${ctx}/channelCurrency.do?method=deleteChannelCurrency",
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
	$("#partnerId").val('');
	$("#orgCodeEdit").val('');
	$("#payTypeEd").val('');
	$("#cardCurrencyCodeEdit").val('');
	$("#channelCurrencyCode").val('');
	$("#currencyCode").val('');
	$("#id").val('');
	selectPayType();
	$('#partnerId').attr("readonly",false);
	$('#orgCodeEdit').attr("disabled",false);
	$('#partnerId').attr("disabled",false);
	$('#orgCodeEdit').attr("readonly",false);
	$('#addLogDiv').dialog({
		position:["center","center"],
		width:600,
		height:400,
		modal:true,
		title:'送渠道送币种配置',
		overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
	});
	$('#addLogDiv').dialog('open');
}
function editpcc(){
	var partnerId = $.trim($("#partnerId").val());
	var orgCode = $("#orgCodeEdit").val();
	var payType = $("#payTypeEd").val();
	var cardCurrencyCode = $("#cardCurrencyCodeEdit").val();
	var channelCurrencyCode = $("#channelCurrencyCode").val();
	var currencyCode = $("#currencyCode").val();
	var id = $("#id").val();
	if('' == partnerId || isNaN(partnerId)){
		alert("请输入正确会员号！");
		return;
	}

	if('' == orgCode){
		alert("请选择渠道！");
		return;
	}

	if('' == payType){
		alert("请输入交易类型！");
		return;
	}
	if('EDC' == payType && '' == currencyCode){
		alert("请输入交易币种！");
		return;
	}
	if('DCC' == payType && '' == cardCurrencyCode){
		alert("请输入卡本币！");
		return;
	}

	if('' == channelCurrencyCode){
		alert("请输入送渠道币种！");
		return;
	}
	var pars ="partnerId=" + partnerId + "&orgCode=" + orgCode+ "&payType=" + payType + "&id=" + id +
			"&currencyCode=" + currencyCode + "&cardCurrencyCode=" + cardCurrencyCode + "&channelCurrencyCode=" + channelCurrencyCode;
	$.ajax({
		type: "POST",
		url: "${ctx}/channelCurrency.do?method=addChannelCurrency",
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
function editConfig(id,partnerId,payType,orgCode,currencyCode,cardCurrencyCode,channelCurrencyCode){
	$("#currencyCode").val(currencyCode);
	$("#partnerId").val(partnerId);
	$("#orgCodeEdit").val(orgCode);
	getChannelRCurrency(channelCurrencyCode);
	$("#cardCurrencyCodeEdit").val(cardCurrencyCode);
	$("#payTypeEd").val(payType);
	selectPayType();
	$("#id").val(id);
	$('#partnerId').attr("readonly",true);
	$('#orgCodeEdit').attr("readonly",true);
	$('#partnerId').attr("disabled",true);
	$('#orgCodeEdit').attr("disabled",true);
	$('#addLogDiv').dialog({
		position:["center","center"],
		width:600,
		height:400,
		modal:true,
		title:'修改渠道送币种配置',
		overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
	});
}
-->
</script>
</head>

<body>
<h2 class="panel_title">送渠道币种配置</h2>
	<c:if test="${not empty msg}">
			<p><font color="red">${msg}</font></p>
	</c:if>
<form id="channelCurrencyForm" name="channelCurrencyForm"  action="channelCurrency.do?method=batchAdd"  method="post" enctype="multipart/form-data">
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="left">
					会员号：<input type="text" name="partnerId"/>
				</select>
				</td>
				<td class="border_top_right4" align="left">
					渠道名称：<select id="orgCode" name="orgCode">
						<option value="" selected>---请选择---</option>
						<c:forEach var="channel" items="${channelItems}" varStatus="v">
							<option value="${channel.code}">${channel.desc}</option>
						</c:forEach>
					</select>	
				</td>
				<td class="border_top_right4" align="left">
				支付类型：<select id="payType" name="payType" onchange="change()">
						<option value="" selected>---请选择---</option>
							<option value="DCC">DCC</option>
							<option value="EDC">EDC</option>
					</select>	
				</td>
				<td class="border_top_right4" align="left" id="currencyCodeTD">
					交易币种：<select id="currencyCodeFind" name="currencyCode">
					<option value="">-----请选择-----</option>
					<option> GBP</option>
					<option> USD</option>
					<option> EUR</option>
					<option> ALL</option>
					<option> AOA</option>
					<option> ARS</option>
					<option> AUD</option>
					<option> BHD</option>
					<option> BDT</option>
					<option> BYR</option>
					<option> BGN</option>
					<option> BOB</option>
					<option> BRL</option>
					<option> BND</option>
					<option> CAD</option>
					<option> CLP</option>
					<option> CNY</option>
					<option> COP</option>
					<option> CDF</option>
					<option> CRC</option>
					<option> HRK</option>
					<option> CZK</option>
					<option> DKK</option>
					<option> EGP</option>
					<option> FJD</option>
					<option> HKD</option>
					<option> HUF</option>
					<option> ISK</option>
					<option> INR</option>
					<option> IDR</option>
					<option> ILS</option>
					<option> JPY</option>
					<option> KRW</option>
					<option> KWD</option>
					<option> LVL</option>
					<option> LTL</option>
					<option> MYR</option>
					<option> MXN</option>
					<option> NZD</option>
					<option> NOK</option>
					<option> PKR</option>
					<option> PHP</option>
					<option> PLN</option>
					<option> RUB</option>
					<option> SAR</option>
					<option> SGD</option>
					<option> ZAR</option>
					<option> SEK</option>
					<option> CHF</option>
					<option> THB</option>
					<option> TRY</option>
					<option> AED</option>
					<option> TWD</option>
					<option> UAH</option>
					<option> MOP</option>
					<option> *</option>
				</select>
				</td>
				<td class="border_top_right4" align="left" id="cardCurrencyCodeTD">
				卡本币：<select id="cardCurrencyCode" name="cardCurrencyCode">
										<option value="" selected>---请选择---</option>
														<option>EUR</option>
														<option>AZM</option>
														<option>BRL</option>
														<option>FJD</option>
														<option>GIP</option>
														<option>LSL</option>
														<option>MKD</option>
														<option>MMK</option>
														<option>MOP</option>
														<option>NGN</option>
														<option>PYG</option>
														<option>SAR</option>
														<option>SYP</option>
														<option>TTD</option>
														<option>VEF</option>
														<option>VUV</option>
														<option>BGL</option>
														<option>AMD</option>
														<option>BAM</option>
														<option>BND</option>
														<option>DOP</option>
														<option>GYD</option>
														<option>HTG</option>
														<option>IQD</option>
														<option>KYD</option>
														<option>LYD</option>
														<option>NAD</option>
														<option>PHP</option>
														<option>ROL</option>
														<option>RWF</option>
														<option>UAH</option>
														<option>UYU</option>
														<option>UZS</option>
														<option>ZWD</option>
														<option>MXV</option>
														<option>XCD</option>
														<option>BOB</option>
														<option>CRC</option>
														<option>DZD</option>
														<option>HNL</option>
														<option>IDR</option>
														<option>KWD</option>
														<option>MDL</option>
														<option>XPF</option>
														<option>OMR</option>
														<option>PLN</option>
														<option>CSD</option>
														<option>RUB</option>
														<option>SOS</option>
														<option>STD</option>
														<option>SZL</option>
														<option>TRY</option>
														<option>TOP</option>
														<option>ZAR</option>
														<option>ANG</option>
														<option>ARS</option>
														<option>BDT</option>
														<option>BMD</option>
														<option>NZD</option>
														<option>EEK</option>
														<option>INR</option>
														<option>LAK</option>
														<option>MRO</option>
														<option>SEK</option>
														<option>SKK</option>
														<option>SLL</option>
														<option>TPE</option>
														<option>TND</option>
														<option>UGX</option>
														<option>XOF</option>
														<option>BYR</option>
														<option>CNY</option>
														<option>MAD</option>
														<option>GBP</option>
														<option>GEL</option>
														<option>GMD</option>
														<option>GNF</option>
														<option>JPY</option>
														<option>KES</option>
														<option>KGS</option>
														<option>KHR</option>
														<option>KRW</option>
														<option>LBP</option>
														<option>LTL</option>
														<option>LVL</option>
														<option>MZM</option>
														<option>TJS</option>
														<option>WST</option>
														<option>YUM</option>
														<option>GWP</option>
														<option>NOK</option>
														<option>BWP</option>
														<option>CDF</option>
														<option>XAF</option>
														<option>CLP</option>
														<option>CUP</option>
														<option>CVE</option>
														<option>CZK</option>
														<option>DJF</option>
														<option>ERN</option>
														<option>FKP</option>
														<option>HUF</option>
														<option>ILS</option>
														<option>JMD</option>
														<option>KZT</option>
														<option>LRD</option>
														<option>MUR</option>
														<option>SBD</option>
														<option>SGD</option>
														<option>SHP</option>
														<option>SIT </option>
														<option>TWD</option>
														<option>XDR</option>
														<option>AED</option>
														<option>ALL</option>
														<option>AOA</option>
														<option>BGN</option>
														<option>BIF</option>
														<option>BTN</option>
														<option>CHF</option>
														<option>CYP</option>
														<option>DKK</option>
														<option>EGP</option>
														<option>GTQ</option>
														<option>HKD</option>
														<option>IRR</option>
														<option>ISK</option>
														<option>JOD</option>
														<option>MTL</option>
														<option>MVR</option>
														<option>NPR</option>
														<option>PAB</option>
														<option>PGK</option>
														<option>PKR</option>
														<option>QAR</option>
														<option>SCR</option>
														<option>SRD</option>
														<option>SVC</option>
														<option>THB</option>
														<option>VND</option>
														<option>ZMK</option>
														<option>AFN</option>
														<option>XXX</option>
														<option>USD</option>
														<option>AUD</option>
														<option>AWG</option>
														<option>BBD</option>
														<option>BHD</option>
														<option>BSD</option>
														<option>BZD</option>
														<option>CAD</option>
														<option>CLF</option>
														<option>COP</option>
														<option>ETB</option>
														<option>GHC</option>
														<option>HRK</option>
														<option>KMF</option>
														<option>KPW</option>
														<option>LKR</option>
														<option>MGA</option>
														<option>MNT</option>
														<option>MWK</option>
														<option>MXN</option>
														<option>MYR</option>
														<option>NIO</option>
														<option>PEN</option>
														<option>SDD</option>
														<option>TMM</option>
														<option>TZS</option>
														<option>YER</option>
														<option>BOV</option>
						</select>	
				</td>
				
			</tr>	
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="5" align="center">
					<a class="s03" href="javascript:query()"><input class="button2" type="button" value="查询"> </a>
					<a class="s03" href="javascript:add()"><input class="button2" type="button" value="新增"> </a>
					&nbsp;&nbsp;&nbsp;
				<!-- 	<input type="file" value="请选择需要上传的文件"  name="addChannelCurrencyFile" id="addChannelCurrencyFile" >
					<input type="button" value="批量新增" onclick="return uploadeFile(this);"> -->
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
				<td class="border_top_right4" align="left" nowrap><input id="partnerId" name="partnerId"/></td>
				<td class="border_top_right4" align="center" nowrap><span style="color: red">*</span></td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="right" nowrap>渠道名称</td>
				<td class="border_top_right4" align="left" nowrap>
					<select id="orgCodeEdit" name="orgCode" onchange="getChannelRCurrency()">
						<option value="" selected>---请选择---</option>
						<c:forEach var="channel" items="${channelItems}" varStatus="v">
							<option value="${channel.code}">${channel.desc}</option>
						</c:forEach>
					</select>
				</td>
				<td class="border_top_right4" align="center" nowrap><span style="color: red">*</span></td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class=border_top_right4 align="right" nowrap>支付类型</td>
				<td class="border_top_right4" align="left" nowrap>
					<select name="payType" id="payTypeEd"  onchange="selectPayType();" >
						<option value="">-----请选择-----</option>
						<option value="EDC">EDC</option>
						<option value="DCC">DCC</option>
					</select>
				</td>
				<td class="border_top_right4" align="center" nowrap><span style="color: red">*</span></td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle" id="currencyCodeTr">
				<td class=border_top_right4 align="right" nowrap>交易币种</td>
				<td class="border_top_right4" align="left" nowrap>
					<select name="currencyCode" id="currencyCode" >
						<option value="">-----请选择-----</option>
						<option> GBP</option>
						<option> USD</option>
						<option> EUR</option>
						<option> ALL</option>
						<option> AOA</option>
						<option> ARS</option>
						<option> AUD</option>
						<option> BHD</option>
						<option> BDT</option>
						<option> BYR</option>
						<option> BGN</option>
						<option> BOB</option>
						<option> BRL</option>
						<option> BND</option>
						<option> CAD</option>
						<option> CLP</option>
						<option> CNY</option>
						<option> COP</option>
						<option> CDF</option>
						<option> CRC</option>
						<option> HRK</option>
						<option> CZK</option>
						<option> DKK</option>
						<option> EGP</option>
						<option> FJD</option>
						<option> HKD</option>
						<option> HUF</option>
						<option> ISK</option>
						<option> INR</option>
						<option> IDR</option>
						<option> ILS</option>
						<option> JPY</option>
						<option> KRW</option>
						<option> KWD</option>
						<option> LVL</option>
						<option> LTL</option>
						<option> MYR</option>
						<option> MXN</option>
						<option> NZD</option>
						<option> NOK</option>
						<option> PKR</option>
						<option> PHP</option>
						<option> PLN</option>
						<option> RUB</option>
						<option> SAR</option>
						<option> SGD</option>
						<option> ZAR</option>
						<option> SEK</option>
						<option> CHF</option>
						<option> THB</option>
						<option> TRY</option>
						<option> AED</option>
						<option> TWD</option>
						<option> UAH</option>
						<option> MOP</option>
						<option> *</option>
					</select>
				</td>
				<td class="border_top_right4" align="center" nowrap><span style="color: red">*</span></td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle" id="cardCurrencyCodeTr">
				<td class=border_top_right4 align="right" nowrap>卡本币</td>
				<td class="border_top_right4" align="left" nowrap>
					<input type="text" name="cardCurrencyCode"  id="cardCurrencyCodeEdit"  > <font color='red' id='cardCurrencyCodeFont' >*</font>
				</td>
				<td class="border_top_right4" align="center" nowrap><span style="color: red">*</span></td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class=border_top_right4 align="right" nowrap>送渠道币种</td>
				<td class="border_top_right4" align="left" nowrap>
					<select name="channelCurrencyCode" id="channelCurrencyCode" >
						<option value="">-----请选择-----</option>
					</select>
				</td>
				<td class="border_top_right4" align="center" nowrap><span style="color: red">*</span></td>
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

