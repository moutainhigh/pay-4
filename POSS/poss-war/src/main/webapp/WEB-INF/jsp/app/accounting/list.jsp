<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台补记账</title>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

</head>
<body>
<c:if test="${errorMsg != null}">
	<p align="left"><font color="red">${errorMsg}</font></p>
</c:if>

<p>
<c:if test="${detail != null}">
<form action="${ctx}/ebill_manualAccounting.do?method=process" method="post">
<input type="hidden" name="orderId" value="${orderId}"/>
<input type="hidden" name="orderAmount" value="${orderAmount}"/>
<input type="hidden" name="payerFee" value="${payerFee}"/>
<input type="hidden" name="payeeFee" value="${payeeFee}"/>
<input type="hidden" name="billType" value="${billType}"/>
<input type="hidden" name="hasCaculatedPrice" value="${hasCaculatedPrice}"/>
<input type="hidden" name="description" value="${description}"/>
<input type="hidden" name="type" value="${type}"/>
<table width="506" height="106" cellspacing="1" bordercolor="#CCCCCC" bgcolor="#CCCCCC">
<tr>
<td width="248" bgcolor="#FFFFFF"><div align="center">账号</div></td>
<td width="178" bgcolor="#FFFFFF"><div align="center">金额</div></td>
<td width="64" bgcolor="#FFFFFF"><div align="center">借/贷</div></td>
</tr>
<c:forEach items="${detail}" var="acct">
<tr>
<td align="left" bgcolor="#FFFFFF">${acct.acctcode}</td>
<td bgcolor="#FFFFFF">
<div align="center">
<c:if test="${payerFee != null && billType != 12}">${payerFee/1000}</c:if>
<c:if test="${payeeFee != null && billType != 12}">${payeeFee/1000}</c:if>
<c:if test="${billType == 12}">${acct.value/1000}</c:if>
</div></td>
<td bgcolor="#FFFFFF">
<c:if test="${acct.crdr == 1}">
  <div align="center">借</div>
</c:if>
<c:if test="${acct.crdr == 2}">
  <div align="center">贷</div>
</c:if></td>
</tr>
</c:forEach>
<tr>
	<td align="center" bgcolor="#FFFFFF">备注</td>
	<td colspan="2" bgcolor="#FFFFFF">${description}</td>
</tr>
<tr>
<td colspan="3" align="center" bgcolor="#FFFFFF"><input type="submit" value="补记账"/><input type="button" onClick="javascript:window.location.href='${ctx}/ebill_manualAccounting.do?orderId='+${orderId}+'&descrition='+${description};" value="返回"/></td>
</tr>
</table>
</form>
</c:if>

</p>
</body>
</html>