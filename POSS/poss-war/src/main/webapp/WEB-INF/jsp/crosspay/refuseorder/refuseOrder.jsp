<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">拒付更新确认</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="" method="post" name="addFrom">
<table class="border_all2" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr class="trForContent1">	
		<td class="border_top_right4" align="right">交易时间：</td>
		<td class="border_top_right4" align="left">
		<fmt:formatDate value="${refuseOrderPage.orderTime}" type="date"/>
		</td>
		<td class="border_top_right4" align="right">拒付订单号：</td>
		<td class="border_top_right4" align="left">
		${refuseOrderPage.id}
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right">原交易订单号：</td>
		<td class="border_top_right4" align="left">
			${refuseOrderPage.tradeOrderNo}
		</td>
		<td class="border_top_right4" align="right">商户号：</td>
		<td class="border_top_right4" align="left">
			${refuseOrderPage.partnerId}
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right">拒付时间：</td>
		<td class="border_top_right4" align="left">
		<fmt:formatDate value="${refuseOrderPage.createTime}" type="date"/>
		</td>
		<td class="border_top_right4" align="right">拒付金额：</td>
		<td class="border_top_right4" align="left">
		<fmt:formatNumber value="${refuseOrderPage.orderAmount}" type="currency" pattern="#,#00.0#"/>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right">拒付原因：</td>
		<td class="border_top_right4" align="left">
		<textarea rows="5" cols="20" name="refuseReason"></textarea>
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
	d.action = "${ctx}/crosspay/refuseOrder.do?method=updateRefuseOrderStatus";
	d.submit();
}
</script>