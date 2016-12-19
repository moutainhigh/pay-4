<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
    已审核退款订单列表
</head>
	<script type="text/javascript">
		function processDo(id){
			location.href = "${ctx}/fundout-withdraw-withdrawrefund.do?method=refundOrderdetail&id=" + id;
		}
	</script>

<body> 

	<table id="withdrawTable" align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	
	<thead>
		<tr>
			<th>退款流水号</th>
			<th>交易流水号</th>
			<th>银行名称</th>
			<th>开户行名称</th>
			<th>银行账户</th>
			<th>汇款金额（元）</th>
			<th>收款人</th>
			<th>交易时间</th>
			<th>业务类型</th>
			<th>状态</th>
			<th>银行流水号</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${page.result}" var="order" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td>${order.refundOrderId}</td>
				<td>${order.orderId}</td>
				<td>${order.payeeBankName}</td>
				<td>${order.payeeOpeningBankName}</td>
				<td>${order.payeeBankAcctCode}</td>
				<td><fmt:formatNumber value="${order.orderAmount * 0.001}" pattern="#,##0.00"/></td>
				<td>${order.payeeName}</td>
				<td><fmt:formatDate value="${order.webAuditTime}" type="date" /></td>
				<td>
				<c:choose>
					<c:when test="${order.orderType==0}">提现</c:when>
					<c:when test="${order.orderType==3}">付款到银行</c:when>
					<c:when test="${order.orderType==4}">批量付款到银行</c:when>
				</c:choose>
				</td>
				<td>成功</td>
				<td>${order.bankOrderId==null?'':order.bankOrderId}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/> 
</body>

