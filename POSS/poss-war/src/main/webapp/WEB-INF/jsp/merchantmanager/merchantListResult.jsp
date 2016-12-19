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
			color="#FFFFFF">用户名</font> </a></td>
		<!--<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">商户类型 </font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">行业</font> </a></td>
		--><td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">企业名称</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">网站地址</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">省份</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">城市</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">联系人</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">联系电话</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">用户状态</font> </a></td>

	</tr>
	<c:forEach items="${page.result}" var="merchant" varStatus = "merchantStatus">
	<c:choose>
       <c:when test="${merchantStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			<td class="border_top_right4" align="center" nowrap>${merchant.userName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${merchant.businessName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${merchant.website}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${merchant.province}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${merchant.city}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${merchant.contactPerson}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${merchant.contactPhone}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${merchant.statusString}&nbsp;</td>
		</tr>
	</c:forEach>
</table>

<li:pagination methodName="merchantQuery" pageBean="${page}" sytleName="black2"/>


</body>

