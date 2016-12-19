<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>购结汇流水号</th>
					<th>会员号</th>
					<th>会员名称</th>
					<th>购结汇时间</th>
					<th>购结汇账户</th>
					<th>交易类型</th>
					<th>卖出金额</th>
					<th>汇率</th>
					<th>买入金额</th>
					<th>手续费</th>
					<th>交易状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="res" items="${resultList}" varStatus="v">
					<c:choose>
	       <c:when test="${v.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
						<td>
								${res.exchangeNo}
						</td>
						<td>${res.partnerId}</td>
						<td>
								${res.partnerName}
						</td>
						<td>
							<label class="createDate">${res.createDate}</label>
						</td>
						<td>
							<c:choose>
							<c:when test="${res.exchCurrencyCode=='CNY'}">人民币账户</c:when>
							<c:when test="${res.exchCurrencyCode=='USD'}">美元账户</c:when>
							<c:when test="${res.exchCurrencyCode=='EUR'}">欧元账户</c:when>
							<c:when test="${res.exchCurrencyCode=='GBP'}">英镑账户</c:when>
							<c:when test="${res.exchCurrencyCode=='HKD'}">港元账户</c:when>
							<c:when test="${res.exchCurrencyCode=='AUD'}">澳元账户</c:when>
							<c:when test="${res.exchCurrencyCode=='CAD'}">加元账户</c:when>
							<c:when test="${res.exchCurrencyCode=='JPY'}">日元账户</c:when>
							<c:when test="${res.exchCurrencyCode=='SGD'}">新加坡元账户</c:when>
						</c:choose>	
						</td>
						<td>
								<c:if test="${res.type  == '0'}">
								购汇
								</c:if>  
								<c:if test="${res.type  == '1'}">
								结汇
								</c:if> 
						</td>
						<td>
						<fmt:formatNumber type="number" value="${res.exchAmount/1000}" maxFractionDigits="2"/>  ${res.exchCurrencyCode}
						</td>
						<td>
								${res.exchangeRate}
						</td>
						<td>
							<fmt:formatNumber type="number" value="${	res.orderAmount/1000}" maxFractionDigits="2"/> ${res.currencyCode}
						</td>
						<td>
							<fmt:formatNumber type="number" value="${	res.tradeFee/1000}" maxFractionDigits="2"/>  ${res.exchCurrencyCode}
						</td>
						<td>
							   <c:if test="${res.status == '0'}">
									   交易中
							   </c:if>	
							   <c:if test="${res.status == '1'}">
									  交易成功
							   </c:if>	
							   <c:if test="${res.status == '2'}">
									   交易失败
							   </c:if>	
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>
<script type="text/javascript">
	function query(pageNo, totalCount){
		var pars = $("#mainfrom").serialize() + "&pageNo=" + pageNo
		+ "&totalCount=" + totalCount;
		$.ajax({
			type : "POST",
			url : "./buySettleOrderQuery.do?method=list",
			data : pars,
			success : function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
</script>
