<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<body>
<form id="formBean" name="formBean" action="artificialfrozenBalance.do" method="post">
	<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
		<thead>
		<tr class="" align="center" valign="middle">
			<th class=""  >
				会员号</th>
			<th class=""  >
				登录名</th>
			<th class=""  >
				账户号</th>	
			<th class=""  >
				可用余额</th>
			<th class=""  >
				冻结金额</th>
			<th class=""  >
				操作</th>				
		</tr></thead>
		
		<c:choose>
	       <c:when test="${dto==null}">
	           <tr class="trForContent1">
	             <td class="border_top_right4" align="center" colspan="6" >无授权记录!</td>
	       </c:when>
	       <c:otherwise>
	          <c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
	            <td class="border_top_right4" align="center" >${dto.memberCode}</td>
				<td class="border_top_right4" align="center" >${dto.loginName}</td>
				<td class="border_top_right4" align="center" >${dto.acctCode}</td>
				<td class="border_top_right4" align="center" ><fmt:formatNumber value="${dto.balance*0.001}"  type="currency" /> </td>
				<td class="border_top_right4" align="center" ><fmt:formatNumber value="${dto.frozenAmount*0.001}"  type="currency"/></td>							
				<td class="border_top_right4" align="center" >
				    <c:if test="${isfrozenAmount>0}">
						<a href="javascript:void(0)" onclick="freeFrozenBalance('${dto.memberCode}','${dto.acctCode}','${dto.balance}','${dto.frozenAmount}','${isfrozenAmount}')">解冻<a/>
					</c:if>
						<a href="javascript:void(0)" onclick="showFrozenBalanceDetail('${dto.memberCode}','${isfrozenAmount}')">查看<a/>
				</td>	
	       </c:otherwise>
		</c:choose>	
			</tr>	
	</table>	
</form>

</body>

