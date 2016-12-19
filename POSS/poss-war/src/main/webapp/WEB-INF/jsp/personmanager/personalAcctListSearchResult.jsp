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


function memberDetail(url){
	parent.addMenu("会员详情信息",url);
}
function operateAcctMessage(url){
	parent.addMenu("账户信息",url);
}
function operateHistory(url){
	parent.addMenu("登录历史查询",url);

}

function operate(url){
	window.location = url;
}
</script>
</head>

<body>

<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
	<tr class="" align="center" valign="middle">
		<th class=""  >登录名</th>
		<th class=""  >会员号</th>
		<th class=""  >姓名</th>
		<th class=""  >会员状态</th>
		<th class=""  >email</th>
		<th class=""  >手机</th>
		<th class=""  >注册时间</th>
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
			<td class="" align="center" >
				<a href="javascript:operateAcctMessage('personalMemberAcctMessage.do?loginName=${personal.loginName}');">${personal.loginName}</a>&nbsp;&nbsp;
			</td>
			<td class="" align="center" >${personal.memberCode}&nbsp;</td>
			<td class="" align="center" >${personal.userName}&nbsp;</td>
			<td class="" align="center" >${personal.statusName}&nbsp;</td>
			<td class="" align="center" >${personal.email}&nbsp;</td>
			<td class="" align="center" >${personal.mobile}&nbsp;</td>
			<td class="" align="center" >${personal.createDateName}&nbsp;</td>
			<td class="" align="center" >
				<c:if test="${cusSerFlag !='customerSer'}">
					<a href="javascript:memberDetail('personalMemberInfoDetail.do?memberCode=${personal.memberCode}');">查看详情</a>&nbsp;
				</c:if>
				<c:if test="${cusSerFlag =='customerSer'}">
					<a href="javascript:memberDetail('personalMemberInfoDetail.do?memberCode=${personal.memberCode}&cusSerFlag=customerSer');">客服查看详情</a>&nbsp;
				</c:if>
				<c:if test="${cusSerFlag !='customerSer'}">
					<c:if test="${personal.statusName=='正常'}">
						<a href="javascript:operate('personAcctInfoSearch.do?memberCode=${personal.memberCode}&operate=frozen');">&nbsp;冻结</a>&nbsp;
					</c:if>
					<c:if test="${personal.statusName=='冻结'}">
						<a href="javascript:operate('personAcctInfoSearch.do?memberCode=${personal.memberCode}&operate=unFrozen');">&nbsp;解冻</a>&nbsp;
					</c:if>
				</c:if>
			</td>
		</tr>
	</c:forEach>
</table>
<c:if test="${cusSerFlag !='customerSer'}">
	<li:pagination methodName="personQuery" pageBean="${page}" sytleName="black2"/>
</c:if>
<c:if test="${cusSerFlag =='customerSer'}">
	<li:pagination methodName="serSerch" pageBean="${page}" sytleName="black2"/>
</c:if>
</body>

