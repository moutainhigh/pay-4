<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function websiteAnnouncementSearch(pageNo,totalCount) {
	
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#websiteAnnouncementSearch").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/websiteAnnouncement.do?method=announcementSearchList",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function load(pageNo,totalCount ){
	websiteAnnouncementSearch(pageNo,totalCount);
}
</script>
</head>

<body  onload="load();">

<h2 class="panel_title">WEBSITE公告列表</h2>

<form id="websiteAnnouncementSearch" name="websiteAnnouncementSearch" >
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr class="trForContent1">
		<td  class="border_top_right4" align="right" >主题：</td>
		<td class="border_top_right4"align="left" >
			<input type="text" id="subject"  name="subject" maxlength= "60" value="" style="width: 160px;"/>
		</td>
		<td  class="border_top_right4" align="right" >内容：</td>
		<td class="border_top_right4"align="left" >
			<input type="text" id="message"  name="message" maxlength= "60" value="" style="width: 160px;"/>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center"><a
			class="s03" href="javascript:websiteAnnouncementSearch()">
			<input class="button2" type="button" value="查询"></a>
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

