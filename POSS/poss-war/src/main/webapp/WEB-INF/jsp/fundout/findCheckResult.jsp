<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>批次号</th>
			<th>上传时间</th>
			<th>操作员</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${checkBatchDtos}" var="dto" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
			<td > ${dto.batchNo} </td>
			<td > <fmt:formatDate value="${dto.checkDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
			<td > ${dto.operator} </td>
			<td><a href="findFundoutCheck.do?method=queryFundoutCheckDetail&batchNo=${dto.batchNo}">详情</a></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
	<li:pagination methodName="submitByHref" pageBean="${page}" sytleName="black2"/>
<script type="text/javascript">
function submitByHref(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
		$.ajax({
			type: "POST",
			url: "./findFundoutCheck.do?method=queryFundoutCheck",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
}
</script>