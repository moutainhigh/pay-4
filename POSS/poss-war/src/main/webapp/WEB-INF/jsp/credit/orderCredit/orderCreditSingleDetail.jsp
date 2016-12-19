<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>融资订单详情</title>
<link rel="stylesheet" type="text/css"
	href="./js/jquery/plugins/autocomplate/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css"
	href="./js/jquery/plugins/autocomplate/thickbox.css" />
<link href="${ctx}/js/jquery/plugins/validate/jquery.validate.css"
	type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/css/page.css">
<link href="${ctx}/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function search(pageNo, totalCount) {
	//$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo
			+ "&totalCount=" + totalCount + "&singDetail=true";
	$.ajax({
		type : "POST",
		url : "./orderCredit.do?method=orderCreditDetailList",
		data : pars,
		success : function(result) {
			//	$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
	
}
function back(){
	window.location.href='./orderCredit.do' ;
}
</script>
</head>
<body>
<h2 class="panel_title">融资订单详情</h2>
	<center>	
		<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th class="item15">会员号</th>
					<th class="item15">商户名称</th>
					<th class="item15">网关订单号</th>
					<th class="item15">授信流水号</th>
					<th class="item15">商户订单号</th>
					<th class="item15">订单金额</th>
					<th class="item15">授信金额</th>
					<th class="item10">利率</th>
					<th class="item10">服务费</th>
					<th class="item10">入账金额</th>
					<th class="item10">原清算时间</th>
					<th class="item10">入账时间</th>
					<th class="item10">申请时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderCreditDetails}" var="orderCreditDetail" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
						<td align="center">${orderCreditDetail.partnerId }</td>
						<td align="center">${orderCreditDetail.partnerName}</td>
						<td align="center">${orderCreditDetail.tradeOrderId}</td>
						<td align="center">${orderCreditDetail.creditDetailId}</td>
						<td align="center">${orderCreditDetail.orderId}</td>
						<td align="center"><fmt:formatNumber type="number"   value="${orderCreditDetail.orderAmount/1000.000 }" />&nbsp;${orderCreditDetail.tranCurrencyCode }</td>
						<td align="center"><fmt:formatNumber type="number"   value="${orderCreditDetail.creditAmount/1000.000 }" />&nbsp;${orderCreditDetail.creditCurrencyCode }</td>
						<td align="center">${orderCreditDetail.dayRate}%</td>
						<td align="center"><fmt:formatNumber type="number"   value="${orderCreditDetail.charge/1000.000 }" />&nbsp;${orderCreditDetail.creditCurrencyCode }</td>
						<td align="center"><fmt:formatNumber type="number"   value="${orderCreditDetail.accountAmount/1000.000 }" />&nbsp;${orderCreditDetail.creditCurrencyCode }</td>
						<td align="center">${orderCreditDetail.settlementDateStr}</td>
						<td align="center">${orderCreditDetail.accountDateStr}</td>
						<td align="center">${orderCreditDetail.applyDateStr}</td>
					</tr>
				</c:forEach>
				<%-- <tr>
					<td colspan="13" align="center"><li:pagination methodName="search" pageBean="${page}" sytleName="black2"/></td>
				</tr> --%>
			</tbody>
			
		</table>
		<br><br><br><br>
		<input type="button" onclick="back();"  value="关闭">
	</center>
</body>
</html>