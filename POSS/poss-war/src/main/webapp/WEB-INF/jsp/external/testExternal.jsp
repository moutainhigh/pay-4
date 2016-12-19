<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<title>对外接口测试</title>
<style type="text/css">
<!--
body {
	background-color: #EAF5FF;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	scrollbar-face-color: #b9daff;  scrollbar-highlight-color: #b9daff; scrollbar-shadow-color: #b9daff; color: #000000; scrollbar-3dlight-color: #4a8ec6; scrollbar-arrow-color: #ffffff; scrollbar-track-color: #d9ebff; font-family: "helvetica", "arial", "verdana",; scrollbar-darkshadow-color: #4a8ec6;
}
-->
</style>
<link href="css/bodystyle.css" rel="stylesheet" type="text/css">

</head>
<body>
<form id="externalForm" name="externalForm" action="isMemberLegal.do" method="post" >
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >会员姓名：</td>
		<td class="border_top_right4" align="left" >
			<input type="text" id="memberName" name="memberName">
		</td>
		<td class="border_top_right4" align="right" >会员登录名:</td>
		<td class="border_top_right4" align="left" colspan="3">
			<input type="text" id="loginName" name="loginName">
		</td>
		
	</tr>

	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
		  <input type="submit" value="彩票调用接口(post)">
		</td>
		
	</tr>

</table>
</form>
<form id="externalForm1" name="externalForm1" action="isMemberActivation.do" method="get" >
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >商户登录名(email)：</td>
		<td class="border_top_right4" align="left" >
			<input type="text" id="memberEmail" name="memberEmail">
		</td>
		<td class="border_top_right4" align="right" >组织机构代码:</td>
		<td class="border_top_right4" align="left" colspan="3">
			<input type="text" id="memberOrgCode" name="memberOrgCode">
		</td>
		
	</tr>

	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
		  <input type="submit" value="商城调用接口(get)">
		</td>
		
	</tr>

</table>
</form>
	
</body>
</html>
