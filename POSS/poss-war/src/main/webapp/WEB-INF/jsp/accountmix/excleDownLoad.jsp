<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta charset="utf-8">
<title>科目余额明细下载</title>
</head>
<body>
	<table class="tablesorter" border="1" cellpadding="0" cellspacing="1">
		<thead>
		<tr><font color="red";size="6";border="1">------------科目余额明细下载------------</font></tr>
		<tr><font color="red";siez="3">科目余额下载时间<%=(new java.util.Date()).toLocaleString()%></font></tr>
			<tr class="tabT">
				<th class="tabTitle" scope="col">科目账户</th>
				<th class="tabTitle" scope="col">关联交易号</th>
				<th class="tabTitle" scope="col">金额(元)</th>
				<th class="tabTitle" scope="col">借贷</th>
				<th class="tabTitle" scope="col">记账时间</th>
			</tr>
		</thead>

		<c:forEach items="${list}" var="entry">
			<tr>
				<td>&nbsp;${entry.acctCode}</td>
				<td>&nbsp;${entry.relatedOrderId}</td>
				<td>&nbsp;${entry.value}</td>
				<td>&nbsp; <c:if test='${entry.crdr == "1"}'> 借</c:if> <c:if
						test='${entry.crdr == "2"}'> 贷</c:if>
				</td>
				<td>&nbsp;<fmt:formatDate value="${entry.postDate}" type="both" />
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>