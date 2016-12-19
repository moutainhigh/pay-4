<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<link rel="stylesheet" href="./css/main.css">

<script language="javascript">

function  validateContractSearchForm(){

	
	this.submitContractSearchForm();
}
function submitContractSearchForm(){
	 document.contractSearchFormBean.submit();
}

function showUrl(menu,Url){	
	parent.addMenu(menu,Url);
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
		<div align="center"><font class="titletext">商 户 合 同  查 询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<br>


<form name="contractSearchFormBean" action="contractList.do" method="post">

<table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">			
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" width="15%">商户名称：</td>
			<td class="border_top_right4" align="left" width="35%">
				<input type="text" id="businessName" name="businessName">
			</td>
			<td class="border_top_right4" align="right" width="15%">签订时间：</td>
			<td class="border_top_right4" align="left" width="35%">
			<input class="Wdate" type="text" name="fromDate"   onClick="WdatePicker()">
			<input class="Wdate" type="text" name="toDate"  onClick="WdatePicker()">	
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" width="15%">合同名称：</td>
			<td class="border_top_right4" align="left" width="35%">
				<input type="text" id="contractName" name="contractName">
			</td>
			<td class="border_top_right4" align="right" width="15%">合同编号：</td>
			<td class="border_top_right4" align="left" width="35%">			
				<input type="text" id="contractCode" name="contractCode">									
			</td>
		</tr>		
		<tr class="trForContent1">			
			<td class="border_top_right4" colspan="4" align="center">
				<a class="s03" href="javascript:validateContractSearchForm()"><img
			src="./images/query.jpg" border="0"></a>&nbsp;&nbsp;
			<a class="s03" href="javascript:showUrl('商户合同新增','contractAdd.do')"><img
			src="./images/add.jpg" border="0"></a>
			</td>
		</tr>
		
</table>

</form>

<table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">合同名称</font> </a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">合同编号 </font> </a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">合同签订日期</font> </a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">商户名称</font> </a></td>		
		<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">联系人</font> </a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">联系电话</font> </a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">合同状态</font> </a></td>
	</tr>
	<c:forEach items="${contractList}" var="contract" varStatus = "contractStatus">
	<c:choose>
       <c:when test="${contractStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>		
			<td class="border_top_right4" align="center">${contract.contractName}&nbsp;</td>
			<td class="border_top_right4" align="center">${contract.contractCode}&nbsp;</td>
			<td class="border_top_right4" align="center">${contract.signedDate}&nbsp;</td>
			<td class="border_top_right4" align="center">${contract.businessName}&nbsp;</td>
			<td class="border_top_right4" align="center">${contract.contact}&nbsp;</td>
			<td class="border_top_right4" align="center">${contract.phone}&nbsp;</td>
			<td class="border_top_right4" align="center">${contract.status}&nbsp;</td>						
			
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

