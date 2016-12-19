<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function order(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/partnerSettlementOrderQuery.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
</script>
<form action="" method="post" name="listFrom">
</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
	<thead>
		<tr>
			<th>清算订单号</th>
			<th>会员号</th>
			<th>网关订单号</th>
			<th nowrap="nowrap">清算金额</th>
			<th nowrap="nowrap">交易币种</th>
			<th nowrap="nowrap">清算币种</th>
			<th nowrap="nowrap">保证金</th>
			<th>支付订单号</th>
			<th nowrap="nowrap">清算汇率</th>
			<th nowrap="nowrap">保证金返还</th>
			<th>商户订单号</th>
			<th nowrap="nowrap">手续费</th>
			<th nowrap="nowrap">是否清算</th>
			<th>创建时间</th>
			<th>清算时间</th>
			<th nowrap="nowrap">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="order" varStatus="index">
		<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
			<td>${order.id}</td>
			<td>${order.partnerId }</td>
			<td>${order.tradeOrderNo}</td>
			<td> <fmt:formatNumber type="number"  value="${order.amount/1000}" />  </td>
			<td>${order.currencyCode }</td>
			<td>${order.settlementCurrencyCode }</td>
			<td>${order.assureAmount/1000 }</td>
			<td>${order.paymentOrderNo}</td>
			<td>${order.settlementRate }</td>
			<td>
				<c:if test="${order.assureSettlementFlg=='0'}">
					未返还
				</c:if>
				<c:if test="${order.assureSettlementFlg=='1'}">
					已返还
				</c:if>
				<c:if test="${order.assureSettlementFlg=='2'}">
					返还失败
				</c:if>
				<c:if test="${order.assureSettlementFlg=='3'}">
					退款返还
				</c:if>
				<c:if test="${order.assureSettlementFlg=='4'}">
					退款返还
				</c:if>
				<c:if test="${order.assureSettlementFlg=='5'}">
					拒付冻结
				</c:if>
			</td>
			<td>${order.orderId }</td>
			<td>${order.fee/1000}</td>
			<td>
				<c:if test="${order.settlementFlg=='0'}">
					未清算
				</c:if>
				<c:if test="${order.settlementFlg=='1'}">
					清算成功
				</c:if>
				<c:if test="${order.settlementFlg=='2'}">
					清算失败
				</c:if>
				<c:if test="${order.settlementFlg=='4'}">
					已退款
				</c:if>
			</td>
			<td><date:date value="${order.createDate}"/></td>
			<td><date:date value="${order.settlementDate}"/></td>
			<td></td>
		</tr>
		</c:forEach>
		
	</tbody>
</table>
<li:pagination methodName="order" pageBean="${page}" sytleName="black2"/>