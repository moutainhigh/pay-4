<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>

<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>


<script language="javascript">
function addNewMenu(url){
	parent.addMenu("账户模板产品配置",url);
	
}
</script>
</head>

<body>

	<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
		<thead>
		<tr class="" align="center" valign="middle">
		
			<th class=""  >
				模板编号</th>
			<th class=""  >
				模板名称</th>
			<th class=""  >
				操作</th>
		</tr>
		</thead>
		<c:forEach items="${page.result}" var="accountTemp" varStatus = "accountTempStatus">
		<c:choose>
	       <c:when test="${accountTempStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td class="" align="center" >${accountTemp.accountTempId}&nbsp;</td>
				<td class="" align="center" >${accountTemp.accountTempName}&nbsp;</td>
				<td class="" align="center" >
					<a href="javascript:addNewMenu('accountTempJoinProduct.do?accountTempId=${accountTemp.accountTempId}');">配置产品</a>
				</td>
			</tr>
		</c:forEach>
	</table>	


<li:pagination methodName="accountTempQuery" pageBean="${page}" sytleName="black2"/>



</body>

