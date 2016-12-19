<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="./js/common.js"></script>
<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function  validateMemberSearchForm(){	

	this.memberQuery();
	
}
function memberQuery(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#enterpriseSearchFormBean").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/enterpriseMemberList.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function changeProvince(relationList){
	var relationArray = new Array();
	<c:forEach items = "${relationList}" var = "relation" varStatus = "relationStatus">
		relationArray[${relationStatus.index}] = new dropDownListMode('${relation.fatherCode}','${relation.code}','${relation.name}');	
	</c:forEach>
	
	this.changeFatherSelect('province','city',relationArray,null);
}
function showUrl(menu,Url){	
	parent.addMenu(menu,Url);
}

</script>
</head>

<body>

<p align="center">
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">企 业 会 员 查 询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form id = "enterpriseSearchFormBean" name="enterpriseSearchFormBean" action="enterpriseMemberList.do" method="post">

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">			
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >用户名：</td>
			<td class="border_top_right4" align="left"  colspan="6">
				<input type="text" id="userName" name="userName">
			</td>
			
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >企业名称：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="businessName" name="businessName">
			</td>
			<td class="border_top_right4" align="right" >网站名称：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="website" name="website">					
			</td>
			<td class="border_top_right4" align="right" >省份：</td>
			<td class="border_top_right4" align="left" >
			<select id="province" name="province" size="1"	onchange="changeProvince();">
				<option value="" selected>---请选择---</option>
				<c:forEach items="${provinceList}" var="province">
					<option value="${province.provincecode}">${province.provincename}</option>
				</c:forEach>
			</select>									
			</td>	
		</tr>
	
		<tr class="trForContent1">
		<td class="border_top_right4" align="right" >联系电话：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="contactPhone" name="contactPhone">
			</td>
			<td class="border_top_right4" align="right" >联系人：</td>
			<td class="border_top_right4" align="left" >
			<input type="text" id="contactPerson" name="contactPerson">					
			</td>
			
			<td class="border_top_right4" align="right" >城市：</td>
			<td class="border_top_right4" align="left" >
				<select	id="city" name="city" size="1">
					<option value="" selected>---请选择---</option>					
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

