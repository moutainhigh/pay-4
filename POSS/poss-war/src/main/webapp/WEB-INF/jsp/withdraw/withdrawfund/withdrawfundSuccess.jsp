<%@ page contentType="text/html;charset=UTF-8" language="java"
	import="java.util.Date"%>
<%@ include file="/common/taglibs.jsp"%>


<h2 class="panel_title">出款退款</h2>
<h4 style="color: blue;">退款申请成功</h4>
<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	<tr class="trForContent1">
		<td class="border_top_right4 textRight">交易流水号：</td>
		<td class="border_top_right4 textLeft">${order.orderId}</td>
		<td class="border_top_right4 textRight">银行流水号：</td>
		<td class="border_top_right4 textLeft">${order.bankOrderId==null?'':order.bankOrderId}</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4 textRight">银行名称：</td>
		<td class="border_top_right4 textLeft">${order.payeeBankName}</td>
		<td class="border_top_right4 textRight">开户行名称：</td>
		<td class="border_top_right4 textLeft">${order.payeeOpeningBankName}</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4 textRight">银行账户：</td>
		<td class="border_top_right4 textLeft">${order.payeeBankAcctCode}</td>
		<td class="border_top_right4 textRight">汇款金额：</td>
		<td class="border_top_right4 textLeft"><fmt:formatNumber value="${order.orderAmount * 0.001}" pattern="#,##0.00"/>元</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4 textRight">收款人：</td>
		<td class="border_top_right4 textLeft">${order.payeeName}</td>
		<td class="border_top_right4 textRight">交易时间：</td>
		<td class="border_top_right4 textLeft"><fmt:formatDate value="${order.createDate}" type="date" /></td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4 textRight">状态：</td>
		<td class="border_top_right4 textLeft">成功</td>
		<td class="border_top_right4 textRight">退款金额：</td>
		<td class="border_top_right4 textLeft"><fmt:formatNumber value="${order.orderAmount * 0.001}" pattern="#,##0.00"/>元</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4 textRight">失败原因：</td>
		<td class="border_top_right4 textLeft" colspan="3">
			${order.refundReason}
		</td>
	</tr>
	<tr class="trForContent1">
	
		<td colspan="4" align="center" class="border_top_right4">
			<input type="button" value="返回" id="cancel" onclick="processNo()"/>
		</td>
	</tr>
</table>

<script type="text/javascript">
	function processNo() {
		location.href = "fundout-withdraw-withdrawrefund.do?method=init";
	}
</script>