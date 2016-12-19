<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
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
		<div align="center"><font class="titletext">
			<c:if test="${ebppConfig != null}">修改参数</c:if>
			<c:if test="${ebppConfig == null}">新增参数</c:if>
			</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="ebpp_config.do?method=save" method="post" id="ebppconfig_add_form" name="ebppconfig_add_form">
     <table class="border_all2 inputTable" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
     	<c:if test="${ebppConfig != null}">
     		<input type="hidden" id="id" name="id" value="${ebppConfig.id}" /> 
     		<tr class="trbgcolor10">
				<th class="border_top_right4 must">KEY</th>
				<td class="border_top_right4">
					<input type="text" name="configKey" id="configKey" maxlength="50" size="30" readonly="readonly" value="${ebppConfig.configKey}"/>
				</td>
			</tr>
            <tr>
             <th class="border_top_right4 must">VALUE:</th>
             <td class="border_top_right4">
             	<input type="text" name="configValue" id="configValue" maxlength="500" size="30" value="${ebppConfig.configValue}"/>
             </td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4">描述:</th>
             <td class="border_top_right4">
             	<input type="text" name="configDesc" id="configDesc" maxlength="25" size="30" value="${ebppConfig.configDesc}"/>
             </td>
            </tr>
       	</c:if>
       	<c:if test="${ebppConfig == null}"> 
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">KEY:</th>
             <td class="border_top_right4">
             	<input type="text" name="configKey" id="configKey" maxlength="50" size="30" value=""/>
             </td>
            </tr>
            <tr>
             <th class="border_top_right4 must">VALUE:</th>
             <td class="border_top_right4">
             	<input type="text" name="configValue" id="configValue" maxlength="500" size="30" value=""/>
             </td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4">描述:</th>
             <td class="border_top_right4">
             	<input type="text" name="configDesc" id="configDesc" maxlength="25" size="30" value=""/>
             </td>
            </tr>
       	</c:if>
   	</table>
	<br>
	<br>
	<table class="border_all4" width="75%" border="0" cellspacing="0" cellpadding="0" align="center" id="buttonDisplay">
		<tr class="trbgcolor6" align="center">
			<td>
			 	<input type="submit" name="Submit" value="保 存">
			 	<input type="button" onclick="retn()" name="Submit2" value="返  回">
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	function retn(){
		document.getElementById("ebppconfig_add_form").action = "ebpp_config.do";
		document.getElementById("ebppconfig_add_form").submit();
	}
	
	$(document).ready(function(){
		$(".must").each(function(i){
			$(this).html("<span style='color:red'>*</span>"+$(this).html());
		 });
	});
</script>
</body>
</html>
