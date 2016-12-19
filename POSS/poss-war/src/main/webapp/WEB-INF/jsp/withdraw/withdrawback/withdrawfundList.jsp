<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
    提现退款订单列表
</head>

<body>
<form action="fundout.withdraw.order.do?method=detail"  method="post" ></form>
<table id="withdrawTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>交易流水号</th>     
				<th>开户行名称</th>     
				<th>银行账户</th>  
				<th>汇款金额</th>     
				<th>收款人</th>     
				<th>省份</th>     
			    <th>城市</th>   
			    <th>交易时间</th> 
		     	<th>交易备注</th> 
		        <th>状态</th> 
				<th colspan="1">操作</th>  
			</tr> 
		</thead> 
	<tbody>	
		<c:forEach items="orderList"  var="order">
			<tr>     
			    <td>${order.sequenceId}</td>
			    <td>${order.bankBranch}</td>
			    <td>${order.bankAcct}</td>
			    <td>${order.amount}</td>
			    <td>${order.accountName}</td>
			    <td>${order.bankProvince}</td>
			    <td>${order.bankCity}</td>
			    <td>${order.createTime}</td>
			    <td>${order.orderRemarks}</td>
			    <td>
			       <c:if test="${order.status=1}">处理中</c:if>
			       <c:if test="${order.status=2}">处理成功</c:if>
			       <c:if test="${order.status=3}">处理失败</c:if>
			   </td>
			   <td> 
			        <c:if test="${order.status=2}"><input  type="submit"  value="退款"/> </c:if>
			   </td>
			</tr>
		</c:forEach>
	</tbody> 
</table>
</body>

