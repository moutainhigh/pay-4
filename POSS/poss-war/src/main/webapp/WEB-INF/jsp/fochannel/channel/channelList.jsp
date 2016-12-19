<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>渠道列表</title>
		<script type="text/javascript">
			function modify(id){
				location.href="${ctx}/fundoutChannel.htm?method=updatePage&channelId="+id;
			}
		</script>
	</head>
	
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th >序号</th>
					<th >出款渠道名称</th>
					<th >出款方式</th>
					<th >出款银行</th>
					<th >状态</th>
					<th >支付平台账号</th>
					<th >支付平台账号名称</th>
					<th >是否支持多笔</th>
					<th >单笔金额上限</th>
					<th >单笔金额下限</th>
					<th >备注</th>
					<th >操作员</th>
					<th >操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="channel" items="${page.result}" varStatus="v">
					<c:choose>
	       <c:when test="${v.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
						<td>${(page.pageNo-1)*page.pageSize+(v.index+1)}</td>
						<td>${channel.channelName}</td>
						<td>${channel.modeName}</td>
						<td>${channel.bankName}</td>
						<td>${channel.statusStr}</td>
						<td align="center">
							<c:if test="${channel.modeCode == 0}">
								${channel.bankChannelConfig.bankAcc}
							</c:if>
							<c:if test="${channel.modeCode == 1}">
								/
							</c:if>
						</td>
						<td align="center">
							<c:if test="${channel.modeCode == 0}">
								${channel.bankChannelConfig.bankAccName}
							</c:if>
							<c:if test="${channel.modeCode == 1}">
								/
							</c:if>
						</td>
						<td align="center">
							<c:if test="${channel.modeCode == 0}">
								<c:if test="${channel.bankChannelConfig.isSupportMultiple == 0}">
									不支持
								</c:if>
								<c:if test="${channel.bankChannelConfig.isSupportMultiple == 1}">
									支持
								</c:if>
							</c:if>
							<c:if test="${channel.modeCode == 1}">
								/
							</c:if>
						</td>
						<td align="center">
							<c:if test="${channel.modeCode == 0}">
								<c:if test="${empty channel.bankChannelConfig.upperLimit}">
									无限制
								</c:if>
								<c:if test="${not empty channel.bankChannelConfig.upperLimit}">
									${channel.bankChannelConfig.upperLimit}
								</c:if>
							</c:if>
							<c:if test="${channel.modeCode == 1}">
								/
							</c:if>
						</td>
						<td align="center">
							<c:if test="${channel.modeCode == 0}">
								<c:if test="${empty channel.bankChannelConfig.lowerLimit}">
									无限制
								</c:if>
								<c:if test="${not empty channel.bankChannelConfig.lowerLimit}">
									${channel.bankChannelConfig.lowerLimit}
								</c:if>
							</c:if>
							<c:if test="${channel.modeCode == 1}">
								/
							</c:if>
						</td>
						<td>${channel.mark}</td>
						<td>${channel.operator}</td>
						<td>
							<input type="button" value="修改" style="width: 80px;" onclick="modify('${channel.channelId}')">
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>