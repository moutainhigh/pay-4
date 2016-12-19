<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function orderlist(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#riskForm").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "./findriskOrder.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
</script>
<body>
	<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
		<thead>
			<tr>
				<th class="" align="center">会员号</th>
				<th class="" align="center">商户订单号</th>
				<th class="" align="center">风险评分</th>
				<th class="" align="center">发卡国家</th>
				<th class="" align="center">下单IP</th>
				<th class="" align="center">IP国家</th>
				<th class="" align="center">cybs返回码</th>
				<th class="" align="center">风险结果</th>
				<th class="" align="center">下单时间</th>
			</tr>
		</thead>
		
			<c:forEach items="${list}" var="c" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>		

				<td align="center">${c.merchantDefinedData1}</td>
				<td align="center">${c.merchantReferenceCode}</td>
				<td align="center">${c.afsreplyAfsresult}</td>
				<td align="center">${c.afsreplyBincountry}</td>
				<td align="center">${c.billtoIPaddress}</td>
				<td align="center">${c.afsreplyIpCountry }</td>
				<td align="center">${c.reasonCode}</td>
				<td align="center">
					<c:if test="${c.decision=='ACCEPT'}">
							通过
					</c:if>
					<c:if test="${c.decision=='REJECT'}">
						 	拒绝
					</c:if>
					<c:if test="${c.decision=='MONITOR'}">
						监控
					</c:if>
					<c:if test="${c.decision=='REAUDIT'}">
						审核
					</c:if>
				</td>
				<td align="center">${c.createDate}</td>
		</tr>
		</c:forEach>
	</table>
</body>
<table align="center">	<tr>
			<td colspan="13" align="center"><li:pagination methodName="orderlist" pageBean="${page}" sytleName="black2"/></td>
		</tr>
		</table>
