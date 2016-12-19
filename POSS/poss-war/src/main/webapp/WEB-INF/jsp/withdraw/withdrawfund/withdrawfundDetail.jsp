<%@ page contentType="text/html;charset=UTF-8" language="java"
	import="java.util.Date"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">出款退款申请</h2>
 <form action="fundout-withdraw-withdrawrefund.do?method=addFundOrder" method="post" name="resultForm"  id="resultForm">
 	<input type="hidden" name="uuid" id="uuidId" />
 	<input type="hidden" name="id" id="idId" />

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
		<td class="border_top_right4 textLeft"><fmt:formatDate value="${order.webAuditTime}" type="date" /></td>
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
			<!--<textarea rows="5" cols="60" name="remarks" id="remarks"></textarea>
			--><li:select name="remarks" defaultStyle="true"  itemList="${failDescList}"  />
		</td>
	</tr>
	<tr class="trForContent1">
		
		<td align="center" colspan="4" class="border_top_right4">
			<input type="button" value="确定" id="ok" onclick="processYes('${order.orderId}')"/>
			<input type="button" value="返回" id="cancel" onclick="processNo()"/>
		</td>
	
	</tr>
</table>
</form>
<script type="text/javascript">
	function processYes(id) {
		var remark = document.getElementById("remarks").value;
		if(remark==null || remark==''){
			alert("请选择失败原因再提交！");
			return;
		}

		$("#uuidId").val("${sessionScope.uuidaddFundOrder}");
		$("#idId").val(id);
		
		$("#ok").attr("disabled","disabled");
		document.resultForm.submit();
	}
	function processNo() {
		location.href = "fundout-withdraw-withdrawrefund.do?method=init";
	}
</script>