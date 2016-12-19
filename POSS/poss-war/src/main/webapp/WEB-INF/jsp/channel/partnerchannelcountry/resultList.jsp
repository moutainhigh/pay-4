<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>渠道路由优先级配置</title>
	</head>
	
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>序号</th>
					<th>会员号</th>
					<th>IP国家</th>
					<th>渠道</th>
					<th>操作员</th>
					<th>创建时间</th>
					<th>修改时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${result}" varStatus="v">
					<c:choose>
	       <c:when test="${v.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
						<td>${item.id}</td>
						<td>${item.memCode}</td>
						<td>${item.countryCodeDesc}</td>
						<td>${item.orgCodeDesc}</td>
						<td>
								${item.operator}
						</td>
						<td><date:date   value="${item.createDate}"/></td>
						<td><date:date   value="${item.updateDate}"/></td>
						<td>
							<a href="javascript:deleteConfig('${item.id}')">删除</a>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:editConfig('${item.id}','${item.memCode}','${item.orgCode}','${item.countryCode}')">修改</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>