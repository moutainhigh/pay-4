<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>黑名单文件列表</title>
		<script type="text/javascript">
			function download(id){
				location.href="${ctx}/context_fundout_foblacklistfile.controller.htm?method=downloadBlackListFile&id="+id;
				search();
			}
		</script>
	</head>
	
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>序号</th>
					<th>文件名</th>
					<th>当前记录条目数</th>
					<th>最大记录条目数</th>
					<th>下载状态</th>
					<th>最后更新时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="file" items="${page.result}" varStatus="v">
					<tr>
						<td>${(page.pageNo-1)*page.pageSize+(v.index+1)}</td>
						<td>${file.name}</td>
						<td>${file.currentLength}</td>
						<td>${file.maxLength}</td>
						<td>
							<c:if test="${file.downloadStatus == 0}">未下载</c:if>
							<c:if test="${file.downloadStatus == 1}">已下载</c:if>
						</td>
						<td>
						<fmt:formatDate value="${file.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<input type="button" value="下载" style="width: 80px;" onclick="download('${file.id}')">
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>