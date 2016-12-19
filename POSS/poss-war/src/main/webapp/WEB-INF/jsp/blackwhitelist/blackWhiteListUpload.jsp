<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";


function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

function download(){
	window.location.href='${ctx}/blackListUpload.do?method=downLoadFile';
}


</script>
</head>

<body >
 
<%-- <table width="700" border="0" cellspacing="0" cellpadding="0" align="center" height="21" >
	<tr>
		<td height="2" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">批量上传黑白名单</font></div>
		</td>
	</tr>
	<c:if test="${message!=null}">
		<tr>
			<td height="2" ><font color="red" >${message}</font></td>
		</tr>
	</c:if>
</table> --%>
<h2 class="panel_title">批量上传黑白名单</h2>
<c:if test="${message!=null}">
		<p>
			<font color="red" >${message}</font>
		</p>
	</c:if>



<form id="businessTypeForm" name="businessTypeForm" enctype="multipart/form-data" method="post" action="${ctx}/blackListUpload.do?method=upload">
	
	<table class="border_all2" width="700" border="1" cellspacing="0" cellpadding="0" align="center">
		<tr class="trForContent1" >
			<td class="border_top_right4"  align="right">文件上传：<font color="red" >*</font></td>
				<td class="border_top_right4"  align="left">
					<input type="file"  name="file" id="file" accept=".csv"  value="点击上传">
				</td>
		</tr>
		
		<tr class="trForContent1">
			<td class="border_top_right4" colspan="2" align="center">
				<input type = "button"  onclick="download()" value="模版下载">
				<input type = "submit" value="保存">
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
