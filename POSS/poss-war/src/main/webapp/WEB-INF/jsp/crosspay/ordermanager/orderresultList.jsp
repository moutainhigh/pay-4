<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		$(document).ready(function(){
			 $("#userTable").tablesorter({
				 headers: {
				 	3:{sorter: false},
				 	12: {sorter: false}
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
				<th>交易号</th>
				<th>订单号</th>
				<th>订单金额（外币）</th> 
				<th>订单金额（人民币）</th> 
				<th>会员号</th>
				<th>所属网站</th>
				<th>商户EMAIL</th>
				<th>订单时间</th>
				<th>订单状态</th>
				<th>卡类型</th>
				<th>详情</th>
			</tr> 
		</thead>
		<tbody>
			<c:forEach items="${page.result}" var="orderResult">
				<tr>
					<td>${orderResult.trade_order_no}</td>
					<td>${orderResult.order_id}</td>
					<td><fmt:formatNumber value="${orderResult.order_amount * 0.001}" pattern="#,##0.00"/></td>
					<td><fmt:formatNumber value="${orderResult.ext_order_info_8 * 0.001}" pattern="#,##0.00"/></td>
					<td>${orderResult.payer}</td>
					<td>${orderResult.trade_url}</td>
					<td>${orderResult.email}</td>
					<td>${orderResult.create_date}</td>
					<td>
					<c:if test="${orderResult.orderstatus=='4'}">交易成功</c:if>
					<c:if test="${orderResult.orderstatus=='2'}">交易成功</c:if>
					<c:if test="${orderResult.orderstatus=='3'}">退款</c:if>
					<c:if test="${orderResult.orderstatus=='1'}">进行中</c:if>
					<c:if test="${orderResult.frozenStatus=='1'}">冻结</c:if>
					<c:if test="${orderResult.frozenStatus=='0'}">交易成功</c:if>
					<c:if test="${orderResult.refuseStatus=='1'}">拒付</c:if>
					<c:if test="${orderResult.refuseStatus=='2'}">交易成功</c:if>
					</td>
					<td>
					<c:if test="${orderResult.card_type=='1'}">VISA</c:if>
					<c:if test="${orderResult.card_type=='2'}">MASTER</c:if>
					<c:if test="${orderResult.card_type=='3'}">JCB</c:if>
					</td>
					<td><a href="javascript:showUrl('订单详情','${ctx}/crosspay/orderQuery.do?method=viewOrder&tradeOrderNo=${orderResult.tradeOrderNo}')">详情</a></td>
				</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="orderQuery" pageBean="${page}" sytleName="black2"/>
</body>

