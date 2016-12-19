<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">拒付录入</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="${ctx}/chargeBackAdd.do?method=add" method="post" name="addFrom" id="addFrom" onsubmit="return add()">
<table class="border_all2" width="1000" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr class="trForContent1">
		<td align="right" class="border_top_right4">网关交易号：</td>	
		<td class="border_top_right4" align="left" style="width: 100px;" nowrap="nowrap">
			<input type="text" name="tradeOrderNo" id="tradeOrderNo" value="" style="width: 150px;" onblur="checkPartRefund();"/><font color="red"><div id="alterMsg"></div></font>
		</td>
		<td align="right" class="border_top_right4" style="width: 80px;">渠道原档号：</td>	
		<td class="border_top_right4" align="left">
			<input type="text" name="oldRefNo" id="oldRefNo" value="" style="width: 150px;"/>
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="border_top_right4" style="width: 80px;">拒付类型：</td>	
		<td class="border_top_right4" align="left">
			<select name="cpType" id="cpType">
	      		<option value="">请选择</option>
	      		<option value="1">内部调查单</option>
	      		<option value="2">银行调查单</option>
	      		<option value="3">拒付单</option>
	      	</select>
		</td>
		<td class="border_top_right4" align="right">
			拒付日期：
		</td>
		<td class="border_top_right4" align="left">
			<input class="Wdate" type="text" id="cpdDate" value="" name="cpdDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="border_top_right4" style="width: 80px;">拒付方式：</td>	
		<td class="border_top_right4" align="left">
			<select name="amountType" id="amountType">
	      		<option value="">请选择</option>
	      		<option value="1">全额拒付</option>
	      		<option value="2">部分拒付</option>
	      	</select>
		</td>
		<td align="right" class="border_top_right4" style="width: 80px;">拒付金额：</td>
		<td class="border_top_right4" align="left" style="width: 100px;">
			<input type="text" name="chargeBackAmount" id="chargeBackAmount" value="" style="width: 50px;"/><font color="red">注：全额拒付不填，部分拒付为必填</font>
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="border_top_right4" style="width: 80px;">拒付币种：</td>	
		<td class="border_top_right4" align="left">
			<select name="currencyCode" id="currencyCode">
	      		<option value="" selected>---请选择---</option>
	      		<c:forEach var="currency" items="${currencyCodes}" varStatus="v">
					<option value="${currency.code}">${currency.desc}</option>
				</c:forEach>
	      	</select>
		</td>
		<td align="right" class="border_top_right4">拒付原因：</td>	
		<td class="border_top_right4" align="left">
			<textarea rows="3" cols="20"></textarea>
		</td>
	</tr>
	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="4" align="center">
			<input type="submit" name="butSubmit" value="确认" class="button2">
		</td>
	</tr>
	
</table>
</form>

<script>

function checkPartRefund() {
	var pars = "";
	var tradeOrderNo = $("#tradeOrderNo").val();
	$.ajax({
		type: "POST",
		url: "${ctx}/chargeBackQuery.do?method=checkPartRefund&tradeOrderNo=" + tradeOrderNo,
		data: pars,
		success: function(result) {
			$('#alterMsg').html("已发生退款：" + result);
		}
	});
}

function add() {
	
	var tradeOrderNo = $('#tradeOrderNo').val();
	var chargeBackAmount = $('#chargeBackAmount').val();
	var cpType = $('#cpType').val();
	var amountType = $('#amountType').val();
	var currencyCode = $('#currencyCode').val();
	
	if('' == tradeOrderNo){
		alert('请输入网关交易号');
		$('#tradeOrderNo').focus();
		return false;
	}
	if('' == cpType){
		alert('请选择拒付类型');
		return false;
	}
	if('' == amountType){
		alert('请选择拒付方式');
		return false;
	}
	
	
	if(amountType == 2 && '' == chargeBackAmount){
		alert('请输入拒付金额');
		$('#chargeBackAmount').focus();
		return false;
	}
	if(amountType == 2 && '' == currencyCode){
		alert('请选择拒付币种');
		return false;
	}
	return true;
}
</script>