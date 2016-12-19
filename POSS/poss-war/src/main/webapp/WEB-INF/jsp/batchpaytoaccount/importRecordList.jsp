<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>批量付款详情</title>
	</head>
	
	<body>
		<div style="text-align: left;position: absolute;left:50px;top:50px;width: 60%;">
			<span style="font-size:15px;float: left;width :50%">批量付款详情</span>
			<span style="float: right;width :50%">
				<a href="${ctx}/downloadFileController.do?method=downloadImportRecordList&fileKy=${importFile.sequenceId}">导出EXCEL</a>
			</span>
			<hr color="gray" noshade="noshade">
			<h5>复核请求信息</h5>
			
			<div style="width: 90%;border: 1px black solid;">
				<table cellpadding="5">
					<tr>
						<td style="width: 50%">业务参考号：${importFile.batchNum}</td>
						<td style="width: 50%">时间：${importFile.uploadTime}</td>
					</tr>
					<tr>
						<td>状态：${importFile.totalCount}笔</td>
						<td>复核员：${importFile.orderCount}笔</td>
					</tr>
					<tr>
						<td colspan="2">备注（原因）：${importFile.totalCount}笔</td>
					</tr>
				</table>
			</div>
			<br>
			核对错误笔数：${importFile.errorCount}<br><br>
			<table border="1" cellpadding="3" cellspacing="0">
				<tr>
					<th>姓名</th>
					<th>收款账户</th>
					<th>金额</th>
					<th>备注</th>
					<th>无法付款原因</th>
				</tr>
				<c:forEach var="record" items="${resultList}">
					<c:if test="${fn:length(record.errorTips)>0}">
						<tr>
							<td>${record.payeeName}</td>
							<td>${record.payeeMemberName}</td>
							<td>${record.amountString}</td>
							<td>${record.remark}</td>
							<td>${record.errorTips}</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
			<br>
			<span style="font-size:medium">错误总金额：</span>
			<span style="color: red;font-size:medium"><fmt:formatNumber value="${importFile.errorMoney}" pattern="0.00"/></span>
			<span style="font-size:medium">元</span>
		</div>
	</body>
</html>