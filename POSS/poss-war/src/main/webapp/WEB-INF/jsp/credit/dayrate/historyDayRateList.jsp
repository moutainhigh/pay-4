<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function search(pageNo, totalCount) {
	//$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo
			+ "&totalCount=" + totalCount;
	$.ajax({
		type : "POST",
		url : "./orderCreditDayRate.do?method=dayRateQuery",
		data : pars,
		success : function(result) {
			//	$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
	
}
</script>
<form action="" method="post" name="listFrom">
</form>
<center>
<table  align="center" id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th class="item15">会员号</th>
			<th class="item15">商户名称</th>
			<th class="item15">日利率</th>
			<th class="item15">更新时间</th>
			<th class="item15">操作员</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${dayRates}" var="dayRate" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td align="center">${dayRate.partnerId}</td>
				<td align="center">${dayRate.partnerName}</td>
				<td align="center">${dayRate.rate}</td>
				<td align="center">${dayRate.updateDateStr}</td>
				<td align="center">${dayRate.operator }</td>
			</tr>
		</c:forEach>
	</tbody>
	
</table>
</center>
<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>