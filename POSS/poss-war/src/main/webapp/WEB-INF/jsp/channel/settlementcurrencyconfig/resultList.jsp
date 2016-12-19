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
					<th>序号</th>
					<th>会员号</th>
					<th>交易类型</th>
					<th>交易币种</th>
					<th>支付币种</th>
					<th>结算币种</th>
					<th>优先级</th>
					<th>备注</th>
					<th>操作员</th>
					<th>操作时间</th>
					<th>操作</th>
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
						<td>${item.configId}</td>
						<td>${item.memberCode}</td>
						<td>${item.payType}</td>
						<td>${item.tradeCurrencyCode}</td>
						<td>${item.payCurrencyCode}</td>
						<td>${item.settlementCurrencyCode}</td>
						<td>${item.grade}</td>
						<td>${item.mark}</td>
						<td>${item.operator}</td>
						<td><date:date   value="${item.updateTime}"/></td>
						<td>
							<c:if test="${item.configId != '0'}"><a href="javascript:deleteConfig('${item.configId}')">删除</a></c:if>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:editConfig('${item.configId}','${item.memberCode}','${item.payType}','${item.tradeCurrencyCode}'
							,'${item.payCurrencyCode}','${item.settlementCurrencyCode}','${item.mark}','${item.grade}')">修改</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>