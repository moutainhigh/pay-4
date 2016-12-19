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
			<th>商户收款协议号</th>
			<th>商户号</th>
			<th>协议状态</th>
			<th>生效时间</th>
			<th>失效时间</th>
			<th>原流水号</th>
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
					${dto.protocolNo}
				</td>
				<td>
					${dto.merchantId}
				</td>
				<td>
					<c:if test="${dto.status == 5}">已修改</c:if>
					<c:if test="${dto.status == 0}">新建</c:if>
				</td>
				<td>
					<fmt:formatDate value="${dto.effectDate}" type="both"/>
				</td>
				<td>
					<fmt:formatDate value="${dto.expirationDate }" type="both"/>
				</td>
				<td>
					${dto.oldSequenceId }
				</td>
				<td>
					<fmt:formatNumber value="${dto.creditLimit/1000}" pattern="#,##0.00"/>
				</td>
				<td>
					<input type="button" onclick="audit('${dto.merchantId}','${dto.sequenceId}','${dto.oldSequenceId}','${dto.status}');"  id="submitBtn" class="button2" value="审核">
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

	function audit(merchantId,sequenceId,oldSequenceId,status){
		document.listFrom.action="context_withhold_recieveprotocol.controller.htm?method=audit&sequenceId="
				                  +sequenceId+"&oldSequenceId="+oldSequenceId+"&status="+status+"&merchantId="+merchantId;
		document.listFrom.submit();
	}
	
</script>