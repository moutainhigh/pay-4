<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>货币列表</title>
	</head>

	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>序列号</th>
					<th>货币名称</th>
					<th>货币编号</th>
					<th>货币号码</th>
					<th>适用范围</th>
					<th>创建日期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${currencyList}" varStatus="v">
					<c:choose>
	       <c:when test="${v.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
						<td>${item.sequenceId}</td>
						<td>
							${item.currencyName}
						</td>
						<td>
                            ${item.currencyCode}
						</td>
						<td>
							${item.currencyNum}
						</td>
						<td>
							<c:if test="${item.flag == '1'}">交易币种</c:if>
							<c:if test="${item.flag == '2'}">货币兑换</c:if>
							<c:if test="${item.flag == '3'}">其他</c:if>
							<c:if test="${item.flag == '4'}">渠道币种</c:if>
							<c:if test="${item.flag == '5'}">授信币种</c:if>
							<c:if test="${item.flag == '6'}">退款手续费币种</c:if>
							<c:if test="${item.flag == '7'}">DCC币种</c:if>
						</td>
						<td>
							${item.createDate}
						</td>
						<td>
							<a href="javascript:deleteCure('${item.sequenceId}')">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>
			<%-- <li:pagination methodName="search" pageBean="${page}" sytleName="black2"/> --%>
<script type="text/javascript">		
function query(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#currency").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/currency/currency.do?method=query",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
</script>