<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function search(pageNo,totalCount,pageSize) {
	
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/merchantRefundPercnt.do?method=list",
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
			<th>退款笔数</th>
			<th>交易总笔数</th>
			<th>退款比例</th>
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
			<td>${order.refundCount}</td>
			<td>${order.tradeTotalCount}</td>
			<td>${order.refundPercnt}</td>
		</tr>
		</c:forEach>
		
	</tbody>
</table>
<c:if test="${ empty  paraMap.memberCode}">
				<p> 总计： 退款笔数：${totalResList.refundTotalCount}  交易总笔数： ${totalResList.totalSuccess}   退款比例：${totalResList.refundPercnt }     </p>
			</c:if>
			<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>
