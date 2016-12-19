
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>
<head>
	<link rel="stylesheet" href="./css/main.css">
	<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
	<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
	<link rel="stylesheet" href="./css/main.css">
	<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
	<script src="./js/common.js"></script>
	<script language="javascript">
	//返回之前页面
	function processBack(id){
		location.href = "paymentChannelItemAddtion.do";
	}
	</script>
</head>

<body>
	<BR/>
	<BR/>
	<c:if test="${isSuccessed}" >
		<table width="25%" border="0" cellspacing="0" cellpadding="0"
		align="center" height="21" style="">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
				<div align="center"><font class="titletext">新增成功!</font></div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td align="center">
					<br/>
					<strong>${message}</strong>	
					<br/>
					<br/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input type="button" value="返回 "  onClick="javascript:processBack(null)">
				</td>
			</tr>
		</table>
	</c:if>
	
	<c:if test="${!isSuccessed}" >
		<table width="25%" border="0" cellspacing="0" cellpadding="0"
		align="center" height="21" style="">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
				<div align="center"><font class="titletext">新增失败!</font></div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>		
			<tr>
				<td>
					<br/>
					<font color="#ff0000">	${message}	</font>			
					<br/>
					<br/>	
				</td>
			</tr>
			<tr>
				<td align="center">
					<input type="button" value="返回 "  onClick="javascript:processBack(null)">
				</td>
			</tr>
		</table>
	</c:if>
		

</body>

