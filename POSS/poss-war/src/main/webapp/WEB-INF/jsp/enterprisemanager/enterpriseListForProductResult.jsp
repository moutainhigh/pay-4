<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<script language="javascript">
function forEditProductOfEnterprise(url){
	parent.addMenu("会员产品设置",url);
	
}


</script>
</head>

<body>

<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
	<tr class="" align="center" valign="middle">
		<th class=""  >
			商户号</th>
		<th class=""  >
			商户名称</th>
		<th class=""  >
			登录名</th>		
		<th class=""  >
			操作</th>
	</tr>
	</thead>
	<c:forEach items="${page.result}" var="enterprise" varStatus = "enterpriseStatus">
	<c:choose>
       <c:when test="${enterpriseStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			<td class="" align="center" >${enterprise.merchantCode}&nbsp;</td>
			<td class="" align="center" >${enterprise.merchantName}&nbsp;</td>
			<td class="" align="center" >${enterprise.loginName}&nbsp;</td>
			<td class="" align="center" >
			<a href="javascript:forEditProductOfEnterprise('productOfEnterpriseEdit.do?memberCode=${enterprise.memberCode}&type=view');">查看产品信息</a>
			<a href="javascript:forEditProductOfEnterprise('productOfEnterpriseEdit.do?memberCode=${enterprise.memberCode}&type=edit');">配置产品信息</a>
			</td>
			
		</tr>
	</c:forEach>
</table>

<li:pagination methodName="enterpriseQuery" pageBean="${page}" sytleName="black2"/>


</body>

