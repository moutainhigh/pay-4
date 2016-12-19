<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>反洗钱报文文件列表</title>
		<script type="text/javascript">
			function download(id){
				location.href="${ctx}/context_fundout_antimoney.controller.htm?method=downloadMessageXml&id="+id;
			}
		</script>
	</head>
	
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>序号</th>
					<th>文件名</th>
					<th>报文分类</th>
					<th>报文类型</th>
					<th>当日批次号</th>
					<th>文件在批次中的编号</th>
					<th>报送日期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="message" items="${page.result}" varStatus="v">
					<tr>
						<td>${(page.pageNo-1)*page.pageSize+(v.index+1)}</td>
						<td>${message.name}</td>
						<td>
							<c:if test="${message.category == 0}">可疑报告</c:if>
							<c:if test="${message.category == 1}">补充报告</c:if>
						</td>
						<td>
							<c:if test="${message.type == 1}">普通报文</c:if>
							<c:if test="${message.type == 2}">纠错报文</c:if>
							<c:if test="${message.type == 3}">重发报文</c:if>
							<c:if test="${message.type == 4}">补报报文</c:if>
						</td>
						<td>${message.batchNo}</td>
						<td>${message.seqNo}</td>
						<td>${message.submitDate}</td>
						<td>
							<input type="button" value="下载" style="width: 80px;" onclick="download('${message.id}')">
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>