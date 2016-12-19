<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>风险审核队列列表</title>
		<script type="text/javascript">
			function deal(recordNo){
				location.href="${ctx}/context_fundout_checklog.controller.htm?method=check&recordNo=" + recordNo;
			}

			function dealed(recordNo){
				location.href="${ctx}/context_fundout_checklog.controller.htm?method=view&recordNo=" + recordNo;
			}
		</script>
	</head>
	
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>序号</th>
					<th>进入日期</th>
					<th>交易号</th>
					<th>规则号</th>
					<th>会员号</th>
					<th>会员类型</th>
					<th>会员名称</th>
					<th>审核状态</th>
					<th>审核日期</th>
					<th>审核人</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="log" items="${page.result}" varStatus="v">
					<tr>
						<td>${(page.pageNo-1)*page.pageSize+(v.index+1)}</td>
						<td>
							<fmt:formatDate value="${log.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>
							${log.orderNo}
						</td>
						<td>
							${log.ruleCode}
						</td>
						<td>
							${log.memberCode}
						</td>
						<td>
							<c:if test="${log.memberType == 1}">个人</c:if>
							<c:if test="${log.memberType == 2}">企业</c:if>
						</td>
						<td>
							${log.memberName}
						</td>
						<td>
							<c:if test="${log.status == 0}"><a href="#" class="s02" onclick="deal('${log.recordNo}');">待审核</a></c:if>
							<c:if test="${log.status == 1}"><a href="#" class="s02" onclick="dealed('${log.recordNo}');">已审核</a></c:if>
						</td>
						<td>
							<fmt:formatDate value="${log.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>
							${log.operator}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="search" pageBean="${page}" sytleName="black2" />
	</body>
</html>