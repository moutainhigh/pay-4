<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>批次号</th>
					<th>会员号</th>
					<th>操作员</th>
					<th>创建时间</th>
					<th>业务类型</th>
					<th>申请笔数</th>
					<th>导入成功笔数</th>
					<th>付款笔数</th>
					<th>汇款费用</th>
					<th>付款金额</th>
					<th>处理状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="acc" items="${resultList}" varStatus="v">
					<c:choose>
	       <c:when test="${v.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
						<td>${acc.batchNo}</td>
						<td>${acc.partnerId}</td>
						<td>${acc.operator}</td>
						<td><%-- <fmt:formatDate value="${acc.createDate}" pattern="yyyy-MM-dd"/> --%>
						${acc.createDate}
						</td>
						<td>			
								<c:choose>
								<c:when test="${acc.type=='1'}">货物贸易</c:when>
								<c:when test="${acc.type=='2'}">机票旅游</c:when>
								<c:when test="${acc.type=='3'}">酒店住宿</c:when>
								<c:when test="${acc.type=='4'}">留学教育</c:when>
							</c:choose>
							</td>
						<td>${acc.allCount}</td>
						<td>${acc.successCount}</td>
						<td>${acc.payCount}</td>
						<td>${acc.remitAmount}</td>
						<td>${acc.payAmount}</td>
						<td>
						<c:choose>
								<c:when test="${acc.status=='0'}">处理中</c:when>
								<c:when test="${acc.status=='1'}">处理成功</c:when>
								<c:when test="${acc.status=='2'}">处理失败</c:when>
								<c:when test="${acc.status=='3'}">部分成功</c:when>
						</c:choose>
						</td>
						<td><!-- 文件类型 资源模版 1，付款文件2，明细文件3  -->
						<%-- ${acc.kfPayResources} --%>
							<c:forEach var="kf" items="${acc.kfPayResources}" varStatus="v">
									<c:if test="${kf.fileType=='2' }">
										<a href="${ kf.url}" >付款文件下载</a>
									</c:if>
									<c:if test="${kf.fileType=='3' }">
										<a href="${ kf.url}"  >处理明细下载</a>
									</c:if>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>
<script type="text/javascript">

function query(pageNo, totalCount){
	var pars = $("#mainfrom").serialize() + "&pageNo=" + pageNo
	+ "&totalCount=" + totalCount;
	$.ajax({
		type : "POST",
		url : "./payFileDownload.do?method=list",
		data : pars,
		success : function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
}
</script>