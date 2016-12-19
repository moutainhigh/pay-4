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
function changeMessage(url){
	parent.addMenu("修改商户信息",url);
}
</script>
</head>

<body>

<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
	<tr class="" align="center" valign="middle">
		<th class=""  >
			会员号</th>
		<th class=""  >
			商户号</th>
		<th class=""  >
			商户名称</th>
		<th class=""  >
			登录名</th>
		<th class=""  >
			会员状态</th>
		<th class=""  >
			操作</th>
		
		

	</tr>
	</thead>
	<c:forEach items="${page.result}" var="merchant" varStatus = "merchantStatus">
	<c:choose>
       <c:when test="${merchantStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>
			<td class="" align="center" >${merchant.memberCode}&nbsp;</td>			
			<td class="" align="center" >${merchant.merchantCode}&nbsp;</td>
			<td class="" align="center" >${merchant.merchantName}&nbsp;</td>
			<td class="" align="center" >${merchant.loginName}&nbsp;</td>
			<td class="" align="center" >
				<c:forEach items="${merchantStatusEnum}" var="merchantStatus">
					<c:if test="${merchantStatus.code == merchant.memberStatus}">
						${merchantStatus.description}
					</c:if>
				</c:forEach>
			&nbsp;
			</td>						
			<td class="" align="center" >
				<a href="javascript:changeMessage('enterpriseMessageForChange.do?memberCode=${merchant.memberCode}');">&nbsp;&nbsp;修改登录名&nbsp;&nbsp;</a>
			</td>
		</tr>
	</c:forEach>
</table>

<li:pagination methodName="enterpriseQuery" pageBean="${page}" sytleName="black2"/>


</body>

