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
	var url="${ctx}/blackWhiteListSearchList.do";
	var listType=$("#listType").val();
	if(listType==1){
		url="${ctx}/whiteListSearchList.do";
	}
	url+="?method=blackWhiteListSearchList"
	
	$.ajax({
		type: "POST",
		url: url,
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
			$("#pageNo").attr("value",pageNo);
		}
	});
} 

function trim(str){ 
	//删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
function load(pageNo,totalCount,pageSize){
	businessTypeSearch(pageNo,totalCount,pageSize);
}


function download(){
	var pars = $("#businessTypeSearchForm").serialize();
	window.location.href = 	"./downloadBlackWhite.do?method=downloadBlackWhite&"+pars;
}

function downloadTemplate(){
	window.location.href='${ctx}/blackListUpload.do?method=downLoadFile';
}

<c:if test="${not empty errorMsg}">
	alert('${errorMsg}');
</c:if>

</script>
</head>

<body>

<%-- <table width="95%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">
		   <c:if test="${listType==2}">
		                    黑名单查询
		   </c:if>
		   <c:if test="${listType==1}">
		                    白名单查询
		   </c:if>
		</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> --%>
<h2 class="panel_title">
<c:if test="${listType==1}">
		                    白名单查询
		   </c:if>
<c:if test="${listType==2}">
		                    黑名单查询
		   </c:if>
		   </h2>


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
			</select>
		</td>
		<td class="border_top_right4" align="right" >内容：</td>
		<td class="border_top_right4" align="left" >
             <input type="text" name="content" id="content" style="padding: 0px">
             <input type="hidden" name="listType" id="listType" value="${listType}">
		</td>
		<td class="border_top_right4" align="right" >状态：</td>
		<td class="border_top_right4" align="left" >
			<select name="status">
				<option value="" >请选择</option>
				<option value="1" >启用</option>
				<option value="0" >停用</option>
			</select>
		</td>
	</tr>
	 
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="8"  align="center">
		    <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="businessTypeSearch();" />
	        <input type="button"  name="butSubmit" value="添  加" class="button2" onclick="toAdd();" />
	        <input type="button"  value="模版下载" onclick="downloadTemplate()" />
	        <input type="button"  name="butSubmit" value="批量添加" class="button2" onclick="batchAdd();" />
			<input type="button" onclick="download()" value="导出所有" />
			<!-- <a href="#">模板下载</a> -->		
	     </td>	
	</tr>
</table>

</form>
<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<div id="uploadDiv" name="uploadDiv" style="display: none" align="center">
	<form action="${ctx}/blackListUpload.do?method=upload" method="post" id="mainfromId" enctype="multipart/form-data" onsubmit="return sub();">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
		    <tr class="trForContent1">
		      <td align="right" class="border_top_right4" >黑名单文件：</td>
		      <td class="border_top_right4">
		      	<input type="file" id="file" name="file">
		      	<input type="hidden" id="listType" value="${listType}" name="listType">
		      </td>
		     </tr>
		    <tr class="trForContent1">
		      <td align="center" class="border_top_right4" colspan="2">
		      <input type="submit"  value="导  入" class="button2">
		      </td>
		    </tr>
	   </table>
	</form>
</div>		

<script type="text/javascript">
function toAdd(){
	var listType = $("#listType").val();
	if(listType==1){
		window.location.href="${ctx}/whiteListAdd.do?method=blackWhiteListCteateView&listType="+${listType};
	}else{
		window.location.href="${ctx}/blackWhiteListAdd.do?method=blackWhiteListCteateView&listType="+${listType};
	}
}

function batchAdd(){
	$('#uploadDiv').dialog({
		position:["center","center"],
		width:500,
		height:200,
		modal:true,
		title:'黑名单批量导入',
		overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
	});
	$('#uploadDiv').dialog('open');
	
	//var listType = $("#listType").val();
	//if(listType==1){
		//window.location.href="${ctx}/whiteListAdd.do?method=blackWhiteListBatchCteateView&listType="+${listType};
	//}else{
		//window.location.href="${ctx}/blackWhiteListAdd.do?method=blackWhiteListBatchCteateView&listType="+${listType};
	//}
}

function sub(){
	var file = $("#file").val();
	if(""== file){
		alert("请选择文件！");
		return false;
	}
	return true;
}
</script>
</body>

