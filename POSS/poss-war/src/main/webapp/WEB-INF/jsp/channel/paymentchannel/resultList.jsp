<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>渠道列表</title>
	</head>
	
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>序号</th>
					<th>渠道编码</th>
					<th>渠道名称</th>
					<th>渠道说明</th>
					<th>状态</th>
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
							${channel.description}
						</td>
						<td>
							<c:if test="${channel.status == '1'}">启用</c:if>
							<c:if test="${channel.status == '0'}">禁用</c:if>
						</td>
						<td>
							<a href="${ctx}/paymentChannel.htm?method=initUpdate&id=${channel.id}">修改</a>
							<a href="#" onclick="del(${channel.id});">删除</a>
							<%-- <a href="${ctx}/paymentChannelItemNew.htm?method=setItem&paymentChannelNewId=${channel.id}">通道设置</a> --%>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
	</body>
</html>