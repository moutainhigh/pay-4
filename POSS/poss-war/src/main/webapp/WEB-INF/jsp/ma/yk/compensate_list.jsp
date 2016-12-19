<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<body>
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trbgcolorForTitle" align="center" valign="middle">
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">流水号</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">卡号</font> </a></td>
				<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">充值金额</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">充值时间</font></a></td>
			
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">付款状态</font></a></td>	
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">充值状态</font></a></td>	
			<td class="border_right4" nowrap ><a class="s03"><font
				color="#FFFFFF">操作</font></a></td>				
		</tr>
		<c:forEach items="${page.result}" var="dto" varStatus = "status">
		<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td class="border_top_right4" align="center" nowrap>${dto.orderNo}</td>
				<td class="border_top_right4" align="center" nowrap>${dto.cardNo}</td>
				<td class="border_top_right4" align="center" nowrap><fmt:formatNumber value="${dto.amountDouble/1000}"  type="currency" /> </td>
				<td class="border_top_right4" align="center" nowrap><fmt:formatDate value="${dto.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>	
					
					<td class="border_top_right4" align="center" nowrap>
						${dto.processStatusDesc}
					</td>
					<td class="border_top_right4" align="center" nowrap>
						<c:set var="exSts" value="${dto.externalProcessStatusDesc}"></c:set>
						${exSts != "" ?exSts:dto.processStatusDesc}
					</td>
					<td class="border_top_right4" align="center" nowrap>
						<c:if test="${dto.refundAble}">
						<a href="javascript:void(0)" onclick="aplayRefund('${dto.orderNo}')">申请退款</a>
						</c:if> &nbsp;
					</td>	
												
			</tr>
		</c:forEach>
	</table>	

<li:pagination methodName="indexQuery" pageBean="${page}" sytleName="black2"/>

</body>

