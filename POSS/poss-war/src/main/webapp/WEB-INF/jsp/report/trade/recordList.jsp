<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function search(pageNo,totalCount,pageSize) {
	
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/tradeBaseMattersAnalyse.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
			$("#pageNo").attr("value",pageNo);
		}
	});
}
</script>
<form action="" method="post" name="listFrom">
</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
	<thead>
		<tr>
			<th>会员号</th>
			<th>商户名称</th>
			<th>起止时间</th>
			<th>卡种</th>
			<th>交易总笔数</th>
			<th>成功笔数</th>
			<th>交易成功率</th>
			<th>交易总额CNY</th>
			<th>客单价CNY</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${resList}" var="order" varStatus="index">
		<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
			<td>${order.memberCode}</td>
			<td>${order.merchantName}</td>
			<td>${order.tradeDate}</td>
			<td>
				${order.cardOrg}
			</td>
			<td>${order.tradeCount}</td>
			<td>${order.tradeSuccessCount}</td>
			<td>${order.tradeSuccessRate}</td>
			<td>${order.tradeAmount}</td>
			<td>${order.perTicketSales}</td>
		</tr>
		</c:forEach>

	</tbody>
</table>
<c:if test="${empty paraMap.memberCode }">
				<p> 总计： 交易总笔数  ${totalResList.totalAll}  成功笔数： ${totalResList.totalSuccess}   交易成功率：${totalResList.percnt } 交易总额： ${totalResList.totalSum } 客单价：${ totalResList.kdj}   
			</p></c:if>
<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>