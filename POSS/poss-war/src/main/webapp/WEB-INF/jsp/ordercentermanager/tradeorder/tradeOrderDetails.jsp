<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>网关订单详情</title>
<link rel="stylesheet" type="text/css"
	href="./js/jquery/plugins/autocomplate/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css"
	href="./js/jquery/plugins/autocomplate/thickbox.css" />
<link href="${ctx}/js/jquery/plugins/validate/jquery.validate.css"
	type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/css/page.css">
<link href="${ctx}/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function back(){
		window.location.replace("${ctx}/tradeOrderQuery.do?method=index");
	}

</script>
</head>
<body>
<h2 class="panel_title">网关订单详情</h2>
<table class="border_all2" width="100%" border="0" cellspacing="0" cellpadding="1" style="margin:0 auto;" align="center">
			<tr class="trForContent1">
				<td colspan="13" class="border_top_right4"><font size="4">商户名称:<span>${map.enName}</span></font>
				</td>
				<td colspan="13" class="border_top_right4"><font size="4">商户订单号:<span>${map.orderId}</span></font>
				</td >
				<td colspan="13" class="border_top_right4"><font size="4">网关订单号:<span>${map.tradeOrderNo}</span></font>
				</td>
			</tr>
	</table>

	<center>	
		<table align="center" width="100%" border="1" cellspacing="1" cellpadding="1">
			
			<tr class="trForContent1">
				<td colspan="13" class="item15" align="left" colspan="" colspan="13"><font size="4">基本信息</font></td>
			</tr>
		</table>
		<table align="center" width="100%" border="1" cellspacing="1" cellpadding="1">
	
			<tr class="trForContent2">
				<td colspan="13" colspan="13" class="item15" colspan="13"><font size="3">订单金额:<span>${map.paymentAmount*1}</span></font>
				</td>
				<td colspan="13" class="item15" ><font size="3">订单币种:<span>${map.orderCurrencyCode}</span></font>
				</td>
				<td colspan="13" class="item15" ><font size="3">网关订单状态:<span>
		<c:choose>
			<c:when test="${map.tradeStatus=='0'}">
				未付款
			</c:when>
			<c:when test="${map.tradeStatus=='4'}">
				支付成功
			</c:when>
			<c:when test="${map.tradeStatus=='1'}">
				支付关闭
			</c:when>
			<c:when test="${map.tradeStatus=='2'}">
				已付款
			</c:when>
			<c:when test="${map.tradeStatus=='3'}">
				支付成功
			</c:when>
			<c:when test="${map.tradeStatus=='5'}">
				支付失败
			</c:when>
		</c:choose>
				</span></font>
				</td>
			</tr>
			<tr class="trForContent2">
				<td colspan="13" class="item15"><font size="3">已退款金额:<span>${map.refundAmount*1}</span></font>
				</td>
				<td colspan="13" class="item15"><font size="3">可退款金额:<span>${map.canRefundAmount*1}</span></font>
				</td >
				<td colspan="13" class="item15"><font size="3">退款中金额:<span>
					<c:choose>
			<c:when test="${map.tradeStatus=='0'}">
				0.0
			</c:when>
			<c:when test="${map.tradeStatus=='4'}">
				${map.refundingAmount}
			</c:when>
			<c:when test="${map.tradeStatus=='1'}">
				0.0
			</c:when>
			<c:when test="${map.tradeStatus=='2'}">
				${map.refundingAmount}
			</c:when>
			<c:when test="${map.tradeStatus=='3'}">
				${map.refundingAmount}
			</c:when>
			<c:when test="${map.tradeStatus=='5'}">
				0.0
			</c:when>
		</c:choose>
			</span></font>
			</tr>
			<tr class="trForContent2">
				<td colspan="13" class="item15"><font size="3">交易网址:<span>${map.siteId}</span></font>
				</td>
				<td colspan="13" class="item15"><font size="3">商户通知网址:<span>${map.notifyMerchantUrl}</span></font>
				</td>
				<td colspan="13" class="item15"><font size="3">结算币种:<span>${map.settleCurrencyCode}</span></font>
				</td>
			</tr>
			<tr class="trForContent2">
				<td colspan="13" class="item15"><font size="3">网关创建时间:<span><date:date value="${map.tradeCreateDate}"/></span></font>
				</td>
				<td colspan="13" class="item15"><font size="3">网关完成时间:<span><date:date value="${map.tradeUpdateDate}"/></span></font>
				</td>
				<td colspan="13" class="item15"><font size="3">商品名称:<span>${map.goodsName}</span></font>
				</td>
			</tr>
			<tr class="trForContent2">
				<td colspan="13" class="item15"><font size="3">下单IP:<span>${map.customerIP}</span></font>
				</td>
				<td colspan="13" class="item15"><span></span>
				</td>
				<td colspan="13" class="item15"><span></span>
				</td>
			</tr>
			<tr class="trForContent2">
				<td colspan="13" class="item15"><font size="3">账单姓名:<span>${map.billName}</span></font>
				</td>
				<td colspan="13" class="item15"><font size="3">账单国家:<span>${map.billCountryCode}</span></font>
				</td>
				<td colspan="13" class="item15"><font size="3">账单州:<span>${map.billState}</span></font>
			</tr>
			<tr class="trForContent2">
				<td colspan="13" class="item15"><font size="3">账单城市:<span>${map.billCity}</span></font>
				</td>
				<td colspan="13" class="item15"><font size="3">账单街道:<span>${map.billStreet}</span></font>
				</td>
				<td colspan="13" class="item15"><font size="3">账单地址:<span>${map.billAddress}</span></font>
				</td>
			</tr>
			<tr class="trForContent2">
				<td colspan="13" class="item15"><font size="3">账单邮编:<span>${map.billPostalCode}</span></font>
				</td>
				<td colspan="13" class="item15">
				<font size="3">	账单邮箱:<span>${map.billEmail}</span></font>
				</td>
				<td colspan="13" class="item15">
				<font size="3">	账单电话:<span>${map.billPhoneNumber }</span></font>
				</td>
			</tr>
			<tr class="trForContent2">
				<td colspan="13" class="item15">
				<font size="3">	收货姓名:<span>${map.shippingFullname}</span></font>
				</td>
				<td colspan="13" class="item15">
				<font size="3">	收货国家:<span>${map.shippingCountryCode}</span></font>
				</td>
				<td colspan="13" class="item15">
				<font size="3">	收货州:<span>${map.shippingState}</span></font>
				</td>
			</tr>
			<tr class="trForContent2">
				<td colspan="13" class="item15">
				<font size="3">	收货城市:<span>${map.shippingCity}</span></font>
				</td>
				<td colspan="13" class="item15">
				<font size="3">	收货街道:<span>${map.shippingStreet}</span></font>
				</td>
				<td colspan="13" class="item15"><font size="3">收货地址:<span>${map.shippingStreet}</span></font>
				</td>
			</tr>
			<tr class="trForContent2">
				<td colspan="13" class="item15">
				<font size="3">	收货邮编:<span>${map.shippingZip}</span></font>
				</td>
				<td colspan="13" class="item15">
				<font size="3">	收货邮箱:<span>${map.shippingMail}</span></font>
				</td>
				<td colspan="13" class="item15">
					<font size="3">收货电话:<span>${map.shippingPhone}</span></font>
				</td>
			</tr>
		</table>
		<table align="center" width="100%" border="1" cellspacing="1" cellpadding="1">
			
			<tr class="trForContent1">
				<td colspan="13" class="item15" align="left" colspan="" colspan="13"><font size="4">支付信息</font></td>
			</tr>
		</table>
		<table align="center" width="100%" border="1" cellspacing="1" cellpadding="1">
		<tr class="trForContent2">
			<td class="item15" align="center">渠道订单号</td>
			<td class="item15" align="center">渠道</td>
			<td class="item15" align="center">二级商户号</td>
			<td class="item15" align="center">支付金额</td>
			<td class="item15" align="center">支付币种</td>
		<!-- 	<td class="item15" align="center">交易汇率</td> -->
			<td class="item15" align="center">渠道订单状态</td>
			<td class="item15" align="center">卡号</td>
			<td class="item15" align="center">发卡国家</td>
			<td class="item15" align="center">机构端流水号</td>
			<td class="item15" align="center">授权码</td>
			<td class="item15" align="center">渠道订单创建时间</td>
			<td class="item15" align="center">渠道订单完成时间</td>
		</tr>
	  <tr class="trForContent2">
			<td class="item15" align="center">${map.channelOrderNo}</td>
			<td class="item15" align="center">${map.channelCode}</td>
			<td class="item15" align="center">${map.orgMerchantCode }</td>
			<td class="item15" align="center">${map.payAmount}</td>
			<td class="item15" align="center">${map.payCurrencyCode}</td>
			<%-- <td class="item15" align="center">${map.tradeRate}</td> --%>
			<td class="item15" align="center">
			<c:choose>
			<c:when test="${map.chnOrderStatus=='0'}">
				处理中
			</c:when>
			<c:when test="${map.chnOrderStatus=='1'}">
				成功
			</c:when>
			<c:when test="${map.chnOrderStatus=='2'}">
				失败
			</c:when>
		</c:choose>
			</td>
			<td class="item15" align="center">${map.cardNo }</td>
			<td class="item15" align="center">${map.issuingCardCountry}</td>
			<td class="item15" align="center">${map.orgReturnNo }</td>
			<td class="item15" align="center">${map.authCode }</td>
			<td class="item15" align="center"><date:date value="${map.chnOrderCreateDate }"/></td>
			<td class="item15" align="center"><date:date value="${map.chnOrderUpdateDate }"/></td>
		</tr > 
		</table>
		<table align="center" width="100%" border="1" cellspacing="1" cellpadding="1">
			<tr class="trForContent1">
				<td colspan="13" class="item15" align="left" colspan="" colspan="13"><font size="4">退款信息</font></td>
			</tr>
		</table>
		<table align="center" width="100%" border="1" cellspacing="1" cellpadding="1">
			<tr class="trForContent2">
			<td class="item15" align="center">退款订单号</td>
			<td class="item15" align="center">渠道</td>
			<td class="item15" align="center">退款来源</td>
			<td class="item15" align="center">退款金额</td>
			<td class="item15" align="center">退款币种</td>
			<td class="item15" align="center">渠道退款金额</td>
			<td class="item15" align="center">渠道退款币种</td>
			<td class="item15" align="center">退款状态</td>
			<td class="item15" align="center">退款创建时间</td>
			<td class="item15" align="center">退款完成时间</td>
			</tr>
		 <c:forEach items="${RefundOrder}" var="ro" varStatus="index">
		 <tr class="trForContent2">
			<td class="item15" align="center">${ro.refundOrderNo}</td>
			<td class="item15" align="center">${map.channelCode}</td>
			<td class="item15" align="center">${ro.refundSource}</td>
			<td class="item15" align="center">${ro.refundAmount}</td>
			<td class="item15" align="center">${channlResults.currencyCode}</td>
			<td class="item15" align="center">${ro.payAmount}</td>
			<td class="item15" align="center">${channlResults.transferCurrencyCode}</td>
			<td class="item15" align="center">
				<c:if test="${ro.status=='0'}">
					进行中
				</c:if>
				<c:if test="${ro.status=='1'}">
					进行中
				</c:if>
				<c:if test="${ro.status=='2'}">
					成功
				</c:if>
				<c:if test="${ro.status=='3'}">
					失败
				</c:if>
				<c:if test="${ro.status=='4'}">
					进行中
				</c:if>
				<c:if test="${ro.status=='5'}">
					进行中
				</c:if>
				<c:if test="${ro.status=='6'}">
					失败
				</c:if>

			</td>
			<td class="item15" align="center"><date:date value="${ro.createDate }"/></td>
			<td class="item15" align="center"><date:date value="${ro.updateDate }"/></td>
		</tr > 
		</c:forEach>
		</table>
		<br>
		<input type="button" onclick="back();" style="width:60px; height:30px;"   value="关闭">
	</center>
</body>
</html>