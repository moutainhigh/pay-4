<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>日历列表</title>
		<script type="text/javascript">
			function modify(id){
				location.href="${ctx}/fundoutChannel.htm?method=updatePage&channelId="+id;
			}

			function modify(date, status) {
				var pars = "&cdateTbsdy=" + date + "&cdateHolidy=" + status;
				$.ajax({
					type: "POST",
					url: "${ctx}/fundout_calendar.htm?method=update",
					data: pars,
					success: function(result) {
						if(result == "1") {
							alert("变更完成");
							$("#btn").click();
						}else {
							alert("变更失败");
						}
					}
				});		
			}
		</script>
	</head>
	
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th align="center">日期</th>
					<th align="center">周几</th>
					<th align="center">假日</th>
					<th align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="calendar" items="${page.result}">
					<tr>
						<td align="center">
							<fmt:formatDate value="${calendar.cdateTbsdy}" pattern="yyyy-MM-dd" />
						</td>
						<td align="center">
							<c:choose>
								<c:when test="${calendar.cdateWeekdy == '1'}">一</c:when>
								<c:when test="${calendar.cdateWeekdy == '2'}">二</c:when>
								<c:when test="${calendar.cdateWeekdy == '3'}">三</c:when>
								<c:when test="${calendar.cdateWeekdy == '4'}">四</c:when>
								<c:when test="${calendar.cdateWeekdy == '5'}">五</c:when>
								<c:when test="${calendar.cdateWeekdy == '6'}">六</c:when>
								<c:otherwise>七</c:otherwise>
							</c:choose>
						</td>
						<td align="center">
							<c:if test="${calendar.cdateHolidy == '0'}">否</c:if>
							<c:if test="${calendar.cdateHolidy == '1'}">是</c:if>
						</td>
						<td align="center">
							<c:if test="${calendar.cdateHolidy == '0'}">
								<input type="button" value="变更为假日" style="width: 80px;" onclick="modify('<fmt:formatDate value="${calendar.cdateTbsdy}" pattern="yyyy-MM-dd" />', '1')">
							</c:if>
							<c:if test="${calendar.cdateHolidy == '1'}">
								<input type="button" value="变更为非假日" style="width: 80px;" onclick="modify('<fmt:formatDate value="${calendar.cdateTbsdy}" pattern="yyyy-MM-dd" />', '0')">
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>