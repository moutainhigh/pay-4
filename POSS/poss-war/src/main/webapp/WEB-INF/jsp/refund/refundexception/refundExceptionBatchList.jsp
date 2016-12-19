<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function query(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/refundOrder.do?method=queryRefundExceptionBatchList",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
}

//add by davis.guo at 2016-07-26
function queryDetail(batchNo) {
	var pars = "batchNo=" + batchNo;
	$.ajax({
		type: "POST",
		url: "${ctx}/refundOrder.do?method=queryRefundExceptionBatchSingleDetail",
		data: pars,
		success: function(result) {
			$('#resultListDiv').html(result);
		}
	});
	
	return true;
}
</script>
<form id="frm" name="manyForm" method="post" action="${ctx}/refundException.htm?method=relation">
<input type="hidden" name="ids" id="ids" value=""/>
<input type="hidden" name="uuid" value="${sessionScope.uuid}"/>
<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>处理批次号</th>
			<th>上传时间</th>
			<th>操作员</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody id="tb"> 
	<c:forEach items="${refundExceptionBatchs}" var="batch" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>	
			<td>${batch.batchNo}</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${batch.createTime}" type="both"/></td>
			<td>${batch.creator}</td>
			<%-- <td><a href="${ctx}/refundOrder.do?method=queryRefundExceptionBatchSingleDetail&batchNo=${batch.batchNo}">详情</a></td> --%>
			<!--修复分页异常  modified by davis.guo at 2016-07-26 -->
			<td><a href="javascript:void(0)" onclick="queryDetail('${batch.batchNo}');">详情</a></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
</form>
<div id="infoLoadingDiv" title="处理信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息处理中, 请稍候...
</div>
