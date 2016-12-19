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
					<th>
						序号
					</th>
					<th>渠道名称</th>
					<th>机构商户号</th>
					<th>终端号</th>
					<th>授权码</th>
					<th>交易类型</th>
					<th>币种</th>
					<th>MCC</th>
					<th>机构密钥</th>
					<th>状态</th>
					<th>创建时间</th>
					<th>模式</th>
					<th>申请商户名称</th>
					<th>商户账单名</th>
					<th>映射网址</th>
					<th>商户类型</th>
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

						<td>${item.id}
						</td>
						<td>
							<c:forEach var="channel" items="${channelItems}">
								<c:if test="${item.orgCode==channel.code}">
									${channel.desc}
								</c:if>
							</c:forEach>
						</td>
						<td>${item.orgMerchantCode}</td>
						<td>${item.terminalCode}</td>
						<td>${item.accessCode}</td>
						<td>
							${item.transType}
						</td>
						<td>${item.currencyCode }</td>
						<td>${item.mcc }</td>
						<td>${item.orgKey}</td>
						<td>
							<c:if test="${item.status == '1'}">启用</c:if>
							<c:if test="${item.status == '0'}">禁用</c:if>
						</td>
						<td>
							<date:date   value="${item.createDate}"/>
						</td>
						<td>
							<c:if test="${item.pattern=='A'}">全部</c:if>
							<c:if test="${item.pattern== 'M'}">MOTO</c:if>
							<c:if test="${item.pattern== 'E'}">3D</c:if>
						</td>
						<td>${item.requestMerchantName}</td>
						<td>${item.merchantBillName}</td>
						<td>${item.supportWebsite}</td>
						<td>
							<c:if test="${item.fitMerchantType== '500'}">iPayLinks</c:if>
							<c:if test="${item.fitMerchantType== '800'}">GCPayment</c:if>
						</td>
						<td>
							<a href="javascript:del('${item.id}')">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
			
		</table>
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>	
	</body>
</html>