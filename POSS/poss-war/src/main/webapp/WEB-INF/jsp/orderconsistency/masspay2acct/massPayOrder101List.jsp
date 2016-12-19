<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/rmtaglibs.jsp"%>
<%
response.setHeader("Pragma", "No-cache"); 	
response.setHeader("Cache-Control", "no-cache"); 	
response.setDateHeader("Expires", 0); 
%>
<head>
	<script type="text/javascript">
		function processSuccess(id){
			var pars =  "orderId=" + id ;
			$.ajax({
				type: "POST",
				url: "${ctx}/masspayorder_backend_support.htm?method=setOrderToSuccess",
				data: pars,
				success: function(result) {
					if (result == 'success') {
						alert("处理完成!");
						search();
					}else{
						alert("处理失败!");
					}
				}
			});
		}
		function processFail(id){
			var pars =  "orderId=" + id ;
			$.ajax({
				type: "POST",
				url: "${ctx}/masspayorder_backend_support.htm?method=setOrderToFail",
				data: pars,
				success: function(result) {
					if (result == 'success') {
						alert("处理完成!");
						search();
					}else{
						alert("处理失败!");
					}
				}
			});
		}		
	</script>
</head>

<body>
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>总订单号</th>     
				<th>子订单号</th> 
				<th>订单状态</th>  
				<th>支付金额</th>
				<th>创建时间</th>
				<th>付款会员号</th>
				<th>收款方会员号</th>   
				<th colspan=2>操作</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${orderList}" var="order">
			<tr>     
				<td>${order.parentOrder}</td>     
				<td>${order.sequenceId}</td>
				<td>${order.status}</td>
				<td><fmt:formatNumber value="${order.amount/1000}" pattern="#,##0.00"/></td>
				<td><fmt:formatDate value="${order.createDate}"  pattern="yyyy年MM月dd日 hh:mm:ss"/></td>
				<td>${order.payerMember}</td>
				<td>${order.payeeMember}</td>
				<td><a href="javascript:processSuccess('${order.sequenceId}')">设置成功</a></td>  
				<td><a href="javascript:processFail('${order.sequenceId}')">设置失败</a></td>  
			</tr>
			</c:forEach>
		</tbody> 
	</table>
</body>

