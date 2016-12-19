<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>批量到银行有未生成子订单的总订单列表</title>
		<script type="text/javascript">
			function genOrder(uploadSeq,massOrderSeq,btn){
				btn.setAttribute("disabled",true);
				window.location="${ctx}/orderMassPayToBank.htm?method=generatorOrder&uploadSeq="+uploadSeq+"&massOrderSeq="+massOrderSeq;
			}

			function toDetail(uploadSeq,massOrderSeq){
				window.location="${ctx}/orderMassPayToBank.htm?method=noChildDetail&uploadSeq="+uploadSeq+"&massOrderSeq="+massOrderSeq;
			}
		</script>
	</head>
	
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<caption>批量到银行有未生成子订单的总订单列表</caption>
			<thead>
				<tr>
					<th >序号</th>
					<th >交易流水</th>
					<th >上传批次号</th>
					<th >付款会员号</th>
					<th >付款总笔数</th>
					<th >付款总金额（元）</th>
					<th >总费用（元）</th>
					<th >状态</th>
					<th >订单创建时间</th>
					<th >批次上传时间</th>
					<th >操作</th>
					<th >详情</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="order" items="${orderList}" varStatus="v">
					<tr>
						<td>${v.index+1}</td>
						<td>${order.massOrderSeq}</td>
						<td>${order.businessNo}</td>
						<td>${order.payerMemberCode}</td>
						<td>${order.totalNum}</td>
						<td><fmt:formatNumber value="${order.totalAmount * 0.001}" pattern="#,##0.00"/></td>
						<td><fmt:formatNumber value="${order.totalFee * 0.001}" pattern="#,##0.00"/></td>
						<td>${order.status==102?"处理中":""}</td>
						<td><fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td> 
						<td><fmt:formatDate value="${order.uploadDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td> 
						<td> 
							<input type="button" value="生成子订单" class="button4" onclick="genOrder('${order.uploadSeq}','${order.massOrderSeq}',this)">
						</td>
						<td>
							<a href="javascript:void(0);" onclick="toDetail('${order.uploadSeq}','${order.massOrderSeq}')">详情</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
	</body>
</html>