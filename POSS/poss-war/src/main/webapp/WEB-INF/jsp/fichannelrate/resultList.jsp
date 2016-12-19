<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>渠道成本费率列表</title>
		<script type="text/javascript">
			function modify(id){
				location.href="${ctx}/channelrate.htm?method=initUpdate&id="+id;
			}
		</script>
	</head>
	
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>序号</th>
					<th>支付渠道</th>
					<th>渠道项目</th>
					<th>原始码</th>
					<th>费率</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="rate" items="${page.result}" varStatus="v">
					<tr>
						<td>${(page.pageNo-1)*page.pageSize+(v.index+1)}</td>
						<td>
							<c:forEach var="channel" items="${channels}">
								<c:if test="${channel.id == rate.paymentChannelId}">${channel.description}</c:if>
							</c:forEach>
						</td>
						<td>
							<c:forEach var="item" items="${channelItems}">
								<c:if test="${item.id == rate.paymentChannelItemId}">${item.itemName}</c:if>
							</c:forEach>
						</td>
						<td>${rate.orgCode}</td>
						<td>${rate.orgCostRate}</td>
						<td>
							<c:if test="${rate.disableFlag == '1'}">启用</c:if>
							<c:if test="${rate.disableFlag == '0'}">禁用</c:if>
						</td>
						<td>
							<input type="button" value="修改" style="width: 80px;" onclick="modify('${rate.id}')">
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="querysearch" pageBean="${page}" sytleName="black2"/>
	</body>
</html>