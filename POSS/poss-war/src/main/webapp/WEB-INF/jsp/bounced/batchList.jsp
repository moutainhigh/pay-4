<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
</script>

<div id="infoLoadingDiv" title="加载信息"
	style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
	请稍候...
</div>
<body>
<table id="userTable" width="80%" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
		<tr >
			<th >批次号</th>
			<th >登记时间</th>
			<th >最晚回复日</th>
			<th >操作员</th>
		</tr>
	</thead>
	<tbody> 
		<c:forEach items="${page}" var="dto" varStatus="status">
		<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td>${dto.batchNo}</td>
				<td><date:date value="${dto.createDate}"/></td>
				<td><date:date value="${dto.lastDate}"/></td>
				<td>${dto.operator}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</body>

<script type="text/javascript" language="javascript">
</script>