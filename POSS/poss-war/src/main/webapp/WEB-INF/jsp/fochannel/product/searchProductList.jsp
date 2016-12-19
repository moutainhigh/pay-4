<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<form action="" method="post" name="listFrom"></form>
<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>序号</th>
			<th>Code</th>
			<th>名称</th>
			<th>状态</th>
			<th>创建时间</th>
			<th>备注</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${resultPage != null && fn:length(resultPage.result) > 0}">
			<c:forEach items="${resultPage.result}" var="dto" varStatus="index">
				<tr>
					<td><c:out value="${index.count}" /></td>
					<td>${dto.code}</td>
					<td>${dto.name}</td>
					<td><li:select name="status" showStyle="desc" selected="${dto.status}" itemList="${statusList}" /></td>
					<td><fmt:formatDate value="${dto.createDate}" type="both" /></td>
					<td>${dto.mark}</td>
					<td><input type="button" onclick="searchProductByCode('${dto.code}');" id="submitBtn" class="button2" value="修 改"></td>
			</c:forEach>
		</c:if>
		<c:if test="${resultPage == null || fn:length(resultPage.result) == 0}">
			<tr>
				<td colspan="7" align="center">没有查询到相关数据!</td>
			</tr>
		</c:if>
	</tbody>
</table>
<li:pagination methodName="search" pageBean="${resultPage}" sytleName="black2" />
<script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				6: {sorter: false}
			}
		});
	 }); 

	function searchProductByCode(code){
		document.listFrom.action="context_fundout_foproduct.controller.htm?method=initModify&code="+code;
		document.listFrom.submit();
	}
	
</script>