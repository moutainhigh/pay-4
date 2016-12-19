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

function addNewMenu(url){
	parent.addMenu("商户信息",url);
	
}

function forEditAccountOfEnterprise(url){
	parent.addMenu("会员账户设置",url);
	
}

</script>
</head>

<body>

<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<th class=""  >
			会员号</th>
		<th class=""  >
			商户号</th>
		<th class=""  >
			商户名称</th>
		<th class=""  >
			登录名</th>
		<!--<th class=""  >
			频道名称</th>-->
		<th class=""  >
			创建时间</th>
		<th class=""  >
			会员状态</th>
		<th class=""  >
			所属销售</th>
		<th class=""  >
			操作</th>

	</tr></thead>
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
			<!--<td class="" align="center" >${merchant.signDepart}&nbsp;</td>-->
			<td class="" align="center" >${merchant.createDate}&nbsp;</td>
			<td class="" align="center" >
				<c:forEach items="${merchantStatusEnum}" var="merchantStatus">
					<c:if test="${merchantStatus.code == merchant.memberStatus}">
						${merchantStatus.description}
					</c:if>
				</c:forEach>
			&nbsp;
			</td>
			<td class="" align="center" >
				${merchant.signName}
				&nbsp;
			</td>
							
			<td class="" align="left" >
			<a href="javascript:addNewMenu('enterpriseView.do?memberCode=${merchant.memberCode}&userRelation=${userRelation.id}');">查看</a>
			&nbsp;
				<c:if test="${empty userRelation}">
			<c:if test="${merchant.memberStatus==1}">
				<a href="enterpriseListForInfo.do?memberCode=${merchant.memberCode}&memberStatus=2">冻结</a>
			</c:if>
			<c:if test="${merchant.memberStatus==2}">
				<a href="enterpriseListForInfo.do?memberCode=${merchant.memberCode}&memberStatus=1">解冻</a>
			</c:if>
			<a href="javascript:forEditAccountOfEnterprise('accountOfEnterpriseEdit.do?memberCode=${merchant.memberCode}');">开通账户</a>
			</td>
		</c:if>
		</tr>
	</c:forEach>
</table>

<li:pagination methodName="enterpriseQuery" pageBean="${page}" sytleName="black2"/>


</body>

