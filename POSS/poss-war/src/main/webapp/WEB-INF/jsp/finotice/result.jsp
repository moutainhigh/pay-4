<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
$(function(){
         $("#btnSubmit").click(function(){
				 document.sendForm.submit();
				 document.initform.submit();
         });
     });
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">重发通知-订单详情<c:out value="${param.searchType}"/></font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<br>
<table  width="100%" align="center" class="tableorder" border="1" cellSpacing="1" cellpadding="1">
<tr>
<th class="header">商家订单号</th>
<th class="header">收单商家名</th>
<th class="header">网关订单流水号</th>
<th class="header">支付订单流水号</th>
<th class="header">订单内容</th>
<th class="header">订单金额(元)</th>
<th class="header">支付方式</th>
<th class="header">渠道名称</th>
<th class="header">付款人</th>
<th class="header">支付状态</th>
<th class="header">订单状态</th>
<th class="header">收单时间</th>
<th class="header">支付完成时间</th>
</tr>
<c:if test = "${empty resultMap}">
<tr  rowspan="13">
<td>未查到该订单的成功付款信息</td>
</tr>
</c:if>
<c:if test = "${not (empty resultMap)}">
<tr>
<td><c:out value="${resultMap.orderId}"/></td>
<td><c:out value="${resultMap.payee}"/></td>
<td><c:out value="${resultMap.tradeOrderNo}"/></td>
<td><c:out value="${resultMap.paymentOrderNo}"/></td>
<td><c:out value="${resultMap.goodsName}"/></td>
<td><c:out value="${resultMap.orderAmount}"/></td>
<td><c:out value="${resultMap.paytype_cn}"/></td>
<td><c:out value="${resultMap.orgName}"/></td>
<td><c:out value="${resultMap.payer}"/></td>
<td><c:out value="${resultMap.paystatus_cn}"/></td>
<td><c:out value="${resultMap.orderstatus_cn}"/></td>
<td><c:out value="${resultMap.createDate}"/></td>
<td><c:out value="${resultMap.updateDate}"/></td>
</tr>
</c:if>
</table>
<form name="resendForm" action="${ctx}/sendNotice.htm?method=onSubmit" method="post">
<input type="hidden" name="tradeOrderNo" value="<c:out value='${resultMap.tradeOrderNo}'/>">
<input type="hidden" name="sendType" value="1">
<input type="submit" value="重发">
</form>
<div id="resultListDiv" class="listFence"></div>