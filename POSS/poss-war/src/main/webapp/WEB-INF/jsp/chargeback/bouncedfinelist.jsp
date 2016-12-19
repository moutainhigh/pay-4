<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>

<form id="orderForm">
<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>拒付月份</th>
			<th>会员号</th>
			<th>卡组织</th>
			<th>商户名称</th>
			<th>本月罚款金额</th>
			<th>扣款状态</th>
			<th>扣款执行时间</th>
			<th>失败原因</th>
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
				<td>${dto.bouncedDate}</td>
				<td>${dto.memberCode}</td>
				<td>${dto.cardOrg}</td>
				<td>${dto.memberName}</td>
				<td>${dto.fineAmount/1000}&nbsp; ${dto.currencyCode}</td>
				<td>
					<c:if test="${dto.status == '1'}">扣款成功</c:if>
					<c:if test="${dto.status == '2'}">扣款失败</c:if>
				</td>
				<td>${dto.executeDate}</td>
				<td>${dto.remark}</td>
			 </tr>
	</c:forEach>
	</tbody>
</table>
		<input type="hidden" id="serialNumber" value="${serialNumber}">
</form>
		<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>
 <script type="text/javascript">
 function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize()+  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
		$.ajax({
			type: "POST",
			url: "${ctx}/bouncedFine.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
</script>