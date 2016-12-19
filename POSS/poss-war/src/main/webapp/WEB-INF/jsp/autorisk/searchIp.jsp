<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
	</head>
	<body>
		<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="18">
					<div>
						<font class="titletext">IP关联</font>
					</div>
				</td>
			</tr>
		</table>
		<br>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>会员号</th>
					<th>会员名称</th>
					<th>关联IP</th>
					<th>IP城市</th>
					<th>主账户最近登录日期</th>
					<th>会员类型</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="ip" items="${page.result}" varStatus="v">
					<tr>
						<td>
							${ip.memberCode}
						</td>
						<td>
							${ip.memberName}
						</td>
						<td>
							${ip.ip}
						</td>
						<td>
							${ip.city}
						</td>
						<td>
							${ip.latestLoginTime}
						</td>
						<td>
							<c:if test="${ip.memberType == 1}">个人</c:if>
							<c:if test="${ip.memberType == 2}">企业</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<li:pagination methodName="searchIp" pageBean="${page}" sytleName="black2" />
	</body>
</html>