<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">交易冻结</h2>
<form action="" method="post" name="addFrom">
<table class="border_all2" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr class="trForContent1">	
		<td class="border_top_right4" align="right">交易时间：</td>
		<td class="border_top_right4" align="left">
		<fmt:formatDate value="${frozenTradePage.orderTime}" type="date"/>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right">交易号：</td>
		<td class="border_top_right4" align="left">
			${frozenTradePage.tradeOrderNo}
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right">商户号：</td>
		<td class="border_top_right4" align="left">
			${frozenTradePage.partnerId}
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right">交易金额：</td>
		<td class="border_top_right4" align="left">
		<fmt:formatNumber value="${frozenTradePage.orderAmount}" type="currency" pattern="#,#00.0#"/>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right">操作：</td>
		<td class="border_top_right4" align="left">
			冻结
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right">冻结原因：</td>
		<td class="border_top_right4" align="left">
			<textarea rows="5" cols="20" name="frozenReason"></textarea>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="2" align="center">
		<input type="hidden" name="tradeOrderNo" value="${frozenTradePage.tradeOrderNo}"/>
		<input type="button" name="butSubmit"
			value="确认" class="button2" onclick="add();"></td>
	</tr>
</table>
</form>
<script>

function isEmpty(fData){
    return ((fData==null) || (fData.length==0) )
}

function add(){
	var d = document.forms[0];
	var frozenReason = document.getElementById("frozenReason").value;
	if(isEmpty(frozenReason)){
		alert("冻结原因不能为空!");
		return;
	}
	d.action = "${ctx}/crosspay/frozenOrder.do?method=frozenOrder";
	d.submit();
}
</script>