<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="li" uri="poss"%>

<head>

<script language="javascript">
function addMenuLabel(url){
	parent.addMenu("账户余额明细",url);
	
}



</script>
</head>

<body>
<c:set var="ASC_DESC_F" value=""></c:set>
<c:if test="${enterpriseSearchDto.ascOrDesc == 'asc' }" ><c:set var="ASC_DESC_F" value="↑"></c:set></c:if>
<c:if test="${enterpriseSearchDto.ascOrDesc == 'desc' }" ><c:set var="ASC_DESC_F" value="↓"></c:set></c:if>

<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
	<tr class="" align="center" valign="middle">
		<th class=""  >
			会员号</th>
		<th class=""  >
			商户号</th>
		<th class=""  >
			商户名称</th>
		<th class=""  >
			登陆名</th>
		<th class=""  >
			账户号 </th>	
		<th class=""  >
			账户类型 </th>	
		<th class=""  >
			总收入</th>	
		<th class=""  >
			总支出 </th>	
		<th class=""  ><a class="s03" onclick="orderBy('blanceOrder')" style="cursor: pointer;"><font
			余额 </font><font color="red"><c:if test="${enterpriseSearchDto.orderParam == 'blanceOrder' }">${ASC_DESC_F}</c:if></th>	
		<th class=""  >
			冻结余额</th>
			
		<th class=""  >
			账户状态</th>
		<th class=""  >
			所属销售</th>
		<th class=""  >
			操作</th>
		

	</tr></thead>
	<c:forEach items="${page.result}" var="account" varStatus = "accountStatus">
	<c:choose>
       <c:when test="${accountStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>	
			<td class="" align="center" >${account.memberCode}&nbsp;</td>		
			<td class="" align="center" >${account.merchantCode}&nbsp;</td>
			<td class="" align="center" >${account.merchantName}&nbsp;</td>			
			<td class="" align="center" >${account.loginName}&nbsp;</td>
			<td class="" align="center" >${account.accountCode}&nbsp;</td>
			<td class="" align="center" >
				<c:forEach items="${accountTypeList}" var="accountType">
					<c:if test="${accountType.code == account.accountType}">
						${accountType.displayName}
					</c:if>
				</c:forEach>
			&nbsp;
			</td>
			<!--会员账户表收支总额是以我司为立场记账,会员显示账户收支总额时和我司借贷方向相反  -->
			<td class="" align="center" >${account.creditBalance}&nbsp;</td>
			<td class="" align="center" >${account.debitBalance}&nbsp;</td>
			<td class="" align="center" >${account.balance}&nbsp;</td>
			<td class="" align="center" >${account.frozenBalance}&nbsp;</td>
			<td class="" align="center" >
				<c:forEach items="${accountStatusEnum}" var="accountStatus">
					<c:if test="${accountStatus.code == account.accountStatus}">
						${accountStatus.description}
					</c:if>
				</c:forEach>
			&nbsp;
			</td>
			<td class="" align="center" >
				${account.signName}
				&nbsp;
			</td>
			<td class="" align="center" ><a href="javascript:addMenuLabel('detailOfAccountList.do?accountCode=${account.accountCode}');">余额明细</a></td>
		</tr>
	</c:forEach>
</table>

<li:pagination methodName="initQuery" pageBean="${page}" sytleName="black2"/>
</body>

