<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>



<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="./js/common.js"></script>
<script language="javascript">

</script>
</head>

<body>

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">商户号</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">商户名称</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">流水号</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">服务号</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">服务名称</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">商户日期</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">商户时间</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">响应码</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">响应名称</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">备注</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">接收者</font> </a></td>
	</tr>
	<c:forEach items="${page.result}" var="smsLog" varStatus = "smsLogStatus">
	<c:choose>
       <c:when test="${smsLogStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			<td class="border_top_right4" align="center" nowrap>${smsLog.merchantCode}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${smsLog.merchantName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${smsLog.orderId}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${smsLog.websvrCode}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${smsLog.websvrName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap><fmt:formatDate value="${smsLog.merchantDate}" pattern="yyyy-MM-dd"/>&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap><fmt:formatDate value="${smsLog.merchantTime}" pattern="HH:mm:ss"/>&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${smsLog.responseCode}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${smsLog.responseDesc}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${smsLog.remark}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${smsLog.receiver}&nbsp;</td>
		</tr>
	</c:forEach>
</table>

<li:pagination methodName="smsLogQuery" pageBean="${page}" sytleName="black2"/>


</body>

