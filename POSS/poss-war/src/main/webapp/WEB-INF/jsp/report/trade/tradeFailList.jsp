<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">

function search(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/tradeFailDeal.do?method=list",
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
			<th>返回码所属</th>
			<th>总交易笔数</th>
			<th>总失败笔数</th>
			<th>总失败率</th>
			<th>失败原因名称</th>
			<th>所占比例</th>
			<th>失败原因笔数</th>
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
			<td>
			<c:if test="${order.target == 'T'}">
				商户
			</c:if>
			<c:if test="${order.target == 'C'}">
				收单行
			</c:if>
			</td>
			<td>${order.totalCount}</td>
			<td>${order.tradeFailCount}</td>
			<td>${order.tradeFailRate}</td>
			<td>${order.failDesc}</td>
			<td>${order.failScale}</td>
			<td>${order.failCount}</td>
		</tr>
		</c:forEach>
		<%-- <tr>
				<td  colspan="4"  align="left"> 总计： 交易总笔数  ${}  成功笔数： ${}   交易成功率：${ } 交易总额： ${ } 客单价：${ }    </td>
				<td  colspan="11"  align="right"></td>
		</tr> --%>
	</tbody>
</table>
<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>
