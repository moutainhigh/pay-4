<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>
<%@ page import="com.hnapay.fundout.util.AmountUtils"%>

<form action="" method="post" name="listFrom">
</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>交易号</th>
			<th>商户号</th>
			<th>交易时间</th>
			<th>域名</th>
			<th>交易状态</th>
			<th>结算状态</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${page.result}" var="dto" varStatus="index">
			<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td>
					${dto.tradeOrderNo}
				</td>
				<td>
					${dto.partnerId}
				</td>
				<td>
					<fmt:formatDate value="${dto.orderTime}" type="date"/>
				</td>
				<td>
					${dto.partnerId}
				</td>
				<td>
					${dto.url}
				</td>
				<td>
					${dto.status}
				</td>
				<td>
					${dto.clearStatus}
				</td>
				<td>
					<c:if test="${dto.status == '6'}"><a href="${ctx}/crosspay/partnerExchRate.do?method=unFrozenOrder&tradeOrderNo=${dto.tradeOrderNo}">结冻</a></c:if>
					<c:if test="${dto.status == '3'}"><a href="${ctx}/crosspay/partnerExchRate.do?method=viewTradeOrder&tradeOrderNo=${dto.tradeOrderNo}">结冻</a></c:if>
				</td>
			</c:forEach>
	</tbody>
</table>

<li_new:page methodName="search" pageBean="${page}" sytleName="black2"/>
 <script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				7: {sorter: false}
			}
		});
	 }); 
	
</script>