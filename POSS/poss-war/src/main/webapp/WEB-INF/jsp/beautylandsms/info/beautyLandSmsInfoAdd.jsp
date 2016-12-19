<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script type="text/javascript">
	function processAdd(){
		if($("#merchantCode").val() == null || $("#merchantCode").val()  == ""){
			alert("请输入商户号!");
			return ;
		}
		if($("#merchantName").val() == null || $("#merchantName").val()  == ""){
			alert("请输入商户名称!");
			return ;
		}
		if($("#merchantKey").val() == null || $("#merchantKey").val()  == ""){
			alert("请输入商户密钥!");
			return ;
		}
		$("#beautyLandSmsInfoFrom").action="${ctx}/beautyLandSmsInfoAddResult.htm";
		$("#beautyLandSmsInfoFrom").submit();
	}
	
	
</script>
</head>

<body>
<br>
<br>
<p align="center">
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">锦 绣 大 地 商 户 短 信 权 限 开 通</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>



<form method="post" id="beautyLandSmsInfoFrom" name="beautyLandSmsInfoFrom" action="${ctx}/beautyLandSmsInfoAddResult.htm">
<table class="border_all2" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr class="trForContent1">	
		<td class=border_top_right4>商户号</td>
		<td class=border_top_right4>
			<input type="text" name="merchantCode" id="merchantCode"  />
			<font color="red">*</font>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4>商户名称</td>
		<td class=border_top_right4>
			<input type="text" name="merchantName" id="merchantName"　　/>
			<font color="red">*</font>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4>商户密钥</td>
		<td class=border_top_right4>
			<input type="text" name="merchantKey" id="merchantKey"　　/>
			<font color="red">*</font>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4>商户IP</td>
		<td class=border_top_right4>
			<input type="text" name="merchantIp" id="merchantIp" />&nbsp;&nbsp;
		</td>
		</tr>
	<tr class="trForContent1">
		<td class=border_top_right4>备注</td>
		<td class=border_top_right4>
			<textarea rows="3" cols="30" id="remark" name="remark"></textarea>
		 </td>
	</tr>
	<c:if test="${not empty info}">
				<li style="color: red;">${info }</li>
			</c:if>
</table>


<br>
<br>

<table class="border_all4" width="75%" border="0" cellspacing="0" cellpadding="0" align="center" id="buttonDisplay">
	<tr class="trbgcolor6" align="center">
		<td align="center" style="text-align: center">
		 	<input id="submitForm" name="submitForm" type="button" value="保存" onclick="processAdd()"/>			
		 	<input type="button" value="重置" onclick="javascript:document.beautyLandSmsInfoFrom.reset()"/>			
		</td>
	</tr>
</table>
</form>
</body>