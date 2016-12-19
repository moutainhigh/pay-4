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
					<th>通道名称</th>
					<th>二级商户号</th>
					<th>操作员</th>
					<th>添加时间</th>
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
							${item.memberCode}
						</td>
						<td>
							<c:forEach var="channel" items="${channelItems}">
								<c:if test="${item.paymentChannelItemId==channel.id}">
									<input type="hidden" name="paymentCategoryCode"  id="paymentCategoryCode" value="${channel.paymentCategoryCode}">
										${channel.name}
								</c:if>
							</c:forEach>
						</td>
						<td>
							${item.orgMerchantCode}
						</td>
						<td>
							${item.operator}
						</td>
						<td>
                          <date:date   value="${item.createDate}"/>
						</td>
						<td>
							<a href="javascript:del(${item.id},'${item.orgMerchantCode}',${item.orgCode})">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
	</body>
</html>