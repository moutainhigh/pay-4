<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<body>
<form id="formBean" name="formBean" action="frozenLog.do" method="post">
	<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
		<thead>
		<tr class="" align="center" valign="middle">
			<th class=""  >
				会员号</th>
			<th class=""  >
				登录名</th>
				<th class=""  >
				可用余额</th>
			<th class=""  >
				冻结/解冻金额</th>
			<th class=""  >
				创建时间</th>
			<th class=""  >
				更新时间</th>	
			<th class=""  >
				流水号</th>
			<th class=""  >
				原流水号</th>	
			<th class=""  >
				冻结/解冻</th>					
			<th class=""  >
				状态</th>	
			<th class=""  >
				备注</th>	
			<th class=""  >
				操作</th>				
		</tr></thead>
		<c:forEach items="${page.result}" var="dto" varStatus = "status">
		<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td class="" align="center" >${dto.memberCode}</td>
				<td class="" align="center" >${dto.loginName}</td>
				<td class="" align="center" ><fmt:formatNumber value="${dto.balanceDouble/1000}" /> </td>
				<td class="" align="center" ><fmt:formatNumber value="${dto.amountDouble/1000}"  /></td>
				<td class="" align="center" ><fmt:formatDate value="${dto.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>	
				<td class="" align="center" ><fmt:formatDate value="${dto.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>	
				<td class="" align="center" >${dto.serialNo}&nbsp;</td>	
				<td class="" align="center" >${dto.oldSerialId}&nbsp;${dto.acctType}</td>
				<td class="" align="center" >${dto.frozenType==1?"冻结":"解冻"}&nbsp;</td>		
				<td class="" align="center" >${dto.status==1?"成功":(dto.status==2?"已解冻":"失败")}&nbsp;</td>
				<td class="" align="center" >${dto.description}&nbsp;</td><!-- add by davis.guo at 2016-08-10 -->
					<td class="" align="center" >
						<c:if test="${dto.status==1 && dto.frozenType==1 }">
								<a href="javascript:void(0)" onclick="freeFrozenLog('${dto.memberCode}','${dto.id}','${dto.serialNo}','<fmt:formatNumber value="${dto.amountDouble/1000}"  type="currency"/>')">解冻<a/>
						</c:if>
						<a href="javascript:void(0)" onclick="showFrozenDetail('${dto.id}')">查看<a/>
					</td>						
			</tr>
		</c:forEach>
	</table>	
</form>

<li:pagination methodName="indexQuery" pageBean="${page}" sytleName="black2"/>

</body>

