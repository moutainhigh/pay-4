<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>渠道成本费率列表</title>
		<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
	</head>

	<body>
		<table id="tabMerchantConfig" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>会员号</th>
					<th>通道名称</th>
					<th>业务类型</th>
					<th>通道别名</th>
					<th>渠道类别</th>
					<th>所属渠道</th>
					<th>状态</th>
					<th>类别</th>
					<th>优先级</th>
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
						<td>${item.memberCode}</td>
						<td>
							${item.name}
						</td>
						<td>
							<c:if test="${item.paymentType == '1'}">充值</c:if>
							<c:if test="${item.paymentType == '2'}">支付</c:if>
							<c:if test="${item.paymentType == '3'}">直连</c:if>
							<c:if test="${item.paymentType == '4'}">预授权</c:if>
						</td>
						<td>
							${item.alias}
							<input type="hidden" id="${item.paymentChannelItemConfigId}ChannelPriority" value="${item.channelPriority}"/>
						</td>
						<td>
							${item.paymentCategoryCode}
						</td>
						<td>
							${item.paymentChannelCode}
						</td>
						<td>
							<c:if test="${item.status == '1'}">启用</c:if>
							<c:if test="${item.status == '0'}">禁用</c:if>
						</td>
						<td>
							<c:if test="${item.type == '1'}">默认配置</c:if>
							<c:if test="${item.type == '2'}">商户订制</c:if>
						</td>
						<td>
						          ${item.channelPriority}
						</td>
						
						<td>
							<c:if test="${ !empty item.memberCode }">
							<a href="javascript:delconfig('${item.id}','${item.memberCode}','${item.paymentType}');">删除</a>
							&nbsp;<a href="javascript:setPriority(${v['index']},${item.paymentChannelItemConfigId});">优先级</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
	</body>
</html>