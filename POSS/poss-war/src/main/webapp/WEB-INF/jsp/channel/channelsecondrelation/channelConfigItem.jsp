<c:forEach var="item" items="${itemConfigs}" varStatus="v">
	<option value="${item.orgMerchantCode}">${item.orgMerchantCode}-${item.transType}</option>
</c:forEach>