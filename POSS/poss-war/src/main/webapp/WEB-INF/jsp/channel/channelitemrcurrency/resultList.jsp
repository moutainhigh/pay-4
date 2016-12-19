<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>渠道支持币种列表</title>
	</head>
	
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>序号</th>
					<th>渠道编码</th>
					<th>渠道名称</th>
					<th>支持币种</th>
					<th>创建时间</th>
					<th>操作员</th>
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
						<td>${item.relateId}</td>
						<td>${item.channelItemCode}</td>
						<td>${item.channelItemName}</td>
						<td>${item.currencyCode}</td>
						<td><date:date   value="${item.createDate}"/></td>
						<td>
							${item.createOperator}
						</td>
						<td>
							<a href="javascript:deleteChannelItemCurrency('${item.relateId}')">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>