<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>批次规则列表</title>
	</head>
	
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>批次编号</th>
					<th>批次名称</th>
					<th>批次产生时间</th>
					<th>批次生效时间</th>
					<th>批次失效时间</th>
					<th>批次接收人员</th>
					<th>批次最大支持的交易笔数</th>
					<th>详情</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="ruleConfig" items="${page.result}">
					<tr>
						<td>${ruleConfig.sequenceId}</td>
						<td>${ruleConfig.batchRuleDesc}</td>
						<td>${ruleConfig.weekDayList}</td>
						<td>${ruleConfig.effectDateStr}</td>
						<td>${ruleConfig.lostEffectDateStr}</td>
						<td>${ruleConfig.operator}</td>
						<td>${ruleConfig.maxOrderCounts}</td>
						<td>
							<a href="${ctx}/batchRuleConfigController.do?method=queryDetail&sequenceId=${ruleConfig.sequenceId}">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="${ctx}/batchRuleConfigController.do?method=queryDetail&sequenceId=${ruleConfig.sequenceId}&update=u">修改</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>