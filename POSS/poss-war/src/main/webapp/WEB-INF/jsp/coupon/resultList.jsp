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
					<th>发行机构</th>
					<th>优惠券号码</th>
					<th>优惠面值</th>
					<th>生效时间</th>
					<th>失效时间</th>
					<th>最低使用金额</th>
					<th>状态</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${list}" varStatus="v">
					<tr>
						<td>${item.id}</td>
						<td>${item.orgCode}</td>
						<td>${item.couponNumber}</td>
						<td>${item.value}</td>
						<td><date:date value="${item.effectDate}"/></td>
						<td><date:date value="${item.expireDate}"/></td>
						<td>${item.minOrderAmount}</td>
						<td>
							<c:if test="${item.status == '1'}">已使用</c:if>
							<c:if test="${item.status == '0'}">未使用</c:if>
						</td>
						<td>
							<date:date value="${item.createDate}"/>
						</td>
						<td>
							<a href="${ctx}/couponList.do?method=initUpdate&id=${item.id}">修改</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
	</body>
</html>