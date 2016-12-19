<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/taglibs.jsp"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>

<link rel="stylesheet" href="${ctx}/css/main.css">
<script language="javascript">

function search() {
	var re2 = /^\d{1,11}$/;
	var partnerid =  $('#partnerid').val() ;

	if(partnerid.length>0){
		if(!re2.test(partnerid)) {
			alert('商户号：只能输入1-11位的数字');
			return false;
	 	}
	}
	var pars = $("#mainfromId").serialize()+ "&partner_Id=" + $("#partnerid").val();
	;
		$.ajax({
			type: "POST",
			url: "ficonfig.htm?method=configQuery",
			data: pars,
			success: function(result) {
			$('#resultListDiv').html(result);
			}
		});
}
</script>

</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="6"></td>
	</tr>
</table>
<p align="center">
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">直连渠道新增</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<br>
<br>
 <form action="" method="post" id="mainfromId">
		<table class="border_all2" width="60%" border="0" cellspacing="0"
			cellpadding="1" align="center">	
		    <tr class="trForContent1">
		      <td align="right" class="border_top_right4" >商户号：</td>
		      <td class="border_top_right4">&nbsp;
		      	<input type="text" name="partnerid" id="partnerid"  value="${partner_Id}" />&nbsp;
		      	<input type="button"  name="butSubmit" value="查  询" class="button2" onclick="search();">
		      </td>
		     </tr>
		  
	   </table>
	
 </form>
 <div id="resultListDiv" class="listFence"></div>
  <div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>  
</body>
</html>
