<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.pay.pe.AmountUtils"%>

<form action="" method="post" name="listFrom">

</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>订单号</th>
			<th>上传时间</th>
			<th>商户号</th>
			<th>订单状态</th>
			<th>扣款金额</th>
			<th>扣款银行</th>
			<th>发送银行流水号</th>
			<th>银行卡号</th>
		</tr>
	</thead>
	
	<tbody>
		<c:if test="${page != null && fn:length(page.result) > 0}">
			<c:forEach items="${page.result}" var="dto" varStatus="index">
			<tr>
				<td>
					${dto.orderId}
				</td>
				<td>
					${dto.strCreateDate}
				</td>
				<td>
					${dto.merchantId}
				</td>
				<td>
					<c:if test="${dto.orderStatus=='1'}">订单已受理</c:if> 
					<c:if test="${dto.orderStatus=='2'}">等待审核</c:if> 
					<c:if test="${dto.orderStatus=='3'}">审核成功</c:if> 
					<c:if test="${dto.orderStatus=='4'}">审核失败</c:if> 
					<c:if test="${dto.orderStatus=='5'}">已发送银行</c:if> 
					
					<c:if test="${dto.orderStatus=='6'}">银行已受理</c:if> 
					<c:if test="${dto.orderStatus=='7'}">银行拒绝</c:if> 
					<c:if test="${dto.orderStatus=='8'}">银行扣款成功</c:if> 
					<c:if test="${dto.orderStatus=='9'}">银行扣款失败</c:if> 
					
					<c:if test="${dto.orderStatus=='10'}">订单处理成功</c:if> 
					<c:if test="${dto.orderStatus=='11'}">订单处理失败</c:if> 
					<c:if test="${dto.orderStatus=='12'}">已退款</c:if> 
				</td>
				<td>
					${dto.strPayerAmount}
				</td>
				<td>
					${dto.payerBankname}
				</td>
				<td>
					${dto.bankOrderid}
				</td>
				<td>
					${dto.payerAccount}
				</td>
			</c:forEach>
		
		</c:if>
		<c:if test="${page == null || fn:length(page.result) == 0}">
			<tr>
			<td colspan="11" align="center">
				没有查询到相关数据!
			</td>
			</tr>
		</c:if>
	</tbody>
</table>

<li:pagination methodName="withholdBankQuery" pageBean="${page}" sytleName="black2"/>
 <script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				7: {sorter: false}
			}
		});
	 }); 

	function audit(sequenceId,oldProtocolNo,status){
		document.listFrom.action="context_withhold_bankprotocol.controller.htm?method=audit&sequenceId="+sequenceId+"&oldProtocolNo="+oldProtocolNo+"&status="+status;
		document.listFrom.submit();
	}
	
</script>