<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function search(pageNo, totalCount) {
	//$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo
			+ "&totalCount=" + totalCount;
	$.ajax({
		type : "POST",
		url : "./orderCredit.do?method=orderCreditQuery",
		data : pars,
		success : function(result) {
			$('#resultListDiv').html(result);
		}
	});
	
}
</script>
<form action="" method="post" name="listFrom">
</form>
<center>
<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th class="item15">授信流水号</th>
			<th class="item15">会员号</th>
			<th class="item15">会员名称</th>
			<th class="item15">授信方式</th>
			<th class="item15">利率</th>
			<th class="item15">授信金额</th>
			<th class="item15">服务费</th>
			<th class="item10">申请时间</th>
			<th class="item10">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${orderCredits}" var="orderCredit" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td align="center">${orderCredit.creditId }</td>
				<td align="center">${orderCredit.partnerId}</td>
				<td align="center">${orderCredit.partnerName}</td>
				<td align="center">${orderCredit.creditType}</td>
				<td align="center">${orderCredit.dayRate }%</td>
				<td align="center"><fmt:formatNumber type="number"   value="${orderCredit.creditAmount/1000.000 }" />&nbsp;${orderCredit.currencyCode }</td>
				<td align="center"><fmt:formatNumber type="number"   value="${orderCredit.charge/1000.000 }" />&nbsp;${orderCredit.currencyCode }</td>
				<td align="center">${orderCredit.applyDateStr}</td>
				<td align="center">
					<a href="./orderCredit.do?method=orderCreditDetailList&creditId=${orderCredit.creditId}&singDetail=single">详情</a>				
				</td>
			</tr>
		</c:forEach>

	</tbody>
	
</table>
</center>
<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>