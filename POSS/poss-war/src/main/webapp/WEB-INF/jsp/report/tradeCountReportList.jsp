<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">

function userQuery(){
  	$('#infoLoadingDiv').dialog('open');
	var pars = $("#form1").serialize();
	$.ajax({
		type: "POST",
		url: "${ctx}/tradeReport/tradeCount.do?method=queryTradeCount",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#paginationResult').html(result);
		}
	});
}

</script>
<form action="" method="post" name="listFrom">
</form>
<center>
<table  align="center" id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr class="even"  align="center">
			<th class="item15">币种</th>
			<th class="item15">金额</th>
			<th class="item10">笔数</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${tradeOrders}" var="order" varStatus="index">
		<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
			<td>
			${order.currencyCode}
			</td>
			<td>
			${order.tradeAmount}
			</td>
			<td>
			${order.tradeCount}
			</td>
		</tr>
		</c:forEach>
	</tbody>
	
</table>
</center>