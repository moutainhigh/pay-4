<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>历史日利率列表</title>
	</head>

	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center" style="float:center;width:80%;text-align: center;margin-left:100px">
			<thead>
				<tr>
					<th>序列号</th>
					<th>历史日利率</th>
					<th>新日利率</th>
					<th>更新日期</th>
					<th>操作人</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${historyList}" varStatus="v">
					<tr>
						<td>${item.rateLogId}</td>
						<td>${item.oldDayRate}</td>
						<td>${item.newDayRate}</td>
						<td>${item.updateTime}</td>
						<td>${item.operator}</td>
					</tr>
				</c:forEach>
			<tr>
<%-- 				<td colspan="14" align="center"><li:pagination methodName="list" pageBean="${page}" sytleName="black2" /></td> --%>
			</tr>
		</tbody>
		</table>	
	</body>
</html>