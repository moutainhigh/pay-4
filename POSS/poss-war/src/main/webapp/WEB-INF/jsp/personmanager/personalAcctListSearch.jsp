<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
function personQuery(pageNo,totalCount,pageSize) {
	var loginName = document.getElementById('loginName').value;
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#personSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/personAcctInfoSearch.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
}
//给客服检索用
function serSerch(pageNo,totalCount,pageSize) {
	var loginName = $("#loginName").val();
	var userName = $("#userName").val();
	var memberCode = $("#memberCode").val();
	
	if("" == loginName && "" == userName && "" == memberCode){
		alert("请输入登录名,姓名或者会员号");
		return ;
	}
	var loginName = document.getElementById('loginName').value;
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#personSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/personAcctInfoSearchForCusSer.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function load(pageNo,totalCount ){
	var str = $('#memberCode').val();
	if(null!=str && ""!=str){
		personQuery(pageNo,totalCount);
		}
}
function trim(str){ 
	//删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
</script>
</head>

<body onload=load()>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">个  人  会  员  基 本 信 息</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">个人会员基本信息</h2>

<form id="personSearchFormBean" name="personSearchFormBean" >

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >登录名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="loginName" name="loginName" maxlength= "64">
		</td>
		<td class="border_top_right4" align="right" >姓名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="userName" name="userName" maxlength= "8"></td>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >会员号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="memberCode" name="memberCode" value="${memberCode}" maxlength= "11">
			<!--  
			<font color="red">会员号为搜索必填项</font>
			-->
		</td>
		<td class="border_top_right4" align="right" >会员状态：</td>
		<td class="border_top_right4" align="left" >
			<select id="status" name="status">
				<option value="" selected >---请选择---</option>
				<option value="0" <c:if test="${status=='0'}">select</c:if> >创建</option>	
				<option value="1" <c:if test="${status=='1'}">select</c:if> >正常</option>	
				<option value="2" <c:if test="${status=='2'}">select</c:if> >冻结</option>	
				<option value="3" <c:if test="${status=='3'}">select</c:if> >未激活</option>	
				<option value="4" <c:if test="${status=='4'}">select</c:if> >删除</option>	
				<option value="5" <c:if test="${status=='5'}">select</c:if> >临时</option>						
		   </select>
		</td>
	</tr>	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
			<input	type="button" id="acctSearch" class="button2" name="acctSearch" value=" 检索 " onclick="personQuery()"/>
			<input	type="button" id="acctSearch" class="button2" name="acctSearch" value="客服检索" onclick="serSerch()"/>
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

