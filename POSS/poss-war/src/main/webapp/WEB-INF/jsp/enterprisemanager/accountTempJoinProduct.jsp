<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>

<link rel="stylesheet" href="${ctx}/css/main.css">
<script type="text/javascript" src="${ctx}/js/prototype/prototype.js"></script>
<script language="javascript">
function closePage(url){
	parent.closePage(url);
}

/*单个移动*/
function moveOptions(objPush,objGet) {   
    for (var i = 0;i < objPush.length;i++) {   
        if (objPush.options[i].selected) {   
              var pushOpt = objPush.options[i];   
              objGet.options.add(new Option(pushOpt.innerText, pushOpt.value));   
              objPush.remove(i);   
              i = i - 1;   
        }   
    }   
}

function select_all(){
    for(var i=0;i<document.accountTempFormBean.funcno.length; i++){
       document.accountTempFormBean.funcno.options[i].selected=true;
    }
 }

 function checkForm(){
    select_all();
	document.accountTempFormBean.submit();

 }
 


</script>

</head>

<body>
<!-- <table width="100%" border="0" cellspacing="0" cellpadding="0">
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
		<div align="center"><font class="titletext">账 户 模 板 产 品 配 置</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<br>
<br> -->
<h2 class="panel_title">账户模板产品配置</h2>

<table class="border_all2" width="60%" border="0" cellspacing="0" cellpadding="1" align="center">
	<form name="accountTempFormBean" action="accountTempJoinProduct.do" method="post">
	<input name="accountTempId" type="hidden" value="${accountTempId}">
	<input name="accountTempName" type="hidden" value="${accountTempName}">
	<tr  class=trForContent1>
		<td align="center" width="643" valign="middle" nowrap class="border_top_right4">&nbsp;&nbsp;&nbsp;&nbsp;模板名称：${accountTempName}</td>
	</tr>
	<tr valign="top" class="trForContent1">
		<td align="center" nowrap class="border_top_right4">
		<table width="617" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="3">&nbsp;</td>
			</tr>
			<tr align="center">
				<td align="center">未分配产品</td>
				<td>&nbsp;</td>
				<td align="center">已分配产品</td>
			</tr>
			<tr align="center">

				<td width="192" align="left">
					<select name="nofuncno" id="nofuncno" class="input1" size="25" multiple="multiple" style="width: 235px;" ondblclick="moveOptions($('nofuncno'),$('funcno'));">
						<c:forEach items="${productNoList}" var="product">
							<option value="${product.code}">${product.name}</option>
						</c:forEach>
					</select>
				</td>
				<td width="121">
				<table width="58" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="58" align="center">
							<input name="addB" type="button" class="button1" value="添 加 &gt;" onClick="moveOptions($('nofuncno'),$('funcno'));">
						</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">
							<input name="delB" type="button" class="button1" value="&lt; 删 除" onClick="moveOptions($('funcno'),$('nofuncno'));">
						</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">
							<input name="saveSubmit" type="button" class="button1" value=" 保 存 " onClick="checkForm();">
						</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
	
					<tr>
						<td align="center">
							<input type="button" class="button1" value=" 关 闭 " onclick="javascript:closePage('accountTempJoinProduct.do?accountTempId=${accountTempId}')" />
						</td>
					</tr>
				</table>
				</td>
				<td width="192" align="right">
					<select name="funcno" id="funcno" size="25" class="input1" multiple style="width: 235px;" onDblClick="moveOptions($('funcno'),$('nofuncno'));">
					<c:forEach items="${productOfAccountTempList}" var="product">
						<option value="${product.code}">${product.name}</option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr align="center">
				<td colspan="3">&nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>
	</form>
</table>
</body>
</html>
