<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<script language="javascript">
function forEditAccountOfEnterprise(url){
	parent.addMenu("会员账户设置",url);
	
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
			color="#FFFFFF">登录名</font> </a></td>		
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">操作</font> </a></td>
		

	</tr>
	<c:forEach items="${page.result}" var="enterprise" varStatus = "enterpriseStatus">
	<c:choose>
       <c:when test="${enterpriseStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			<td class="border_top_right4" align="center" nowrap>${enterprise.merchantCode}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${enterprise.merchantName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${enterprise.loginName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap><a href="javascript:forEditAccountOfEnterprise('accountOfEnterpriseEdit.do?memberCode=${enterprise.memberCode}');">开通账户</a></td>
		</tr>
	</c:forEach>
</table>

<li:pagination methodName="enterpriseQuery" pageBean="${page}" sytleName="black2"/>


</body>

