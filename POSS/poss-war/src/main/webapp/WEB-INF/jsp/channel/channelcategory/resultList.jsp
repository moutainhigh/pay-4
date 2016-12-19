<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>渠道成本费率列表</title>
	</head>
	
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>序号</th>
					<th>编号</th>
					<th>名称</th>
					<th>操作人</th>
					<th>描述</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="channel" items="${resultList}" varStatus="v">
					<c:choose>
	       <c:when test="${v.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
						<td>${channel.id}</td>
						<td>
							${channel.code}
						</td>
						<td>
							${channel.name}
						</td>
						<td>
							${channel.operator}
						</td>
						<td>
							${channel.description}
						</td>
						<td>
							<a href="${ctx}/channelCategory.htm?method=initUpdate&id=${channel.id}">修改</a>
							<a href="#" onclick="del(${channel.id})">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
	</body>
</html>
<script type="text/javascript">
	function del(id){
		if (!confirm("确认删除？")) {
			return;
		 }
		window.location.href="${ctx}/channelCategory.htm?method=remove&id="+id;
	}
</script>