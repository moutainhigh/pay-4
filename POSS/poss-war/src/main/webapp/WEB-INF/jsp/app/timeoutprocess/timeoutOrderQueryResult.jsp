<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<c:if test="${queryResult != null}">
<style>
.table_a, .table_a td{ padding:10px; border:1px solid #ddd; border-collapse:collapse;}
</style>
<table border="0" cellspacing="1" background="#ddd" class="table_a">
<tr>
<td align="center" nowrap="nowrap" bgcolor="#dddddd">订单号</td>
<c:if test="${billType != null && billType == 12}">
<td align="center" nowrap="nowrap" bgcolor="#dddddd">手机号码</td>
</c:if>
<td align="center" nowrap="nowrap" bgcolor="#dddddd">申请金额</td>
<c:if test="${billType != null && billType == 12}">
<td align="center" nowrap="nowrap" bgcolor="#dddddd">完成金额</td>
</c:if>
<td align="center" nowrap="nowrap" bgcolor="#dddddd">充值状态</td>
<td align="center" nowrap="nowrap" bgcolor="#dddddd">入款流水号</td>
<td align="center" nowrap="nowrap" bgcolor="#dddddd">出款流水号</td>
<td align="center" nowrap="nowrap" bgcolor="#dddddd">渠道状态</td>
<td align="center" nowrap="nowrap" bgcolor="#dddddd">渠道名称</td>
<td align="center" nowrap="nowrap" bgcolor="#dddddd">创建时间</td>
<c:if test="${billType != null && billType == 12}">
<td align="center" nowrap="nowrap" bgcolor="#dddddd">最大等待时间</td>
</c:if>
<td align="center" nowrap="nowrap" bgcolor="#dddddd">付款方</td>
<td align="center" nowrap="nowrap" bgcolor="#dddddd">收款方姓名</td>
<td align="center" nowrap="nowrap" bgcolor="#dddddd">收款方</td>
<td align="center" nowrap="nowrap" bgcolor="#dddddd">操作</td>
</tr>
<c:forEach items="${queryResult}" var="result">
	<tr>
		<td height="20" align="center" valign="middle" bgcolor="#ffffff">${result.chargeOrderId}</td>
		<c:if test="${billType != null && billType == 12}">
		<td height="20" align="center" valign="middle" bgcolor="#ffffff">${result.mobileNo}</td>
		</c:if>
		<td height="20" align="center" valign="middle" bgcolor="#ffffff">${result.applyAmount}元</td>
		<c:if test="${billType != null && billType == 12}">
		<td height="20" align="center" valign="middle" bgcolor="#ffffff">${result.acceptedAmount}元</td>
		</c:if>
		<td height="20" align="center" valign="middle" bgcolor="#ffffff">
			<c:if test="${result.itemStatus == 1}">支付成功</c:if>
		</td>
		<td height="20" align="center" valign="middle" bgcolor="#ffffff">${result.gatewayTradeNo}</td>
		<td height="20" align="center" valign="middle" bgcolor="#ffffff">${result.channelOrderId}</td>
		<td height="20" align="center" valign="middle" bgcolor="#ffffff">
			<c:if test="${result.channelStatus == 999}">提交成功</c:if>
			<c:if test="${result.channelStatus == 101}">初始化</c:if>
		</td>
		<td height="20" align="center" valign="middle" bgcolor="#ffffff">${result.channelCode}</td>
		<td height="20" align="center" valign="middle" bgcolor="#ffffff">${result.createDate}</td>
		<c:if test="${billType != null && billType == 12}">
		<td height="20" align="center" valign="middle" bgcolor="#ffffff">${result.maxWaitingTime}分钟</td>
		</c:if>
		<td height="20" align="center" valign="middle" bgcolor="#ffffff">${result.payerIdContent}</td>
		<td height="20" align="center" valign="middle" bgcolor="#ffffff">${result.payeeName}</td>
		<td height="20" align="center" valign="middle" bgcolor="#ffffff">${result.payeeMemberCode}</td>
		<td height="20" align="center" valign="middle" bgcolor="#ffffff">
		<a href="${ctx}/timeoutprocess.do?method=toProcess&billType=${result.billType}&orderId=${result.chargeOrderId}&channelOrderId=${result.channelOrderId}&requestAmount=${result.applyAmount}">操作</a>
		</td>
	</tr>
</c:forEach>
</table>
</c:if>
<c:if test="${queryResult == null}">
没有订单记录！
</c:if>
