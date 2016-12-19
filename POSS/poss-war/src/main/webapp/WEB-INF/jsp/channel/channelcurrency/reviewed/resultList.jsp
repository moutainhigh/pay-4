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
					<th>会员号</th>
					<th>渠道编码</th>
					<th>渠道名称</th>
					<th>交易类型</th>
					<th>交易币种</th>
					<th>卡本币</th>
					<th>送渠道币种</th>
					<th>申请时间</th>
					<th>操作员</th>
					<th>操作类型</th>
					<th>审核状态</th>
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
						<td>${item.operator}</td>
						<td>
						<c:if test="${item.flag=='1'}">
									新增配置
						</c:if>
						<c:if test="${item.flag=='2'}">
									修改配置
						</c:if>
						<c:if test="${item.flag=='3'}">
									删除配置
						</c:if>
						</td>
						<td>      
							<c:if test="${item.status=='0'}">
									待审核
							</c:if>
							<c:if test="${item.status=='1'}">
									审核通过
							</c:if>
							<c:if test="${item.status=='2'}">
									审核拒绝
							</c:if>
					    </td>
					    <td>
					    	<c:if test="${item.status=='0'}">
							<input type="button" value="通过" onclick="reviewed(1,'${item.id}','${item.flag}','${item.channelCurrencyId }');">
									&nbsp;&nbsp;&nbsp;
							<input type="button" value="拒绝" onclick="reviewed(2,'${item.id}','${item.flag}','${item.channelCurrencyId }');">
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
			
		</table>
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>