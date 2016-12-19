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

function addNewMenu(id){
	parent.addMenu("商户信息",'${ctx}/operatorSearchList.do?method=gotoDetail&operatorId=' + id);
	
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
			用户名</th>
		<th class=""  >
			操作员</th>
		<th class=""  >
			类型</th>
		<th class=""  >
			状态</th>
		<th class=""  >
			操作</th>
	</tr>
	</thead>
	<c:forEach items="${page.result}" var="operator" varStatus = "operatorStatus">
	<c:choose>
       <c:when test="${operatorStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>
			<td class="" align="center" >${operator.memberCode}&nbsp;</td>
			<td class="" align="center" >${operator.merchantCode}&nbsp;</td>
			<td class="" align="center" >${operator.merchantName}&nbsp;</td>
			<td class="" align="center" >${operator.loginName}&nbsp;</td>
			<td class="" align="center" >${operator.identity}&nbsp;</td>
			<td class="" align="center" >
				<c:choose>
					<c:when test="${operator.name != null && operator.name == 'admin' }">
					管理员
					</c:when>
					<c:otherwise>
					普通操作员
					</c:otherwise>
				</c:choose>
			&nbsp;
			</td>
			<td class="" align="center" >
				<c:forEach items="${operatorEnums}" var="item">
					<c:if test="${item.code == operator.status}">
						${item.desc}
					</c:if>
				</c:forEach>
			&nbsp;
			</td>						
			<td class="" align="center" >
			<a href="javascript:addNewMenu('${operator.operatorId}');">详情</a>
			&nbsp;
			</td>
		</tr>
	</c:forEach>
</table>
<li:pagination methodName="operatorQuery" pageBean="${page}" sytleName="black2"/>
</body>