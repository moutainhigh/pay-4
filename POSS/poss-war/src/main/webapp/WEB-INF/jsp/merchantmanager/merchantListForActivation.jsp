<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

$(document).ready(
		function(){
			merchantQuery();
		}
);

function merchantQuery(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#merchantSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/merchantListForActivation.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

</script>
</head>

<body>

<!-- <table width="35%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">逾 期 未 激 活 商 户 列 表</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table> -->
<h2 class="panel_title">逾期未激活商户列表</h2>
<c:if test="${sign!=null}">
<br><br>
	<center>
		<font color="red">${sign}</font>
	</center>

</c:if>
<!-- <form id="merchantSearchFormBean" name="merchantSearchFormBean" >
	
</form> -->
<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>

</body>

