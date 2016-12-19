<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>


<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function queryList(pageNo,totalCount,pageSize) {
	
	var pars = $("#enterpriseSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	
	var exportT =  $("#export").val();
	if(exportT == "1"){
		enterpriseSearchFormBean.target = "hidIfrForExport";
		enterpriseSearchFormBean.method = "post";
		enterpriseSearchFormBean.submit();
		return false ;
	}
	
	$('#infoLoadingDiv').dialog('open');
	$.ajax({
		type: "POST",
		url: "${ctx}/accountOfEnterpriseList.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function validFrom(pageNo,totalCount,pageSize) {
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
    	queryList(pageNo,totalCount,pageSize);
    }else{
    	alert('商户号或会员号必须为数字!');
    }
   
}

function initQuery(pageNo,totalCount,pageSize){
	$("#ascOrDesc").val("");
	$("#orderParam").val("");
	$("#export").val("");
	validFrom(pageNo,totalCount,pageSize);
}

function orderBy(param){
	
	var ascOrDesc = $("#ascOrDesc").val();
	if(ascOrDesc.length == 0 || ascOrDesc == "asc"){
		$("#ascOrDesc").val("desc");
	}else{
		$("#ascOrDesc").val("asc");
	}
	$("#orderParam").val(param);
	$("#export").val("");
	validFrom();
}

function exportCrrList(){
	$("#export").val("1");
	validFrom();
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
		<div align="center"><font class="titletext"> 账 户 信 息</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">账户属性</h2>

<form id="enterpriseSearchFormBean" name="enterpriseSearchFormBean" >

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >商户号：</td>
		<td class="border_top_right4" align="left" >
		<input	type="text" id="merchantCode" name="merchantCode"></td>
		<td class="border_top_right4" align="right" >商户名称：</td>
		<td class="border_top_right4" align="left" >
		<input	type="text" id="merchantName" name="merchantName"></td>
		<td class="border_top_right4" align="right" >登陆名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="loginName" name="loginName">
		</td>		
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
		<td class="border_top_right4" align="right" >会员号：</td>
		<td class="border_top_right4" align="left"  >
			<input	type="text" id="memberCode" name="memberCode">
		</td>
		<td class="border_top_right4" align="right" >所属销售：</td>
		<td class="border_top_right4" align="left" colspan="3" >
				<select id="signLoginId" name="signLoginId" size="1">
				<option value="" selected>---请选择---</option>
				<c:forEach items="${loginSubNodes}" var="node">
				<option value="${node.loginId}">${node.name}</option>
				</c:forEach>
			</select>
		</td>
		
	</tr>				
		<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
		<a class="s03" href="javascript:initQuery()">
			<input class="button2" type="button" value="查询"></a>
		<a class="s03" href="javascript:exportCrrList()">
			<input class="button2" type="button" value="下载"></a></td>
	
</tr>
</table>
<input type="hidden" id="orderParam" name="orderParam"  value="" />
<input type="hidden" id="ascOrDesc" name="ascOrDesc"  value="" />
<input type="hidden" id="export" name="export"  value="0" />
</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
		<iframe name="hidIfrForExport"  style="display: none"></iframe>

</body>

