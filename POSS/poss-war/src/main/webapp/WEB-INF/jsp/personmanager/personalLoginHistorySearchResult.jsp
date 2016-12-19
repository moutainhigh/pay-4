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


function operate(url){
	parent.addMenu("疑似关联账户",url);
}

</script>
</head>

<body>

<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
	<tr class="" align="center" valign="middle">
		<th class=""  >姓名</th>
		<th class=""  >登录用户名</th>
		<th class=""  >会员号</th>
		<th class=""  >会员类型</th>
		<th class=""  >登录IP</th>
		<th class=""  >操作动作</th>
		<th class=""  >日志类型</th>
		<th class=""  >登录时间</th>
		<th class=""  >操作</th>
	</tr>
	</thead>
	<c:forEach items="${page.result}" var="personal" varStatus = "personalStatus">
	<c:choose>
       <c:when test="${personalStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			
			<td class="" align="center" >${personal.name}&nbsp;</td>
			<td class="" align="center" >${personal.loginName}&nbsp;</td>
			<td class="" align="center" >${personal.memberCode}&nbsp;</td>
			<td class="" align="center" >${personal.memberType}&nbsp;</td>
			<td class="" align="center" >${personal.loginIp}&nbsp;</td>
			<td class="" align="center" >${personal.actionUrl}&nbsp;</td>
			<td class="" align="center" >${personal.logType}&nbsp;</td>
			<td class="" align="center" >${personal.loginDate}&nbsp;</td>
			<td class="" align="center" >
				<a href="javascript:operate('personalAcctAssociate.do?loginIp=${personal.loginIp}');">IP关联账户</a>&nbsp;
			</td>
		</tr>
	</c:forEach>
</table>
<li:pagination methodName="personalLogHistoryQuery" pageBean="${page}" sytleName="black2"/>
</body>

