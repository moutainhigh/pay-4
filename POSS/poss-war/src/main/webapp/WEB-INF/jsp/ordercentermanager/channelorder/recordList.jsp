<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 <meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0"> 
<script type="text/javascript">
function order(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/channelOrderQuery.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
				}
	});
} 
</script>
<form action="" method="post" name="listFrom">
</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
	<thead>
		<tr>
			<th>渠道订单号</th>
			<th>通道名称</th>
			<th>支付订单号</th>
			<th>二级商户号</th>
			<th>授权码</th>
			<!-- <th>卡号</th>
			<th>发卡国家</th> -->
			<th>会员号</th>
			<th>网关订单号</th>
			<th nowrap="nowrap">交易金额</th>
			<th nowrap="nowrap">交易币种</th>
			<th nowrap="nowrap">支付金额</th>
			<th nowrap="nowrap">支付币种</th>
			<th nowrap="nowrap">支付汇率</th>
			<th nowrap="nowrap">状态</th>
			<th>创建时间</th>
			<th>完成时间</th>
			<th nowrap="nowrap">是否对账</th>
			<th nowrap="nowrap">结算汇率</th>
			<th nowrap="nowrap">返回码</th>
			<th nowrap="nowrap">返回消息</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="order" varStatus="index">
		<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
			<td><span></span>${order.channelOrderNo}<span></span></td>
			<td>
				<c:choose>
					<c:when test="${order.orgCode=='0000000'}">测试通道</c:when>
					<c:when test="${order.orgCode=='10076001'}">中银卡司</c:when>
					<c:when test="${order.orgCode=='10075001'}">CREDORAX</c:when>
					<c:when test="${order.orgCode=='10003001'}">中国银行</c:when>
					<c:when test="${order.orgCode=='10002001' }">农业银行</c:when>
					<c:when test="${order.orgCode=='10080001' }">中银MIGS</c:when>
					<c:when test="${order.orgCode=='10079001' }">中银MOTO</c:when>
					<c:when test="${order.orgCode=='10078001' }">农行CTV</c:when>
					<c:when test="${order.orgCode=='10077003' }">Cashu</c:when>
					<c:when test="${order.orgCode=='10077002' }">Boleto</c:when>
					<c:when test="${order.orgCode=='10081001' }">ct_boleto</c:when>
					<c:when test="${order.orgCode=='10081002' }">ct_safetyPay</c:when>
					<c:when test="${order.orgCode=='10081003' }">ct_DebitCard</c:when>
					<c:when test="${order.orgCode=='10081004' }">ct_sofortbanking</c:when>
					<c:when test="${order.orgCode=='10081005' }">ct_eps</c:when>
					<c:when test="${order.orgCode=='10081006' }">ct_giropay</c:when>
					<c:when test="${order.orgCode=='10081007' }">ct_pagDebitCard</c:when>
					<c:when test="${order.orgCode=='10081008' }">ct_PagBrasilOTF</c:when>
					<c:when test="${order.orgCode=='10081009' }">ct_poli</c:when>
					<c:when test="${order.orgCode=='10081010' }">ct_przelewy24</c:when>
					<c:when test="${order.orgCode=='10081011' }">ct_qiwi</c:when>
					<c:when test="${order.orgCode=='10081012' }">ct_sepa</c:when>
					<c:when test="${order.orgCode=='10081013' }">ct_teringso</c:when>
					<c:when test="${order.orgCode=='10081014' }">ct_trustPay</c:when>
					<c:when test="${order.orgCode=='10081015' }">ct_ideal</c:when>
					<c:when test="${order.orgCode=='10081016' }">前海万融</c:when>
					<c:otherwise>未知机构</c:otherwise> 
				</c:choose>
			</td>
			<td>${order.paymentOrderNo}</td>
			<td>${order.merchantNo}</td>
			<td>${order.authorisation}</td>
			<!-- <td>&nbsp;</td>
			<td>&nbsp;</td> -->
			<td>${order.partnerId }</td>
			<td>${order.tradeOrderNo }</td>
			<td>${order.amount/1000}</td>
			<td>${order.currencyCode}</td>
			<td> <fmt:formatNumber type="number"   value="${order.payAmount/1000}" /> </td>
			<td>${order.transferCurrencyCode}</td>
			<td>${order.transferRate}</td>
			<td>
				<!--0：冻结1：正常2：待审核3：审核未通过4：已删除  --> 
				<c:if test="${order.status=='0'}">
					进行中
				</c:if>
				<c:if test="${order.status=='1'}">
					成功
				</c:if>
				<c:if test="${order.status=='2'}">
					失败
				</c:if>
			</td>
			<td><date:date value="${order.createDate}"/></td>
			<td><date:date value="${order.completeDate}"/></td>
			<td>
				<c:if test="${order.reconciliationFlg=='0'}">
					未对账
				</c:if>
				<c:if test="${order.reconciliationFlg=='1'}">
					已对账
				</c:if>
				<!-- add by davis.guo 2016-08-26 begin-->
				<c:if test="${order.reconciliationFlg=='2'}">
					对账失败
				</c:if>
				<c:if test="${order.reconciliationFlg=='4'}">
					对账成功,记账失败
				</c:if>
				<c:if test="${order.reconciliationFlg=='8'}">
					需要补单
				</c:if>
				<!-- add by davis.guo 2016-08-26 end-->
			</td>
			<td>${order.settlementRate }</td>
			<td>${order.errorCode }</td>
			<td>${order.errorMsg }</td>
		</tr>
		</c:forEach>
		
	</tbody>
</table>
<li:pagination methodName="order" pageBean="${page}" sytleName="black2"/>