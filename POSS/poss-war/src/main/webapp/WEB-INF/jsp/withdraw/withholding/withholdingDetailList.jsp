<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<script type="text/javascript">
		function query(pageNo,totalCount,pageSize) {
			$('#infoLoadingDiv').dialog('open');
			var parsPage = "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/withholding.htm?method=queryWithholdingDetail&fileKy=${fileKy}",
				data:parsPage,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
		}
		</script>
	</head>
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>商户交易号</th>
					<th>代扣人姓名</th>
					<th>支付平台账号</th>
					<th>金额</th>
					<th>流水状态</th>
					<th>失败原因</th>
					<th>创建时间</th>
					<th>修改时间</th>
				</tr>
			</thead>
			<tbody id="tb">
				<c:forEach var="obj" items="${page.result}" varStatus="v"> 
					<tr>  
						<td>
							${obj.merchantOrderId}
						</td>
						<td>
							${obj.payerName}
						</td>
						<td>
							${obj.payerLoginName}
						</td>
						<td>
							${obj.orderAmount}
						</td>
						<td>
							${obj.payOrderId}
						</td>
						<td>
							${obj.errorMsg}
						</td>
						<td>
							<fmt:formatDate value="${obj.createDate}" type="date" />
						</td>
						<td>
							<fmt:formatDate value="${obj.updateDate}" type="date" />
						</td>
					</tr>                   
				</c:forEach>                   
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>