<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		$(document).ready(function(){
			 $("#userTable").tablesorter({
				 headers: {
				 	4:{sorter: false},
				 	13: {sorter: false}
				 }});      
		});
	</script>
</head>

<body>

		<c:if test="${errorMsg != null}">
			<div><font color="red">${errorMsg}</font></div>
		</c:if>

	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>
				<th>原订单号</th>
				<th>交易流水号</th>     
				<th>银行订单号</th>
				<th>订单金额</th> 
				<th>订单状态</th>
				<th>付款会员号</th>
				<!-- th>付款组织号</th>
				<th>付款组织名</th-->
				<th>收款账号</th>
				<th>收款组织号</th>
				<th>收款组织名</th>
				<th>订单时间</th>
				<th>详细</th>  
			</tr> 
		</thead>
		<tbody>
			<c:forEach items="${page.result}" var="orderCenterResultDTO">
				<tr>
					<td>${orderCenterResultDTO.orderSrc}</td>
					<td>${orderCenterResultDTO.orderKy}</td>
					<td>${orderCenterResultDTO.bankOrderKy}</td>
					<td><fmt:formatNumber value="${orderCenterResultDTO.orderAmount * 0.001}" pattern="#,##0.00"/></td>
					<td>
						<c:if test="${ orderCenterResultDTO.orderStatus == 100}"> 初始 </c:if>
						<c:if test="${ orderCenterResultDTO.orderStatus == 101}"> 进行中 </c:if>
						<c:if test="${ orderCenterResultDTO.orderStatus == 102}"> 申请失败 </c:if>
						<c:if test="${ orderCenterResultDTO.orderStatus == 111}"> 成功 </c:if>
						<c:if test="${ orderCenterResultDTO.orderStatus == 112}"> 失败 </c:if>
						<c:if test="${ orderCenterResultDTO.orderStatus == 113}"> 退票 </c:if>
					</td>
					<td>${orderCenterResultDTO.payerAccountNo}</td>
					<!-- td>${orderCenterResultDTO.payerBankNo}</td>
					<td>${orderCenterResultDTO.payerBankName}</td-->
					<td>${orderCenterResultDTO.payeeAccountNo}</td>
					<td>${orderCenterResultDTO.payeeBankNo}</td>
					<td>${orderCenterResultDTO.payeeBankName}</td>
					<td><fmt:formatDate value="${orderCenterResultDTO.orderDate}" type="both"/></td>
					<td><a href="javascript:showUrl('订单详情','${ctx}/fo-ordercentermanager-list.htm?method=processOrderDetail&orderType=${orderType}&workflowKy=${orderCenterResultDTO.workflowKy}&orderKy=${orderCenterResultDTO.orderKy}&payerMemberCode=${orderCenterResultDTO.payerMemberCode}&payerAccType=${orderCenterResultDTO.payerAccType}&payeeMemberCode=${orderCenterResultDTO.payeeMemberCode}&payeeAccType=${orderCenterResultDTO.payeeAccType}&orderStatus=${orderCenterResultDTO.orderStatus}&busiType=${orderCenterResultDTO.busiType}&orderSrc=${orderCenterResultDTO.orderSrc}')">详细</a></td>
				</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="processAllQuery" pageBean="${page}" sytleName="black2"/>
</body>

