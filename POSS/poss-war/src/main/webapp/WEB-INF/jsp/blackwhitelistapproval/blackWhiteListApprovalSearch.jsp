<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
function businessTypeSearch(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#businessTypeSearchForm").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/blackWhiteListApprovalQuery.do?method=blackWhiteListApprovalSearchList",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
			$("#pageNo").attr("value",pageNo);
		}
	});
}

$(function(){
	businessTypeSearch();
});

function trim(str){ 
	//删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
function load(pageNo,totalCount,pageSize){
	businessTypeSearch(pageNo,totalCount,pageSize);
}

function refreshBlackList(){
	$.ajax({
		type: "POST",
		url: "${ctx}/blackWhiteList/blackWhiteListRefrsh.do?method=refresh",
		success: function(result) {
            alert(result);
		}
	});
}

<c:if test="errorMsg not empty">
	alert('${errorMsg}');
</c:if>

</script>
</head>

<body>

<!-- <table width="95%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">黑名单审核查询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table> -->

<h2 class="panel_title">黑名单审核查询</h2>
<form id="businessTypeSearchForm" name="businessTypeSearchForm" >

<input type="hidden" name="queryHisStatus" value="0"/>
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center" >
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >业务类型:</td>
		<td class="border_top_right4" align="left" >
			<select name="businessTypeId">
				<option value="" >---请选择---</option>
				<option value="1" >卡号</option>
				<option value="2" >邮箱</option>
				<option value="3" >IP</option>
				<option value="4" >国家</option>
				<option value="5" >MAC地址</option>
				<option value="6" >手机号</option>
				<option value="7" >证件号码</option>
				<option value="8" >收货地址</option>
				<option value="9" >账单地址</option>
				<option value="10" >卡Bin段</option>
				<option value="11" >IP地址段</option>
			</select>
		</td>
		<td class="border_top_right4" align="right" >匹配类型：</td>
		<td class="border_top_right4" align="left" >
			<select name="partType" id="partType">
			    <option value="" >请选择</option>
				<option value="1" >全匹配</option>
				<option value="2" >部分匹配</option>
				<option value="3" >包含</option>
				<option value="4" >区段</option>
			</select>
		</td>
		<td class="border_top_right4" align="right" >内容：</td>
		<td class="border_top_right4" align="left" >
             <input type="text" name="content" id="content" style="padding: 0px">
		</td>
		<td class="border_top_right4" align="right" >审核状态：</td>
		<td class="border_top_right4" align="left" >
			<select name="status">
				<option value="" >请选择</option>
				<option value="0" <c:if test="${dto.status==0}">selected</c:if>>待审核</option>
				<option value="1" <c:if test="${dto.status==1}">selected</c:if>>审核通过</option>
				<option value="-1" <c:if test="${dto.status==-1}">selected</c:if>>审核拒绝</option>
			</select>
		</td>
	</tr>
	 
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="8"  align="center">
		    <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="businessTypeSearch();">
	        <input type="button"  name="butSubmit" value="刷新黑名单" class="button2" onclick="refreshBlackList();">	
	     </td>	
	</tr>
</table>

</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>

<script type="text/javascript">
function toAdd(){
	window.location.href="${ctx}/blackWhiteListAdd.do?method=blackWhiteListCteateView";
}

function batchAdd(){
	window.location.href="${ctx}/blackWhiteListAdd.do?method=blackWhiteListBatchCteateView";
}
</script>
</body>

