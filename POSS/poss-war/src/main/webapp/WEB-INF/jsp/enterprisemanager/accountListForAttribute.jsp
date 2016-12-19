<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>


<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function queryList(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#enterpriseSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/accountListForAttribute.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function validFrom() {
    var reg = /^\d+$/;
    var merchantCode = document.getElementById('merchantCode').value; 
    if(merchantCode!=null&&merchantCode!=''){ 
    	
	    var isNumber = reg.test(merchantCode.replace(/(^\s*)|(\s*$)/g, ""));
	    if(isNumber){
	    	queryList();
	    }else{
	    	alert('商户号必须为数字!');
	    }
    }else{
    	queryList();
    }
}

</script>
</head>

<body>

<!-- <table width="30%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">账 户 属 性 管 理</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->

<h2 class="panel_title">账户属性管理</h2>
<form id="enterpriseSearchFormBean" name="enterpriseSearchFormBean" >

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >登录名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="loginName" name="loginName">
		</td>
		<td class="border_top_right4" align="right" >商户号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="merchantCode" name="merchantCode"></td>
		<td class="border_top_right4" align="right" >商户名称：</td>
		<td class="border_top_right4" align="left" >
		<input	type="text" id="merchantName" name="merchantName"></td>
		
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >账户号：</td>
		<td class="border_top_right4" align="left" >
		<input	type="text" id="accountCode" name="accountCode">
		</td>
		<td class="border_top_right4" align="right" >账户类型：</td>
		<td class="border_top_right4" align="left" >
			<select id="accountType" name="accountType" size="1">
				<option value="" selected>---请选择---</option>
				<c:forEach items="${accountTypeList}" var="accountType">
				<option value="${accountType.code}">${accountType.displayName}</option>
				</c:forEach>
			</select>
		</td>
		<td class="border_top_right4" align="right" >账户状态：</td>
		<td class="border_top_right4" align="left" >
			<select id="accountStatus" name="accountStatus" size="1">
				<option value="" selected>---请选择---</option>
				<c:forEach items="${accountStatusEnum}" var="accountStatus">
				<option value="${accountStatus.code}">${accountStatus.description}</option>
				</c:forEach>
			</select>
		</td>
		
	</tr>		
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center"><a
			class="s03" href="javascript:validFrom()">
<input class="button2" type="button" value="查询"></a></td></tr>

</table>

</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>

</body>

