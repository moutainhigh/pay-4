<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台出款订单成功置失败</title>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
</head>
<body>

<p>
<p align="left"><em><strong>订单成功置失败确认信息</strong></em>
<form action="${ctx}/ebill_channelOrderprocess.do?method=process" method="post">
<input type="hidden" name="backSeqId" value="${backSeqId}"/>
<input type="hidden" name="innerOrderId" value="${innerOrderId}"/>
<input type="hidden" name="outerOrderId" value="${outerOrderId}"/>
<input type="hidden" name="status" value="${status}"/>
<input type="hidden" name="description" value="${desc}"/>
<table width="421" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
<tr>
<td width="165" align="right" bgcolor="#FFFFFF">FO退款流水号：</td>
<td width="184" align="left" bgcolor="#FFFFFF">${backSeqId}</td>
</tr>
<tr>
<td align="right" bgcolor="#FFFFFF">FO原交易流水号：</td>
<td align="left" bgcolor="#FFFFFF">${innerOrderId}</td>
</tr>
<tr>
<td align="right" bgcolor="#FFFFFF">APP交易流水号：</td>
<td align="left" bgcolor="#FFFFFF">${outerOrderId}</td>
</tr>
<tr>
<td align="right" bgcolor="#FFFFFF">FO退款状态：</td>
<td align="left" bgcolor="#FFFFFF"><c:if test="${status != null && status == 111}">退款成功</c:if>
<c:if test="${status != null && status == 101}">退款进行中</c:if>
<c:if test="${status != null && status == 112}">退款失败</c:if></td>
</tr>
<tr>
<td align="right" bgcolor="#FFFFFF">原因描述：</td>
<td align="left" bgcolor="#FFFFFF">
${desc}</td>
</tr>
<tr>
<td colspan="2" align="center" bgcolor="#FFFFFF"><input type="submit" value="确认操作" />
<input type="button" onClick="javascript:window.location.href='${ctx}/ebill_channelOrderprocess.do?orderId='+${orderId}+'&descrition='+${description};" value="返回"/>
</td>
</tr>
</table>
</form>
</p>
</body>
</html>