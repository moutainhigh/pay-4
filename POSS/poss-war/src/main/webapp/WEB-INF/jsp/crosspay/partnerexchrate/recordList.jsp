<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>
<%@ page import="com.hnapay.fundout.util.AmountUtils"%>

<form action="" method="post" name="listFrom">
</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>开始时间</th>
			<th>结束时间</th>
			<th>币种</th>
			<th>交易单位</th>
			<th>当前汇率</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
			<c:forEach items="${page.result}" var="dto" varStatus="index">
			<tr>
				<td>
					<fmt:formatDate value="${dto.fromDate}" type="date"/>
				</td>
				<td>
					<fmt:formatDate value="${dto.toDate}" type="date"/>
				</td>
				<td>
					${dto.currencyName}
				</td>
				<td>
					${dto.currencyUnit}
				</td>
				<td>
					${dto.exchangeRate}
				</td>
				<td>
					<a href="${ctx}/crosspay/partnerExchRate.do?method=toUpdate&id=${dto.id}">修改</a>
				</td>
			</c:forEach>
	</tbody>
</table>

<li_new:page methodName="search" pageBean="${page}" sytleName="black2"/>
 <script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				7: {sorter: false}
			}
		});
	 }); 
	
</script>