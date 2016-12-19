<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script type="text/javascript">
	function processEdit(){
		if($("#merchantName").val() == null || $("#merchantName").val()  == ""){
			alert("请输入商户名称!");
			return ;
		}
		if($("#merchantKey").val() == null || $("#merchantKey").val()  == ""){
			alert("请输入商户密钥!");
			return ;
		}
		$("#beautyLandSmsInfoFrom").action="${ctx}/beautyLandSmsInfoEditResult.htm";
		$("#beautyLandSmsInfoFrom").submit();
	}
	function processBack(){
		var pars = $("#beautyLandSmsInfoFrom").serialize();
		window.location.href="${ctx}/beautyLandSmsInfoInit.htm"; 
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
		<div align="center"><font class="titletext">锦 绣 大 地 商 户 短 信 权 限 修 改</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>



<form method="post" id="beautyLandSmsInfoFrom" name="beautyLandSmsInfoFrom" action="${ctx}/beautyLandSmsInfoEditResult.htm">
<input type="hidden" name="smsMerchantId" value="${smsInfo.smsMerchantId}" />
<table class="border_all2" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr class="trForContent1">	
		<td class=border_top_right4>商户号</td>
		<td class=border_top_right4>
			<input type="text" name="merchantCode" id="merchantCode"  value="${smsInfo.merchantCode}" readonly="readonly" />
			<font color="red">*</font>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4>商户名称</td>
		<td class=border_top_right4>
		<input type="text" name="merchantName" id="merchantName" value="${smsInfo.merchantName}" />
			<font color="red">*</font>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4>商户密钥</td>
		<td class=border_top_right4>
		<input type="text" name="merchantKey" id="merchantKey" value="${smsInfo.merchantKey}" />
			<font color="red">*密钥修改需要通知商户</font>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4>商户IP</td>
		<td class=border_top_right4>
			<input type="text" name="merchantIp" id="merchantIp" value="${smsInfo.merchantIp}" />&nbsp;&nbsp;
		</td>
		</tr>
	<tr class="trForContent1">
		<td class=border_top_right4>备注</td>
		<td class=border_top_right4>
			<textarea rows="3" cols="30" id="remark" name="remark"  >${smsInfo.remark}</textarea>
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
		 	<input id="submitForm" name="submitForm" type="button" value="保存" onclick="processEdit()"/>			
		 	<input type="button" value="返回" onclick="javascript:processBack()" />			
		</td>
	</tr>
</table>
</form>
</body>