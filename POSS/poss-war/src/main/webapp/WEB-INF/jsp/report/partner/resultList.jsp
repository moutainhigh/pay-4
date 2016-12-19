<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
	
	<table class="tablesorter" style="margin-left:10px;" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>日期</th>
					<th>加油站号</th>
					<th>订单号</th>
					<th>订单金额</th>
					<th>客户账户</th>
					<th>是否有组合支付</th>
					<th>支付方式</th>
					<th>支付金额</th>
					<th>是否使用优惠券</th>
					<th>优惠券金额</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${resultList}" varStatus="v">
					<tr>
						<td>${item.orderTime}</td>
						<td>chaoyue-pc</td>
						<td>${item.orderId}</td>
						<td>${item.orderAmount/1000}</td>
						<td>test@163.com</td>
						<td>
							<c:if test="${item.orderAmount != (item.paymentAmount + item.couponValue)}">是</c:if>
							<c:if test="${item.orderAmount == (item.paymentAmount + item.couponValue)}">否</c:if>
						</td>
						<td>
							<c:if test="${item.payType == 'alipay'}">支付宝</c:if>
							<c:if test="${item.payType == 'weixin'}">微信</c:if>
						</td>
						<td>${item.paymentAmount/1000}</td>
						<td>
							<c:if test="${item.couponValue == 0}">否</c:if>
							<c:if test="${item.couponValue != 0}">是</c:if>
						</td>
						<td>
							${item.couponValue/1000}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
