<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.pay.pe.AmountUtils"%>

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
			<th>信用额度</th>
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
					<c:if test="${dto.status == 2}">已审核</c:if>
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
					<fmt:formatNumber value="${dto.creditLimit/1000}" pattern="#,##0.00"/>
				</td>
				<td>
					<input type="button" onclick="audit('${dto.sequenceId}','${dto.oldProtocolNo}','${dto.status}');"  id="submitBtn" class="button2" value="审核">
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

	function audit(sequenceId,oldProtocolNo,status){
		document.listFrom.action="context_withhold_bankprotocol.controller.htm?method=audit&sequenceId="+sequenceId+"&oldProtocolNo="+oldProtocolNo+"&status="+status;
		document.listFrom.submit();
	}
	
</script>