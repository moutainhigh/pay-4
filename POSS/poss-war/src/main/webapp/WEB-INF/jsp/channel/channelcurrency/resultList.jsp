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
					<th>会员号</th>
					<th>渠道编码</th>
					<th>渠道名称</th>
					<th>交易类型</th>
					<th>交易币种</th>
					<th>卡本币</th>
					<th>送渠道币种</th>
					<th>创建时间</th>
					<th>更新时间</th>
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
						<td>${item.id}</td>
						<td>${item.partnerId}</td>
						<td>
							<c:forEach var="channel" items="${channelItems}">
								<c:if test="${item.orgCode==channel.code}">
									${channel.code}
								</c:if>
							</c:forEach>
						</td>
						<td>
							<c:forEach var="channel" items="${channelItems}">
								<c:if test="${item.orgCode==channel.code}">
									${channel.desc}
								</c:if>
							</c:forEach>
						</td>
						<td>${item.payType}</td>
						<td>${item.currencyCode}</td>
						<td>${item.cardCurrencyCode}</td>
						<td>
							${item.channelCurrencyCode}
						</td>
						<td>	<date:date   value="${item.createDate}"/></td>
						<td>	<date:date   value="${item.updateDate}"/></td>
						<td>${item.operator}</td>
						<td>
							<c:if test="${item.status == '0' }">
								<c:if test="${item.flag == '3' }">
							    		&nbsp;	----
								</c:if>
							</c:if>
								<c:if test="${item.flag != '3' }">
									<c:if test="${item.status != '0'}">
										<a href="javascript:deleteConfig('${item.id}')">删除</a>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<a href="javascript:editConfig('${item.id}','${item.partnerId}','${item.payType}','${item.orgCode}'
									,'${item.currencyCode}','${item.cardCurrencyCode}','${item.channelCurrencyCode}')">修改</a></c:if>

								</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>