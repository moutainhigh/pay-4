<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		function showDetail(detailUrl,workflowKy){
			parent.addTabMenu('查看复核数据详细',detailUrl,workflowKy+"liquidate");
		}
	</script>
</head>

<body>
	<form id="orderForm">
	<li id="infoMsg" style="color: red;display: none">
		</li>
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>序号</th>   
				<th>交易号</th> 
				<th>批次号</th> 
				<th>会员号</th>
				<th >商户名称</th> 
				<th >结算周期</th>
				<th>目的银行</th>
				<th>出款银行</th>
				<th>银行账户</th>
				<th>汇款金额（元）</th>
				<th>收款人</th>
				<th>状态</th>
				<th>操作</th> 
			</tr> 
		</thead> 
		<tbody id="tb">
			<c:forEach items="${page.result}" var="orderDto" varStatus="status">
			<tr>     
				<td><c:out value="${status.count}" /></td>  
				<td>${orderDto.sequenceId}</td>  
				<td>${orderDto.batchNo}</td>
				<td>${orderDto.memberCode}</td>
				<td>${orderDto.memberName}</td> 
				<td><li:code2name itemList="${accountModeList}" selected="${orderDto.accountMode}"/></td>
				<td><li:code2name itemList="${withdrawBankList}" selected="${orderDto.bankKy}"/></td>
				<td><li:code2name itemList="${withdrawBankList}" selected="${orderDto.withdrawBankCode}"/></td>
				<td>${orderDto.bankAcct}</td>
				<td><fmt:formatNumber value="${orderDto.amount/1000}" pattern="#,##0.00"  /></td>
				<td>${orderDto.accountName}</td>
				<td>${orderDto.statusStr}</td>
				<td><a href="javascript:showDetail('${ctx}/withdrawLiquidateAudit.do?method=showProcessingOrderDetail&orderId=${orderDto.sequenceId}&workOrderKy=${orderDto.workOrderky}&nodeId=2','${orderDto.sequenceId}')">查看</a></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
	<li:pagination methodName="withdrawQuery" pageBean="${page}" sytleName="black2"/>
</body>

