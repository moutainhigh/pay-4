<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>批量付款记录</title>
	</head>
	
	<body>
		<div style="text-align: left;position: absolute;left:50px;top:50px;width: 60%;">
			<span style="font-size:15px;float: left;width :50%">批量付款记录</span>
			<span style="float: right;width :50%">
				<a href="${ctx}/downloadFileController.do?method=downloadImportFileList">导出EXCEL</a>
			</span>
			<hr color="gray" noshade="noshade">
			<form action="" method="post">
				时间：
				<input type="text" name="startTime1" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',vel:'startTime'})" class="Wdate"/>--
				<input type="text" name="endTime1" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss',vel:'endTime'})" class="Wdate"/>
				<input type="hidden" name="startTime" id="startTime">
				<input type="hidden" name="endTime" id="endTime">
				业务参考编号：
				<input type="text" name="batchNum"/>
				<input type="submit" value="查  询"/>
			</form>
			<hr color="gray" noshade="noshade">
			<br>
			<table border="1" cellpadding="5" cellspacing="0" style="width: 100%">
				<tr>
					<th>时间</th>
					<th>业务参考号</th>
					<th>账户类型</th>
					<th>付款笔数</th>
					<th>总金额（元）</th>
					<th>详情</th>
				</tr>
				<c:forEach var="importFile" items="${importFileList}">
					<tr>
						<td><fmt:formatDate value="${importFile.uploadTime}" type="date" pattern="yyyy/MM/dd:HH:mm:ss"/></td>
						<td>${importFile.batchNum}</td>
						<td>${importFile.payerAcctType}</td>
						<td>${importFile.totalCount}</td>
						<td>${importFile.totalAmount/100.0}</td>
						<td>
							<a href="${ctx}/batchPayToAccountController.do?method=importRecordList&fileKy=${importFile.sequenceId}">详情</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>