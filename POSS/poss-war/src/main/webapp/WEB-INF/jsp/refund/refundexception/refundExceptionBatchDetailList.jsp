<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
//add by davis.guo at 2016-07-26
function queryList(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
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

//modified by davis.guo at 2016-07-26
function queryDetails(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#frm").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/refundOrder.do?method=queryRefundExceptionBatchSingleDetail",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
}


</script>
<h2 class="panel_title">退款状态批量处理详情</h2>
<form id="frm" name="manyForm" method="post" action="${ctx}/refundException.htm?method=relation">
<input type="hidden" name="batchNo" id="batchNo" value="${batchNo}"/>
<input type="hidden" name="uuid" value="${sessionScope.uuid}"/>

<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>处理批次号</th>
			<th>渠道订单号</th>
			<th>上传时间</th>
			<th>退款result</th>
			<th>更新结果</th>
			<th>失败原因</th>
		</tr>
	</thead>
	<tbody id="tb"> 
	<c:forEach items="${refundExceptionBatchDetails}" var="detail" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>	
			<td>${detail.batchNo}</td>
			<td>${detail.channelOrderNo}</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${detail.createTime}" type="both"/></td>
			<td>${detail.refundResult}</td>
			<td>
				<%-- ${detail.updateResult} --%>
				<c:choose>
					<c:when test="${detail.updateResult == 'F' }">
						更新失败
					</c:when>
					<c:when test="${detail.updateResult == 'S' }">
						更新成功
					</c:when>
				</c:choose>
			</td>
			<td>${detail.remark}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div style="text-align: center;width: 100%">
	<!-- <input type="button" value="返回" onclick="javascript:window.history.go(-1) ;"/> -->
	<input type="button" onclick="queryList();" name="submitBtn" value="返回" >
</div>
<li:pagination methodName="queryDetails" pageBean="${page}" sytleName="black2"/>
</form>

<div id="infoLoadingDiv" title="处理信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息处理中, 请稍候...
</div>