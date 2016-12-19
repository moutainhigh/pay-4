<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
function recordList(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/refundOrderQuery.do?method=list",
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

<table  id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
	<thead>
		<tr>
			<th>退款交易号</th>
			<th>网关交易号</th>
			<th>支付交易号</th>
			<th>商户会员号</th>
			<th>商户订单号</th>
			<th>汇率</th>
			<th>退款金额</th>
			<th>币种</th>
			<th>退款金额(支付币种)</th>
			<th>退款类型</th>
			<th>状态</th>
			<th>是否对账</th>
			<th>创建时间</th>
			<th>完成时间</th>
			<th>机构</th>
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
			<td>${order.refundOrderNo}</td>
			<td>${order.tradeOrderNo}</td>
			<td>${order.paymentOrderNo}</td>
			<td>${order.partnerId}</td>
			<td>${order.partnerRefundOrderId }</td>
			<td>${order.rate}</td>
			<td> <fmt:formatNumber type="number"   value="${order.refundAmount/1000}" /> </td>
			<td>${order.currencyCode }</td>
			<td> <fmt:formatNumber type="number"   value="${order.transferAmount/1000}" /> </td>
			<td>
				<c:if test="${order.refundType == '1' }">全额退款</c:if>
				<c:if test="${order.refundType == '2' }">部分退款</c:if>
				<c:if test="${order.refundType == '3' }">比例退款</c:if>
			</td>
			<td>
				<!--0：冻结1：正常2：待审核3：审核未通过4：已删除  --> 
				<c:if test="${order.status=='0'}">
					进行中
				</c:if>
				<c:if test="${order.status=='1'}">
					退款中
				</c:if>
				<c:if test="${order.status=='2'}">
					成功
				</c:if>
				<c:if test="${order.status=='3'}">
					失败
				</c:if>
				<c:if test="${order.status=='4'}">
					机构退款超时
				</c:if>
				<c:if test="${order.status=='5'}">
					人工处理
				</c:if>
				<c:if test="${order.status=='6'}">
					退款失败
				</c:if>
			</td>
			<td>
				<c:if test="${order.reconciliationFlg=='0'}">
					未对账
				</c:if>
				<c:if test="${order.reconciliationFlg=='1'}">
					已对账
				</c:if>
				<c:if test="${order.reconciliationFlg=='2'}">
					对账失败
				</c:if>
				<c:if test="${order.reconciliationFlg=='4'}">
					调单
				</c:if>
			</td>
			<td><date:date value="${order.createDate}"/></td>
			<td><date:date value="${order.complateDate}"/></td>
			<td>${order.refundOrgCode}</td>
		</tr>
		</c:forEach>
		
	</tbody>
</table>
<li:pagination methodName="recordList" pageBean="${page}" sytleName="black2"/>