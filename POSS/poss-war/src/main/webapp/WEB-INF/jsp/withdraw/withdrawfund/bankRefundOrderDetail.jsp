<%@ page contentType="text/html;charset=UTF-8" language="java"
	import="java.util.Date"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">出款退款</h2>
<h4>待审核记录详情</h4>
<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	<tr class="trForContent1">
		<td class="border_top_right4 textRight">退款流水号：</td>
		<td class="border_top_right4 textLeft">${order.refundOrderId}</td>
		<td colspan="2" class="border_top_right4"></td>
	</tr>
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
		<td class="border_top_right4 textLeft">${order.refundReason}</td>
		<td colspan="2" class="border_top_right4"></td>
		
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4 textRight">备注：</td>
		<td class="border_top_right4 textLeft" colspan="3">
			<textarea rows="5" cols="60" name="remarks" id="remarks"></textarea>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4 textLeft">&nbsp;</td>
		<td class="border_top_right4 textLeft">
			<input type="button" id="pass" class="buttont2" value="通过" onclick="processYes('${order.refundOrderId}')"/>
		</td>
		<td class="border_top_right4 textLeft">
			<input type="button" id="reject" class="buttont2" value="拒绝" onclick="processNo('${order.refundOrderId}')"/>
		</td>
		<td class="border_top_right4 textLeft">
			<input type="button" value="返回" onclick="toList();"/>
		</td>
	</tr>
</table>

<script type="text/javascript">
	function processYes(id) {
		var remark =document.getElementById("remarks").value;
		if(remark==null || remark==''){
			alert("请输入备注信息再提交！");
			return;
		}
		remark=encodeURIComponent(remark);
		$("#pass").attr("disabled","disabled");
		$("#reject").attr("disabled","disabled");
		location.href = "${ctx}/fundout-withdraw-withdrawrefund.do?method=batchVerify&ids=" + id 
						+"&flag=yes&uuidVerify=${sessionScope.uuidVerify}"+"&remarks="+ remark;
	}
	function processNo(id) {
		var remark =document.getElementById("remarks").value;
		if(remark==null || remark==''){
			alert("请输入备注信息再提交！");
			return;
		}
		remark=encodeURIComponent(remark);
		$("#reject").attr("disabled","disabled");
		$("#pass").attr("disabled","disabled");
		location.href = "${ctx}/fundout-withdraw-withdrawrefund.do?method=batchVerify&ids=" + id 
						+"&flag=no&uuidVerify=${sessionScope.uuidVerify}"+"&remarks="+remark;
	}
	function toList(){
		window.location="${ctx}/fundout-withdraw-withdrawrefund.do?method=query";
	}
</script>