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
function processEdit(merchantCode){
			location.href = "${ctx}/beautyLandSmsInfoEdit.htm?merchantCode=" + merchantCode;
		}
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
			color="#FFFFFF">商户IP</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">商户密钥</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">备注</font> </a></td>
	</tr>
	<c:forEach items="${page.result}" var="smsInfo" varStatus = "smsInfoStatus">
	<c:choose>
       <c:when test="${smsInfoStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>	
			<td class="border_top_right4" align="center" nowrap><a href="javascript:processEdit('${smsInfo.merchantCode}')">${smsInfo.merchantCode}</a>&nbsp;</td>		
			<td class="border_top_right4" align="center" nowrap>${smsInfo.merchantName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${smsInfo.merchantIp}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${smsInfo.merchantKey}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${smsInfo.remark}&nbsp;</td>
		</tr>
	</c:forEach>
</table>

<li:pagination methodName="merchantQuery" pageBean="${page}" sytleName="black2"/>


</body>

