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
					<th>渠道code</th>
					<th>渠道名称</th>
					<th>渠道说明</th>
					<th>所属分类</th>
					<th>费率</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="channel" items="${page.result}" varStatus="v">
					<tr>
						<td>${v.index+1 }</td>
						<td>
							${channel.code}
						</td>
						<td>
							${channel.name}
						</td>
						<td>
							${channel.description}
						</td>
						<td>
							${channel.categoryDescription}
						</td>
						<td>
							${channel.rate}
						</td>
						<td>
							<c:if test="${channel.status == '1'}">启用</c:if>
							<c:if test="${channel.status == '0'}">禁用</c:if>
						</td>
						<td>
							<a href="${ctx}/paymentchannelconfig.htm?method=initUpdate&id=${channel.id}">修改</a>
							<a href="${ctx}/paymentchannelconfig.htm?method=remove&id=${channel.id}">删除</a>
							<a href="${ctx}/paymentChannelItemNew.htm?method=setItem&paymentChannelNewId=${channel.id}">通道设置</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="querysearch" pageBean="${page}" sytleName="black2"/>
	</body>
</html>