<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>


<script language="javascript">

function acctDeal(url){
	parent.addMenu("账户余额明细",url);
}

</script>
<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="./js/common.js"></script>
<script language="javascript">
</script>
</head>

<body>

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">登录名</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">会员号</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">账户号</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">账户类型</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">账户状态</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">总收入</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">总支出</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">余额</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">冻结余额</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">操作</font> </a></td>
	</tr>
	<c:forEach items="${page.result}" var="personal" varStatus = "personalStatus">
	<c:choose>
       <c:when test="${personalStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			
			<td class="border_top_right4" align="center" nowrap>${personal.loginName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.memberCode}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.acctCode}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>
				<c:if test="${personal.type=='10'}">人民币账户</c:if>
				<c:if test="${personal.type=='20'}">移动影子账户</c:if>
				<c:if test="${personal.type=='21'}">电信影子账户</c:if>
				<c:if test="${personal.type=='22'}">联通影子账户 </c:if>
				<c:if test="${personal.type=='23'}">骏网影子账户</c:if>
				<c:if test="${personal.type=='24'}">移动点账户</c:if>
				<c:if test="${personal.type=='25'}">电信点账户</c:if>
				<c:if test="${personal.type=='26'}">联通点账户</c:if>
				<c:if test="${personal.type=='27'}">骏网点账户</c:if>
			&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.status}&nbsp;</td>
			
			<td class="border_top_right4" align="center" nowrap>${personal.debitBalance}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.creditBalance}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.balance}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.frozenAmount}&nbsp;</td>
			
			<td class="border_top_right4" align="center" nowrap>
				<a href="javascript:acctDeal('personalAcctBalance.do?acctCode=${personal.acctCode}&loginName=${personal.loginName}&type=${personal.type}');">明细</a>&nbsp;
			</td>
		</tr>
	</c:forEach>
</table>
<li:pagination methodName="personAcctMessageQuery" pageBean="${page}" sytleName="black2"/>
</body>

