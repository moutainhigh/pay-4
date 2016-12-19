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
					<th>序号</th>
					<th>批次名称</th>
					<th>出款渠道</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="dto" items="${page.result}" varStatus="v">
					<tr>
						<td>${(page.pageNo-1)*page.pageSize+(v.index+1)}</td>
						<td>${dto.ruleConfigDesc}</td>
						<td>${dto.channelDesc}</td>
						<td>
							<a href="javascript:void(0)" onclick="deleteBusi('${dto.seqKy}')">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>