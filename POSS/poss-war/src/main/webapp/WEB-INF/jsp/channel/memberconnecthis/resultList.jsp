<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>渠道路由优先级配置</title>
	</head>
	
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>会员号</th>
					<th>通道名称</th>
					<th>二级商户号</th>
					<th>卡组织</th>
					<th>触发阈值项</th>
					<th>添加时间</th>
					<th>删除时间</th>
					<th>使用时间（天）</th>
					<th>交易笔数</th>
					<th>交易金额（CNY）</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${result}" varStatus="v">
					<c:choose>
	       <c:when test="${v.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
						<td>${item.partnerId}</td>
						<td><c:forEach var="channel" items="${channelItems}">
							<c:if test="${item.paymentChannelItemId==channel.id}">
								${channel.name}
							</c:if>
						</c:forEach></td>
						<td>${item.orgMerchantCode}</td>
						<td>${item.cardOrg}</td>
						<td><c:if test="${item.deleteReason == '1'}">交易笔数</c:if>
							<c:if test="${item.deleteReason == '2'}">交易金额</c:if>
							<c:if test="${item.deleteReason == '3'}">使用天数</c:if>
							<c:if test="${item.deleteReason == '4'}">隔月切换</c:if>
							<c:if test="${item.deleteReason == '5'}">手动删除</c:if>
							<c:if test="${item.deleteReason == '6'}">手动删除</c:if></td>
						<td><date:date   value="${item.connectTime}"/></td>
						<td><date:date   value="${item.deleteDate}"/></td>
						<td>${item.days}</td>
						<td>${item.countTimes}</td>
						<td>${item.countAmount}</td>
					</tr>
				</c:forEach>
			</tbody>
			
		</table>
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>