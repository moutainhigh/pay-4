<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<style type="text/css"> 
.excel_txt {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: black;
	font-size: 11.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: "\@";
	text-align: general;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}
</style> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付平台支付分账付款交易汇总表</title>
</head>
<body>
	<table width="1200" border="1">
	<thead>
		<tr>
			<td>标题：</td>
			<td colspan="2">支付平台支付分账付款交易汇总表</td>
			<td colspan="9"></td>
	    </tr>
		<tr>
			<td>分账笔数</td>
			<td colspan="2">${count}</td>
			<td colspan="9"></td>
		</tr>
		<tr>
		   <td>分账总金额</td>
		   <td colspan="2"><fmt:formatNumber value="${sumAmount*0.001}" pattern=",##0.00"/></td>
		   <td colspan="9"></td>
		</tr>
		<tr>
		   <td>对账开始日期</td>
		   <td colspan="2">${startTime}</td>
		   <td colspan="9"></td>
		</tr>
		<tr>
		    <td>对账结束日期</td>
		    <td colspan="2">${endTime}</td>
		    <td colspan="9"></td>
		 </tr>
		<tr>
			<td style="width:8%" >支付平台交易号</td>
			<td style="width:8%" >交易起始时间</td>
			<td style="width:8%" >交易结束时间</td>
			<td style="width:8%" >交易类型</td>
			<td style="width:8%" >商家订单号</td>
			<td style="width:8%" >购买方订单金额(元)</td>
			<td style="width:9%" >付款方账号</td>
			<td style="width:9%" >付款方名称</td>						
			<td style="width:9%" >收款方名称</td>
			<td style="width:9%" >收款方账号</td>
			<td style="width:8%" >分账金额(元)</td>
			<td style="width:8%" >手续费(元)</td>
			
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${sharingPaymentList}" var="ls">
			<tr>
			<!--付款方-->
			
			<c:choose>
				<c:when test="${ls.partnerIdentity eq 'Y'}">
					<td class="excel_txt">${ls.tradeOrderNo}</td>
					<td class="excel_txt"><fmt:formatDate value="${ls.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="excel_txt"><fmt:formatDate value="${ls.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="excel_txt">分账付款</td>
					<td class="excel_txt">${ls.orderId}</td>
					<td class="excel_txt"><fmt:formatNumber value="${ls.orderAmount*0.001}" pattern="#0.00"/></td>
					<td class="excel_txt">${ls.payerLoginName}</td>
					<td class="excel_txt">${ls.payerName}</td>			
				</c:when>
				<c:when test="${ls.partnerIdentity eq 'N'}">	
					<td class="excel_txt"></td>
					<td class="excel_txt"></td>
					<td class="excel_txt"></td>
					<td class="excel_txt"></td>
					<td class="excel_txt"></td>
					<td class="excel_txt"></td>
					<td class="excel_txt"></td>
					<td class="excel_txt"></td>
				</c:when>
			</c:choose>
				<td class="excel_txt">${ls.payeeLoginName}</td>
				<td class="excel_txt">${ls.payeeName}</td>
				<td class="excel_txt"><fmt:formatNumber value="${ls.sharingAmount*0.001}" pattern="#0.00"/></td>
				<td class="excel_txt"><fmt:formatNumber value="${ls.fee*0.001}" pattern="#0.00"/></td>	
			 </tr>
		</c:forEach>
	</tbody>
</table>
</body>
</html>