<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
    出款退款订单列表
</head>
	<script type="text/javascript">
		function processDo(id){
			location.href = "${ctx}/fundout-withdraw-withdrawrefund.do?method=detail&id=" + id;// + "&uuid=" + ${Session['uuid']};
		}
	</script>

<body>

<table id="withdrawTable" align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
		<tr>
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
			<th>操作</th>
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

				<td>${order.orderId}</td>
				<td>${order.payeeBankName}</td>
				<td>${order.payeeOpeningBankName}</td>
				<td>${order.payeeBankAcctCode}</td>
				<td>
					<fmt:formatNumber value="${order.orderAmount * 0.001}" pattern="#,##0.00"/>
				</td>
				<td>${order.payeeName}</td>
				<td><fmt:formatDate value="${order.webAuditTime}" type="date" /></td>
				<td>
				<c:choose>
					<c:when test="${order.orderType==0}">提现</c:when>
					<c:when test="${order.orderType==3}">付款到银行</c:when>
					<c:when test="${order.orderType==4}">批量付款到银行</c:when>
				</c:choose>
				</td>
				<td>处理成功</td>
				<td>${order.bankOrderId==null?'':order.bankOrderId}</td>
				<td>
					<a href="javascript:void(0)" onclick="processDo('${order.orderId}')">退款</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/> 
</body>

