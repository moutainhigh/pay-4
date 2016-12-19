<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<body>
<form id="formBean" name="formBean" action="conreg.do" method="post">
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trbgcolorForTitle" align="center" valign="middle">
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">交易流水号</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">商户号</font> </a></td>
				<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">状态</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">金额费用</font></a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">姓名</font></a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">证件号码</font></a></td>	
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">创建时间</font></a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">更新时间</font></a></td>	
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
			<td class="border_top_right4" align="center" nowrap>${dto.conregOrderNo}</td>
			<td class="border_top_right4" align="center" nowrap>${dto.partnerId}</td>
			<td class="border_top_right4" align="center" nowrap>
				<c:if test="${dto.status=='0'}">初始化</c:if>
				<c:if test="${dto.status=='1'}">认证成功</c:if>
				<c:if test="${dto.status=='2'}">认证失败</c:if>
			</td>
			<td class="border_top_right4" align="center" nowrap><fmt:formatNumber value="${dto.conregAmt/1000}"  type="currency"/></td>
			<td class="border_top_right4" align="center" nowrap>${dto.realName}</td>	
			<td class="border_top_right4" align="center" nowrap>${dto.idNumber}</td>	
			<td class="border_top_right4" align="center" nowrap><fmt:formatDate value="${dto.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td class="border_top_right4" align="center" nowrap><fmt:formatDate value="${dto.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		</c:forEach>
	</table>	
</form>

<li:pagination methodName="indexQuery" pageBean="${page}" sytleName="black2"/>

</body>

