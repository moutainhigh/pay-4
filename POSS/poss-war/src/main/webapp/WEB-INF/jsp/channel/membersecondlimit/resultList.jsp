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
					<th>卡组织</th>
					<th>使用时间限制/天</th>
					<th>笔数限制/笔</th>
					<th>金额限制/CNY</th>
					<th>隔月切换二级商户号</th>
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
						<td>${item.partnerId}</td>
						<td>${item.cardOrg}</td>
						<td>${item.limitDay}</td>
						<td>${item.limitTimes}</td>
						<td>${item.limitAmount}</td>
						<td><c:if test="${item.switchFlag == '1'}">是</c:if>
							<c:if test="${item.switchFlag == '0'}">否</c:if></td>
						<td align="center">
							<c:if test="${item.partnerId != '0'}"><a href="javascript:deleteConfig('${item.id}')"><input class="button2" type="button" value="删除"></a></c:if>
							<a href="javascript:editConfig('${item.id}','${item.partnerId}','${item.cardOrg}','${item.limitDay}'
							,'${item.limitTimes}','${item.limitAmount}','${item.switchFlag}')"><input class="button2" type="button" value="修改"></a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>