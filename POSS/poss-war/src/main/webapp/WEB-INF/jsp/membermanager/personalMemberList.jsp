<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript"><!--
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function  validateMemberSearchForm(){
	
	
	this.memberQuery();
}
function memberQuery(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#personalSearchFormBean").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/personalMemberList.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 



function showUrl(menu,Url){	
	parent.addMenu(menu,Url);
}

--></script>
</head>

<body>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">个 人 会 员 查 询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form id="personalSearchFormBean" name="personalSearchFormBean" action="personalMemberList.do" method="post">

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">			
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >用户名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="userName" name="userName">
			</td>
			<td class="border_top_right4" align="right" >手机号：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="moblie" name="moblie">
			</td>
			
			<td class="border_top_right4" align="right" >用户状态：</td>
			<td class="border_top_right4" align="left" >
				<select id="userStatus" name="userStatus" size="1">
					<option value="" selected>---请选择---</option>
					<c:forEach items="${userStatusArray}" var="userStatus" >
						<option value="${userStatus.code}" >${userStatus.description}</option>
					</c:forEach>
				</select>						
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >eMail：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="email" name="email">
			</td>					
			<td class="border_top_right4" align="right" >证件号码：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="certNo" name="certNo">
			</td>	
			<td class="border_top_right4" align="right" >证件类型：</td>
			<td class="border_top_right4" align="left" >
				<select id="certType" name="certType" size="1">
					<option value="" selected>---请选择---</option>					
					<c:forEach items="${certTypeArray}" var="certType" >
						<option value="${certType.code}">${certType.description}</option>
					</c:forEach>
				</select>							
			</td>			
		</tr>
	
		<tr class="trForContent1">
			
			<td class="border_top_right4" colspan="6" align="center">
				<a class="s03" href="javascript:validateMemberSearchForm()"><img
			src="./images/query.jpg" border="0"> </a>
			</td>
		</tr>

</table>

</form>

<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
</body>

