<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>渠道成本费率列表</title>
	</head>
	
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>序号</th>
					<th>通道名称</th>
					<th>收款类型</th>
					<th>会员类型</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="channel" items="${page.result}" varStatus="v">
					<tr>
						<td>${v.index+1 }</td>
						<td>
							${channel.channelItemName}
						</td>
						<td>
							<c:if test="${channel.paymentType == '1'}">充值</c:if>
							<c:if test="${channel.paymentType == '2'}">支付</c:if>
							<c:if test="${channel.paymentType == '3'}">直连</c:if>
						</td>
						<td>
							<c:if test="${channel.memberType == '2'}">企业</c:if>
							<c:if test="${channel.memberType == '1'}">个人</c:if>
						</td>
						<td>
							<a href="${ctx}/defaultChannelItem.htm?method=initUpdate&id=${channel.id}">修改</a>
							<a href="${ctx}/defaultChannelItem.htm?method=remove&id=${channel.id}">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="querysearch" pageBean="${page}" sytleName="black2"/>
	</body>
</html>