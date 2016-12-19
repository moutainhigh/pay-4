<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>

<form id="orderForm">
<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>序号</th>
			<th>渠道</th>
			<th>拒付原因</th>
			<th>包装原因</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${bouncedReasonMappings}" var="dto" varStatus="index">
			<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
			<td>${dto.id }</td>
			<td>
			        <c:if test="${dto.orgCode=='10076001'}">
			        	中银卡司
			        </c:if>
					<c:if test="${dto.orgCode=='10079001'}">中银MOTO</c:if>
					<c:if test="${dto.orgCode=='10080001'}">中银MIGS</c:if>
					<c:if test="${dto.orgCode=='10003001'}">中国银行</c:if>
					<c:if test="${dto.orgCode=='10002001'}">农业银行</c:if>
					<c:if test="${dto.orgCode=='10075001'}">CREDORAX</c:if>
					<c:if test="${dto.orgCode=='10077001'}">Adyen</c:if>
					<c:if test="${dto.orgCode=='10077002'}">Belto</c:if>
					<c:if test="${dto.orgCode=='10077003'}">Cashu</c:if>
					<c:if test="${dto.orgCode=='10078001'}">农行CTV</c:if>
			</td>
			<td>${dto.bouncedReason }</td>
			<td>${dto.visableName }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
		<input type="hidden" id="serialNumber" value="${serialNumber}">
</form>
		<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>
 <script type="text/javascript">
 	function search(pageNo,totalCount) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
		$.ajax({
			type: "POST",
			url: "${ctx}/bouncedReasonMapping.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
</script>