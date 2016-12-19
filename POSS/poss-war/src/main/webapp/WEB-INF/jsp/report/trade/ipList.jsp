<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function search(pageNo,totalCount,pageSize) {
	var memberCode = $("#memberCode").val();
	if(memberCode == ''){
		alert("请输入会员号！！！");
		return ;
	}
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/ipDistrReportRep.do?method=list",
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
			<th>地区</th>
			<th>所占比例</th>
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
				<td>${order.cardType}</td>
				<td>${order.state}</td>
				<td>${order.sPercent}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
