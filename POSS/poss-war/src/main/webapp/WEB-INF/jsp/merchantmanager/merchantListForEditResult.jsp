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
function forCheck(url){
	parent.addMenu("商户编辑",url);
	
}


</script>
</head>

<body>

<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
	<tr class="" align="center" valign="middle">
		<th class="border_right4"  >
			商户号</th>
		<th class="border_right4"  >
			会员号</th>
		<th class="border_right4"  >
			登录名</th>
		<th class="border_right4"  >
			商户名称</th>
		<th class="border_right4"  >
			商户类型</th>
		<th class="border_right4"  >
			行业</th>
		<th class="border_right4"  >
			商户等级</th>
		<th class="border_right4"  >
			审核状态</th>
		<th class="border_right4"  >
			商户状态</th>
		

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
			<td class="" align="center" ><a href="javascript:forCheck('enterpriseView.do?memberCode=${merchant.memberCode}');">${merchant.merchantCode}</a>&nbsp;</td>
			<td class="" align="center" >${merchant.memberCode}</td>
			<td class="" align="center" >${merchant.email}</td>
			<td class="" align="center" >${merchant.merchantName}&nbsp;</td>
			<td class="" align="center" >${merchant.merchantType}&nbsp;</td>
			<td class="" align="center" >${merchant.industry}&nbsp;</td>
			<td class="" align="center" >${merchant.serviceLevel}&nbsp;</td>
			<td class="" align="center" >${merchant.merchantCheckStatus}&nbsp;</td>
			<td class="" align="center" >${merchant.merchantStatus}&nbsp;</td>
		
		</tr>
	</c:forEach>
</table>

<li:pagination methodName="merchantQuery" pageBean="${page}" sytleName="black2"/>


</body>

