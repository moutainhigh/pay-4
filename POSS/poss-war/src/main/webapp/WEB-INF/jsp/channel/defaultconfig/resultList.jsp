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
					<th>会员类型</th>
					<th>通道名称</th>
					<th>业务类型</th>
					<th>通道别名</th>
					<th>渠道类别</th>
					<th>所属渠道</th>
					<th>通道费率</th>
					<th>状态</th>
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
						<td>
							<c:if test="${item.memberType == '1'}">个人</c:if>
							<c:if test="${item.memberType == '2'}">企业</c:if>
						</td>
						<td>
							${item.name}
						</td>
						<td>
							<c:if test="${item.paymentType == '1'}">充值</c:if>
							<c:if test="${item.paymentType == '2'}">支付</c:if>
							<c:if test="${item.paymentType == '3'}">直连</c:if>
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
							<a href="javascript:delconfig('${item.id}','${item.memberCode}','${item.paymentType}');">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
	</body>
</html>