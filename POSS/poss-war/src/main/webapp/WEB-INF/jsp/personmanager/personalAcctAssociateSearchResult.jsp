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
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">关联会员号</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">姓名</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">会员标示</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">账户号</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">IP</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">操作动作</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">登录时间</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">会员状态</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">账户状态</font> </a></td>

	</tr>
	<c:forEach items="${page.result}" var="personal" varStatus = "personalStatus">
	<c:choose>
       <c:when test="${personalStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			<td class="border_top_right4" align="center" nowrap>${personal.memberCode}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.userName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.loginName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.acctCode}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.loginIp}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.actionUrl}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.loginDate}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.memberStatus}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.acctStatus}&nbsp;</td>
			
		</tr>
	</c:forEach>
</table>
<li:pagination methodName="personalAcctAssociateQuery" pageBean="${page}" sytleName="black2"/>
</body>

