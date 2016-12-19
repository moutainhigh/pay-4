<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function operatorQuery(pageNo,totalCount,pageSize) {
	

	$('#infoLoadingDiv').dialog('open');
	var pars = $("#operatorSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/operatorSearchList.do?method=doQuery",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function validFrom() {
    var reg = /^\d+$/;
    var memberCode = document.getElementById('memberCode').value; 
    var merchantCode = document.getElementById('merchantCode').value; 
    var isMemberCodeNum = false ;
    var isMerchantCodeNum = false ;
    if(memberCode==null||memberCode==''){
    	isMemberCodeNum=true;
    }else{
    	isMemberCodeNum = reg.test(memberCode.replace(/(^\s*)|(\s*$)/g, ""));
    }
    
    if(merchantCode==null||merchantCode==''){
    	isMerchantCodeNum=true;
    }else{
    	isMerchantCodeNum = reg.test(merchantCode.replace(/(^\s*)|(\s*$)/g, ""));
    }
    if(isMemberCodeNum&&isMerchantCodeNum){ 
    	operatorQuery();
    }else{
    	alert('商户号或会员号必须为数字!');
    }
   
}
</script>
</head>

<body>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">商 户 操 作 员 信 息 查 询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">商户操作员信息查询</h2>

<form id="operatorSearchFormBean" name="operatorSearchFormBean" >

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >会员号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="memberCode" name="memberCode">
		</td>
		<td class="border_top_right4" align="right" >商户号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="merchantCode" name="merchantCode"></td>
		<td class="border_top_right4" align="right" >用户名：</td>
		<td class="border_top_right4" align="left" >
			<input type="text" id="loginName" name="loginName" value="">
		</td>
			
	</tr><tr class="trForContent1">
	<td colspan="6" class="border_top_right4" align="center" >
			<input type="button" onclick="validFrom();" name="submitBtn" value="查询" class="button2">
		</td></tr>
</table>

</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>

</body>

