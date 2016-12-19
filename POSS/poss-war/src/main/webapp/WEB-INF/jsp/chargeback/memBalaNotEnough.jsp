<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>
<h2 class="panel_title">罚款余额不足商户</h2>
<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>会员号</th>
			<th>商户名称</th>
			<th>预计罚款金额</th>
			<th>商户基本户余额</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${result}" var="dto" varStatus="index">
			<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td>${dto.memberCode}</td>
				<td>${dto.memberName}</td>
				<td>${dto.fineAmount/1000}&nbsp; ${dto.currencyCode}</td>
				<td>${dto.balance/1000}&nbsp; ${dto.currencyCode}</td>
			 </tr>
	</c:forEach>
	</tbody>
</table>
 <script type="text/javascript">
</script>