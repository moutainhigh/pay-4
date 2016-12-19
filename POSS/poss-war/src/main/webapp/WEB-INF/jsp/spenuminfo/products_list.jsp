<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<body>
<form id="formBean" name="formBean" action="productsManager.do" method="post">
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trbgcolorForTitle" align="center" valign="middle">
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">名称</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">代码</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">类型</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">操作</font> </a></td>					
		</tr>
		<c:forEach items="${page.result}" var="dto" varStatus = "status">
		<c:choose>
		   <c:when test="${dto==null}">
	          <tr class="trForContent1">
	            <td class="border_top_right4" align="center" colspan="6" nowrap>无授权记录!</td>
	       </c:when>
	       <c:otherwise>
	          <tr class="trForContent2">
	  
				<td class="border_top_right4" align="center" nowrap>${dto.enumName}</td>
				<td class="border_top_right4" align="center" nowrap>${dto.enumCode}</td>
				<td class="border_top_right4" align="center" nowrap>经营范围</td>
				<td class="border_top_right4" align="center" nowrap>
					<a href="javascript:void(0)" onclick="updateSpEnumInfoDetail('${dto.enumId}','${dto.enumName}','${dto.enumCode}','${dto.value1}','${dto.value2}')">修改<a/>
					<a href="javascript:void(0)" onclick="delSpEnumInfo('${dto.enumId}','${dto.enumName}','${dto.enumCode}','${dto.value1}','${dto.value2}')">删除<a/>
					<a href="javascript:void(0)" onclick="showSpEnumInfoDetail('${dto.enumId}')">查看<a/>
				</td>
			</c:otherwise>
		</c:choose>				
		      </tr>
		</c:forEach>
	</table>	
</form>

<li:pagination methodName="indexQuery" pageBean="${page}" sytleName="black2"/>

</body>

