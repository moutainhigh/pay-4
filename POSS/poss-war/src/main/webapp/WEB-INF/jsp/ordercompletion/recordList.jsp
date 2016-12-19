<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function search(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/orderCompletionCheck.do?method=searchOrderFillBatch",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
</script>

<form action="" method="post" name="listFrom">
</form>
<div align="center">
	<table  id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
		<thead>
			<tr>
				<th class="item15">批次号</th>
				<th class="item15">银行机构</th>
				<th class="item15">补单创建时间</th>
				<th class="item15">文件名</th>
				<th class="item15">申请人</th>
				<th class="item15">申请补单笔数</th>
				<th class="item10">审核状态</th>
				<th class="item10">补单成功笔数</th>
				<th class="item10">补单失败笔数</th>
				<th class="item30">审核时间</th>
				
				<th class="item10">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${resultList }" var="result" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
					<td>${result.reqBatchNo }</td>
					<td>
						<%-- ${result.orgCode } --%>
						<c:choose>
							<c:when test="${result.orgCode=='0000000'}">测试通道</c:when>
							<c:when test="${result.orgCode=='10076001'}">中银卡司</c:when>
							<c:when test="${result.orgCode=='10075001'}">CREDORAX</c:when>
							<c:when test="${result.orgCode=='10003001'}">中国银行</c:when>
							<c:when test="${result.orgCode=='10002001' }">农业银行</c:when>
							<c:when test="${result.orgCode=='10081016' }">前海万融</c:when>
							<c:otherwise>未知机构</c:otherwise>
						</c:choose>
					</td>
					<td>${result.createTime }</td>
					<td>${result.fileName }</td>
					<td>${result.applicant }</td>
					<td>${result.applyAmount }笔</td>
					<td>
						<c:choose>
							<c:when test="${result.auditStatus == 0 }">
								待审核
							</c:when>
							<c:when test="${result.auditStatus == 1 }">
								审核通过
							</c:when>
							<c:when test="${result.auditStatus == 2 }">
								审核拒绝
							</c:when>
						</c:choose>
					</td>
					<td>成功笔数</td>
					<td>失败笔数</td>
					<td>${result.auditTime }</td>
					<td>
						<c:choose>
							<c:when test="${result.auditStatus == 0 }">
								<a href="${ctx }/orderCompletionCheck.do?method=toCheck&reqBatchNo=${result.reqBatchNo }&orgCode=${result.orgCode }">审核</a>
							</c:when>
							<c:otherwise>
								<a href="${ctx }/orderCompletionCheck.do?method=viewDetail&reqBatchNo=${result.reqBatchNo }&auditStatus=${result.auditStatus }&orgCode=${result.orgCode}">
									详情
								</a>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
			
		</tbody>
		
	</table>
</div>
<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>