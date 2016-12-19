<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<body>
<form id="formBean" name="formBean" action="frozenLog.do" method="post">
	<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead>
		<tr class="trbgcolorForTitle" align="center" valign="middle">
			<th class=""  nowrap>
				登录名</th>
			<th class=""  nowrap>
				IP地址</th>
			<th class=""  nowrap>
				姓名</th>	
			<th class=""  nowrap>
				部门</th>		
			<th class=""  nowrap>
				创建时间</th>
			<th class=""  nowrap>
				更新时间</th>	
			<th class=""  nowrap>
				状态</th>	
			<th class="" nowrap >
				操作</th>				
		</th></thead>
		<c:forEach items="${page.result}" var="dto" varStatus = "status">
		<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td class="" align="center" nowrap>${dto.loginName}</td>
				<td class="" align="center" nowrap>${dto.authIp}&nbsp;</td>	
				<td class="" align="center" nowrap>${dto.authUser}&nbsp;</td>
				<td class="" align="center" nowrap>${dto.authDept}&nbsp;</td>
				<td class="" align="center" nowrap><fmt:formatDate value="${dto.creationDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>	
				<td class="" align="center" nowrap><fmt:formatDate value="${dto.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>	
				<td class="" align="center" nowrap>${dto.status==1?"有效":"无效"}&nbsp;</td>	
					<td class="" align="center" nowrap>
						<a href="javascript:void(0)" class="button2" onclick="editAuthIp('${dto.authId}')">修改<a/>
						<a href="javascript:void(0)" class="button2" onclick="deleteAuthIp('${dto.authId}')">删除<a/>
					</td>						
			</tr>
		</c:forEach>
	</table>	
</form>

<li:pagination methodName="indexQuery" pageBean="${page}" sytleName="black2"/>

</body>

