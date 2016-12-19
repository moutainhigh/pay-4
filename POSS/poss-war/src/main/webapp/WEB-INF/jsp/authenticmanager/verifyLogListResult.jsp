<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<script language="javascript">
function forEditVerifyLogStatus(url){
	parent.addMenu("实名认证信息",url);
	
}


</script>
</head>

<body>

<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
	<tr class="" align="center" valign="middle">
		<th class=""  >
			会员号</th>
		<th class=""  >
			登录名</th>
		<th class=""  >
			身份证号码</th>
		<th class=""  >
			姓名</th>
		<th class=""  >
			认证状态</th>
		<th class=""  >
			申请时间</th>							
		<th class=""  >
			操作</th>
	</tr>
	</thead>
	<c:forEach items="${page.result}" var="verifyLog" varStatus = "verifyLogStatus">
	<c:choose>
       <c:when test="${verifyLogStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			<td class="border_top_right4" align="center" >${verifyLog.memberCode}&nbsp;</td>
			<td class="border_top_right4" align="center" >${verifyLog.loginName}&nbsp;</td>
			<td class="border_top_right4" align="center" >${verifyLog.cardId}&nbsp;</td>
			<td class="border_top_right4" align="center" >${verifyLog.memberName}&nbsp;</td>
			<td class="border_top_right4" align="center" >
			<c:forEach items="${verifyLogStatusEnum}" var="verifyLogStatus">
				<c:if test="${verifyLogStatus.code == verifyLog.verifyStatus}"> 
					${verifyLogStatus.description}			
				</c:if>
			</c:forEach>
			&nbsp;
			</td>
			<td class="border_top_right4" align="center" >${verifyLog.createDate}&nbsp;</td>
			<td class="border_top_right4" align="center" >
			<a href="javascript:forEditVerifyLogStatus('verifyLogEdit.do?verifyId=${verifyLog.verifyId}&type=view');">查看</a>
			<c:if test="${verifyLog.verifyStatus==3 || verifyLog.verifyStatus==4 }"> 
					<a href="javascript:forEditVerifyLogStatus('verifyLogEdit.do?verifyId=${verifyLog.verifyId}&type=edit');">处理</a>		
			</c:if>
			
			</td>
			
		</tr>
	</c:forEach>
</table>

<li:pagination methodName="forQuery" pageBean="${page}" sytleName="black2"/>


</body>

