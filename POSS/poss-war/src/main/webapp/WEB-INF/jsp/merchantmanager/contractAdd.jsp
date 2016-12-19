
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">

<script language="javascript">

function  validateContractForm(){

	
	this.submitContractForm();
}
function submitContractForm(){
	 document.contractFormBean.submit();
}



</script>
</head>

<body>
<br>
<br>
<p align="center">
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">商 户 合 同  新 增</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<c:if test="${sign!=null}">
	<center>
		<font color="red">${sign}</font>
	</center>
</c:if>


		
<br>


<form name="contractFormBean" action="contractAdd.do" method="post" enctype="multipart/form-data">

<table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">			
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" width="25%">商户名称：</td>
			<td class="border_top_right4" align="left" width="25%">
				<input type="text" id="businessName" name="businessName">
			</td>
			<td class="border_top_right4" align="right" width="25%">签订时间：</td>
			<td class="border_top_right4" align="left" width="25%">
				<input type="text" id="signedDate" name="signedDate">				
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" width="25%">合同名称：</td>
			<td class="border_top_right4" align="left" width="25%">
				<input type="text" id="contractName" name="contractName">
			</td>
			<td class="border_top_right4" align="right" width="25%">合同编号：</td>
			<td class="border_top_right4" align="left" width="25%">			
				<input type="text" id="contractCode" name="contractCode">									
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" width="25%">联系人：</td>
			<td class="border_top_right4" align="left" width="25%">
				<input type="text" id="contact" name="contact">
			</td>
			<td class="border_top_right4" align="right" width="25%">联系电话：</td>
			<td class="border_top_right4" align="left" width="25%">			
				<input type="text" id="phone" name="phone">									
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" width="25%">上传附件：</td>
			<td class="border_top_right4" align="left" width="25%">				
				<input type="file" id="file" name="file"  onkeydown= "return false"/>
			</td>
			<td class="border_top_right4" align="right" width="25%">备注：</td>
			<td class="border_top_right4" align="left" width="25%">							
				<textarea id="remark" name="remark"></textarea>								
			</td>
		</tr>					
		<tr class="trForContent1">	
			<td class="border_top_right4" align="center" colspan="4">					
			<a class="s03" href="javascript:validateContractForm()"><img
			src="./images/save.jpg" border="0"></a>
			</td>
		</tr>
		
</table>

</form>


</body>

