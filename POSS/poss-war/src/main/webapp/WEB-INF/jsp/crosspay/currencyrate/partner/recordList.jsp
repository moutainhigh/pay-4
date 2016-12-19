<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<form action="" method="post" name="listFrom">
</form>

<table   id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
	<thead>
		<tr>
			<th class="item15">商户会员号</th>
			<th class="item15">货币代码</th>
			<th class="item15">货币名称</th>
			<th class="item10">交易单位</th>
			<th class="item10">汇率(100人民币兑换外币)</th>
			<th class="item10">汇率(100外币兑换人民币)</th>
			<th class="item10">目标币种</th>
			<th class="item10">目标币种名称</th>
			<th class="item10">生效时间</th>
			<th class="item10">失效时间</th>
			<th class="item10">创建时间</th>
			<th class="item30">操作人</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${rateList}" var="rate" varStatus="index">
		<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
			<td><span></span>${rate.partnerId}<span></span></td>
			<td><span></span>${rate.currency}<span></span></td>
			<td>
				<c:forEach var="currency" items="${currencys}" varStatus="v">
					<c:if test="${currency.code == rate.currency}">${currency.desc}</c:if>
				</c:forEach>
			</td>
			<td>
				${rate.currencyUnit }				
			</td>
			<td>
				${rate.exchangeRate }				
			</td>
			<td>
				${rate.reverseExchangeRate }				
			</td>
			<td><span></span>${rate.targetCurrency}<span></span></td>
			<td>
				<c:forEach var="currency" items="${currencys}" varStatus="v">
					<c:if test="${currency.code == rate.targetCurrency}">${currency.desc}</c:if>
				</c:forEach>
			</td>
			<td><date:date value="${rate.effectDate}"/></td>
			<td><date:date value="${rate.expireDate}"/></td>
			<td><date:date value="${rate.createDate}"/></td>
			<td align="left">${rate.operator}</td>
			
		</tr>
		</c:forEach>
	</tbody>
</table>

 <script type="text/javascript">
 
	
</script>