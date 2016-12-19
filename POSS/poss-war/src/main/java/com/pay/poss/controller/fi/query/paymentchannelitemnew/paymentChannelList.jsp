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
					<th>通道别名</th>
					<th>通道说明</th>
					<th>状态</th>
					<th>排序序号</th>
					<th>机构代码</th>
					<th>通道服务地址</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="channel" items="${page.result}" varStatus="v">
					<tr>
						<td>${v.index+1 }</td>
						<td>
							${channel.itemName}
						</td>
						<td>
							${channel.alias}
						</td>
						<td>
							${channel.description}
						</td>
						<td>
							<c:if test="${channel.status == '1'}">启用</c:if>
							<c:if test="${channel.status == '0'}">禁用</c:if>
						</td>
						<td>
							${channel.serialNo}
						</td>
						<td>
							${channel.orgcode}
						</td>
						<td>
							${channel.serviceUrl}
						</td>
						<td>
							<a href="${ctx}/paymentChannelItemNew.htm?method=initUpdate&id=${channel.id}">修改</a>
							<a href="${ctx}/paymentChannelItemNew.htm?method=remove&id=${channel.id}">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="querysearch" pageBean="${page}" sytleName="black2"/>
	</body>
</html>