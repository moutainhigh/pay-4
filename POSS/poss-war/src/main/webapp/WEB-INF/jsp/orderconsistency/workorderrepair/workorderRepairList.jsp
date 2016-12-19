<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<c:if test="${not empty err1}">
	<font color="red"><b>${err1 }</b></font>
</c:if>
<c:if test="${empty err1}">
	工单状态修复查询列表
	<form name="manyForm" method="post">
		
	<table id="refundInfoTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead>
			<tr>
				<th>序号</th>
				<th>交易号</th>
				<th>交易时间</th>
				<th>交易金额</th>
				<th>订单状态</th>
				<th>订单类型</th>
				<th>付款会员号</th>
				<th>收款银行名称</th>
				<th>收款银行账号</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.result}" var="dto" varStatus="status">
			<tr>
				<td >
					<c:out value="${status.count}" />
				</td>
				<td > ${dto.orderId} </td>
				<td > 
					<fmt:formatDate value="${dto.createDate}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td ><fmt:formatNumber value="${order.orderAmount * 0.001}" pattern="#,##0.00"/></td>
				<td >
					进行中
				</td>
				<td >
					<c:if test="${dto.orderSmallType == '001'}">普通提现</c:if>
					<c:if test="${dto.orderSmallType == '002'}">委托提现</c:if>
					<c:if test="${dto.orderSmallType == '003'}">强制提现</c:if>
					<c:if test="${dto.orderSmallType == '301'}">付款到银行</c:if>
					<c:if test="${dto.orderSmallType == '401'}">批付到银行</c:if>
				</td>
				<td > 
					${dto.payerMemberCode}
				</td>
				<td > 
					${dto.payeeBankName}
				</td>
				<td > 
					${dto.payeeBankAcctCode}
				</td>
				<td>
					<input type="button" class="button4" value="补工单"
						onclick="haderRefundApply(this,'${dto.orderId}');" >
				</td> 
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form>
	
	<li:pagination methodName="submitByHref" pageBean="${page}" sytleName="black2"/>

</c:if>

<script type="text/javascript">
	$(function(){
		$("#refundInfoTable").tablesorter({
			 headers: {
		 		0: {sorter: false},
		 		7: {sorter: false},
		 		8: {sorter: false},
		 		9: {sorter: false}
		 	}
		});   
	}); 
	
	function haderRefundApply(btn,orderId){
		if(confirm('确认补生成工单吗？')){
			btn.setAttribute("disabled",true);
			window.location="${ctx}/workorder_repair.htm?method=workorderRepair&orderId="+orderId;
		}
	}

</script>