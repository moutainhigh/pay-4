<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>

<form action="" method="post" name="listFrom">
</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>对账批次号</th>
			<th>上传时间</th>
			<th>结束时间</th>
			<th>机构编号</th>
			<th>文件</th>
			<th>批次状态</th>
			<th>条目数</th>
			<th>成功数</th>
			<th>操作员</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${batchDTOs}" var="dto" varStatus="index">
			<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td>${dto.bacthNo}</td>				
				<td><fmt:formatDate value="${dto.createDate}" type="both"/></td>
				<td><fmt:formatDate value="${dto.batchEndTime}" type="both"/></td>
				<td>${dto.orgCode}</td>
				<td>${dto.fileName}</td>
				<td>
				<c:if test="${dto.status==0}">创建</c:if>
				<c:if test="${dto.status==1}">成功</c:if>
				<c:if test="${dto.status==2}">失败</c:if>
				<c:if test="${dto.status==10}">预处理消息发送</c:if>
				<c:if test="${dto.status==11}">预处理消息发送失败</c:if>
				<c:if test="${dto.status==12}">预处理开始</c:if>
				<c:if test="${dto.status==13}">预处理结束</c:if>
				<c:if test="${dto.status==20}">处理消息发送</c:if>
				<c:if test="${dto.status==21}">处理消息发送失败</c:if>
				<c:if test="${dto.status==22}">处理开始</c:if>
				<c:if test="${dto.status==23}">处理结束</c:if>
				</td>

				<td>${dto.applyCount}</td>
				<td>${dto.successCount}</td>				
				<td>${dto.operator}</td>				
				<td>
				<a href="reconcile.do?method=queryReconcileDetail&batchNo=${dto.bacthNo}">详情</a>
				</td>				
			</tr>
		</c:forEach>
	</tbody>
</table>
 <script type="text/javascript">
 function search(pageNo,totalCount) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
			$.ajax({
				type: "POST",
				url: "reconcile.do?method=queryReconcile",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
		});
	}
</script>
 <li:pagination methodName="search" pageBean="${page}" sytleName="black2"/> 