<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function list(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#floatRate").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/partnerRateFloat.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
</script>
<html>
	<head>
		<title>货币列表</title>
	</head>

	<body>
		<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
			<thead>
				<tr>
					<th>序列号</th>
					<th>会员号</th>
					<th>起始点</th>
					<th>截止点</th>
					<th>操作人</th>
					<th>创建日期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${rateList}" varStatus="v">
					<c:choose>
	       <c:when test="${v.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
						<td>${item.id}</td>
						<td>
							${item.partnerId}
						</td>
						<td>
							${item.startPoint}
						</td>
						<td>
                            ${item.endPoint}
						</td>
						<td>
							${item.operator}
						</td>
						<td>
							${item.createDate}
						</td>
						<td>
							<a href="javascript:deleteCure('${item.id}')">删除</a>
						</td>
					</tr>
				</c:forEach>
	
			</tbody>
		</table>	
		<li:pagination methodName="list" pageBean="${page}" sytleName="black2"/>
	</body>
</html>