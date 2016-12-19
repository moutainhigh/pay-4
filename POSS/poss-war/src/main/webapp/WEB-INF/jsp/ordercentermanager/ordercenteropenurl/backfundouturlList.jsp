<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		$(document).ready(function(){
			 $("#userTable").tablesorter({
				 headers: {
				 	4:{sorter: "currency"},
				 	13: {sorter: false}
				 }});      
		});
	</script>
</head>

<body>
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>
				<th>原订单号</th>
				<th>交易流水号</th>     
				<th>银行订单号</th>
				<th>订单类型</th>
				<th>订单金额</th> 
				<th>订单状态</th>
				<th>付款登录名</th>
				<th>付款组织号</th>
				<th>付款组织名</th>
				<th>收款登录名</th>
				<th>收款组织号</th>
				<th>收款组织名</th>
				<th>订单时间</th>
				<!--<th>详细</th>-->  
			</tr> 
		</thead>
		<tbody>
			<c:forEach items="${page.result}" var="orderCenterResultDTO">
				<tr>
					<td>${orderCenterResultDTO.orderSrc}</td>
					<td>${orderCenterResultDTO.orderKy}</td>
					<td>${orderCenterResultDTO.bankOrderKy}</td>
					<td><li:select showStyle="desc" name="" itemList="${orderStatus}" selected="${orderCenterResultDTO.orderType}" /></td>
					<td><fmt:formatNumber value="${orderCenterResultDTO.orderAmount * 0.001}" pattern="#,##0.00"/></td>
					<td>					
						<c:if test="${ orderCenterResultDTO.orderStatus == 119 || orderCenterResultDTO.orderStatus == '119'  }"> 已付款待完成 </c:if>
						<c:if test="${ orderCenterResultDTO.orderStatus != 119 && orderCenterResultDTO.orderStatus != '119' }">
							<li:select showStyle="desc" name="" itemList="${tradeStatus}" selected="${orderCenterResultDTO.orderStatus}"/>
						</c:if>					
					</td>
					<td>${orderCenterResultDTO.payerAccountNo}</td>
					<td>${orderCenterResultDTO.payerBankNo}</td>
					<td>${orderCenterResultDTO.payerBankName}</td>
					<td>${orderCenterResultDTO.payeeAccountNo}</td>
					<td>${orderCenterResultDTO.payeeBankNo}</td>
					<td><li:select showStyle="desc" name="" itemList="${bankList}" selected="${orderCenterResultDTO.payeeBankNo}"/></td>
					<td><fmt:formatDate value="${orderCenterResultDTO.orderDate}" type="both"/></td>
					
				</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="processAllQuery" pageBean="${page}" sytleName="black2"/>
</body>

