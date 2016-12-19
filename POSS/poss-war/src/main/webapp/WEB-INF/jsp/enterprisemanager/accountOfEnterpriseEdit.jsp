<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<script language="javascript">
function closePage(){
	parent.closePage('accountOfEnterpriseEdit.do?memberCode=${enterprise.memberCode}');
}
</script>
</head>
<body>


<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">会 员 账 户 设 置</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">会员账户设置</h2>
<c:if test="${sign!=null}">
	<center>
		<font color="red">${sign}</font>
	</center>
</c:if>

<form id="enterpriseFormBean" name="enterpriseFormBean" action="accountOfEnterpriseEdit.do" method="post">
<input type="hidden" id="memberCode" name="memberCode" value="${enterprise.memberCode}"></input>
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">			
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >商户号：</td>
			<td class="border_top_right4" align="left" ><input id="merchantCode" name="merchantCode" value = "${enterprise.merchantCode}" readonly></td>
			<td class="border_top_right4" align="right" >会员号：</td>
			<td class="border_top_right4" align="left" ><input id="memberCode" name="memberCode" value = "${enterprise.memberCode}" readonly></td>
			<td class="border_top_right4" align="right" >商户名称：</td>
			<td class="border_top_right4" align="left" ><input id="merchantName" name="merchantName" value = "${enterprise.merchantName}" readonly></td>
			<td class="border_top_right4" align="right" >登录名：</td>
			<td class="border_top_right4" align="left" ><input id="loginName" name="loginName" value = "${enterprise.loginName}" readonly></td>
		</tr>		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" nowrap>账户信息：</td>
			<td class="border_top_right4" align="left" colspan="3">
			<c:forEach items="${baseDataList}" var="baseData">
				<input type="checkbox" id="accountType" name="accountType" value="${baseData.code}" 
					<c:forEach items="${accountOfEnterpriseList}" var="accountOfEnterprise">
						<c:if test="${baseData.code == accountOfEnterprise.code}"> 
						checked  disabled
						</c:if>
					</c:forEach>
				>${baseData.displayName}&nbsp;</input><br/>
			</c:forEach>				
			</td>
			<td class="border_top_right4" align="left" colspan="4">
			<c:forEach items="${guaranteeDataList}" var="baseData">
				<input type="checkbox" id="accountType" name="accountType" value="${baseData.code}" 
					<c:forEach items="${accountOfEnterpriseList}" var="accountOfEnterprise">
						<c:if test="${baseData.code == accountOfEnterprise.code}"> 
						checked  disabled
						</c:if>
					</c:forEach>
				>${baseData.displayName}</input><br/>
			</c:forEach>				
			</td>
		</tr>
		
		<tr class="trForContent1">	
		<td  align="center" colspan="10" class="border_top_right4">	
			<input type="submit" class="button2" value="保存" >		
		 	<input type = "button" class="button2"  onclick="javascript:closePage();" value="关闭">
		</td>
	</tr>	
		
</table>
<br>

</form>

</body>