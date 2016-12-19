<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>
<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="./js/common.js"></script>
</head>

<body>
<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead><tr class="" align="center" valign="middle">
		<th class=""  >
			机构编号</th>
		<th class=""  >
			机构名称</th>
		<th class=""  >
			机构类型</th>
		<th class=""  >
			描述</th>
		<th class=""  >
			创建日期</th>
		<th class=""  >
			是否在提现时显示</th>
		<th class=""  >
			是否在充值时显示</th>
		<th class=""  >
			是否在还信用卡时显示</th>
		<th class=""  >
			是否渠道</th>
		<th class=""  >
			显示名称</th>

	</tr>
	</thead>
	<c:forEach items="${pageList}" var="org" varStatus = "merchantStatus">
	<c:choose>
       <c:when test="${merchantStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			<td class="" align="center" ><a href="javascript:forCheck('${org.orgCode}');">${org.orgCode}</a>&nbsp;</td>
			<td class="" align="left" >${org.orgName}&nbsp;</td>
			<td class="" align="center" >
				<c:if test="${org.orgType==1 }">借记卡</c:if>
				<c:if test="${org.orgType==2 }">贷记卡</c:if>
				<c:if test="${org.orgType==3 }">信用卡</c:if>
			</td>
			<td class="" align="left" >${org.description}&nbsp;</td>
			<td class="" align="center" ><fmt:formatDate value="${org.creationDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td class="" align="center" >
				<c:if test="${org.displayForWithdraw==0 }">否</c:if>
				<c:if test="${org.displayForWithdraw==1 }">是</c:if>
			</td>
			<td class="" align="center" >
				<c:if test="${org.displayForDeposit==0 }">否</c:if>
				<c:if test="${org.displayForDeposit==1 }">是</c:if>
			</td>
			<td class="" align="center" >
				<c:if test="${org.displayForCredit==0 }">否</c:if>
				<c:if test="${org.displayForCredit==1 }">是</c:if>
			</td>
			<!-- add by davis.guo 2016-08-11 -->
			<td class="" align="left" >
				<c:if test="${org.displayForChannel==0 }">否</c:if>
				<c:if test="${org.displayForChannel==1 }">是</c:if>
			</td>
			<td class="" align="left" >${org.displayName}&nbsp;</td>
		</tr>
	</c:forEach>
</table>


</body>

