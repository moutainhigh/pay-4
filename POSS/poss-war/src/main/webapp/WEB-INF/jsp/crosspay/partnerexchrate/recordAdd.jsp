<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">商户汇率</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="" method="post" name="addFrom"><c:if
	test="${result != null}">
	<input type="hidden" name="id" value="${result.id}" />
</c:if>
<table class="border_all2" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr class="trForContent1">	
		<td class="border_top_right4" align="right">汇率有效期：</td>
		<td class="border_top_right4" align="left">
		<input class="Wdate" type="text" id="fromDate" name="fromDate"  onClick="WdatePicker()" value="<c:if test='${result != null}'><fmt:formatDate value="${result.fromDate}" type="date"/></c:if>"/>
		~
		<input class="Wdate" type="text" id="toDate" name="toDate"  onClick="WdatePicker()" value="<c:if test='${result != null}'><fmt:formatDate value="${result.toDate}" type="date"/></c:if>"/>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right">币种：</td>
		<td class="border_top_right4" align="left">
			<select name="currency" id="currency">
				<option value="">-请选择-</option>
				<option value="RMB" <c:if test="${result != null && result.currency == 'RMB'}">selected</c:if> >人民币</option>
				<option value="USD" <c:if test="${result != null && result.currency == 'USD'}">selected</c:if> >美元</option>
				<option value="HKD" <c:if test="${result != null && result.currency == 'HKD'}">selected</c:if> >港币</option>
				<option value="EUR" <c:if test="${result != null && result.currency == 'EUR'}">selected</c:if> >欧元</option>
				<option value="GBP" <c:if test="${result != null && result.currency == 'GBP'}">selected</c:if> >英镑</option>
				<option value="JPY" <c:if test="${result != null && result.currency == 'JPY'}">selected</c:if> >日元</option>
			</select>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right">交易单位：</td>
		<td class="border_top_right4" align="left">
			<input type="text" name="currencyUnit" value="100" readonly="readonly"/>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right">当前汇率：</td>
		<td class="border_top_right4" align="left">
		<input type="text" name="exchangeRate" id="exchangeRate" value="<c:if test='${result != null}'>${result.exchangeRate}</c:if>" />
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="2" align="center">
		<input type="hidden" name="partnerId" value="ALL"/>
		<input type="button" name="butSubmit"
			value="提  交" class="button2" onclick="add();"></td>
	</tr>
</table>
</form>
<script>

function isEmpty(fData){
    return ((fData==null) || (fData.length==0) )
}

function add(){

	var d = document.forms[0];
	var fromDate = document.getElementById("fromDate").value;
	var toDate = document.getElementById("toDate").value;
	if(isEmpty(fromDate)){
		alert("开始时间不能为空");
		return;
	}
	if(isEmpty(toDate)){
		alert("结束时间不能为空");
		return;
	}
	if(fromDate > toDate){
		alert("开始时间必须小于结束时间");
		return;
	}
	if('' == d.currency.value){
		alert("请选择币种");
		return;
	}
	if('' == d.exchangeRate.value){
		alert("当前汇率不能为空");
		return;
	}
	<c:if test="${result != null}">
		d.action = "${ctx}/crosspay/partnerExchRate.do?method=update";
	</c:if>

	<c:if test="${result == null}">
		d.action = "${ctx}/crosspay/partnerExchRate.do?method=add";
	</c:if>
	d.submit();
}
</script>