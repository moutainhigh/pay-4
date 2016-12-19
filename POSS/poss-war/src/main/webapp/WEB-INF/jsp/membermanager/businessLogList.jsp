
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language="javascript">
function  validatMemberLogSearchForm(){
	      
	var i=0;
		
	if(document.getElementById('memberCode').value!=""){i++;}
	if(document.getElementById('memberEmail').value!=""){i++;}
	if(document.getElementById('logType').value!=""){i++;}
	if(document.getElementById('logTime').value!=""){i++;}
	
	if(i==0){alert("必须至少输入一个查询条件 !");return;}

	this.submitMemberLogSearchForm();
}
function submitMemberLogSearchForm(){
	 document.memberLogSearchFormBean.submit();
}

</script>
</head>

<body>
<br>
<br>
<p align="center">
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">企 业 会 员 日 志  查 询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<br>
<br>

<form name="memberLogSearchFormBean" action="memberLogList.do" method="post">
<input type="hidden" id="memberId" name="memberId" value="${memberId}">
<table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">			
		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" width="15%">日志类型：</td>
			<td class="border_top_right4" align="left" width="15%">
				<select id="logType" name="logType" size="1">
					<option value="" selected>---请选择---</option>
					<option value="1">登陆 </option>
					<option value="2">充值</option>
					<option value="3">付款</option>
				</select>
			</td>
			<td class="border_top_right4" align="right" width="25%">操作时间：</td>
			<td class="border_top_right4" align="left" width="35%">
				<input type="text" id="logTime" name="logTime">
				～
				<input type="text" id="logTime" name="logTime">
			</td>
		</tr>
		<tr class="trForContent1">			
			<td class="border_top_right4" colspan="4" align="center">
				<a class="s03" href="javascript:validatMemberLogSearchForm();"><img
			src="./images/query.jpg" border="0"> </a>
			</td>
		</tr>

</table>

</form>

<table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">用户名</font> </a></td>	
			<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">操作员</font> </a></td>		
		<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">日志类型</font> </a></td>
			<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">操作说明</font> </a></td>
			<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">时间</font> </a></td>
			
	</tr>



	<c:forEach items="${memberLogList}" var="memberLog" varStatus = "memberStatus">
	<c:choose>
       <c:when test="${memberStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			<td class="border_top_right4" align="center">${memberLog.memberCode}</td>	
			<td class="border_top_right4" align="center">${memberLog.opCode}</td>		
			<td class="border_top_right4" align="center">${memberLog.logType}</td>
			<td class="border_top_right4" align="center">${memberLog.logRemarks}</td>
			<td class="border_top_right4" align="center">${memberLog.logTime}</td>											
		</tr>
	</c:forEach>
</table>



<br>
<br>

<table class="border_all4" width="75%" border="0" cellspacing="0"
	cellpadding="0" align="center" id="buttonDisplay">
	<tr class="trbgcolor6" align="center">
		<td><a class="s03" href="">首页 </a>&nbsp; <a class="s03" href="">上一页
		</a>&nbsp; <a class="s03" href="">下一页 </a>&nbsp; <a class="s03" href="">尾页
		</td>
	</tr>
</table>
</body>

