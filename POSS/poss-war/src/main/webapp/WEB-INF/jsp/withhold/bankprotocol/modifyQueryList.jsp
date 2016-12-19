<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<form action="" method="post" name="listFrom">
</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>序号</th>
			<th>流水号</th>
			<th>银行</th>
			<th>协议状态</th>
			<th>生效时间</th>
			<th>失效时间</th>
			<th>原协议号</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:if test="${page != null && fn:length(page.result) > 0}">
			<c:forEach items="${page.result}" var="dto" varStatus="index">
			<tr>
				<td>
					<c:out value="${index.count}" />
				</td>
				<td>
					${dto.sequenceId}
				</td>
				<td>
					${dto.bankName}
				</td>
				<td>
					<c:if test="${dto.status == 0}">新建</c:if>
					<c:if test="${dto.status == 1}">已审核</c:if>
					<c:if test="${dto.status == 2}">已复核</c:if>
					<c:if test="${dto.status == 3}">已暂停</c:if>
					<c:if test="${dto.status == 4}">已归档</c:if>
					<c:if test="${dto.status == 5}">已修改</c:if>
				</td>
				<td>
					<fmt:formatDate value="${dto.effectiveDate}" type="both"/>
				</td>
				<td>
					<fmt:formatDate value="${dto.expirationDate }" type="both"/>
				</td>
				<td>
					${dto.oldProtocolNo }
				</td>
				<td>
					<c:set var="seqId" value="${dto.sequenceId}" />
					<c:if test="${merchantOperateTypeMap[seqId] != 2}">
						<input type="button" onclick="modify('${dto.sequenceId}');"  id="submitBtn" class="button2" value="修改">
					</c:if>
				</td>
			</c:forEach>
		
		</c:if>
		<c:if test="${page == null || fn:length(page.result) == 0}">
			<tr>
			<td colspan="11" align="center">
				没有查询到相关数据!
			</td>
			</tr>
		</c:if>
	</tbody>
</table>

<li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>
 <script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				7: {sorter: false}
			}
		});
	 }); 

	function modify(sequenceId){
		document.listFrom.action="context_withhold_bankprotocol.controller.htm?method=modifyInit&sequenceId="+sequenceId;
		document.listFrom.submit();
	}
	
</script>