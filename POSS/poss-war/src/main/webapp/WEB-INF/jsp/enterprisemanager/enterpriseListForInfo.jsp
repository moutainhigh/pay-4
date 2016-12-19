<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function enterpriseQuery(pageNo,totalCount,pageSize) {
	var endDate = $("#endDate").val();
	var startDate = $("#startDate").val();
	if(null != endDate && 0 != endDate.length){
		if(0 == startDate.length || null == startDate){
			alert("请输入创建日期起始端");
			return;
			}
		}

	$('#infoLoadingDiv').dialog('open');
	var pars = $("#enterpriseSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/enterpriseListForInfo.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function enterpriseQueryCC(pageNo,totalCount,pageSize) {
	var endDate = $("#endDate").val();
	var startDate = $("#startDate").val();
	if(null != endDate && 0 != endDate.length){
		if(0 == startDate.length || null == startDate){
			alert("请输入创建日期起始端");
			return;
			}
		}

	$('#infoLoadingDiv').dialog('open');
	var pars = $("#enterpriseSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/enterpriseListForInfoCC.do",
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
    	enterpriseQuery();
    }else{
    	alert('商户号或会员号必须为数字!');
    }
   
}
function validFromCC() {
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
    	enterpriseQueryCC();
    }else{
    	alert('商户号或会员号必须为数字!');
    }
   
}
function validFromCallCenter() {
	var sign = false;
	
    var memberCode   = document.getElementById('memberCode').value; 
    var merchantCode = document.getElementById('merchantCode').value; 
    var loginName    = document.getElementById('loginName').value;
    var merchantName = document.getElementById('merchantName').value;
  

    if(merchantCode!=null&&merchantCode!=''){
    	sign = true;
    	validFromCC();
    	return;
    }
    if(memberCode!=null&&memberCode!=''){
    	sign = true;
    	validFromCC();
    	return;
    }
    if(loginName!=null&&loginName!=''){
    	sign = true;
    	validFromCC();
    	return;
    }
    if(merchantName!=null&&merchantName!=''){
    	sign = true;
    	validFromCC();
    	return;
    }
  
   
    if(sign==false){
        alert('商户号,会员号,登录名,商户名称必须输入其中一个');
    }
}
<c:if test="${merchantEmail != null}">
$(document).ready(
		function(){
			enterpriseQuery();
		}
);
</c:if>

$(function(){ 
	//alert(123);
	$.ajax({
		type : "POST",
		url : "${ctx}/bdController.do?method=getUserRelation",
		dataType:"json",
		success : function(result) {
			$.each(result,function(i,item){ 
			$("#signLoginId").append("<option value="+item.loginId+">"+item.name+"</option>");	
			});
		}
	}); 
}); 
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
		<div align="center"><font class="titletext">商 户 信 息 查 询
</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">商户信息查询</h2>


<form id="enterpriseSearchFormBean" name="enterpriseSearchFormBean" >

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >商户号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="merchantCode" name="merchantCode"></td>
		<td class="border_top_right4" align="right" >登录名：</td>
		<td class="border_top_right4" align="left" >
			<input type="text" id="loginName" name="loginName" value="${merchantEmail}">
		</td>
		<td class="border_top_right4" align="right" >商户名称：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="merchantName" name="merchantName">
		</td>		
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >会员号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="memberCode" name="memberCode">
		</td>
		<td class="border_top_right4" align="right" >会员状态：</td>
		<td class="border_top_right4" align="left" >
			<select id="merchantStatus" name="merchantStatus" size="1">
				<option value="" selected>---请选择---</option>
				<c:forEach items="${merchantStatusEnum}" var="merchantStatus">
				<option value="${merchantStatus.code}">${merchantStatus.description}</option>
				</c:forEach>
			</select>
		</td>
		<td class="border_top_right4" align="right" >所属销售：</td>
		<td class="border_top_right4" align="left" >
				<select id="signLoginId" name="signLoginId" size="1">
				<option value="" selected>---请选择---</option>
			</select>
		</td>
		<%-- 
		<td class="border_top_right4" align="right" >频道名称：</td>
		<td class="border_top_right4" align="left" >
			<select id="signDepart" name="signDepart" size="1">
				<option value="" selected>---请选择---</option>
				<c:forEach items="${info}" var="dto">
					<option value="${dto.departmentName}">${dto.departmentName}</option>
				</c:forEach>
			</select>
		</td>
		--%>
	</tr>		
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >创建时间：</td>
		<td class="border_top_right4" align="left" colspan="6">
			<input class="Wdate" type="text" id= "startDate" name="startDate"  onClick="WdatePicker()">
			～
			<input class="Wdate" type="text" id= "endDate" name="endDate"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})">
		 </td>
	</tr>	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
		<a	href="javascript:validFrom()">
			<input class="button2" type="button" value="查询"></a>
		<!--<a  href="javascript:validFromCallCenter()">
			客服检索</a>-->
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

