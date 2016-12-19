<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function personalAcctBalanceQuery(pageNo,totalCount,pageSize) {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();

	var acctCode = $("#acctCode").val();
	var loginName = $("#loginName").val();
	
	if("" == loginName){
		alert("请输入登录名");
		return;
	}

	if(null != endDate && 0 != endDate.length){
		if(0 == startDate.length || null == startDate){
			alert("请输入交易日期起始端");
			return;
			}
		}

	
	
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#personSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount +"&pageSize="+pageSize;
	pars+="&d="+Math.random();
	$.ajax({
		type: "POST",
		url: "${ctx}/personalAcctBalance.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function load(pageNo,totalCount ){
	var str = $('#acctCode').val();
	if(null!=str && ""!=str){
		personalAcctBalanceQuery(pageNo,totalCount);
		}
}
function checknumber(){   
   // var re = /^[\-\+]?([0-9]\d*|0|[1-9]\d{0,2}(,\d{3})*)(\.\d+)?$/;   
   	var re = /^(([1-9]\d*)|0)(\.\d{2})?$/; 
	var balanceStrat = $("#balanceStrat").val();
	var balanceEnd = $("#balanceEnd").val(); 
    if(re.test(balanceStrat)==false){
    	document.getElementById("balanceStrat").value="";
        }
    if(re.test(balanceEnd)==false){
    	document.getElementById("balanceEnd").value="";
        }
  }
</script>
</head>

<body onload=load();>

<!-- <table width="35%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">个  人  会   员  账  户  余  额   明  细  </font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">个人会员账户余额明细</h2>

<form id="personSearchFormBean" name="personSearchFormBean" >

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >登录名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="loginName" name="loginName" value="${loginName}" maxlength= "64">
		</td>
		<td class="border_top_right4" align="right" >交易类型：</td>
		<td class="border_top_right4"align="left" >
			<select id="dealType" name="dealType">
				<c:forEach items="${dealTypeList}" var="dtype">
					<option value="${dtype.code }" >${dtype.message}</option>
				</c:forEach>
				<%-- 			
				<option value="2" >即时交易</option>
				<option value="3" >充值</option>
				<option value="4" >担保交易退款</option>
				<option value="5" >充值退款</option>
				<option value="6" >提现</option>
				<option value="7" >提现退款</option>
				<option value="8" >付款到银行</option>
				<option value="9" >付款到账户</option>
				<option value="10">分账</option>		
				
				<option value="11">付款到银行退款</option>		
				<option value="12">付款到账户退款</option>		
				<option value="13">信用卡还款申请</option>		
				<option value="14">信用卡还款</option>		
				<option value="15">信用卡退款</option>		
				<option value="16">实名认证</option>	
				<option value="17">实名认证退款</option>		
				
				<option value="18">手工帐</option>		
				<option value="19">手机充值</option>		
				<option value="20">信用卡还款</option>		
				<option value="21">水电煤缴费</option>		
				<option value="22">交房租</option>		
				<option value="23">还房贷</option>	
				<option value="24">送礼金</option>	
				<option value="25">通信账单</option>	
				<option value="26">跨行转账</option>	
				<option value="27">AA收款</option>	
				<option value="28">赡养费</option>	
				<option value="29">生活费</option>	
				<option value="30">冲正</option>	
				<option value="31">已冲正</option>		
				<option value="32">冻结</option>	
				<option value="33">解冻</option>	
				<option value="34">批量付款到账户</option>	
				--%>
	
							
		   </select>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >账户类型：</td>
		<td class="border_top_right4" align="left" >
			<select id="type" name="type">
				<option value="10" <c:if test="${type=='10'}">select</c:if> >人民币账户</option>	
		   </select>
		</td>
		<!-- 
			<td  class="border_top_right4" align="right" >余额范围：</td>
			<td class="border_top_right4" align="left" >
				<input	type="text" id="balanceStrat" name="balanceStrat" maxlength= "15" onblur="checknumber()";>
				～
				<input	type="text" id="balanceEnd" name="balanceEnd" maxlength= "15" onblur="checknumber()";>
			</td>
		 -->
		 <td  class="border_top_right4" align="right" >交易日期：</td>
		 <td  class="border_top_right4" align="left" >
			<input class="Wdate" type="text" id= "startDate" name="startDate"  onClick="WdatePicker()">
			～
			<input class="Wdate" type="text" id= "endDate" name="endDate"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})">
		 </td>
	</tr>
	<tr class="trForContent1">
		
		<td  class="border_top_right4" align="right" >账户号：</td>
		<td class="border_top_right4"align="left" >
			<input	type="text" id="acctCode" name="acctCode" maxlength= "32" value="${acctCode}">
		</td>
	 	<td  class="border_top_right4" align="right" >&nbsp;</td>
		<td  class="border_top_right4" align="left" >&nbsp;</td>
	</tr>
	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center"><a
			class="s03" href="javascript:personalAcctBalanceQuery()">
			<input class="button2" type="button" value="查询"></a>
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

