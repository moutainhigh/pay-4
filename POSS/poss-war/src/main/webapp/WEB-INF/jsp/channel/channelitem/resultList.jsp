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
					<th>通道编码</th>
					<th>通道别名</th>
					<th>渠道类别</th>
					<th>所属渠道</th>
					<th>通道费率</th>
					<th>状态</th>
					<th>排序序号</th>
					<th>机构代码</th>
					<th>商户账单名</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${resultList}" varStatus="v">
					<c:choose>
	       <c:when test="${v.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
						<td>${item.id}</td>
						<td>
							${item.name}
						</td>
						<td>
							${item.code}
						</td>
						<td>
							${item.alias}
						</td>
						<td>
							${item.paymentCategoryCode}
						</td>
						<td>
							${item.paymentChannelCode}
						</td>
						<td>
							${item.rate}
						</td>
						<td>
							<c:if test="${item.status == '1'}">启用</c:if>
							<c:if test="${item.status == '0'}">禁用</c:if>
						</td>
						<td>
							${item.serialNo}
						</td>
						<td>
							${item.orgCode}
						</td>
						<td>
							${item.merchantBillName}
						</td>
						<td>
							<a href="${ctx}/paymentChannelItem.htm?method=initUpdate&code=${item.code}">修改</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
	</body>
</html>