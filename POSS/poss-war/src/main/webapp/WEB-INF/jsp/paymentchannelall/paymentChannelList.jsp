<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>商户号</th>
					<th>商户名称</th>
					<th>充值渠道</th>
					<th>支付渠道</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="channel" items="${page.result}" varStatus="v">
					<tr>
						<td>
							${channel.memberCode}
						</td>
						<td>
							${channel.loginName}
						</td>
						<td>
							<a href="${ctx }/paymentchannelconfig.htm?method=getChannelList&memberCode=${channel.memberCode}&type=1">
							${channel.deposit}
							
							</a>
							
						</td>
						<td>
							<a href="${ctx }/paymentchannelconfig.htm?method=getChannelList&memberCode=${channel.memberCode}&type=2">
							${channel.cust}
							
							</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>