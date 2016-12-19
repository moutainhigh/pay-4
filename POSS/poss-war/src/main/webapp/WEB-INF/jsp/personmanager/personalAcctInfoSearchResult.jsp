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
function operate(url){
	location.href = url;
	//location.href = "personalMemberOperate.do?id=" + id;
}

function acctDeal(url){
	parent.addMenu("账户余额明细",url);
}
function show(){
	
}
</script>
</head>

<body onload=show();>
<form>
<input	type="hidden" id="frozen" name="frozen"  value="${personal.frozen}">
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">账户号</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">用户名称</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">登录名</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">账户类型</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">账户状态</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">可用余额</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">冻结</font> </a></td>
		

		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">止入</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">止出</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">账户创建时间</font> </a></td>
		<c:if test="${cusSerFlag !='customerSer'}">
			<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">操作</font> </a></td>
		</c:if>
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
			
			<td class="border_top_right4" align="center" nowrap>
			<!--  
				<a href="javascript:acctDeal('personalAcctBalance.do?acctCode=${personal.acctCode}&loginName=${personal.loginName}&type=${personal.type}');">明细</a>&nbsp;
				<a href="javascript:acctDeal('personalAcctBalance.do?acctCode=${personal.acctCode}');">${personal.acctCode}</a>&nbsp;
			-->
				${personal.acctCode}&nbsp;
			</td>
			<td class="border_top_right4" align="center" nowrap>${personal.userName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.loginName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.typeName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.status}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.balance}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.frozen}&nbsp;</td>

			<td class="border_top_right4" align="center" nowrap>${personal.allowIn}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.allowOut}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${personal.createDate}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>
			<c:if test="${cusSerFlag !='customerSer'}">
				<c:if test="${personal.frozen=='是'}"><a href="javascript:operate('personalMemberOperate.do?acctCode=${personal.acctCode}&operate=unFrozen');">解冻</a>&nbsp;</c:if>
				<c:if test="${personal.frozen=='否'}"><a href="javascript:operate('personalMemberOperate.do?acctCode=${personal.acctCode}&operate=frozen');">&nbsp;冻结</a></c:if>
				<!--  当账户为冻结时不给其有止入或者止出的操作(不为冻结时放开止入止出的操作)-->
				<c:if test="${personal.frozen!='是'}">
					<c:if test="${personal.allowIn=='否'}"><a href="javascript:operate('personalMemberOperate.do?acctCode=${personal.acctCode}&operate=allowIn');">&nbsp;止入</a>&nbsp;</c:if>	
					<c:if test="${personal.allowIn=='是'}"><a href="javascript:operate('personalMemberOperate.do?acctCode=${personal.acctCode}&operate=unAllowIn');">&nbsp;解除止入</a>&nbsp;</c:if>
					<c:if test="${personal.allowOut=='否'}"><a href="javascript:operate('personalMemberOperate.do?acctCode=${personal.acctCode}&operate=allowOut');">&nbsp;止出</a>&nbsp;</c:if>	
					<c:if test="${personal.allowOut=='是'}"><a href="javascript:operate('personalMemberOperate.do?acctCode=${personal.acctCode}&operate=unAllowOut');">&nbsp;解除止出</a>&nbsp;</c:if>
				</c:if>	
			</c:if>		
			</td>
		</tr>
	</c:forEach>
</table>
</form>

<c:if test="${cusSerFlag !='customerSer'}">
	<li:pagination methodName="personAcctInfoQuery" pageBean="${page}" sytleName="black2"/>
</c:if>
<c:if test="${cusSerFlag =='customerSer'}">
	<li:pagination methodName="serSerch" pageBean="${page}" sytleName="black2"/>
</c:if>
</body>

