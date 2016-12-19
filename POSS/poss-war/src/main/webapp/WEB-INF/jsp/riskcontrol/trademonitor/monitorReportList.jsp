<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">

function dataQuery(pageNo,totalCount){
  	var startDate = document.getElementById("startDate").value;
  	var businessType = document.getElementById("businessType").value;
  	var pars =  "&startDate=" + startDate + "&businessType=" + businessType +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;;
	$.ajax({
		type: "POST",
		url: "${ctx}/orderrisk/orderriskmonitor.do?method=query",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#paginationResult').html(result);
		}
	});	       
}
</script>
<body>
	<c:if test="${ empty resList}">
		<c:if test="${ empty paraMap}">
			无数据
		</c:if>
		<c:if test="${!empty paraMap.monitorType}">
			选定的规则下无数据
		</c:if>
		<c:if test="${!empty paraMap.monitorDate}">
			选定的日期下无数据
		</c:if>
		<c:if test="${!empty paraMap.monitorType}">
			<c:if test="${!empty paraMap.monitorDate}">
			选定的日期+规则下无数据
			</c:if>
		</c:if>
	</c:if>	
	<c:if test="${! empty resList}">
	<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
		<thead><tr>
			<th class="" align="center">网关订单号</th>
			<th class="" align="center">触碰规则类型</th>
			<th class="" align="center">会员号</th>
			<th class="" align="center">商户名称</th>
			<th class="" align="center">商户类型</th>
			<th class="" align="center">商户订单号</th>
			<th class="" align="center">交易金额</th>
			<th class="" align="center">交易币种</th>
			<th class="" align="center">是否退款</th>
			<th class="" align="center">创建时间</th>
			<th class="" align="center">交易完成时间</th>
			<th class="" align="center">渠道支付结果</th>
			<th class="" align="center">渠道返回原因</th>
			<th class="" align="center">交易网址</th>
			<th class="" align="center">商品名称</th>
			<th class="" align="center">下单IP地址</th>
			<th class="" align="center">卡号</th>
			<th class="" align="center">发卡国家</th>
			<th class="" align="center">账单姓名</th>
			<th class="" align="center">账单国家</th>
			<th class="" align="center">账单州</th>
			<th class="" align="center">账单城市</th>
			<th class="" align="center">账单街道</th>
			<th class="" align="center">账单地址</th>
			<th class="" align="center">账单邮编</th>
			<th class="" align="center">账单邮箱</th>
			<th class="" align="center">账单电话</th>
			<th class="" align="center">收货姓名</th>
			<th class="" align="center">收货国家</th>
			<th class="" align="center">收货州</th>
			<th class="" align="center">收货城市</th>
			<th class="" align="center">收货街道</th>
			<th class="" align="center">收货地址</th>
			<th class="" align="center">收货邮编</th>
			<th class="" align="center">收货邮箱</th>
			<th class="" align="center">收货电话</th>
		</tr>
		</thead>
			<c:forEach items="${resList}" var="c" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td class="" align="center">${c.tradeOrderNo}</td>
				<td class=""align="center">${c.monitorType}
				<c:if test="${c.monitorType == '1'}">
					时段内失败交易数超限
				</c:if>
				<c:if test="${c.monitorType == '2'}">
					同一Email累计交易次数超限
				</c:if>
				<c:if test="${c.monitorType == '3'}">
					账单地址不符
				</c:if>
				<c:if test="${c.monitorType == '4'}">
					账单国与收货国不符
				</c:if>
				<c:if test="${c.monitorType == '5'}">
					单笔交易金额超限
				</c:if>
				</td>
				<td class="" align="center">${c.partnerId}</td>
				<td class="" align="center">${c.merchantName}</td>
				<td class="" align="center">${c.merchantId}</td>
				<td class="" align="center">${c.orderId}</td>
				<td class="" align="center">${c.orderAmount }</td>
				<td class="" align="center">${c.currencyCode}</td>
				<td class="" align="center">
					<c:if test="${c.tradeStatus == '3'}">
						是
					</c:if>
					<c:if test="${c.tradeStatus != '3'}">
						否
					</c:if>
				</td>
				<td class="" align="center">${c.monitorDate}</td>
				<td class="" align="center">${c.completeDate}</td>
				<td class="" align="center">${c.channelRespCode}</td>
				<td class="" align="center">${c.channelRespMsg}</td>
				<td class="" align="center">${c.siteId}</td>
				<td class="" align="center">${c.goodsName}</td>
				<td class="" align="center">${c.coustomerIp}</td>
				<td class="" align="center">${c.cardId}</td>
				<td class="" align="center">${c.cardCountry}</td>
				<td class="" align="center">${c.billName}</td>
				<td class="" align="center">${c.billCountry}</td>
				<td class="" align="center">${c.billState}</td>
				<td class="" align="center">${c.billCity}</td>
				<td class="" align="center">${c.billStreet}</td>
				<td class="" align="center">${c.billAddress}</td>
				<td class="" align="center">${c.billPostalCode}</td>
				<td class="" align="center">${c.billEmail}</td>
				<td class="" align="center">${c.billPhone}</td>
				<td class="" align="center">${c.shippingName}</td>
				<td class="" align="center">${c.shippingCountry}</td>
				<td class="" align="center">${c.shippingState}</td>
				<td class="" align="center">${c.shippingCity}</td>
				<td class="" align="center">${c.shippingStreet}</td>
				<td class="" align="center">${c.shippingAddress}</td>
				<td class="" align="center">${c.shippingPostalCode}</td>
				<td class="" align="center">${c.shippingEmail}</td>
				<td class="" align="center">${c.shippingPhone}</td>
		</tr>
		</c:forEach>
		</table>
	</c:if>	
	<li:pagination methodName="dataQuery" pageBean="${page}" sytleName="black2"/>
</body>
